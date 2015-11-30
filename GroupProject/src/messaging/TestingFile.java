package messaging;
import java.util.*;

import java.sql.Timestamp;;

public class TestingFile {
	
	public static void main(String[]args) {
		AccountManager manager = new AccountManager();
		MessageManager m = new MessageManager();
		 Timestamp stamp = new Timestamp(System.currentTimeMillis());
//		 Date date = new Date(stamp.getTime());
		 
		User one = new User("alfonce");
		String subject = "food";
		String body = "I was once a singe man, but now am not";
		String sender = one.getUserName();
		String receiver = "nzioka";
		String quizname = "fwllow";	
		NoteMessage msg = new NoteMessage(sender, receiver, subject,stamp, body);
////		msg.setMessageType("draft");
		m.addMessage((NoteMessage)msg);
		
		FriendRequest friend = new FriendRequest(sender, receiver, subject, stamp, body);
		m.addMessage(friend);
		ChallengeMessage challenge = new ChallengeMessage(sender, receiver, subject, stamp, body, quizname);
		m.addMessage(challenge);
//			
	}
}
