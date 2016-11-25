import java.rmi.Remote;
import java.rmi.RemoteException;
public interface Component_RMI extends Remote{
	public void setRegistrySet(Component_RMI[] c) throws RemoteException;
	public void receive(int level, int id, Component_RMI c) throws RemoteException;
	public void send(int level, int id, Component_RMI c) throws RemoteException;
}
