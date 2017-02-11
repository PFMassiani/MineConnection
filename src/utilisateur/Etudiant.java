package utilisateur;

import java.sql.*;
import exception.*;
import java.util.Set;

public class Etudiant extends Utilisateur {

  // -----------------------------------------------------------------------------------------------------------------------
  // ATTRIBUTS -------------------------------------------------------------------------------------------------------------
  // -----------------------------------------------------------------------------------------------------------------------
  
  private String nom, prenom, telephone;
  private int promo;
  //  Date naissance;
  //  Calendrier calendrier;
  
  private static DAEtudiant dae = new DAEtudiant();
  
 //------------------------------------------------------------------------------------------------------------------------
 // CONSTRUCTEURS ---------------------------------------------------------------------------------------------------------
 // -----------------------------------------------------------------------------------------------------------------------
  
  //Constructeur utilisé pour créer un nouvel étudiant
  public Etudiant (String n, String p, String t, int pr){
    super((new DAEtudiant()).getNewID());
    
    nom = n;
    prenom = p;
    promo = pr;
    telephone = t;
    
    dae.update(this);
    if (IDENTIFIANT == -1) throw new InvalidIDException("");
  }
  
  //Charger un étudiant depuis la BDD
  public Etudiant (ResultSet r) throws SQLException{
    super(r.getInt("id"));
    try {
      ResultSetMetaData meta = r.getMetaData();
      if (meta.getTableName(1).compareTo("Etudiant") != 0) throw new InvalidResultException("Le résultat ne provient pas de la table Etudiant");

      nom = r.getString("nom");
      prenom = r.getString("prenom");
      telephone = r.getString("telephone");
      promo = r.getInt("promotion");
    } catch (InvalidResultException ex) {
      System.out.println("InvalidResultException : " + ex.getMessage());
    }
  }
  
  //------------------------------------------------------------------------------------------------------------------------
  // ACCESSEURS ------------------------------------------------------------------------------------------------------------
  // -----------------------------------------------------------------------------------------------------------------------
  
  public String getNom(){
    return nom;
  }
  public String getPrenom(){
    return prenom;
  }
  public String getTelephone(){
    return telephone;
  }
  public int getPromo(){
    return promo;
  }
  public String getUpdate(){
    return "nom = '" + nom + "', prenom = '" + prenom + "', telephone = '" + telephone + "', promotion = '" + promo;
  }
  
  public void setNom(String n){
    nom = n;
  }
  public void setPrenom(String p){
    prenom = p;
  }
  public void setTelephone(String t){
    telephone = t;
  }
  public void setPromo(int p){
    promo = p;
  }
  
  //------------------------------------------------------------------------------------------------------------------------
  // UTILITAIRE ------------------------------------------------------------------------------------------------------------
  // -----------------------------------------------------------------------------------------------------------------------
  
  public boolean supprimer(){
    return dae.supprimer(this);
  }
  public static boolean supprimer (int id){
    return dae.supprimer(id);
  }
  public boolean update(){
    return dae.update(this);
  }
  public static Etudiant chercher(int id){
    return dae.chercher(id);
  }
  public static int getIDMax(){
    return dae.getIDMax();
  }
  public static Set<Integer> ids(){
    return dae.ids();
  } 
  
  //------------------------------------------------------------------------------------------------------------------------
  // EVENEMENTS ------------------------------------------------------------------------------------------------------------
  // -----------------------------------------------------------------------------------------------------------------------
  
//  Evenement organiserEvenement(String nom, String lieu, int places, Date debut, Time duree, String description){
//    return new Evenement(nom, lieu, places, debut, duree, description, this);
//  }
//  
//  Evenement organiserEvenementDansAsso(Association asso, String nom, String lieu, int places, Date debut, Time duree, String description)
//                                          throws NotInAssociationException{
//    if (!asso.estDansAssociation(this)) throw new NotInAssociationException(this.toString() + asso);
//    return new Evenement(nom, lieu, places, debut, duree, description, asso);
//  }
  
   public String toString(){
     return "Étudiant n°" + IDENTIFIANT + ", " + prenom + " " + nom + ", " + telephone + ", P" + promo;
   }
   
   public boolean equals (Object o){
     if (!(o instanceof Etudiant)) return false;
     
     Etudiant e = (Etudiant) o;
     return e.getID() == IDENTIFIANT;
   }

}
