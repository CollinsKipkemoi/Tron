public class Main {
    public static void main(String[] args) {
        // Test database connection
        Database db = new Database();
        if(db.isConnected()) {
            System.out.println("Connected to the database");
            db.showTables();
            
            // Start the game
            TronGame game = new TronGame();
            game.setVisible(true);
        } else {
            System.out.println("Failed to connect to the database");
        }
    }
}