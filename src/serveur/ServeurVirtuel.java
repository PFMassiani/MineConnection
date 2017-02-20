package serveur;

import java.net.*;
import java.io.*;
import java.util.*;
import utilisateur.Etudiant;

public class ServeurVirtuel extends Thread {
  
  public final static int CHERCHER_UTILISATEUR = 1;
  
  private Socket client;
  private OutputStream os;
  private InputStream is;
  
  private boolean stop = false, suspendre = false;
  
  String message = "";
  int choix, buffer;
  
  /**
   * @param args
   */
  
  
  public ServeurVirtuel (Socket client){
    
    try{
      this.client = client;
      
      System.out.println("Récupération des flux de lecture/écriture...");
      
      os = client.getOutputStream();
      is = client.getInputStream();
      
      System.out.println("Connexion au client effectuée !");
    } catch (IOException e){
      System.err.println("Erreur lors de la connexion au client : " + e.getMessage());
      e.printStackTrace();
    }
    
  }
  
  public void run(){
    while (!stop){
      if(!suspendre){
        try{
          choix = is.read();
          buffer = is.read();
          while (buffer != -1) message += buffer;
          effectuerChoix();
          
        } catch (IOException e){
          System.out.println("Erreur lors de la communication avec le client : " + e.getMessage());
          e.printStackTrace();
        }
        
      }
    }
  }
  
  public void effectuerChoix(){
    try{
      switch (choix){
      case CHERCHER_UTILISATEUR:
        int id = Integer.parseInt(message);
        Etudiant etudiant = Etudiant.chercher(id);
        os.write(etudiant.encoder());
      }
    } catch (IOException e){
      System.out.println("Erreur lors de l'exécution du choix " + choix + " : " + e.getMessage());
      e.printStackTrace();
    }
  }
}
