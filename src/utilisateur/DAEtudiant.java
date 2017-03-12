package utilisateur;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import bdd_communication.DAO;

class DAEtudiant extends DAO<Etudiant> {

  // -----------------------------------------------------------------------------------------------------------------------
  // CONSTRUCTEURS ---------------------------------------------------------------------------------------------------------
  // -----------------------------------------------------------------------------------------------------------------------

  public DAEtudiant() {
    super("Etudiant");
  }

  // -----------------------------------------------------------------------------------------------------------------------
  // METHODES --------------------------------------------------------------------------------------------------------------
  // -----------------------------------------------------------------------------------------------------------------------
  
  @Override
  public Etudiant chercher(int id) {
    Etudiant etudiant = null;
    
    try{
      ResultSet r = connexion.prepareStatement("SELECT * FROM Etudiant WHERE id = " + id).executeQuery();
      if (!r.next()) System.out.println("Aucun résultat");
      else etudiant = new Etudiant(r);
      
    } catch (SQLException ex){
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
    return etudiant;
  }
  
  @Override
  public Set<Etudiant> getAll(){
	  Set<Etudiant> all = new HashSet<>();
	  try {
		  ResultSet r = connexion.prepareStatement("SELECT * FROM Etudiant").executeQuery();
		  while(r.next()) all.add(new Etudiant(r));
	  } catch (SQLException ex) {
		  System.out.println("SQLException: " + ex.getMessage());
	      System.out.println("SQLState: " + ex.getSQLState());
	      System.out.println("VendorError: " + ex.getErrorCode());
	  }
	  return all;
  }

}