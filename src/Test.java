import exception.*;
import utilisateur.*;
import bdd_communication.*;

public class Test {
  public static void main(String[] args0) throws DuplicateIdentifierException{
    remplir();
  }
  
  public static void remplir(){
    String nom[] = {"Massiani", "Martinelli","Legrand","Li"};
    String prenom[] = {"Pierre-François", "Giacomo","Domitille","Nicolas"};
    String numeros[] = {"01","02","03","04"};
    int promo = 16;
    
    for (int i = 0; i < 4; i++){
     new Etudiant(nom[i],prenom[i],numeros[i],promo); 
    }
    System.out.println("Table remplie !");
  }
  
  public static void vider(){
    DAEtudiant daE = new DAEtudiant();
    for (int i = 1; i <= daE.getIDMax(); i++){
      daE.supprimer(i);
    }
    System.out.println("Table vidée !");
  }
}
