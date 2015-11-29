package message;

import account.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.DatabaseConnection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alfonce on 11/11/15.
 * Represents a note message
 */

public class NoteMessage implements Message {
    private int Id;
    private int senderId;
    private int receiverId;
    private Date sentDate;
    private String contents;
    private String subject;
    private boolean seen;
    DatabaseConnection databaseConnection = new DatabaseConnection();


    /*
    Table: Notes
		+-------------+--------------+------+-----+---------+----------------+
		| Field       | Type         | Null | Key | Default | Extra          |
		+-------------+--------------+------+-----+---------+----------------+
		| id          | int(11)      | NO   | PRI | NULL    | auto_increment |
		| SenderID    | int(11)      | NO   | MUL | NULL    |                |
		| ReceiverID  | int(11)      | NO   | MUL | NULL    |                |
		| Contents    | longtext     | NO   |     | NULL    |                |
		| seen        | Boolean      | NO   |     | false   |                |
		| SentDate    | datetime     | NO   |     | NULL    |                |
		+-------------+--------------+------+-----+---------+----------------+
	 */
    /**
     * Creates a new NoteMessage object
     * @param Id id of the note
     * @param senderId Id of the sender
     * @param receiverId id of the receiver
     * @param sentDate date when note was sent
     * @param contents contents of the note
     * @param subject subject of the note
     * @param seen whether note has been read or not
     */
    private NoteMessage(int Id, int senderId, int receiverId, Date sentDate,
                        String contents, String subject, boolean seen) {
        this.Id = Id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.sentDate = sentDate;
        this.contents = contents;
        this.seen = seen;
        this.subject = subject;
    }

    /**
     * Gets all note messages for a given account
     * @param account account whose messages are to be obtained
     * @return list of all messages
     * @throws SQLException if errors
     */
    public ArrayList<NoteMessage> getNotes(User account)
            throws SQLException {
        ArrayList<NoteMessage> messages = new ArrayList<NoteMessage>();
        Statement statement = databaseConnection.getStatement();
        ResultSet rs = statement.executeQuery(String.format(
                "SELECT * FROM Notes WHERE ReceiverID = %s", account.getId()));
        while (rs.next()) {
            messages.add(new NoteMessage(rs.getInt("ID"), rs.getInt("SenderID"), rs
                    .getInt("ReceiverID"), databaseConnection.convertSQLDateToDate(rs
                    .getString("SentDate")), rs.getString("Contents"), rs
                    .getString("Subject"), rs.getBoolean("Seen")));
        }
        return messages;
    }


    /**
     * Get notes messages (sorted by sent date)
     * @param account account whose messages to be obtained (recipient)
     * @return list of messages
     * @throws SQLException
     */
    public ArrayList<NoteMessage> getSortedNotes(User account)
            throws SQLException {
        ArrayList<NoteMessage> messages = new ArrayList<NoteMessage>();
        ArrayList<NoteMessage> unseenMessages = new ArrayList<NoteMessage>();
        ArrayList<NoteMessage> seenMessages = new ArrayList<NoteMessage>();

        Statement s = databaseConnection.getStatement();
        ResultSet rs = s.executeQuery(String.format(
                "SELECT * FROM Notes WHERE ReceiverID = %s AND Seen = 1 "
                        + "ORDER BY SentDate DESC", account.getId()));

        while (rs.next()) {
            seenMessages.add(new NoteMessage(rs.getInt("ID"), rs.getInt("SenderID"),
                    rs.getInt("ReceiverID"), databaseConnection.convertSQLDateToDate(rs
                    .getString("SentDate")), rs.getString("Contents"), rs
                    .getString("Subject"), true));
        }
        rs = s.executeQuery(String.format(
                "SELECT * FROM Notes WHERE ReceiverID = %s AND Seen = 0 "
                        + "ORDER BY SentDate DESC", account.getId()));

        while (rs.next()) {
            unseenMessages.add(new NoteMessage(rs.getInt("ID"),
                    rs.getInt("SenderID"), rs.getInt("ReceiverID"), databaseConnection
                    .convertSQLDateToDate(rs.getString("SentDate")), rs
                    .getString("Contents"), rs.getString("Subject"), false));
        }
        messages.addAll(unseenMessages);
        messages.addAll(seenMessages);
        return messages;
    }

    /**
     * Creates a new note, and updates Notes Table on database
     * @param sender sender  of note
     * @param receiver receiver of note
     * @param contents contents of note
     * @param subject subject of note
     * @return
     * @throws SQLException
     */
    public NoteMessage sendNewNote(User sender, User receiver,
                                           String contents, String subject) throws SQLException {

        if (sender.equals(receiver))
            return null;

        int senderId = sender.getId();
        int receiverId = receiver.getId();
        Date currentDate = new Date();

        Statement statement = databaseConnection.getStatement();
        statement.executeUpdate(
                String.format(
                        "INSERT INTO Notes (SenderID, ReceiverID, SentDate, Contents, Subject)"
                                + " VALUES (%s, %s, %s, '%s', '%s')", senderId, receiverId,currentDate, contents, subject),
                Statement.RETURN_GENERATED_KEYS);
        ResultSet keys = statement.getGeneratedKeys();
        keys.next();
        return new NoteMessage(keys.getInt(1), senderId, receiverId, currentDate,
                contents, subject, false);
    }

    public NoteMessage getById(int Id) throws SQLException {

        NoteMessage message = null;
        Statement statement = databaseConnection.getStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Notes WHERE ID = " + Id);
        if (rs.next()) {
            message = new NoteMessage(Id, rs.getInt("SenderId"), rs.getInt("ReceiverID"),
                    databaseConnection.convertSQLDateToDate(rs.getString("SentDate")),
                    rs.getString("Contents"), rs.getString("Subject"),
                    rs.getBoolean("Seen"));
        }
        return message;
    }
    public Date getSentDate() {
        return sentDate;
    }

    public boolean isSeen() {
        return seen;
    }

    public String getContents() {
        return contents;
    }

    public String getSubject() {
        return subject;
    }

    public User getSender() throws SQLException {
        return User.getAccountById(senderId);
    }

    public User getReceiver() throws SQLException {
        return User.getAccountById(receiverId);
    }

    public int getId() {
        return Id;
    }

//    /**
//     * Views the current note.
//     *
//     * @throws SQLException
//     *           If SQL operations fail.
//     */
//    public void view() throws SQLException {
//        // Modify SQL tables to set the note to be viewed. To find the note,
//        // search by note ID.
//        Statement s = databaseConnection.getStatement();
//        s.executeUpdate(String.format("UPDATE Notes SET Seen = %s WHERE ID = %s",
//                "TRUE", Id));
//    }

}
