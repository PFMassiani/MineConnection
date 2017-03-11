package bdd_communication;

import java.sql.*;

public class Connexion {
  //On utilise ici le pattern Singleton pour s'assurer de n'avoir qu'une seule connexion à la BDD
  
  private static String url = "jdbc:mysql://localhost/mine_connection?useSSL=false";
  private static String user = "pierre-francois";
  private static String password = "pfm";
  
  private static Connection connexion;
  
  
  private Connexion(){
    System.out.println("Connexion à la base de données...");
    System.out.println("Connexion établie !");

    try{      
      connexion = DriverManager.getConnection(url,user,password);
    } catch (SQLException e){
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("SQLState: " + e.getSQLState());
      System.out.println("VendorError: " + e.getErrorCode());
      System.err.println("Échec de la connexion !");

    }
  }
  
  public static synchronized Connection getConnection(){
    if (connexion == null) new Connexion();
    else System.out.println("Connexion récupérée !");
    return connexion;
  }
}
