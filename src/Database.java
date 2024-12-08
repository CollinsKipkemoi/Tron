import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    Connection connection;
    String url = "jdbc:mysql://localhost:3306/Tron";
    Properties connectionProps = new Properties();

    public Database() {
        connectionProps.put("user", "root");
        connectionProps.put("password", "Java$cript1080");
        connectionProps.put("serverTimezone", "UTC");

        try{
            connection = DriverManager.getConnection(url, connectionProps);
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            System.out.println("An error occurred while connecting to the database");
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        if(connection != null) {
            try {
                return connection.isValid(5);
            } catch (Exception e) {
                System.err.println("Error testing connection: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    public void showTables() {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            
            System.out.println("\nTables in the database:");
            System.out.println("----------------------");
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println(tableName);
            }
            System.out.println("----------------------");
        } catch (SQLException e) {
            System.out.println("Error getting tables: " + e.getMessage());
        }
    }


}
