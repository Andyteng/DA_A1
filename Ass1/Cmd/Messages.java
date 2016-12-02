import java.io.Serializable;

public class Messages implements Serializable{

	private static final long serialVersionUID = 5071660006406928270L;
	public String msg;
	public int idSender;      //This variable shows the source of the message
	public int[] vectorClock;
	
	public Messages(){

		vectorClock = new int[3];
		for(int i=0; i<vectorClock.length; i++){
			vectorClock[i] = 0;
		}
	}
}
