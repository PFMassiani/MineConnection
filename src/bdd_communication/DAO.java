package bdd_communication;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.HashSet;

import utilitaire.*;

public abstract class DAO<T extends Backupable> {
  
  // -----------------------------------------------------------------------------------------------------------------------
  // ATTRIBUTS -------------------------------------------------------------------------------------------------------------
  // -----------------------------------------------------------------------------------------------------------------------
  
  protected Connection connexion = Connexion.getConnection();
  protected String table, champPrimaire = "id";
  
  // -----------------------------------------------------------------------------------------------------------------------
  // CONSTRUCTEURS ---------------------------------------------------------------------------------------------------------
  // -----------------------------------------------------------------------------------------------------------------------
  
  public DAO(String t){
    table = t;
  }
  
  // -----------------------------------------------------------------------------------------------------------------------
  // METHODES --------------------------------------------------------------------------------------------------------------
  // -----------------------------------------------------------------------------------------------------------------------
  
  public Set<Integer> ids(){
    Set<Integer> ids = new HashSet<Integer>();
    try{
      ResultSet r = connexion.prepareStatement("SELECT " + champPrimaire + " FROM " + table).executeQuery();
      
      while (r.next()) ids.add(r.getInt("id"));
    } catch (SQLException ex) {
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
    
    return ids;
  }
  
  public int getNewID(){
    int id = -1, idMax = 0;
    synchronized(DBModification.getInstance()){
      try{
        
        idMax = getIDMax();

        connexion.prepareStatement("INSERT INTO " + table + " VALUES ()").executeUpdate();

        ResultSet r = connexion.prepareStatement("SELECT " + champPrimaire + " FROM " + table + " WHERE id > " + idMax).executeQuery();
        
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
      ResultSet r = connexion.prepareStatement("SELECT MAX(" + champPrimaire + ") FROM " + table).executeQuery();
      if (r.next()) idMax = r.getInt("MAX(" + champPrimaire + ")");
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
        if( connexion.prepareStatement("DELETE FROM " + table + " WHERE " + champPrimaire + " = " + obj.getID()).executeUpdate() == 1) reussi = true;
        
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
        connexion.prepareStatement("DELETE FROM " + table + " WHERE " + champPrimaire + " = " + id).executeUpdate();
        reussi = true;
      } catch (SQLException ex){
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
      
      }
      
    }
    return reussi;
  }
  public boolean update(T obj) {
    boolean reussi = false;
    synchronized(DBModification.getInstance()){
      try{
        ResultSet r = connexion.createStatement().executeQuery("SELECT * FROM " + table + " WHERE " + champPrimaire + " = " + obj.getID());
        
        if (r.next()){
          String query = "UPDATE " + table + " SET " + obj.getUpdate() + " WHERE " + champPrimaire + " = " + obj.getID() ;
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
  
  public abstract T chercher(int id);  
  
  protected void setChampPrimaire(String s){
    champPrimaire = s;
  }
}
