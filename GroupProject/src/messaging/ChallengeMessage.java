package messaging;
import java.sql.Timestamp;;
/**
 * Created by alfonce on 11/11/15.
 */
public class ChallengeMessage extends Message {
	private String quizname;
    /*
    Table: ChallengeMessage
		+-------------+--------------+------+-----+---------+----------------+
		| Field       | Type         | Null | Key | Default | Extra          |
		+-------------+--------------+------+-----+---------+----------------+
		| ID          | int(11)      | NO   | PRI | NULL    | auto_increment |
		| Sender      | varchar(256) | NO   | MUL | NULL    |                |
		| Receiver    | varchar(256) | NO   | MUL | NULL    |                |
		| Subjec      | varchar(256) |      |     | NULL    |                |
		| TimeSent    | varchar(256) | NO   |     | NULL    |                |
		| QuizName    | datetime     | NO   |     | NULL    |                |
		| MessageBody | varchar(256) |      |     | NULL    |                |
		| IsRead      | BOOL         | NO   |     | NULL    |                |
		+-------------+--------------+------+-----+---------+----------------+
	 */
    /**
     * Constructs a single challenge message
     * @param senderId id of the sender
     * @param receiverId id of the receiver
     * @param sentDate date sent
     * @param quizId id of the quiz (foreign key in Challenges which references Id field
     *               in Accounts table
     */
    public ChallengeMessage(String sender , String receiver, String subject, Timestamp date, String body,String quizname) {
       super(sender, receiver, subject, date, body);
       this.quizname = quizname;
       this.setMessageType("challenge");
    }
    /**
     * Returns name of the quiz associated with a challenge
     * @return name of quiz
     */
    public String getQuizName() {
    	return this.quizname;
    }
    public void setQuizName(String name) {
    	this.setQuizName(name);
    }

}