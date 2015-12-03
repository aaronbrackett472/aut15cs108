package messaging;
import account.*;
import java.sql.Date;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.mysql.jdbc.Connection;


import database.DatabaseConnection;

/**
 * 
 * @author alfonce
 *
 */
public class MessageManager {
	private DatabaseConnection con; 
	private Connection connection;
	public MessageManager( ) {
		con = new DatabaseConnection();	
		connection = (Connection) con.getConnection();

	}

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
		String sql = "INSERT INTO messages (MsgType, Sender, Receiver, Subject, TimeSent, MessageBody, IsRead, QuizId) VALUES (?,?,?,?,?,?,?,?)" ;
		PreparedStatement st = null;
        int isRead = 0;
		if(msg.isRead()) isRead = 1;
		try {
			st = connection.prepareStatement("USE c_cs108_mateog");
			st.executeQuery();	
			st = connection.prepareStatement(sql);
			st.setString(1, msg.getMessageType());
			st.setString(2, msg.getSenderName());
			st.setString(3, msg.getReceiverName());
			st.setString(4, msg.getSubject());
			st.setTimestamp(5, msg.getDateSent());
			st.setString(6, msg.getMessageBody());
			st.setInt(7, isRead);
			st.setInt(8, msg.getQuizId());
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
	public void sendRequest(FriendRequest msg) {
		String sql = "INSERT INTO FriendRequest (Sender, Receiver,TimeSent) VALUES (?,?,?)" ;
		PreparedStatement st = null;
		try {
			st = connection.prepareStatement("USE c_cs108_mateog");
			st.executeQuery();	
			st = connection.prepareStatement(sql);
			st.setString(1, msg.sender);
			st.setString(2, msg.receiver);
			st.setTimestamp(3, msg.stamp);
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
	public List<Message>getUserMessages(String user) {
		List<Message>list = new ArrayList<Message>();
		ResultSet result = null;
		Message msg = null;
		PreparedStatement st = null;
		String sql = "SELECT * FROM  messages WHERE Receiver ='"+user+"'";
		try {
			connection = (Connection)con.getConnection();
			st = connection.prepareStatement("USE c_cs108_mateog");
			st.executeQuery();
			st = connection.prepareStatement(sql);
			result = st.executeQuery();
			while (result.next()) {
						msg = new Message(
						result.getInt("ID"),
						result.getString("MsgType"),
						result.getString("Sender"), 
						result.getString("Receiver"),
						result.getString("Subject"), 
						result.getTimestamp("TimeSent"), 
						result.getString("MessageBody"),
						result.getInt("QuizId"));
						list.add(msg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
		
	}
	
	
	public int getNumMessages(String user){
		List<Message>list = getUserMessages(user);
		return list.size();
	}
	/**
	 * Deletes a message (friendship request, note, challenge or draft) from the
	 * relevant table
	 * @param msg
	 * @param status specifies whether to store as draft or regular note
	 */
	public void deleteMessage(int id) {
		PreparedStatement st = null;		
		String sql = "DELETE FROM messages WHERE ID =?";
		try {
			connection = (Connection)con.getConnection();
			st = connection.prepareStatement("USE c_cs108_mateog");
			st.executeQuery();
			st = connection.prepareStatement(sql);
			st.setInt(1,id);
			st.executeUpdate();
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Marks a message as unread. Updates the IsRead field on the relevant table
	 * @param msg message to be marked unread
	 */
	public void markUnread(int id) {
		PreparedStatement st = null;
		String sql = "UPDATE messages SET IsRead = 0 WHERE ID =?";
		try {
			connection = (Connection)con.getConnection();
			st = connection.prepareStatement("USE c_cs108_mateog");
			st.executeQuery();
			st = connection.prepareStatement(sql);
			st.setInt(1,id);
			st.executeUpdate();
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Marks a message as read
	 * @param msg message to be marked read
	 */
	public void markRead(int id) {
		Connection connection = null;
		PreparedStatement st = null;
		String sql = "UPDATE messages SET IsRead = 1 WHERE ID =?";
		try {
			connection = (Connection)con.getConnection();
			st = connection.prepareStatement("USE c_cs108_mateog");
			st.executeQuery();
			st = connection.prepareStatement(sql);
			st.setInt(1,id);
			st.executeUpdate();
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Message getMessage(int id) {
		Connection connection = null;
		ResultSet result = null;
		Message msg = null;
		PreparedStatement st = null;
		String sql = "SELECT * FROM  messages WHERE ID ='"+id+"'";
		try {
			connection = (Connection)con.getConnection();
			st = connection.prepareStatement("USE c_cs108_mateog");
			st.executeQuery();
			st = connection.prepareStatement(sql);
			result = st.executeQuery();
			while (result.next()) {
						msg = new Message(
						result.getInt("ID"),
						result.getString("MsgType"),
						result.getString("Sender"), 
						result.getString("Receiver"),
						result.getString("Subject"), 
						result.getTimestamp("TimeSent"), 
						result.getString("MessageBody"),
						result.getInt("QuizId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	/**
	 * Returns a list of all unviewed friendship requests where "user" is the requestee
	 * @param user requestee
	 * @return list of unviewed requests
	 */
	
	public List<FriendRequest>getFriendRequests(String user) {
		List<FriendRequest> list = new ArrayList<FriendRequest>();
		Connection connection = null;
		ResultSet result = null;
		PreparedStatement st = null;
		String sql =  "SELECT * FROM  FriendRequest WHERE Receiver ='"+user+"'";
		
		try {
			connection = (Connection)con.getConnection();
			st = connection.prepareStatement("USE c_cs108_mateog");
			st.executeQuery();
			st = connection.prepareStatement(sql);
			result = st.executeQuery();
			while (result.next()) {
				list.add(new FriendRequest(
						result.getInt("ID"),
						result.getString("Sender"), 
						result.getString("Receiver"),
						result.getTimestamp("TimeSent"))); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public void deleteRequest(int id) {
		PreparedStatement st = null;		
		String sql = "DELETE FROM FriendRequest WHERE ID =?";
		try {
			connection = (Connection)con.getConnection();
			st = connection.prepareStatement("USE c_cs108_mateog");
			st.executeQuery();
			st = connection.prepareStatement(sql);
			st.setInt(1,id);
			st.executeUpdate();
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int numRequests(String user) {
		List<FriendRequest> list = getFriendRequests(user);
		return list.size();
	}
	
}
