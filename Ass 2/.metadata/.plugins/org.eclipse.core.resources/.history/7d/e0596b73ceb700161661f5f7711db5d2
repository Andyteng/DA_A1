import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

public class Component extends UnicastRemoteObject implements Component_RMI{
	
	private Server server;
	private int level;
	private int id;
	
	protected Component(int i, Server s) throws RemoteException {
		this.level = -1;
		this.id = i;
		this.server = s;
	}


	@Override
	public void setRegistrySet(Component_RMI[] c) throws RemoteException {
		ArrayList<Component_RMI> E = new ArrayList<Component_RMI>(Arrays.asList(c));	
		
	}
	
	@Override
	public void receive(int l, int id, Component_RMI c) throws RemoteException {
		System.out.println("Process " + this.id + " Level" + this.level + " has reveived message: Level" + l + ", ID" + id);
		
	}

	@Override
	public void send(int level, int id, Component_RMI c) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
