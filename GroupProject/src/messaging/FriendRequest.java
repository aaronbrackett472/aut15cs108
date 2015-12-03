package messaging;
import java.sql.Timestamp;

public class FriendRequest{
	public String sender, receiver, subject;
	public Timestamp stamp;
	public int id;
    public FriendRequest(int id,String sender, String receiver, Timestamp stamp) {
		this.sender = sender;
		this.receiver = receiver;
		this.stamp = stamp;		
		this.id = id;
    }
}