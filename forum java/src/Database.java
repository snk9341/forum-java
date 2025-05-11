import java.sql.*;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:chat_forum.db";

    // Création des tables si elles n'existent pas
    public static void initDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            Statement stmt = conn.createStatement();

            // Table des utilisateurs
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT NOT NULL UNIQUE)");

            // Table des forums
            stmt.execute("CREATE TABLE IF NOT EXISTS forums (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL UNIQUE)");

            // Table des messages
            stmt.execute("CREATE TABLE IF NOT EXISTS messages (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER," +
                    "forum_id INTEGER," +
                    "content TEXT," +
                    "timestamp TEXT," +
                    "recipient TEXT," +
                    "FOREIGN KEY(user_id) REFERENCES users(id)," +
                    "FOREIGN KEY(forum_id) REFERENCES forums(id))");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:chat_forum.db");
    }
}
