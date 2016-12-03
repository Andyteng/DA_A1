import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Process1 {

	static int i =1;
	public static void main(String[] args) throws Exception{
		DA_BSS daimp;
		System.setSecurityManager(new RMISecurityManager());
		try{
			LocateRegistry.createRegistry(1098);   //Do the registry
		}catch(RemoteException e){
			e.printStackTrace();
		}
		daimp = new DA_BSS(i);
		Naming.rebind("rmi://localhost:1098/DA"+i, daimp);
		
		DA_BSS_RMI proc = (DA_BSS_RMI) Naming.lookup("rmi://localhost:1098/DA"+i);        //Now we have Total_Process_Num of processes
		
		try{
			Thread.sleep(1750);	
			proc.setProcessesNetwork();
			proc.setLocalVector(i);
			Messages testmsg = new Messages();                //Define the message to be sent
			testmsg.msg = "the Message P"+i;
			testmsg.idSender = i;
			testmsg.vectorClock = proc.getLocalVector();
			proc.broadcastMessage(testmsg);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
