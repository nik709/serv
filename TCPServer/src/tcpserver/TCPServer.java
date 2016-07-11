/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anna
 */


public class TCPServer {

    /**
     * @param args the command line arguments
     */
    

    public static void run(Socket clientSocket) {
                try{
                System.out.printf("new client: %s\n",clientSocket.getRemoteSocketAddress());
                BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String GET = br.readLine();
                System.out.println(GET);
                while (true) {
                    String s = br.readLine();
                    System.out.println(s);
                    if (s == null || s.trim().length() == 0) {
                        break;
                    }
                }
                System.out.println("read loop exit");
                if (GET != null) {
                    StringTokenizer st = new StringTokenizer(GET);
                    st.nextToken();
                    String resource = st.nextToken();
                    System.out.printf("resoure=%s\n\n", resource);

                    StringBuilder responce = new StringBuilder();

                    /*HTTP/1.1 200 OK
                    Server: GlassFish Server Open Source Edition  4.1.1 
                    X-Powered-By: Servlet/3.1 JSP/2.3 (GlassFish Server Open Source Edition  4.1.1  Java/Oracle Corporation/1.8)
                    Accept-Ranges: bytes
                    ETag: W/"5120-1467889844908"
                    Last-Modified: Thu, 07 Jul 2016 11:10:44 GMT
                    Content-Type: application/java
                    Date: Thu, 07 Jul 2016 11:13:56 GMT
                    Content-Length: 5120*/
                    SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.US);
                    responce.append("HTTP/1.1 200 OK\r\n");
                    responce.append("Server: MyWebServer\r\n");
                    responce.append("Content-Type: application/java\r\n");
                    //responce.append("Content-Type: text/html\r\n");
                    responce.append("Date: "); responce.append(df.format(new Date())); responce.append("\r\n");

                    BufferedInputStream is = new BufferedInputStream(new FileInputStream("./" + resource));
                    ByteArrayOutputStream aos = new ByteArrayOutputStream();
                    int data;
                    int len = 0;
                    while ((data = is.read()) != -1) {
                        aos.write(data);
                        len++;
                    }
                    is.close();
                    
                    responce.append("Content-Length: ");responce.append(len);responce.append("\r\n");
                    responce.append("Connection: close\r\n");
                    responce.append("\r\n");
                    
                    OutputStream os = clientSocket.getOutputStream();
                    os.write(responce.toString().getBytes());
                    os.write(aos.toByteArray());
                    os.close();
                }
                }
                catch ( Exception e){
                        e.printStackTrace();
                        }
        
    }
    public static void main(String[] args) {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(81);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(0);
        }

        while (true) {
            try {
                Socket clientSocket = ss.accept();
                Worker w = new Worker(clientSocket);
                w.start();
                
                
                }

             catch (Exception ex) {
                //Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
            }

        
    }

   
     
  

}
 