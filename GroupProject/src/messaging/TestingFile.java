package messaging;
import java.sql.Date;

public class TestingFile {
	
	public static void main(String[]args) {
		AccountManager manager = new AccountManager();
		MessageManager m = new MessageManager();
		java.sql.Date date = new Date(0);
		String subject = "food";
		String body = "I was once a singe man, but now am not";
		String sender = "alfonce";
		String receiver = "nzioka";
		String quizname = "fwllow";	
		NoteMessage msg = new NoteMessage(sender, receiver, subject,date, body);
		m.addMessage(msg);
		
		msg.setMessageType("draft");
		m.addMessage(msg);
		ChallengeMessage msg2 = new ChallengeMessage(sender, receiver, subject, date, body, quizname);
		m.addMessage(msg2);
		m.markRead(msg2);
		
		
		FriendRequest request = new FriendRequest(sender, receiver, subject, date, body);
		m.addMessage(request);
		m.markRead(request);

		
		
	}
}
