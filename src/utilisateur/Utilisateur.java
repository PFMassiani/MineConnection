package utilisateur;

import utilitaire.*;
import serveur_communication.*;

/* Classe mère de tout ce qui peut participer à une Interaction. */

public abstract class Utilisateur extends Encodable implements Backupable {
  final protected int IDENTIFIANT;
  

  public Utilisateur (int id){
    IDENTIFIANT = id;
  }

  public int getID(){
    return IDENTIFIANT;
  }
  
}