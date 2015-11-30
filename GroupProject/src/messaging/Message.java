package messaging;

import java.sql.Date;
/**
 * Created by alfonce on 11/11/15.
 * Represents received messages.
 */
import java.sql.Timestamp;

public abstract class Message {
	private String sender, receiver, type, subject, body, quizname;
	private Timestamp date;
	boolean seen;
	
	public Message(String sender, String receiver, String subject,Timestamp date,String body) {
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;
		this.date = date;
		this.body = body;
		this.seen = false;
		this.quizname = "";
	}

	public void setMessageType(String type) {
		this.type = type;
	}
	
	public void setQuizName(String name) {
		this.quizname = name;
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

