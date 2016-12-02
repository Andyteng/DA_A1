package BSS;

import java.util.ArrayList;
import java.util.UUID;

public class test {
	public static void main(String[] args) {
		UUID uuid = UUID.randomUUID();
		UUID uuid1 = UUID.randomUUID();
		long i = uuid.getMostSignificantBits();
		long i1 = uuid1.getMostSignificantBits();
		System.out.println(uuid);
		System.out.format("%X",uuid.getMostSignificantBits());
		System.out.println("");
		System.out.println(uuid1);
		System.out.format("%X",uuid1.getMostSignificantBits());
		System.out.println();
		System.out.println(i+" "+i1);
		System.out.println(i>i1);
	}
}
