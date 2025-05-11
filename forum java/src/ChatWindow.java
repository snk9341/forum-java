import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatWindow extends JFrame {
    private JComboBox<User> userBox;
    private JComboBox<Forum> forumBox;
    private JTextArea messageArea;
    private JTextField inputField;
    private JTextField recipientField;

    public ChatWindow() {
        setTitle("Chat Forum");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel haut : sélection utilisateur et forum
        JPanel topPanel = new JPanel();
        userBox = new JComboBox<>();
        forumBox = new JComboBox<>();
        loadUsers();
        loadForums();
        topPanel.add(new JLabel("Utilisateur:"));
        topPanel.add(userBox);
        topPanel.add(new JLabel("Forum:"));
        topPanel.add(forumBox);
        add(topPanel, BorderLayout.NORTH);

        // Zone des messages
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        add(scrollPane, BorderLayout.CENTER);

        // Panel bas : champ message + destinataire
        JPanel bottomPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        recipientField = new JTextField();
        JButton sendButton = new JButton("Envoyer");
        sendButton.addActionListener(e -> sendMessage());

        bottomPanel.add(new JLabel("Message:"), BorderLayout.WEST);
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(recipientField, BorderLayout.EAST);
        bottomPanel.add(sendButton, BorderLayout.SOUTH);

        recipientField.setPreferredSize(new Dimension(100, 25));
        recipientField.setToolTipText("Destinataire (optionnel)");

        add(bottomPanel, BorderLayout.SOUTH);

        refreshMessages();
        setVisible(true);
    }

    private void loadUsers() {
        try (Connection conn = Database.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM users");
            while (rs.next()) {
                userBox.addItem(new User(rs.getInt("id"), rs.getString("username")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadForums() {
        try (Connection conn = Database.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM forums");
            while (rs.next()) {
                forumBox.addItem(new Forum(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refreshMessages() {
        messageArea.setText("");
        Forum selectedForum = (Forum) forumBox.getSelectedItem();
        if (selectedForum == null) return;

        try (Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT messages.*, users.username FROM messages JOIN users ON messages.user_id = users.id WHERE forum_id = ? ORDER BY timestamp"
            );
            stmt.setInt(1, selectedForum.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Message msg = new Message(
                    rs.getString("content"),
                    rs.getString("timestamp"),
                    rs.getString("username"),
                    rs.getString("recipient")
                );
                messageArea.append(msg.getFormattedMessage() + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        User user = (User) userBox.getSelectedItem();
        Forum forum = (Forum) forumBox.getSelectedItem();
        String content = inputField.getText();
        String recipient = recipientField.getText();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        if (user == null || forum == null || content.isEmpty()) return;

        try (Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO messages(user_id, forum_id, content, timestamp, recipient) VALUES (?, ?, ?, ?, ?)"
            );
            stmt.setInt(1, user.getId());
            stmt.setInt(2, forum.getId());
            stmt.setString(3, content);
            stmt.setString(4, timestamp);
            stmt.setString(5, recipient.isEmpty() ? null : recipient);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        inputField.setText("");
        recipientField.setText("");
        refreshMessages();
    }
}
