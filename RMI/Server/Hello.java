import java.rmi.*;

public interface Hello extends Remote {
	public String sayHello(String who) throws RemoteException;
	public Object getObject(String str) throws RemoteException;
}