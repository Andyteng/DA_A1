package Afek;

import java.io.Serializable;

public class Messages implements Serializable{

	private static final long serialVersionUID = -1811636992706035062L;
	public int id;
	public int level;
	
	public Messages(int i){
		this.level = -1;
		id = i;
	}
	
	
}