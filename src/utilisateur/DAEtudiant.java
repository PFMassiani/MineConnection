package utilisateur;

import java.sql.*;
import bdd_communication.ConnexionEtudiant;
import bdd_communication.DAO;
import bdd_communication.DBModification;
import exception.*;

public class DAEtudiant extends DAO<Etudiant> {

  public DAEtudiant() {
    super("Etudiant",ConnexionEtudiant.getConnection());
  }

//  public int getNewID(){
//    int id = -1, idMax = 0;
//    synchronized(DBModification.getInstance()){
//      try{
//        
//        idMax = getIDMax();
//
//        connexion.prepareStatement("INSERT INTO Etudiant VALUES ()").executeUpdate();
//
//        ResultSet r = connexion.prepareStatement("SELECT id FROM Etudiant WHERE id > " + idMax).executeQuery();
//        
//        if (r.next()) id = r.getInt("id");
//        
//      } catch (SQLException ex){
//        System.out.println("SQLException: " + ex.getMessage());
//        System.out.println("SQLState: " + ex.getSQLState());
//        System.out.println("VendorError: " + ex.getErrorCode());
//      }
//    }
//    return id;
//  }
//  public int getIDMax(){
//    int idMax = 0;
//    try{
//      ResultSet r = connexion.prepareStatement("SELECT MAX(id) FROM Etudiant").executeQuery();
//      if (r.next()) idMax = r.getInt("MAX(id)");
//    } catch (SQLException ex){
//      System.out.println("SQLException: " + ex.getMessage());
//      System.out.println("SQLState: " + ex.getSQLState());
//      System.out.println("VendorError: " + ex.getErrorCode());
//    }
//    return idMax;
//  }
//  
//  @Override
//  public boolean sauvegarder(Etudiant e) {
//    // TODO Auto-generated method stub
//    return false;
//  }
//
//  @Override
//  public boolean supprimer(Etudiant e) {
//    boolean reussi = false;
//    synchronized(DBModification.getInstance()){
//      try{
//        connexion.prepareStatement("DELETE FROM Etudiant WHERE id = " + e.getID()).executeUpdate();
//        reussi = true;
//      } catch (SQLException ex){
//        System.out.println("SQLException: " + ex.getMessage());
//        System.out.println("SQLState: " + ex.getSQLState());
//        System.out.println("VendorError: " + ex.getErrorCode());
//      
//      }
//      
//    }
//    return reussi;
//  }
//  
//  @Override
//  public boolean supprimer(int id) {
//    boolean reussi = false;
//    synchronized(DBModification.getInstance()){
//      try{
//        connexion.prepareStatement("DELETE FROM Etudiant WHERE id = " + id).executeUpdate();
//        reussi = true;
//      } catch (SQLException ex){
//        System.out.println("SQLException: " + ex.getMessage());
//        System.out.println("SQLState: " + ex.getSQLState());
//        System.out.println("VendorError: " + ex.getErrorCode());
//      
//      }
//      
//    }
//    return reussi;
//  }

  @Override
  public boolean update(Etudiant e) {
    boolean reussi = false;
    synchronized(DBModification.getInstance()){
      try{
        ResultSet r = connexion.createStatement().executeQuery("SELECT * FROM Etudiant WHERE id = " + e.getID());
        
        if (r.next()){
          String query = "UPDATE Etudiant SET nom = '" + e.getNom() + "', prenom = '" + e.getPrenom() + "', telephone = '" 
              + e.getTelephone() + "', promotion = '" + e.getPromo() + "' WHERE id = " + e.getID() ;
          connexion.prepareStatement(query).executeUpdate();
          
          reussi = true;
        }
        
      } catch (SQLException ex){
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
      }
    }
    return reussi;
  }

  @Override
  public Etudiant chercher(int id) {
    Etudiant etudiant = null;
    
    try{
      ResultSet r = connexion.prepareStatement("SELECT * FROM Etudiant WHERE id = " + id).executeQuery();
      
      if (r.next()) etudiant = new Etudiant(id,r.getString("nom"), r.getString("prenom")
                                                 , r.getString("telephone"), r.getInt("promotion"));
    } catch (SQLException ex){
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
    return etudiant;
  }

}