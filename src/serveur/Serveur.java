package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {
  
  private static int port = 10000;
  private static boolean stop = false;
  
  public static void main(String[] args){
    try {
      ServerSocket serv;
      serv = new ServerSocket(port);
      Socket client;
      while (!stop) {
        System.out.println("En attente de connexion client...");
        client = serv.accept();
        new ServeurVirtuel(client);
      }
      
      serv.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
     
  }
}
