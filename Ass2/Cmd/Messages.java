import java.io.Serializable;

public class Messages implements Serializable{

	private static final long serialVersionUID = -1811636992706035062L;
	public int id;
	public int level;
	public int pro_id;
	
	public Messages(int i){
		pro_id = i;
	}
}
