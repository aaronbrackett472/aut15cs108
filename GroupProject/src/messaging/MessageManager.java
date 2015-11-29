package messaging;

import java.sql.Date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

import com.mysql.jdbc.Connection;

import database.DatabaseConnection;

//takes care of messages etc
public class MessageManager {
	private DatabaseConnection con = new DatabaseConnection();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public MessageManager() {}

	/**
	 * Adds a msg to one of the four tables: ChallengeMessage, NoteMessage, NoteDraft, FriendRequest
	 * NoteDraft table stores note messages that are saved for later editing
	 * ChallengeMessage stores all challenge messages
	 * NoteMessage stores note messages that have been sent to other users
	 * FriendRequest stores friendship requests
	 * @param msg message to be stored
	 * @param status specifies whether to store as draft or regular note
	 */
	@SuppressWarnings("resource")
	public void addMessage(Message msg) {
		String tableName = "";
		if(msg.getMessageType().equals("note")) tableName = "NoteMessage";
		if(msg.getMessageType().equals("friendrequest")) tableName = "FriendRequest";
		if (msg.getMessageType().equals("draft"))tableName = "NoteDraft";
		if(msg.getMessageType().equals("challenge")){	
			updateChallengeTable((ChallengeMessage)msg);
			return;
		}
		String sql = "INSERT INTO " + tableName + " (Sender, Receiver, Subject, TimeSent, MessageBody, IsRead) VALUES (?,?,?,?,?,?)" ;
		Connection connection = null;
		PreparedStatement st = null;
        int isRead = 0;
		if(msg.isRead()) isRead = 1;
		
		try {
			connection = (Connection) con.getConnection();
			st = connection.prepareStatement("USE c_cs108_mateog");
			st.executeQuery();	
			st = connection.prepareStatement(sql);
			st.setString(1, msg.getSenderName());
			st.setString(2, msg.getReceiverName());
			st.setString(3, msg.getSubject());
			st.setDate(4,(Date) msg.getDateSent());
			st.setString(5, msg.getMessageBody());
			st.setInt(6, isRead);	
			st.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	@SuppressWarnings("resource")
	private void updateChallengeTable( ChallengeMessage msg) {	
		String sql = "INSERT INTO ChallengeMessage (Sender, Receiver, Subject, TimeSent, QuizName, MessageBody, "
				+ "IsRead) VALUES (?,?,?,?,?,?,?)" ;		
		Connection connection = null;
		PreparedStatement st = null;
        int isRead = 0;
		if(msg.isRead()) isRead = 1;
		try {
			connection = (Connection) con.getConnection();
			st = connection.prepareStatement("USE c_cs108_mateog");
			st.executeQuery();	
			st = connection.prepareStatement(sql);
			st.setString(1, msg.getSenderName());
			st.setString(2, msg.getReceiverName());
			st.setString(3, msg.getSubject());
			st.setDate(4,(Date) msg.getDateSent());
			st.setString(5,msg.getQuizName());
			st.setString(6, msg.getMessageBody());
			st.setInt(7, isRead);	
			st.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		}
		
	}

	/**
	 * Deletes a message (friendship request, note, challenge or draft) from the
	 * relevant table
	 * @param msg
	 * @param status specifies whether to store as draft or regular note
	 */
	public void deleteMessage(Message msg) {
		Connection connection = null;
		PreparedStatement st = null;
		String tableName = "";
		
		if(msg.getMessageType().equals("note")) tableName = "NoteMessage";
		if(msg.getMessageType().equals("friendrequest")) tableName = "FriendRequest";
		if(msg.getMessageType().equals("challenge")) tableName = "ChallengeMessage";
		if (msg.getMessageType().equals("draft")) tableName = "NoteDraft";	
		String sql = "DELETE FROM " + tableName + " WHERE ID =?";
		try {
			connection = (Connection)con.getConnection();
			st = connection.prepareStatement("USE c_cs108_mateog");
			st.executeQuery();
			st = connection.prepareStatement(sql);
			st.setInt(1,getId(msg));
			st.executeUpdate();
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Returns a unique Id of the message as identified by the field ID in the relevant table
	 * @param msg message whose ID is to be returned
	 * @return
	 */
	public int getId(Message msg) {
		Connection connection = null;
		PreparedStatement st = null;
		ResultSet result = null;	
		String tableName = "";
		int id = 0;
		if(msg.getMessageType().equals("draft")) tableName = "NoteDraft";
		if(msg.getMessageType().equals("note")) tableName = "NoteMessage";
		if(msg.getMessageType().equals("friendrequest")) tableName = "FriendRequest";
		if(msg.getMessageType().equals("challenge")) tableName = "ChallengeMessage";
		String sql = "SELECT * FROM " + tableName + " WHERE "
				+ "Sender ='"+ msg.getSenderName() + "' AND TimeSent ='"+ msg.getDateSent() + "'" ;
		try {
			connection = (Connection)con.getConnection();
			st = connection.prepareStatement("USE c_cs108_mateog");
			st.executeQuery();
			st = connection.prepareStatement(sql);
			result = st.executeQuery();
			
			while(result.next()) {
				id = result.getInt("ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * Marks a message as unread. Updates the IsRead field on the relevant table
	 * @param msg message to be marked unread
	 */
	public void markUnread(Message msg) {
		Connection connection = null;
		PreparedStatement st = null;
		String tableName = "";
		
		if(msg.getMessageType().equals("note")) tableName = "NoteMessage";
		if(msg.getMessageType().equals("friendrequest")) tableName = "FriendRequest";
		if(msg.getMessageType().equals("challenge")) tableName = "ChallengeMessage";
		if (msg.getMessageType().equals("draft")) tableName = "NoteDraft";	
		
		String sql = "UPDATE " + tableName + " SET IsRead = 0 WHERE ID =?";
		try {
			connection = (Connection)con.getConnection();
			st = connection.prepareStatement("USE c_cs108_mateog");
			st.executeQuery();
			st = connection.prepareStatement(sql);
			st.setInt(1,getId(msg));
			st.executeUpdate();
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Marks a message as read
	 * @param msg message to be marked read
	 */
	public void markRead(Message msg) {
		Connection connection = null;
		PreparedStatement st = null;
		String tableName = "";
		
		if(msg.getMessageType().equals("note")) tableName = "NoteMessage";
		if(msg.getMessageType().equals("friendrequest")) tableName = "FriendRequest";
		if(msg.getMessageType().equals("challenge")) tableName = "ChallengeMessage";
		if (msg.getMessageType().equals("draft")) tableName = "NoteDraft";	
		
		String sql = "UPDATE " + tableName + " SET IsRead = 1 WHERE ID =?";
		try {
			connection = (Connection)con.getConnection();
			st = connection.prepareStatement("USE c_cs108_mateog");
			st.executeQuery();
			st = connection.prepareStatement(sql);
			st.setInt(1,getId(msg));
			st.executeUpdate();
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Lists all unread note messages for a particular user 
	 * @param user user whose messages are to be obtained
	 * @return list of all unread notes messages
	 */
	public List<NoteMessage>getNoteMessages(User user) {
		List<NoteMessage> list = new ArrayList<NoteMessage>();
		Connection connection = null;
		ResultSet result = null;
		PreparedStatement st = null;
		String sql =  "SELECT * FROM  NoteMessage WHERE Sender ='"+user.getUserName()+"'";	
		try {
			connection = (Connection)con.getConnection();
			st = connection.prepareStatement("USE c_cs108_mateog");
			st.executeQuery();
			st = connection.prepareStatement(sql);
			result = st.executeQuery();
			while (result.next()) {
				list.add(new NoteMessage(result.getString("Sender"), 
						result.getString("Receiver"),
						result.getString("Subject"), 
						result.getDate("TimeSent"), 
						result.getString("MessageBody")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	/**
	 * Returns a list of all unviewed friendship requests where "user" is the requestee
	 * @param user requestee
	 * @return list of unviewed requests
	 */
	
	public List<FriendRequest>getFriendRequests(User user) {
		List<FriendRequest> list = new ArrayList<FriendRequest>();
		Connection connection = null;
		ResultSet result = null;
		PreparedStatement st = null;
		String sql =  "SELECT * FROM  FriendRequest WHERE Sender ='"+user.getUserName()+"'";
		
		try {
			connection = (Connection)con.getConnection();
			st = connection.prepareStatement("USE c_cs108_mateog");
			st.executeQuery();
			st = connection.prepareStatement(sql);
			result = st.executeQuery();
			while (result.next()) {
				list.add(new FriendRequest(result.getString("Sender"), 
						result.getString("Receiver"),
						result.getString("Subject"), 
						result.getDate("TimeSent"), 
						result.getString("MessageBody")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	/**
	 * Returns a list of all draft messages
	 * @param user user 
	 * @return list of drafts
	 */
	public List<NoteMessage>getDrafts(User user) {
		
		List<NoteMessage> list = new ArrayList<NoteMessage>();
		Connection connection = null;
		ResultSet result = null;
		PreparedStatement st = null;
		String sql =  "SELECT * FROM  NoteDraft WHERE Sender ='"+user.getUserName()+"'";	
		try {
			connection = (Connection)con.getConnection();
			st = connection.prepareStatement("USE c_cs108_mateog");
			st.executeQuery();
			st = connection.prepareStatement(sql);
			result = st.executeQuery();
			while (result.next()) {
				list.add(new NoteMessage(result.getString("Sender"), 
						result.getString("Receiver"),
						result.getString("Subject"), 
						result.getDate("TimeSent"), 
						result.getString("MessageBody")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * Returns a list of all challenges for a user
	 * @param user user
	 * @return list of challenges
	 */
	public List<ChallengeMessage>getChallenges(User user) {
		List<ChallengeMessage> list = new ArrayList<ChallengeMessage>();
		Connection connection = null;
		ResultSet result = null;
		PreparedStatement st = null;
		String sql =  "SELECT * FROM  ChallengeMessage WHERE Sender ='"+user.getUserName()+"'";
		try {
			connection = (Connection)con.getConnection();
			st = connection.prepareStatement("USE c_cs108_mateog");
			st.executeQuery();
			st = connection.prepareStatement(sql);
			result = st.executeQuery();
			while (result.next()) {
				list.add(new ChallengeMessage(result.getString("Sender"), 
						result.getString("Receiver"),
						result.getString("Subject"), 
						result.getDate("TimeSent"), 
						result.getString("MessageBody"),
						result.getString("QuizName")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
