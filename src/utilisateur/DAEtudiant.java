package utilisateur;

import java.sql.ResultSet;
import java.sql.SQLException;


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
  public Etudiant charger(ResultSet r) throws SQLException{
	  return new Etudiant(r);
  }

}