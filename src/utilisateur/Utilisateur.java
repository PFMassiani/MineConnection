package utilisateur;

import utilitaire.*;
import serveur_communication.*;

/* Classe mère de tout ce qui peut participer à une Interaction. */

public abstract class Utilisateur implements Backupable,Encodable {
  final protected int IDENTIFIANT;
  

  public Utilisateur (int id){
    IDENTIFIANT = id;
  }

  public int getID(){
    return IDENTIFIANT;
  }
  
  
  // Sert à transmettre un Utilisateur au client
  @Override
  public byte[] encoder(){
    return new byte[1];
  }
  
}