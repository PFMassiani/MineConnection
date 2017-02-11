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
  
  @Override
  public int getNewID(){
    int id = super.getNewID();
    synchronized (DBModification.getInstance()){
      try{
        if (id != -1){
          String stmt = "CREATE TABLE IF NOT EXISTS evt_" + id + " (" +
              "ordre_adhesion INT UNSIGNED NOT NULL, " +
              "id_etudiant INT UNSIGNED NOT NULL, " +
              "date_adhesion DATE NOT NULL, " +
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

  public Set<Etudiant> updateEvenement(Evenement evt){
    Set<Etudiant> etudiantsRetires = new HashSet<Etudiant>();
    synchronized(DBModification.getInstance()){
      try{
        String requestSelection = "SELECT id_etudiant " +
            "FROM " + evt.getTable() + 
            " WHERE liste_principale = TRUE " +
            "ORDER BY id_etudiant DESC";
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
            etudiantsRetires.add(new Etudiant(r));
            
            stmtMAJ.setInt(1,id);
            if (stmtMAJ.executeUpdate() == 1) evt.placesRestantes++;
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
  
  public boolean ajouterParticipant(Evenement evt, Etudiant e){
    boolean reussi = false;
    synchronized(DBModification.getInstance()){
      try{
        ResultSet r = connexion.prepareStatement("SELECT places_restantes FROM Evenement WHERE id = " + evt.getID()).executeQuery();
        if (r.next()){
          if (r.getInt("places_restantes") > 0 && !evt.inscrits().contains(e)){
            if (connexion.prepareStatement("INSERT INTO " + evt.getTable() + " VALUES " +
                "(id_etudiant = " + e.getID() + 
                ", date_adhesion = " + new Date((new java.util.Date()).getTime()) + 
                ", liste_principale = TRUE)")
                .executeUpdate() == 1 )
              reussi = true;
          }
        }
      } catch (SQLException ex){
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
      }
      return reussi;
    }
  }
}
