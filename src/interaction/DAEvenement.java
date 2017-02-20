package interaction;

import bdd_communication.DAO;
import bdd_communication.DBModification;
import utilisateur.Etudiant;

import java.sql.*;
import java.util.Set;
import java.util.HashSet;

public class DAEvenement extends DAO<Evenement> {
  
 //------------------------------------------------------------------------------------------------------------------------
 // CONSTRUCTEURS ---------------------------------------------------------------------------------------------------------
 // -----------------------------------------------------------------------------------------------------------------------
 
  public DAEvenement() {
    super("Evenement");
  }
  
 //-----------------------------------------------------------------------------------------------------------------------
 // METHODES --------------------------------------------------------------------------------------------------------------
 // -----------------------------------------------------------------------------------------------------------------------
  
  // Réécriture de la fonction DAO<Evenement>.getNewID() pour qu'elle créée en plus une table du nom evt_[id événement]
  @Override
  public int getNewID(){
    int id = super.getNewID();
    synchronized (DBModification.getInstance()){
      try{
        if (id != -1){
          String stmt = "CREATE TABLE IF NOT EXISTS evt_" + id + " (" +
              "ordre_adhesion INT UNSIGNED AUTO_INCREMENT NOT NULL, " +
              "id_etudiant INT UNSIGNED NOT NULL, " +
              "date_adhesion DATE NoT NULL, " +
              "liste_principale BOOLEAN NOT NULL, " +
              "PRIMARY KEY(ordre_adhesion)," +
              "CONSTRAINT fk_id_etudiant_" + id + " FOREIGN KEY (id_etudiant) REFERENCES Etudiant(id)" +
              ") " +
              "ENGINE = INNODB";
          connexion.prepareStatement(stmt).executeUpdate();
        }
        else System.err.println("L'attribution d'un nouvel ID a échoué");

      } catch (SQLException ex){
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
      }
    }
    return id;
  }
  
  // Réécriture de la fonction DAO<Evenement>.supprimer(Evenement) pour supprimer la table evt_[id événement]
  @Override
  public boolean supprimer(Evenement evt){
    boolean reussi = super.supprimer(evt);
    synchronized(DBModification.getInstance()){
      try{
        if (connexion.prepareStatement("DROP TABLE evt_" + evt.getID()).executeUpdate() == 1) reussi = true; 
      } catch (SQLException ex){
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
      }
    }
    return reussi;
  }
  
  // Idem pour DAO<Evenement>.supprimer(int)
  @Override
  public boolean supprimer(int id){
    boolean reussi = super.supprimer(id);
    synchronized(DBModification.getInstance()){
      try{
        if (connexion.prepareStatement("DROP TABLE evt_" + id).executeUpdate() == 1) reussi = true; 
      } catch (SQLException ex){
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
      }
    }
    return reussi;
  }
  
