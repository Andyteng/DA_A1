import java.rmi.Naming;

public class Process2 {

	static int i =2;
	public static void main(String[] args) throws Exception{
		
		DA_BSS_RMI proc = (DA_BSS_RMI) Naming.lookup("rmi://localhost:1097/DA"+i);        //Now we have Total_Process_Num of processes
		proc.setProcessesNetwork();
		
		Thread tr = new Thread("Main_"+i){
			public void run(){
				try{
					Thread.sleep(1200);
					proc.setLocalVector(i);
					Messages testmsg = new Messages();                //Define the message to be sent
					testmsg.msg = "the Message P"+i;
					testmsg.idSender = i;
					testmsg.vectorClock = proc.getLocalVector();
					proc.broadcastMessage(testmsg);
				}
				catch (Exception e) {
						// TODO: handle exception
					e.printStackTrace();
				}
			}
		};
		tr.start();
	}
}
