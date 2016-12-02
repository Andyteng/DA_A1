import java.io.IOException;
import java.nio.channels.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

public class Server{
	
	public static int candidateAmount;
	public static int ordinaryAmount;

	public void main() throws AlreadyBoundException, NotBoundException, IOException{
		
		candidateAmount = 2;
		ordinaryAmount = 2;
		
		ArrayList<Component_RMI> components = new ArrayList<Component_RMI>();
		for(int i=0; i<candidateAmount; i++){
			CandidateComponent new_component = new CandidateComponent(i+ordinaryAmount, this);
			components.add(new_component);
			registry.bind("CC"+(i+1), new_component );
		}
		for(int i=0; i<ordinaryAmount; i++){
			OrdinaryComponent new_component = new OrdinaryComponent(this, i);
			components.add(new_component);
			registry.bind("OC"+(i+1), new_component );
		}	
		
		System.out.println("started");
		setRegistry();
		
	}
}
