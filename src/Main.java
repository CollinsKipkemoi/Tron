public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        if(db.isConnected()) {
            System.out.println("Connected to the database");
            db.showTables();
        }

        
    }
}