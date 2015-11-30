package messaging;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by alfonce on 11/11/15.
 * Represents a note message
 */

public class NoteMessage extends Message {
	/*
    Table: NoteMessage / NoteDrafts
		+-------------+--------------+------+-----+---------+----------------+
		| Field       | Type         | Null | Key | Default | Extra          |
		+-------------+--------------+------+-----+---------+----------------+
		| ID          | int(11)      | NO   | PRI | NULL    | auto_increment |
		| Sender      | varchar(256) | NO   | MUL | NULL    |                |
		| Receiver    | varchar(256) | NO   | MUL | NULL    |                |
		| Subjec      | varchar(256) |      |     | NULL    |                |
		| TimeSent    | varchar(256) | NO   |     | NULL    |                |
		| MessageBody | varchar(256) |      |     | NULL    |                |
		| IsRead      | BOOL         | NO   |     | NULL    |                |
		+-------------+--------------+------+-----+---------+----------------+
	 */
    /**
     * Creates an instance of a new Note message
     * @param sender - name of sender
     * @param receiver - name of receiver
     * @param subject - subject of message
     * @param stamp - date message sent
     * @param body - body/contents of message
     */
    public NoteMessage(String sender, String receiver, String subject, Timestamp stamp,  String body) {
        super(sender, receiver,  subject, stamp, body);
        this.setMessageType("note");        
    }
}
