package serveur_communication;

public enum TypeBackupable {
  ETUDIANT("utilisateur.Etudiant"),
  ASSOCIATION("utilisateur.Association"),
  EVENEMENT("interaction.Evenement"),
  PAPS("interaction.PAPS");
  
  String nom;
  
  TypeBackupable(String nom){
    this.nom = nom;
  }
  
  public String getNomClasse(){
    return nom;
  }
}
