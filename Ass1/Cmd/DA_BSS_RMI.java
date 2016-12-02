import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * @author lmf
 *
 */
public interface DA_BSS_RMI extends Remote{
	public void broadcastMessage(Messages msg) throws RemoteException;
	public void receiveMessage(Messages msg) throws RemoteException;
	public void setProcessesNetwork() throws RemoteException;
	public void setLocalVector(int i) throws RemoteException;
	public int[] getLocalVector() throws RemoteException;
	public int getId() throws RemoteException;
}
