import java.io.Serializable;

public class Messages implements Serializable{

	private static final long serialVersionUID = 5071660006406928270L;
	public int type;
	public String msg;
	public int idSender;
	public long timestamp;
	
	public Messages(){
		type = 0;
	}
	
	public String toString(){
		String s = "ACK";
		if(type == 0) s = "MESSAGE";
		return "{"+s+"}"+msg+" from Process "+idSender+" at ["+timestamp+"]";
	}
	
	public boolean equals(Object obj){
		if(obj == null) return false;
		Messages m = (Messages) obj;
		if(timestamp == m.timestamp && msg.equals(m.msg)) return true;
		return false;
	}
}




