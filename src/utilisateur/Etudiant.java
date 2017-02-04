package utilisateur;

import java.sql.*;
import bdd_communication.*;
import exception.*;

public class Etudiant extends Utilisateur {

  String nom, prenom, telephone;
//  Date naissance;
  int promo;
//  Calendrier calendrier;
  
  //Constructeur utilisé pour créer un nouvel étudiant
  public Etudiant (String n, String p, String t, int pr){
    super((new DAEtudiant()).getNewID());
    
    nom = n;
    prenom = p;
    promo = pr;
    telephone = t;
    
    (new DAEtudiant()).update(this);
    if (IDENTIFIANT == -1){
      throw new InvalidIDException("");
    }
  }
  
  //Constructeur utilisé pour le chargement d'un étudiant depuis la BDD: ce constructeur ne doit être visible que depuis le paquetage, 
  // on ne doit pas mettre n'importe quoi dans le champ "id"!
  Etudiant (int id, String n, String p, String t, int pr){
    super(id);
    nom = n;
    prenom = p;
//    naissance = na;
    promo = pr;
    telephone = t;
    
//    calendrier = new Calendrier(IDENTIFIANT);
  }
  
  public int getID(){
    return IDENTIFIANT;
  }
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
//  
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

}
