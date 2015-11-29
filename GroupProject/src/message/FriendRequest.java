package message;
import database.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alfonce on 11/11/15.
 */

public class FriendRequest implements Message{
    private int senderId;
    private int receiverId;
    private Date sentDate;
    private int Id;
    private DatabaseConnection databaseConnection = new DatabaseConnection();

    /*
    Table: FriendRequests
		+-------------+--------------+------+-----+---------+----------------+
		| Field       | Type         | Null | Key | Default | Extra          |
		+-------------+--------------+------+-----+---------+----------------+
		| ID          | int(11)      | NO   | PRI | NULL    | auto_increment |
		| SenderID    | int(11)      | NO   |     | NULL    |                |
		| ReceiverID  | int(11)      | NO   |     | NULL    |                |
		| SentDate    | datetime     | NO   |     | NULL    |                |
		+-------------+--------------+------+-----+---------+----------------+
	 */
    /**
     * Private constructor for creating FriendRequest objects.
     *
     * @param senderId
     *          The account ID of the sender.
     * @param receiverId
     *          The account ID of the receiver.
     * @param sentDate
     *          The date the message was sent on.
     */
    private FriendRequest(int senderId, int receiverId, Date sentDate, int Id) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.sentDate = sentDate;
        this.Id = Id;
    }

    /**
     * Gets list all friend requests for a given account
     * @param account
     * @return list of
     * @throws SQLException
     */
    public ArrayList<FriendRequest> getAllFriendRequests(Account account)
            throws SQLException {
        ArrayList<FriendRequest> requests = new ArrayList<FriendRequest>();
        Statement s = databaseConnection.getStatement();
        ResultSet rs = s.executeQuery(String.format(
                "SELECT * FROM FriendRequests WHERE ReceiverID = %s", account.getId()));

        while (rs.next()) {
            requests.add(new FriendRequest(rs.getInt("SenderID"), rs
                    .getInt("ReceiverID"), databaseConnection.convertSQLDateToDate(rs
                    .getString("SentDate")), rs.getInt("ID")));
        }

        return requests;
    }

    /**
     * Send a new friend request
     * @param sender sender of request
     * @param receiver recipient
     * @return FriendRequest object
     * @throws SQLException
     */
    public FriendRequest sendNewFriendRequest(Account sender,
                                                      Account receiver) throws SQLException {
        if (sender.equals(receiver))
            return null;

        int senderId = sender.getId();
        int receiverId = receiver.getId();
        FriendRequest request;
        Statement statement = databaseConnection.getStatement();

        // See if the request already exists
        ResultSet rs = statement
                .executeQuery(String
                        .format(
                                "SELECT * FROM FriendRequests WHERE SenderID = %s AND ReceiverID = %s",
                                senderId, receiverId));
        // return already existing message
        if (rs.next())
            return new FriendRequest(senderId, receiverId,
                    databaseConnection.convertSQLDateToDate(rs.getString("SentDate")), rs.getInt("ID"));

        // See if inverse request exists
        rs = statement
                .executeQuery(String
                        .format(
                                "SELECT * FROM FriendRequests WHERE SenderID = %s AND ReceiverID = %s",
                                receiverId, senderId));
        if (rs.next()) {
            // this message represents the already existing message
            request = new FriendRequest(receiverId, senderId,
                    databaseConnection.convertSQLDateToDate(rs.getString("SentDate")), rs.getInt("ID"));
            request.accept();
            return request; // return already existing message
        }

        // Otherwise, no entry exists, so we create one in the DB and return the
        // message
        Date currentDate = new Date();
        statement.executeUpdate(
                String
                        .format(
                                "INSERT INTO FriendRequests (SenderID, ReceiverID, SentDate) VALUES (%s, %s, %s)",
                                senderId, receiverId, currentDate),
                Statement.RETURN_GENERATED_KEYS);
        ResultSet rs2 = statement.getGeneratedKeys();
        rs2.next();
        int Id = rs2.getInt(1);
        return new FriendRequest(senderId, receiverId, currentDate, Id);
    }

    public Account getSender() throws SQLException {
        return Account.getAccountById(senderId);
    }

    public Account getReceiver() throws SQLException {
        return Account.getAccountById(receiverId);
    }

    public Date getSentDate() {
        return sentDate;
    }

    public int getId() {
        return Id;
    }

    /**
     * Get friend request by ID
     * @param Id
     * @return
     * @throws SQLException
     */
    public FriendRequest getFriendRequestByID(int Id) throws SQLException {
        FriendRequest request = null;
        Statement s = databaseConnection.getStatement();
        ResultSet rs = s
                .executeQuery("SELECT * FROM FriendRequests WHERE ID=" + Id);
        if (rs.next()) {
            request = new FriendRequest(rs.getInt("SenderID"), rs.getInt("ReceiverID"),
                    databaseConnection.convertSQLDateToDate(rs.getString("SentDate")), Id);
        }
        return request;
    }

    /**
     * Rejects the current friend request, removing the request.
     * @throws SQLException
     *           If SQL operations fail.
     */
    public void declineFriendRequest() throws SQLException {
        // Modify SQL tables to remove this request from the FriendRequest table.
        Statement statement = databaseConnection.getStatement();
        statement.executeUpdate(String
                .format(
                        "DELETE FROM FriendRequests WHERE SenderID = '%s' AND ReceiverID = '%s'",
                        senderId, receiverId));
    }
    /**
     * Accepts the friend request, updates tables
     * @throws SQLException
     */
    public void accept() throws SQLException {

        // Remove request from FriendRequests table
        Statement statement = databaseConnection.getStatement();
        statement.executeUpdate(String
                .format(
                        "DELETE FROM FriendRequests WHERE SenderID = '%s' AND ReceiverID = '%s'",
                        senderId, receiverId));
        // Add dual friendship entries to Friends table
        statement.executeUpdate(String.format(
                "INSERT INTO Friends (UserID, FriendID) VALUES ('%s', '%s')", senderId,
                receiverId));
        statement.executeUpdate(String.format(
                "INSERT INTO Friends (UserID, FriendID) VALUES ('%s', '%s')",
                receiverId, senderId));
    }

}
