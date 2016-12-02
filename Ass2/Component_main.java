import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Component_main {
	public static void main(String[] args) {
		System.setSecurityManager(new RMISecurityManager());
		try {
			LocateRegistry.createRegistry(1099);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		try {
			Component component = new Component();
			Naming.rebind("rmi://localhost/DA_A2", component);
			System.out.print("test!");
		} catch (RemoteException | MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			Component_RMI proc = (Component_RMI) Naming.lookup("rmi://localhost/DA_A2");
		} catch (Exception e) {
			System.out.println("not ready for process");
		}
	}
}
