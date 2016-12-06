import java.io.Serializable;
import java.util.UUID;

public class Messages implements Serializable{

	private static final long serialVersionUID = -1811636992706035062L;
	public UUID id;
	public int level;
	public int pro_id;
	
	public Messages(int i){
		pro_id = i;
	}
}
