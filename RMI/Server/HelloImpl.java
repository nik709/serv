import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloImpl extends UnicastRemoteObject implements Hello {
        
	public String sayHello(String who){
		return "hello, dear "+who;	
	}
	public HelloImpl() throws RemoteException{
		super();
	}
	public Object getObject(String str) throws RemoteException{
            RemoteClass tmp = new RemoteClass();
            try {                
               tmp = (RemoteClass) connectToDataBase("unc16_user4", str);
            } catch (ClassNotFoundException ex) {
                System.out.println("Class not found");
            } catch (SQLException ex) {
                System.out.println("SQLException");
                ex.printStackTrace();
            }            
            return tmp;
        }
        public Vector connectToDataBase(String password, String str) throws ClassNotFoundException, SQLException{
            RemoteClass remote = new RemoteClass();
            Class.forName("oracle.jdbc.driver.OracleDriver");
            try{
                Locale origLocale = Locale.getDefault();
                if (origLocale.toString().equals("ru_RU"))
                    Locale.setDefault(new  Locale("en", "US"));
            } catch(Exception e){
                System.out.println(e);
            }
            
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@edu-netcracker.com:1520:xe", "unc16_user4", password);
            //System.out.println("ÄÎØËÎ2");
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pers WHERE lastname='Ìèëèÿ'");
            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            while(rs.next()) {
                for(int i = 1; i <= cc; i++){
                    remote.add(rs.getObject(i));
                    //System.out.print(rs.getObject(i) + " ");
                }
                //System.out.println();
            }

            //System.out.println(conn);
            conn.close();
            return remote;
        }
}