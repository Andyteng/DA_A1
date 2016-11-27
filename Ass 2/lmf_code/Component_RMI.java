package Afek;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Component_RMI extends Remote{
	public void startcandidate() throws RemoteException;
	public void ordinary() throws RemoteException;
	public void setProcessesNetwork(Component_RMI[] proc) throws RemoteException;
	public int getprocid() throws RemoteException;
	public void setLevel() throws RemoteException;
	public void requestElection(Messages msg) throws RemoteException;
	public void acknowledge() throws RemoteException;
}
