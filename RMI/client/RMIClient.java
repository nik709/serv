import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.*;
import java.rmi.server.RMIClassLoader;
import java.util.Vector;

public class RMIClient {


    public static void main(String[] args) {

	//System.setProperty("java.rmi.server.hostname","192.168.0.199");

	String host = (args.length < 1) ? null : args[0];
	System.setSecurityManager(new RMISecurityManager());
	try {
            
	    Registry registry = LocateRegistry.getRegistry(host);
	    Hello stub = (Hello) registry.lookup("Hello");
	    System.out.println(stub);
            
            Class cl = RMIClassLoader.loadClass("RemoteClass");
            //Method[] m = cl.getMethods();
            Object inst = cl.newInstance();
            //Object res = m[0].invoke(inst, null);
            
	    String response = stub.sayHello("Stefan");
	    System.out.println("response: " + response);
            //BufferedReader bReader = new BufferedReader (new InputStreamReader(System.in, "UTF-8"));
            // ввод строки данных
            String cStr = new String();
            //cStr = bReader.readLine();
            //запрос
            inst = (stub.getObject(cStr));
            //вывод
            System.out.println(inst);

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}