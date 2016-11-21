
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author lmf
 *
 */
public interface DA_BSS_RMI extends Remote{
	public void broadcastMessage(Messages msg) throws RemoteException;
	public void receiveMessage(Messages msg) throws RemoteException;
	public void setProcessesNetwork(DA_BSS_RMI[] proc) throws RemoteException;
	public void setStartTime(long t) throws RemoteException;
}
