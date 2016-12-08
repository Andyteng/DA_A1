import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Process {
	public static void main(String[] args) throws Exception {
		int i = Integer.parseInt(args[0]);
		int Total_Pro_Num = Integer.parseInt(args[1]);
		int port = i+1099;
		
		System.setSecurityManager(new RMISecurityManager());		
		try{
			LocateRegistry.createRegistry(port);
		} catch (RemoteException e){
			e.printStackTrace();
		}
		Component c = new Component(i, Total_Pro_Num);
		Naming.rebind("rmi://localhost:"+port+"/AFEK"+i, c);
		
		Component_RMI proc = (Component_RMI) Naming.lookup("rmi://localhost:"+port+"/AFEK"+i);	
		try{
			Thread.sleep(3000);
			System.out.println(proc.getid());
			proc.setProcessesNetwork();
			if(proc.IsCandidate()){
				System.out.println("I am candidate!\n");
			}
			while(proc.IsCandidate()){
				proc.setLevel();
				Thread.sleep(3000);
				proc.startcandidate();
				if(proc.isElected()){
					break;
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("No!");
		}		
	}	
}
