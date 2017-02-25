package serveur_communication;

import exception.InvalidParameterException;
import utilisateur.Utilisateur;
import interaction.Interaction;

public class Communication {
  private TypeBackupable type;
  private Action action;
  private Object o;
  private int id;

  public Communication(TypeBackupable type, Action action, Object o) throws InvalidParameterException{
    this.type = type;
    this.action = action;
    this.o = o;
    id = -1;
    switch (action){
    case SAUVEGARDER:
    case SUPPRIMER_OBJ:
      if (o == null || (!(o instanceof Utilisateur) && !(o instanceof Interaction))) throw new InvalidParameterException("L'action demandée requiert un objet de type Utilisateur ou Interaction");
      break;
    default:
      break;
    }
  }

  public Communication(TypeBackupable type, Action action, int id) throws InvalidParameterException{
    this.type = type;
    this.action = action;
    o = null;
    this.id = id;
    switch (action){
    case CHARGER:
    case SUPPRIMER_ID:
      if (id < 0) throw new InvalidParameterException("L'action demandée requiert un identifiant positif ( id = " + id);
      break;
    default:
      break;
    }
  }

  public TypeBackupable getType(){
    return type;
  }
  public Action getAction(){
    return action;
  }
  public int getID(){
    return id;
  }
  public Object getObject(){
    return o;
  }
}
