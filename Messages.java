
import java.io.Serializable;

public class Messages implements Serializable{

	private static final long serialVersionUID = 5071660006406928270L;
	public String msg;
	public int idSender;
	public int[] vectorClock;
	
	public Messages(){

		vectorClock = new int[DA_BSS_main.Total_Process_Num];
	}
	
//	public String toString(){
//		String s = "ACK";
//		if(type == 0) s = "MESSAGE";
//		return "{"+s+"}"+msg+" from Process "+idSender+" at ["+timestamp+"]";
//	}
	
	public boolean equals(Object obj){
		if(obj == null) return false;
		Messages m = (Messages) obj;
		if(msg.equals(m.msg)) return true;
		return false;
	}
}
