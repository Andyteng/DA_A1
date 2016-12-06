import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Component_RMI extends Remote{
	public void setProcessesNetwork() throws RemoteException;
	public boolean IsCandidate() throws RemoteException;
	public void startcandidate() throws RemoteException;
	public int getprocid() throws RemoteException;
	public void receiveRequest(Messages msg) throws RemoteException;
	public void compareTime() throws RemoteException;
	public long getid() throws RemoteException;
	public void acknowledge(boolean ack) throws RemoteException;
}
