import java.time.LocalDateTime;

public class Message {
    private String content;
    private String timestamp;
    private String sender;
    private String recipient;

    public Message(String content, String timestamp, String sender, String recipient) {
        this.content = content;
        this.timestamp = timestamp;
        this.sender = sender;
        this.recipient = recipient;
    }

    public String getFormattedMessage() {
        if (recipient != null && !recipient.isEmpty()) {
            return "[" + timestamp + "] " + sender + " → @" + recipient + ": " + content;
        } else {
            return "[" + timestamp + "] " + sender + ": " + content;
        }
    }
}
