package messaging;

/**
 * Created by alfonce on 11/11/15.
 * Represents received messages.
 */

import java.sql.Timestamp;

public class Message {
	private String sender, receiver, type, subject, body;
	private Timestamp date;
	private int id = 0;
	private int quiz_id = 0;
	boolean seen;
	public Message(int id, String type, String sender, String receiver, String subject,Timestamp date,String body, int quiz_id) {
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;
		this.type = type;
		this.date = date;
		this.body = body;
		this.seen = false;
		this.quiz_id = quiz_id;
		this.id = id;

	}

	public void setMessageType(String type) {
		this.type = type;
	}
	
	public int getId() {
		return id;
	}
	
	public int getQuizId(){
		return quiz_id;
	}

	public String getMessageBody() {
		return body;
	}
	
	public String getSenderName() {
		return sender;
	}
	public String getReceiverName() {
		return receiver;
	}
	public String getMessageType() {
		return type;
	}
	
	public Timestamp getDateSent() {
		return date;
	}
	public boolean isRead() {
		return seen;
	}
	public String toString() {
		return "message [senderUserName=" + sender
				+ ", recieverUserName=" + receiver + ", timeSent="
				+ date + ", messageBody=" + body + ", messageType=" + type;
	}
	public String getSubject() {
		return subject;
	}

	public void setIsRead(boolean b) {
		this.seen = b;
		
		// TODO Auto-generated method stub
		
	}
	
}

