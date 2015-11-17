package messaging;

/**
 * Created by alfonce on 11/11/15.
 * Represents received messages.
 */
import java.sql.SQLException;
import java.util.Date;

public interface Message {

    /**
     * Gets date message was sent
     * @return The date the message was sent.
     */
    public Date getSentDate();

    /**
     * Gets account of receiver
     * @return The Account of the receiver.
     * @throws SQLException .
     */

    public Account getReceiver() throws SQLException;
    /**
     * Gets account of sender
     * @return The Account of the sender.
     * @throws SQLException .
     */
    public Account getSender() throws SQLException;
}
