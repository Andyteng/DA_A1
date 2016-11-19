import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DA_BSS extends UnicastRemoteObject implements DA_BSS_RMI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected DA_BSS() throws RemoteException{
		super();
	}
	
	@Override
	public void sendMessage() throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("sending...!");
	}

	@Override
	public void receiveMessage() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}