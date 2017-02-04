package utilisateur;

import utilitaire.*;

public abstract class Utilisateur implements Identifiable {
//  static protected Set<Integer> identifiants = new HashSet<Integer>();
  static protected int idMax = -1;
  final protected int IDENTIFIANT;
  
  
  public Utilisateur (int id){
    IDENTIFIANT = id;
  }
  
//  public boolean estCreateurDe(Evenement e){
//    if (e.renvoyerCreateur() == this) return true;
//    else return false;
//  }
  
  public int identifiant(){
    return IDENTIFIANT;
  }
}
