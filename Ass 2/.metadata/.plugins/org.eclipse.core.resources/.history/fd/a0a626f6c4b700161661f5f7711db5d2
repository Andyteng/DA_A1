import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

public class Component_main {
	
	public static int candidateAmount;
	public static int ordinaryAmount;
	public static Server server;
	
	public static void main(String[] args) throws Exception{
		
		candidateAmount = 2;
		ordinaryAmount = 2;
		
		ArrayList<Component_RMI> components = new ArrayList<Component_RMI>();
		
		for(int i=0; i<candidateAmount; i++){
			CandidateComponent new_component = new CandidateComponent(i+ordinaryAmount, server);
			components.add(new_component);
			Naming.rebind("CC"+(i+1), new_component );
		}
		for(int i=0; i<ordinaryAmount; i++){
			OrdinaryComponent new_component = new OrdinaryComponent(server, i);
			components.add(new_component);
			Naming.rebind("OC"+(i+1), new_component );
		}	
		
		System.out.println("started");
		setRegistry();
		
		System.setSecurityManager(new RMISecurityManager());
		try {
			LocateRegistry.createRegistry(1099);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		
		try {
			for(int i=0; i<candidateAmount; i++){
				CandidateComponent new_component = new CandidateComponent(i+ordinaryAmount, this);
				components.add(new_component);
				Naming.rebind("CC"+(i+1), new_component );
			}
			for(int i=0; i<ordinaryAmount; i++){
				OrdinaryComponent new_component = new OrdinaryComponent(this, i);
				components.add(new_component);
				registry.bind("OC"+(i+1), new_component );
			}	
			
			System.out.println("started");
			setRegistry();
			Naming.rebind("rmi://localhost/DA_A2", component);
			
			System.out.print("test!");
		} catch (RemoteException | MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// create processes for both candidate components and ordinary components
		boolean ready = false;
		while(!ready){
			try {
				for (int i=0;i<candidateAmount; i++){
					Component_RMI proc_CC = (Component_RMI) Naming.lookup("CC"+(i+1));
				}
				for (int i=0;i<ordinaryAmount; i++){
					Component_RMI proc_OC = (Component_RMI) Naming.lookup("OC"+(i+1));
				}
				
			} catch (Exception e) {
				System.out.println("not ready for process");
			}
		}
		
		// create threads for all processes
		for (int i=0; i<)
		for (int i=0; i<candidateAmount; i++){
			Component_RMI daobj = proc[i];
			Thread tr = new Thread("Main_"+i){
				public void run(){
					
				}
			}
			
		}
	}
}
