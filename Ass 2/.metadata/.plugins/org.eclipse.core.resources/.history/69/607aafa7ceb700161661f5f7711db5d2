import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CandidateComponent extends UnicastRemoteObject implements Component_RMI{

	private int level;
	private int id;
	private Server server;

	protected CandidateComponent(int i, Server s) throws RemoteException {
		this.level = 0;
		this.id = i;
		this.server = s;
		
	}

	@Override
	public void receive(int level, int id, Component_RMI c) throws RemoteException {
		System.out.println("Process " + this.id + " Level" + this.level + " has reveived message: Level" + l + ", ID" + id);
		
		
	}

	@Override
	public void send(int level, int id, Component_RMI c) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
