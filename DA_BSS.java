package BSS;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class DA_BSS extends UnicastRemoteObject implements DA_BSS_RMI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	PriorityQueue<Messages> queue;
	ArrayList<Messages> ackQueue;
	DA_BSS_RMI[] proc;
	int id;
	long timeStart;
	
	protected DA_BSS(int id) throws RemoteException{
		super();
		this.id = id;
		queue = new PriorityQueue<Messages>(10, new MsgComparator());
		ackQueue = new ArrayList<Messages>();
		
	}
	
	@Override
	public void broadcastMessage(Messages msg) throws RemoteException {
		// TODO Auto-generated method stub
		int i = 0;
		for(DA_BSS_RMI Bss_I : proc){
			Thread tr = new Thread("t_"+(i++)){
				public void run(){
					try{
						Thread.sleep((long)(Math.random() * 100));
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

	@Override
	public void receiveMessage(Messages msg) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProcessesNetwork(DA_BSS_RMI[] proc) throws RemoteException {
		// TODO Auto-generated method stub
		this.proc = proc;
	}

	@Override
	public void setStartTime(long t) throws RemoteException {
		// TODO Auto-generated method stub
		timeStart = t;
	}
	
	class MsgComparator implements Comparator<Messages>{

		@Override
		public int compare(Messages o1, Messages o2) {
			// TODO Auto-generated method stub
			if(o1.timestamp == o2.timestamp)
				return o1.idSender - o2.idSender;
			return (int)(o1.timestamp - o2.timestamp);
		}
		
	}

}
