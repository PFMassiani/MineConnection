package bdd_communication;

import java.sql.*;

public class ConnexionEtudiant {
  //On utilise ici le pattern Singleton pour s'assurer de n'avoir qu'une seule connexion à la BDD
  
  private static String url = "jdbc:mysql://localhost/mine_connection";
  private static String user = "root";
  private static String password = "pifou";
  
  private static Connection connexion;
  
  
  private ConnexionEtudiant(){
    System.out.println("Connexion à la base de données...");
    try{      
      connexion = DriverManager.getConnection(url,user,password);
    } catch (SQLException e){
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("SQLState: " + e.getSQLState());
      System.out.println("VendorError: " + e.getErrorCode());
    }
    System.out.println("Connexion établie !");
  }
  
  public static synchronized Connection getConnection(){
    if (connexion == null) new ConnexionEtudiant();
    return connexion;
  }
}
