package Afek;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Component_RMI extends Remote{
	public void candidate(Messages msg) throws RemoteException;
	public void ordinary(Messages msg) throws RemoteException;
	public void setProcessesNetwork(Component_RMI[] proc) throws RemoteException;
	public void setcandidateArray(int[] arr) throws RemoteException;
	public int getid() throws RemoteException;
	public int getprocid() throws RemoteException;
}
