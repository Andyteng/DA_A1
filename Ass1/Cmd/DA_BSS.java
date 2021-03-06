import java.util.Iterator;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

public class DA_BSS extends UnicastRemoteObject implements DA_BSS_RMI {
	
	private static final long serialVersionUID = 1L;
	
	ArrayList<Messages> queue;
	DA_BSS_RMI[] proc = new DA_BSS_RMI[3];
	int id;
	public int[] localVector = new int[3];
	
	protected DA_BSS(int id) throws RemoteException{
		super();
		this.id = id;
		queue = new ArrayList<Messages>();
		for(int i = 0; i<localVector.length; i++){     //Initiate the vectorclock
			localVector[i] = 0;
		}	
	}
	
	@Override
	public void broadcastMessage(Messages msg) throws RemoteException {
		// TODO Auto-generated method stub
		int i = 0;
		for(DA_BSS_RMI Bss_I : proc){
			if(Bss_I.getId() != msg.idSender){
				Thread tr = new Thread("t_"+(i++)){
					public void run(){
						try{
							if(msg.idSender == 0 && Bss_I.getId() == 1) Thread.sleep(500);       //A simple case
							else if(msg.idSender == 0 && Bss_I.getId() == 2) Thread.sleep(1000);	
							else if(msg.idSender == 1 && Bss_I.getId() == 0) Thread.sleep(1250);
							else if(msg.idSender == 1 && Bss_I.getId() == 2) Thread.sleep(50);	
							else if(msg.idSender == 2 && Bss_I.getId() == 0) Thread.sleep(600);
							else if(msg.idSender == 2 && Bss_I.getId() == 1) Thread.sleep(300);					
							Bss_I.receiveMessage(msg);    // After a certain second, process receive some messages
						} 
						catch (InterruptedException e){
							e.printStackTrace();
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				};
				tr.start();
			}
		}
	}
	
	//This method is to return whether the message can be delivered
	//That is V + ej >= Vm
	public boolean deliver(Messages msg){
		
		int[] compareVector = new int[proc.length];
		for(int i=0; i<proc.length; i++){
			compareVector[i] = 	localVector[i];
		}
		compareVector[msg.idSender] += 1;
		boolean deliver = true;
		for(int i=0; i<proc.length; i++){
			if(compareVector[i] < msg.vectorClock[i]){
				deliver = false;
			}
		}
		return deliver;
	}
	
	@Override
	public void receiveMessage(Messages msg) throws RemoteException {
		// TODO Auto-generated method stub
		if(deliver(msg)){	   //Determine whether this message can be delivered
			localVector[msg.idSender]++;    //If it can be delivered, update the local vector.
			System.out.println("The process"+this.id+" receive "+msg.msg+". ");
			System.out.println(Arrays.toString(localVector));
			System.out.println("");
			while(queue != null){         //If there are messages in the buffer, examine it whether it can be delivered in this round
				boolean end = true;
				for(Iterator<Messages> it = queue.iterator();it.hasNext();){
					Messages m = it.next();
					if(deliver(m)){
						end = false;       //If some messages in the buffer are delivered, the vector clock will be updated. Then you need to test it again.
						localVector[m.idSender]++;
						System.out.println("The process"+this.id+" receive "+m.msg+". ");
						it.remove();
						System.out.println(Arrays.toString(localVector));
					}
				}
				if(end) break;
			}
		}
		else{              // If the message can not be delivered now, put it into a buffer
			queue.add(msg);
			System.out.println(msg.msg+" cannot be delivered now!");
			System.out.println("The size of queue in process"+id+ " now is "+queue.size());
			System.out.println("");
		}
	}

	@Override
	public void setProcessesNetwork() throws RemoteException {
		// TODO Auto-generated method stub
		try{
			proc[0] = (DA_BSS_RMI) Naming.lookup("rmi://localhost/DA0");
			proc[1] = (DA_BSS_RMI) Naming.lookup("rmi://localhost:1098/DA1");
			proc[2] = (DA_BSS_RMI) Naming.lookup("rmi://localhost:1097/DA2");
		} catch(NotBoundException e){
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
		}
		
	}

	@Override
	public void setLocalVector(int i) throws RemoteException {
		// TODO Auto-generated method stub
		localVector[i] += 1;
	}

	@Override
	public int[] getLocalVector() throws RemoteException {
		// TODO Auto-generated method stub
		return localVector;
	}

	@Override
	public int getId() throws RemoteException {
		// TODO Auto-generated method stub
		return this.id;
	}
}

