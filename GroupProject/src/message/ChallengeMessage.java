package message;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import databaseConnection.DatabaseConnection;

/**
 * Created by alfonce on 11/11/15.
 */
public class ChallengeMessage implements Message {
    private int senderId;
    private int receiverId;
    private Date sentDate;
    private int quizId;
    private int Id;
    DatabaseConnection databaseConnection = new DatabaseConnection();

    /*
    Table: Challenges
		+-------------+--------------+------+-----+---------+----------------+
		| Field       | Type         | Null | Key | Default | Extra          |
		+-------------+--------------+------+-----+---------+----------------+
		| ID          | int(11)      | NO   | PRI | NULL    | auto_increment |
		| SenderID    | int(11)      | NO   | MUL | NULL    |                |
		| ReceiverID  | int(11)      | NO   | MUL | NULL    |                |
		| QuizID      | int(11)      | NO   |     | NULL    |                |
		| SentDate    | datetime     | NO   |     | NULL    |                |
		+-------------+--------------+------+-----+---------+----------------+
	 */

    /**
     * Constructs a ChallengeMessage object
     * @param senderId id of the sender
     * @param receiverId id of the receiver
     * @param sentDate date sent
     * @param quizId id of the quiz (foreign key in Challenges which references Id field
     *               in Accounts table
     * @param Id id of message
     */
    private ChallengeMessage(int senderId, int receiverId, Date sentDate,
                             int quizId, int Id) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.sentDate = sentDate;
        this.quizId = quizId;
        this.Id = Id;
    }

    /**
     * Gets challenge message by Id 
     * @param Id
     * @return
     * @throws SQLException
     */
    public ChallengeMessage getChallengeById(int Id) throws SQLException {
        ChallengeMessage message = null;
        Statement s = databaseConnection.getStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Challenges WHERE ID="
                + Id);
        if (rs.next()) {
            message = new ChallengeMessage(rs.getInt("SenderID"), rs.getInt("ReceiverID"),
                    databaseConnection.convertSQLDateToDate(rs.getString("SentDate")),
                    rs.getInt("QuizID"), Id);
        }

        return message;
    }

    /**
     * Get all pending challenges for a given account
     * @param account account whose messages are to be retrieved
     * @return list of all
     * @throws SQLException
     */
    public  ArrayList<ChallengeMessage> getAccPendingChallenges(Account account)
            throws SQLException {
        ArrayList<ChallengeMessage> messages = new ArrayList<ChallengeMessage>();
        Statement s = databaseConnection.getStatement();
        ResultSet rs = s.executeQuery(String.format(
                "SELECT * FROM Challenges WHERE ReceiverID = %s", account.getId()));
        while (rs.next()) {
            messages.add(new ChallengeMessage(rs.getInt("SenderID"), rs
                    .getInt("ReceiverID"), databaseConnection.convertSQLDateToDate(rs
                    .getString("SentDate")), rs.getInt("QuizID"), rs.getInt("ID")));
        }
        return messages;
    }

    /**
     * Issue new challenge message, update the Challenges table
     * @param sender sender of message
     * @param receiver receiver of message
     * @param q quiz to be sent
     * @return ChallengeMessage object
     * @throws SQLException
     */
    public ChallengeMessage sendNewChallenge(Account sender,
                                                            Account receiver, Quiz q) throws SQLException {
        if (sender.equals(receiver))
            return null;
        Statement statement = databaseConnection.getStatement();
        int senderId = sender.getId();
        int receiverId = receiver.getId();
        int quizId = q.getId();
        Date currentDate = new Date();

        // Check if challenge already exists in table
        ResultSet rs = statement.executeQuery(String.format(
                "SELECT * FROM Challenges WHERE SenderID = %s "
                        + "AND ReceiverID = %s AND QuizID = %s", sender.getId(),
                receiver.getId(), q.getId()));
        // Entry already exists
        if (rs.next()) {
            return new ChallengeMessage(senderId, receiverId, currentDate, quizId,
                    rs.getInt("ID"));

        } else { // Add new
            int Id;
            statement.executeUpdate(
                    String.format(
                            "INSERT INTO Challenges (SenderID, ReceiverID, SentDate, QuizID) "
                                    + "VALUES (%s, %s, %s, %s)", senderId, receiverId,currentDate, quizId),
                    Statement.RETURN_GENERATED_KEYS);
            ResultSet rs2 = statement.getGeneratedKeys();
            rs2.next();
            Id = rs2.getInt(1);
            return new ChallengeMessage(senderId, receiverId, currentDate, quizId, Id);
        }
    }

    public Account getReceiver() throws SQLException {
        return Account.getAccountById(receiverId);
    }

    /**
     * Get date when challenge was sent
     * @return Date when challenge was sent
     */
    public Date getSentDate() {
        return sentDate;
    }

    public Account getSender() throws SQLException {
        return Account.getAccountById(senderId);
    }

    /**
     * Gets the quiz contained in this request.
     * @return The quiz contained in this challenge.
     * @throws SQLException
     */
    public Quiz getQuiz() throws SQLException {
        return Quiz.getQuizById(quizId);
    }

    /**
     * Accepts the challenge
     * @return
     * @throws SQLException
     */
    public Quiz acceptChallenge() throws SQLException {
        removeChallenge();
        return getQuiz();
    }

    /**
     * Declines challenge (remove from table)
     * @throws SQLException
     */
    public void declineChallenge() throws SQLException {
        removeChallenge();
    }

    /**
     * Removes accepted/declined challenge from table
     * @throws SQLException
     */
    private void removeChallenge() throws SQLException {
        Statement s = databaseConnection.getStatement();
        s.executeUpdate(String.format(
                "DELETE FROM Challenges WHERE SenderID = %s AND ReceiverID = %s AND QuizID = %s",
                senderId, receiverId, quizId));
    }
}
