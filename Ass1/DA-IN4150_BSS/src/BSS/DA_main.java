package BSS;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class DA_main {
	
	public static int Total_Process_Num = 3;
	public static int Total_Msg_Num = 1;
	
	public static void main(String[] args) throws Exception{
		
		DA_BSS daimp;
		System.setSecurityManager(new RMISecurityManager());
		
		try{
			LocateRegistry.createRegistry(1099);   //Do the registry
		}catch(RemoteException e){
			e.printStackTrace();
		}
		int i=0; 
		daimp = new DA_BSS(i);
		Naming.rebind("rmi://localhost:1099/DA"+i, daimp);
		
		try{
			LocateRegistry.createRegistry(1098);   //Do the registry
		}catch(RemoteException e){
			e.printStackTrace();
		}
		i=1; 
		daimp = new DA_BSS(i);
		Naming.rebind("rmi://localhost:1098/DA"+i, daimp);
		
		try{
			LocateRegistry.createRegistry(1097);   //Do the registry
		}catch(RemoteException e){
			e.printStackTrace();
		}
		i=2; 
		daimp = new DA_BSS(i);
		Naming.rebind("rmi://localhost:1097/DA"+i, daimp);
		
		System.out.println("Server is running!");
	}
}