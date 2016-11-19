import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;

public class DA_BSS_main {
	public static void main(String[] args){
		try{
			System.setSecurityManager(new RMISecurityManager());
			LocateRegistry.createRegistry(1092);

			DA_BSS_RMI c = new DA_BSS();
			Naming.bind("rmi://localhost:1092/P1", c);
			System.out.println("RMI server start now!");
		}catch(Exception e){
			e.printStackTrace();
		}
			
		try{
			DA_BSS_RMI P1 = (DA_BSS_RMI) Naming.lookup("rmi://localhost:1092/P1");
			P1.sendMessage();
		}catch(Exception e){
			e.printStackTrace();
		}
		

	}
}
