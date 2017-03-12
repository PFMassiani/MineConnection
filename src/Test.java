import exception.*;
import java.util.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;

import utilisateur.*;
import interaction.*;
import serveur_communication.*;

@SuppressWarnings("unused")
public class Test {
	private static int port = 10000;
	public static void main(String[] args0) throws DuplicateIdentifierException{
		viderEvenement();

		testServeur();
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
		Set<Integer> ids = Etudiant.ids();
		try {
			for (int i : ids){
				Etudiant.supprimer(i);
			}
			System.out.println("Table vidée !");
		} catch (MissingObjectException e) {
			System.out.println(e.getMessage());
		}
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
		try {
			while (it.hasNext()) Evenement.chercher(it.next()).supprimer();
		} catch (MissingObjectException e) {
			System.out.println(e.getMessage());
		}
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

	public static void testServeur() {
		
		Communication com;
		Etudiant et;
		Set<Etudiant> allStudents;
		Set<Integer> allIDs;
		int id = 0;
		try {
			Socket serv = new Socket ("localhost", port);
			ObjectOutputStream oos = new ObjectOutputStream(serv.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(serv.getInputStream());
			// TEST SUPPRESSION EVENEMENT
			// Remplissage en événements
//			remplirEvenement();
//			Evenement e = Evenement.getRandomEvent();
//			id = e.getID();
//			Communication com = new Communication (TypeBackupable.EVENEMENT, Action.SUPPRIMER_OBJ, e);
//			ObjectOutputStream oos = new ObjectOutputStream(serv.getOutputStream());
//			oos.writeObject(com);
//
//			e = Evenement.getRandomEvent();
//			com = new Communication (TypeBackupable.EVENEMENT, Action.SUPPRIMER_ID, e.getID());
//			oos.writeObject(com);
			
			
			// TEST SUPPRESSION ETUDIANT
			
			viderEvenement();
			viderEtudiant();
			remplirEtudiant();
			
//			com = new Communication (TypeBackupable.ETUDIANT,Action.CHARGER,Etudiant.getRandomStudent().getID());
//			oos.writeObject(com);
//			et = (Etudiant) ois.readObject();
//			System.out.println("Étudiant chargé: " + et);
			
//			et = Etudiant.getRandomStudent();
//			com = new Communication (TypeBackupable.ETUDIANT, Action.SUPPRIMER, et);
//			oos.writeObject(com);
			
//			et.setNom("Nom changé");
//			com = new Communication(TypeBackupable.ETUDIANT,Action.SAUVEGARDER,et);
//			oos.writeObject(com);
			
//			et = Etudiant.getRandomStudent();
//			com = new Communication (TypeBackupable.ETUDIANT, Action.SUPPRIMER, et.getID());
//			oos.writeObject(com);
			
			com = new Communication(TypeBackupable.ETUDIANT, Action.GET_ALL,null);
			oos.writeObject(com);
			allStudents = (Set<Etudiant>) ois.readObject();
			
			com = new Communication(TypeBackupable.ETUDIANT, Action.GET_IDS,null);
			oos.writeObject(com);
			allIDs = (Set<Integer>) ois.readObject();

			System.out.println(allStudents + "");
			System.out.println(allIDs + "");
			
			serv.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidParameterException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
