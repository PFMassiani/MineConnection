package serveur_communication;

public enum TypeBackupable {
  ETUDIANT("Etudiant"),
  ASSOCIATION("Association"),
  EVENEMENT("Evenement"),
  PAPS("PAPS");
  
  String nom;
  
  TypeBackupable(String nom){
    this.nom = nom;
  }
  
  public String getNomClasse(){
    return nom;
  }
}
