
import java.util.Iterator;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;



public class DA_BSS extends UnicastRemoteObject implements DA_BSS_RMI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	ArrayList<Messages> queue;
	DA_BSS_RMI[] proc;
	int id;
	long timeStart;
	public int[] localVector = new int[DA_BSS_main.Total_Process_Num];
	
	protected DA_BSS(int id) throws RemoteException{
		super();
		this.id = id;
		queue = new ArrayList<Messages>();
		
	}
	
	@Override
	public void broadcastMessage(Messages msg) throws RemoteException {
		// TODO Auto-generated method stub
		int i = 0;
		for(DA_BSS_RMI Bss_I : proc){
			Thread tr = new Thread("t_"+(i++)){
				public void run(){
					try{
						Thread.sleep((long)(Math.random() * 500));
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
	
	public boolean deliver(Messages msg){
		
		int[] compareVector = localVector;
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
			localVector = msg.vectorClock;
			System.out.println(msg.msg+" "+localVector.toString());
			while(queue != null){
				boolean end = true;
				for(Iterator<Messages> it = queue.iterator();it.hasNext();){
					Messages m = it.next();
					if(deliver(m)){
						end = false;
						localVector = m.vectorClock;
						System.out.println(m.msg+" "+localVector.toString());
						it.remove();
					}
				}
				if(end) break;
			}
			
			
		}
		else{
			queue.add(msg);
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

}
