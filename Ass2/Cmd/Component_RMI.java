import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Component_RMI extends Remote{
	public void setProcessesNetwork() throws RemoteException;
	public boolean IsCandidate() throws RemoteException;
	public void startcandidate() throws RemoteException;
	public int getprocid() throws RemoteException;
	public void receiveRequest(Messages msg) throws RemoteException;
	public int getid() throws RemoteException;
	public void acknowledge(boolean ack) throws RemoteException;
	public boolean isElected() throws RemoteException;
	public void setLevel() throws RemoteException;
	public void ordinaryProcess() throws RemoteException;
	public boolean isAwaken() throws RemoteException;
}
