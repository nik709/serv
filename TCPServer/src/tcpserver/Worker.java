/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.net.*;

/**
 *
 * @author vo
 */
class Worker extends Thread{
          
            Socket s;
          public Worker(Socket s){
          this.s = s;}
          
          public void run(){
              try{
                  TCPServer.run(s);
              }
              catch( Exception e){
                  e.printStackTrace();
              }
                  
          }  
 }
