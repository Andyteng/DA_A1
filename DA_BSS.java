package BSS;
import java.util.Iterator;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

public class DA_BSS extends UnicastRemoteObject implements DA_BSS_RMI {
	
	private static final long serialVersionUID = 1L;
	
	ArrayList<Messages> queue;
	DA_BSS_RMI[] proc;
	int id;
	public int[] localVector = new int[DA_BSS_main.Total_Process_Num];
	
	protected DA_BSS(int id) throws RemoteException{
		super();
		this.id = id;
		queue = new ArrayList<Messages>();
		for(int i = 0; i<localVector.length; i++){
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
							if(msg.idSender == 0 && Bss_I.getId() == 1)
							Thread.sleep(500);
							if(msg.idSender == 0 && Bss_I.getId() == 2)
							Thread.sleep(1000);	
							
							if(msg.idSender == 1 && Bss_I.getId() == 0)
							Thread.sleep(1250);
							if(msg.idSender == 1 && Bss_I.getId() == 2)
							Thread.sleep(50);	
							
							if(msg.idSender == 2 && Bss_I.getId() == 0)
							Thread.sleep(600);
							if(msg.idSender == 2 && Bss_I.getId() == 1)
							Thread.sleep(300);	
													
							Bss_I.receiveMessage(msg);
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
	
	public boolean deliver(Messages msg){
		
		int[] compareVector = new int[DA_BSS_main.Total_Process_Num];
		for(int i=0; i<DA_BSS_main.Total_Process_Num; i++){
			compareVector[i] = 	localVector[i];
		}
		compareVector[msg.idSender] += 1;
		boolean deliver = true;
		for(int i=0; i<DA_BSS_main.Total_Process_Num; i++){
			if(compareVector[i] < msg.vectorClock[i]){
				deliver = false;
			}
		}
		return deliver;
	}
	
	@Override
	public void receiveMessage(Messages msg) throws RemoteException {
		// TODO Auto-generated method stub
		if(deliver(msg)){	
			localVector[msg.idSender]++;
			System.out.println("The process"+this.id+" receive "+msg.msg+". "+Arrays.toString(msg.vectorClock)+Arrays.toString(localVector));
			for(DA_BSS_RMI Bss_s : proc){
				System.out.println("Process"+Bss_s.getId()+" 's localvector is "+Arrays.toString(Bss_s.getLocalVector()));
			}
			while(queue != null){
				boolean end = true;
				for(Iterator<Messages> it = queue.iterator();it.hasNext();){
					Messages m = it.next();
					if(deliver(m)){
						end = false;
						localVector[m.idSender]++;
						System.out.println("The process"+this.id+" receive "+m.msg+". "+Arrays.toString(m.vectorClock)+Arrays.toString(localVector));
						it.remove();
						for(DA_BSS_RMI Bss_s : proc){
							System.out.println("Process"+Bss_s.getId()+" 's localvector is "+Arrays.toString(Bss_s.getLocalVector()));
						}
					}
				}
				if(end) break;
			}
		}
		else{
			queue.add(msg);
			System.out.println(msg.msg+" cannot be delivered now!");
			System.out.println(queue.size());
		}
	}

	@Override
	public void setProcessesNetwork(DA_BSS_RMI[] proc) throws RemoteException {
		// TODO Auto-generated method stub
		this.proc = proc;
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
