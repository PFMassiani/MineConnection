package bdd_communication;

public class DBModification {
  
  //Cett classe a uniquement une fonction de verrou: on créée une instance unique d'un objet, obj, qu'on va verrouiller avec synchronized dès
  // qu'une modification est faite sur la BDD. Ainsi, on s'assure que deux Threads ne modifient pas la BDD en même temps.
  private static DBModification obj = new DBModification();
  
  private DBModification(){}
  
  public static DBModification getInstance(){
    if (obj == null) obj = new DBModification();
    return obj;
  }
}
