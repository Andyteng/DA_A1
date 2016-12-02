import java.rmi.Naming;

public class Process1 {

	static int i =1;
	public static void main(String[] args) throws Exception{
		
		DA_BSS_RMI proc = (DA_BSS_RMI) Naming.lookup("rmi://localhost:1098/DA"+i);        //Now we have Total_Process_Num of processes
		proc.setProcessesNetwork();
		
		Thread tr = new Thread("Main_"+i){
			public void run(){
				try{
					Thread.sleep(750);		
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
