import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author lmf
 *
 */
public interface DA_BSS_RMI extends Remote{
	public void sendMessage() throws RemoteException;
	public void receiveMessage() throws RemoteException;
}

