package BSS;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Date;

public class DA_BSS_main {
	
	public static int Total_Process_Num = 3;
	public static int Total_Msg_Num = 2;
	
	public static void main(String[] args) throws Exception{
		DA_BSS da;
		System.setSecurityManager(new RMISecurityManager());
		try{
			LocateRegistry.createRegistry(1099);
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
					proc[i] = (DA_BSS_RMI) Naming.lookup("rmi://localhost/DA"+i);
				}
			}
			catch(NotBoundException e){
				System.out.println("not ready for all process");
			}
		}
		
		long timeStart = new Date().getTime();
		
		for(int i=0; i<Total_Process_Num; i++){
			proc[Total_Process_Num].setProcessesNetwork(proc);
			proc[Total_Process_Num].setStartTime(timeStart);
		}

		for(int i=0; i<Total_Process_Num; i++){
			int countP = i;
			DA_BSS_RMI daobj = proc[i];
			Thread tr = new Thread("Main_"+i){
				public void run(){
					for(int j=0; j<Total_Msg_Num; j++){
						
						try{
							Thread.sleep((long)(Math.random() * 500));
							Messages testmsg = new Messages();
							testmsg.msg = "The Message P"+countP+" N"+j;
							testmsg.idSender = countP*Total_Msg_Num+j;
							testmsg.timestamp = new Date().getTime();
							daobj.broadcastMessage(testmsg);
						}
						catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				}
			};
			tr.start();
		}
	}
}