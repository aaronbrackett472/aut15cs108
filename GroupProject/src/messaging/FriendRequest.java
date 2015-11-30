package messaging;
import java.sql.Timestamp;


/**
 * Created by alfonce on 11/11/15.
 */

public class FriendRequest extends Message{
	/*
    Table: FriendRequest
		+-------------+--------------+------+-----+---------+----------------+
		| Field       | Type         | Null | Key | Default | Extra          |
		+-------------+--------------+------+-----+---------+----------------+
		| ID          | int(11)      | NO   | PRI | NULL    | auto_increment |
		| Sender      | varchar(256) | NO   | MUL | NULL    |                |
		| Receiver    | varchar(256) | NO   | MUL | NULL    |                |
		| Subjec      | varchar(256) |      |     | NULL    |                |
		| TimeSent    | varchar(256) |      |     | NULL    |                |
		| MessageBody | varchar(256) |      |     | NULL    |                |
		| IsRead      | BOOL         | NO   |     | NULL    |                |
		+-------------+--------------+------+-----+---------+----------------+
	*/
	/**
	 * Constructs a single friend request
	 * @param sender name of sender
	 * @param receiver name of sendee
	 * @param subject subject - subject 
	 * @param sentDate date when request was made
	 * @param body optional text
	 */
    public FriendRequest(String sender, String receiver, String subject, Timestamp sentDate, String body) {
    	super(sender, receiver, subject, sentDate, body);
    	this.setMessageType("friendrequest");
    }
}