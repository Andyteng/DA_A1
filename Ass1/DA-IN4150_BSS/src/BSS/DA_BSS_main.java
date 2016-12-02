package BSS;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class DA_BSS_main {
	
	public static int Total_Process_Num = 3;
	public static int Total_Msg_Num = 1;
	
	public static void main(String[] args) throws Exception{
		DA_BSS da;
		System.setSecurityManager(new RMISecurityManager());
		try{
			LocateRegistry.createRegistry(1099);   //Do the registry
		}catch(RemoteException e){
			e.printStackTrace();
		}
		
		for(int i=0; i<Total_Process_Num; i++){
			da = new DA_BSS(i);
			Naming.rebind("rmi://localhost/DA"+i, da);
		}
		
		DA_BSS_RMI proc[] = new DA_BSS_RMI[Total_Process_Num];
		
		boolean ready = false;
		while(!ready){
			try{
				for(int i=0; i<Total_Process_Num; i++){
					proc[i] = (DA_BSS_RMI) Naming.lookup("rmi://localhost/DA"+i);        //Now we have Total_Process_Num of processes
				}
				ready = true;
			}
			catch(NotBoundException e){
				System.out.println("not ready for all process");
			}
		}
		
		for(int i=0; i<Total_Process_Num; i++){
			proc[i].setProcessesNetwork(proc);
		}
		
		System.out.println("Server is running!");
	}
}
