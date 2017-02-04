package bdd_communication;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import utilisateur.Etudiant;
import utilitaire.*;

public abstract class DAO<T extends Identifiable> {
  protected Connection connexion = null;
  protected String table;
  
  public DAO(String t, Connection c){
    connexion = c;
    table = t;
  }
  
  public int getNewID(){
    int id = -1, idMax = 0;
    synchronized(DBModification.getInstance()){
      try{
        
        idMax = getIDMax();

        connexion.prepareStatement("INSERT INTO " + table + " VALUES ()").executeUpdate();

        ResultSet r = connexion.prepareStatement("SELECT id FROM " + table + " WHERE id > " + idMax).executeQuery();
        
        if (r.next()) id = r.getInt("id");
        
      } catch (SQLException ex){
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
      }
    }
    return id;
  }
  public int getIDMax(){
    int idMax = 0;
    try{
      ResultSet r = connexion.prepareStatement("SELECT MAX(id) FROM " + table).executeQuery();
      if (r.next()) idMax = r.getInt("MAX(id)");
    } catch (SQLException ex){
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
    return idMax;
  }
  
  public boolean supprimer(T obj) {
    boolean reussi = false;
    synchronized(DBModification.getInstance()){
      try{
        connexion.prepareStatement("DELETE FROM " + table + " WHERE id = " + obj.getID()).executeUpdate();
        reussi = true;
      } catch (SQLException ex){
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
      }
      
    }
    return reussi;
  }
  public boolean supprimer(int id) {
    boolean reussi = false;
    synchronized(DBModification.getInstance()){
      try{
        connexion.prepareStatement("DELETE FROM " + table + " WHERE id = " + id).executeUpdate();
        reussi = true;
      } catch (SQLException ex){
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
      
      }
      
    }
    return reussi;
  }
  public abstract boolean update(T obj);
  public abstract T chercher(int id);
  
}
