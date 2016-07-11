import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class RMIServer{
	
    public static void main(String args[]) {
	
	try {
	    //System.setProperty("java.rmi.server.hostname","192.168.0.199");
	    HelloImpl obj = new HelloImpl();
            System.out.println("instance created");
	    // Bind the remote object's stub in the registry
	    Registry registry = LocateRegistry.getRegistry();

	    System.out.println("locale got");
	    registry.rebind("Hello", obj);

	    //registry.rebind("rmi://127.0.0.1/Hello",obj);
	    System.out.println("Server ready");
	} catch (Exception e) {
	  //System.err.println("Server exception: " + e.toString());
	    e.printStackTrace();
	}
    }
}