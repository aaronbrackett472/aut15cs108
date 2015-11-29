package messaging;
import java.util.*;

public class TestingFile {
	
	public static void main(String[]args) {
		AccountManager manager = new AccountManager();
		MessageManager m = new MessageManager();
		Date date = new Date();
		
		User one = new User("alfonce");
		
		String subject = "food";
		String body = "I was once a singe man, but now am not";
		String sender = one.getUserName();
		String receiver = "nzioka";
		String quizname = "fwllow";	
		NoteMessage msg = new NoteMessage(sender, receiver, subject,date, body);
		m.addMessage((NoteMessage)msg);
		
		
//		ChallengeMessage msg2 = new ChallengeMessage(sender, receiver, subject, date, body, quizname);
//		m.addMessage(msg2);
		List<NoteMessage> msgs = new ArrayList<NoteMessage>();	
		msgs =m.getNoteMessages(one);
		for (NoteMessage note: msgs) {
		}
//		
////		m.addMessage(msg);
//			
	}
}