  @Override
  public Evenement chercher(int id) {
    Evenement evt = null;
    
    try{
      ResultSet r = connexion.prepareStatement("SELECT * FROM Evenement WHERE id = " + id).executeQuery();
      r.next();
      evt = new Evenement(r);
      
    } catch (SQLException ex){
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
    return evt;
  }

  // Fonction appelée lorsque le nombre de places change et qu'il faut retirer des étudiants de liste principale. Renvoie l'ensemble
  // des étudiants retirés
  public Set<Etudiant> updateEvenement(Evenement evt){
    Set<Etudiant> etudiantsRetires = new HashSet<Etudiant>();
    synchronized(DBModification.getInstance()){
      try{
        String requestSelection = "SELECT id_etudiant " +
            "FROM " + evt.getTable() + 
            " WHERE liste_principale = TRUE " +
            "ORDER BY ordre_adhesion DESC";
        String requestMAJ = "ALTER TABLE " + evt.getTable() + " SET liste_principale = FALSE WHERE id_etudiant = ?";
        
        PreparedStatement stmtSelection = connexion.prepareStatement(requestSelection);
        PreparedStatement stmtMAJ = connexion.prepareStatement(requestMAJ);
        
        ResultSet r = null;
        
        int id = 0;
        while (evt.placesRestantes < 0){
          r = stmtSelection.executeQuery();
          if (r.next()){
            //Forcément vérifié, car evt.placesRestantes < 0, donc on a au moins une personne
            //   sur liste principale car placesRestantes = places - taille liste principale et places >= 0
            id = r.getInt("id_etudiant");
            
            stmtMAJ.setInt(1,id);
            if (stmtMAJ.executeUpdate() == 1) {
              etudiantsRetires.add(new Etudiant(r));
              evt.placesRestantes++;
            }
          }
        }
      } catch (SQLException ex){
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
      }
    }
    return etudiantsRetires;
  }
  
  // Renvoie les participants sur liste principale
  public Set<Etudiant> participants(Evenement evt){
    Set<Etudiant> participants = new HashSet<>();

    try{
      String query = "SELECT * FROM Etudiant WHERE id IN " +
      		"( SELECT id_etudiant FROM " + evt.getTable() + " WHERE liste_principale = TRUE )";
      ResultSet r = connexion.prepareStatement(query).executeQuery();

      while (r.next()) participants.add(new Etudiant(r));
      
    } catch (SQLException ex){
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }

    return participants;
  }
  
  // Renvoie les étudiants sur liste principale ou sur liste d'attente
  public Set<Etudiant> inscrits(Evenement evt){
    Set<Etudiant> inscrits = new HashSet<>();

    try{
      String query = "SELECT * FROM Etudiant WHERE id IN " +
          "( SELECT id_etudiant FROM " + evt.getTable() +" )";
      ResultSet r = connexion.prepareStatement(query).executeQuery();

      while (r.next()) inscrits.add(new Etudiant(r));
      
    } catch (SQLException ex){
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }

    return inscrits;
  }
  
  // Ajoute e sur la liste principale de evt. Renvoie false s'il n'y a pas assez de places
  public boolean ajouterPrincipale(Evenement evt, Etudiant e){
    boolean reussi = false;
    synchronized(DBModification.getInstance()){
      try{
        if (evt.getPlacesRestantes() > 0 && !evt.inscrits().contains(e)){
          java.sql.Date maintenant = new java.sql.Date((new java.util.Date()).getTime());
          String query = "INSERT INTO " + evt.getTable() + " (id_etudiant,date_adhesion,liste_principale) VALUES " +
              "( " + e.getID() + 
              ", '" + maintenant +
              "', TRUE)";
          // + 
          if (connexion.prepareStatement(query).executeUpdate() == 1 )
            reussi = true;
        }
      } catch (SQLException ex){
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
      }
      return reussi;
    }
  }
  
  // Ajoute e sur la liste d'attente de evt. 
  public boolean ajouterAttente(Evenement evt, Etudiant e){
    boolean reussi = false;
    synchronized(DBModification.getInstance()){
      try{
        java.sql.Date maintenant = new java.sql.Date((new java.util.Date()).getTime());
        String query = "INSERT INTO " + evt.getTable() + " (id_etudiant,date_adhesion,liste_principale) VALUES " +
            "( " + e.getID() + 
            ", '" + maintenant +
            "', FALSE)";
        if (connexion.prepareStatement(query).executeUpdate() == 1 )
          reussi = true;

      } catch (SQLException ex){
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
      }
    }
    return reussi;
  }
  
  public int placesRestantes(Evenement evt){
    int restantes = 0;
    try{
      ResultSet r = connexion.prepareStatement("SELECT places_restantes FROM Evenement WHERE id = " + evt. getID()).executeQuery();
      if(r.next()) restantes = r.getInt("places_restantes");
    } catch (SQLException ex){
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
    
    return restantes;
  }
}
