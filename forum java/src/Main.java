public class Main {
    public static void main(String[] args) {
        // Initialise la base de données
        Database.initDatabase();
        

        // Lance la fenêtre de chat
        new ChatWindow();
    }
}