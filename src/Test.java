import exception.*;
import java.util.*;
import java.sql.Date;

import utilisateur.*;
import interaction.*;

public class Test {
  public static void main(String[] args0) throws DuplicateIdentifierException{
//    Evenement e = Evenement.chercher(34);
//    Set<Etudiant> ets = e.participants();
//    Set<Etudiant> ets2 = e.participants();
//    Set<Etudiant> ets3 = new HashSet<>(ets);
//    for (Etudiant et : ets) {
//      System.out.println(et + " participe à l'événement: " + ets.contains(et));
//      System.out.println(et + " participe à l'événement: " + ets2.contains(et));
//      System.out.println(et + " participe à l'événement: " + ets3.contains(et));
//    }
//    
//    System.out.println(ets);
//    System.out.println(ets2);
//    System.out.println(ets3);
    
    viderEvenement();
    evenementParticipants();
    System.out.println("Done!");
  
  }
  
  public static void remplirEtudiant(){
    String nom[] = {"Massiani", "Martinelli","Legrand","Li"};
    String prenom[] = {"Pierre-François", "Giacomo","Domitille","Nicolas"};
    String numeros[] = {"01","02","03","04"};
    int promo = 16;
    
    for (int i = 0; i < 4; i++){
     new Etudiant(nom[i],prenom[i],numeros[i],promo); 
    }
    System.out.println("Table remplie !");
  }
  
  public static void viderEtudiant(){
    for (int i = 1; i <= Etudiant.getIDMax(); i++){
      Etudiant.supprimer(i);
    }
    System.out.println("Table vidée !");
  }
  
  public static void afficherEtudiant(){
    Set<Integer> ids = Etudiant.ids();
    for (int i : ids){
      System.out.println(Etudiant.chercher(i));
    }
  }
  
  public static void remplirEvenement(){
    String[] nom = {"Soirée Minception", "Amphi retap"};
    String[] description = {"La soirée de la meilleure liste", "Test"};
    int[] places = {10,20};
    java.sql.Date[] date = {new java.sql.Date((new java.util.Date()).getTime()), new java.sql.Date(1000000000)};
    int[] debutH = {1,2}, debutM = {30,40}, duree = {3,4};
    Etudiant[] e = {Etudiant.chercher(75), Etudiant.chercher(76)};
    
    for (int i = 0; i < 2; i++) new Evenement(nom[i],description[i], places[i], date[i], debutH[i], debutM[i],duree[i],e[i]);
  }
  
  public static void viderEvenement(){
    Set<Integer> id = Evenement.ids();
    Iterator<Integer> it = id.iterator();
    while (it.hasNext()) Evenement.chercher(it.next()).supprimer();
  }
  
  public static Evenement evenementParticipants(){
    Evenement evt = new Evenement("Événement test", "Test", 10, new java.sql.Date((new java.util.Date()).getTime()), 10,20,30,Etudiant.chercher(71));
    Set<Integer> ids = Etudiant.ids();
    for (int i : ids) evt.ajouter(Etudiant.chercher(i));
    return evt;
  }
  
  public static void testerAntecedence(){
    Set<Integer> ids = Evenement.ids();
    Evenement e,f;
    for (int i : ids){
      for (int j : ids){
        e = Evenement.chercher(i);
        f = Evenement.chercher(j);
        System.out.println(e + " est avant " + f + " : " + e.estAvant(f));
      }
    }
  }
  
  public static void testerCompatibilite(){
    Set<Integer> ids = Evenement.ids();
    Evenement e,f;
    for (int i : ids){
      for (int j : ids){
        e = Evenement.chercher(i);
        f = Evenement.chercher(j);
        System.out.println(e + " est compatible avec " + f + " : " + e.estCompatibleAvec(f));
      }
    }
  }
}
