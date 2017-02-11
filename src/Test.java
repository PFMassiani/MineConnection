import exception.*;
import java.util.*;
import java.sql.Date;

import utilisateur.*;
import interaction.*;

public class Test {
  public static void main(String[] args0) throws DuplicateIdentifierException{
    remplirEvenement();
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
}
