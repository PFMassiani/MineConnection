package utilisateur;

import utilitaire.*;

public abstract class Utilisateur implements Backupable {
  final protected int IDENTIFIANT;
  

  public Utilisateur (int id){
    IDENTIFIANT = id;
  }

  public int getID(){
    return IDENTIFIANT;
  }
  
}