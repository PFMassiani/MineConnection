package serveur;

import interaction.Evenement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.util.Collections;
import java.util.Set;
import java.io.*;

import serveur_communication.*;
import utilisateur.Etudiant;
import exception.*;

public class ServeurVirtuel extends Thread {
  
  private Socket client;
  private InputStream is;
  private ObjectInputStream ois;
  private OutputStream os;
  private ObjectOutputStream oos;
  
  private boolean fin = false, pause = false;
  
  public ServeurVirtuel(Socket client){
    this.client = client;
    try {
      is = client.getInputStream();
      ois = new ObjectInputStream(is);
      os = client.getOutputStream();
      oos = new ObjectOutputStream(os);
    } catch (IOException e) {
            e.printStackTrace();
    }
  }
  
  public void pause(){
    pause = true;
  }
  public void reprendre(){
    pause = false;
  }
  public void fin(){
    fin = true;
  }
  
  @Override
  public void run(){
    while (!fin){
      if (!pause){
        try{
          Object o = ois.readObject();
          if (!(o instanceof Communication)) throw new InvalidCommunicationException("La communication n'est pas du type Communication");
          Communication com = (Communication) o;
          Action action = com.getAction();
          switch (action){
          case CHARGER:
            envoyerObjet(com.getType(), com.getID());
            break;
          case SAUVEGARDER:
            sauvegarderObjet(com.getObject());
            break;
          case SUPPRIMER_ID:
          case SUPPRIMER_OBJ:
            supprimer(com);
            break;
          case GET_IDS:
            envoyerIDs(com.getType());
            break;
          case GET_ALL:
            envoyerAll(com.getType());
            break;
            default:
              break;
          }
        } catch (ClassNotFoundException e){
          e.printStackTrace();
        } catch (IOException e){
          e.printStackTrace();
        }catch (InvalidCommunicationException e){
          e.printStackTrace();
        }
      }
    }
  }
  
  public void envoyerObjet(TypeBackupable type, int id){
    switch(type){
    case ETUDIANT:
      Etudiant e = Etudiant.chercher(id);
      try{
        oos.writeObject(e);
        oos.flush();
      } catch (IOException ex){
        ex.printStackTrace();
      }
      break;
    case ASSOCIATION:
      // TODO Quand les associations seront implémentées
      break;
    case EVENEMENT:
      Evenement evt = Evenement.chercher(id);
      try{
        oos.writeObject(evt);
        oos.flush();
      } catch(IOException ex){
        ex.printStackTrace();
      }
      break;
    case PAPS:
      // TODO Quand les PAPS seront implémentés
      break;
      default:
        break;
    }
  }
  
  public boolean sauvegarderObjet(Object o){
    boolean reussi = false;
    if (o instanceof Etudiant) reussi = ((Etudiant) o).update();
    else if (o instanceof Evenement) reussi = ((Evenement) o).update();
    
    // TODO Cas où o est une Association ou un PAPS
    
    return reussi;
  }
  
  public boolean supprimer (Communication com){
    boolean reussi = false;

    if (com.getAction() == Action.SUPPRIMER_OBJ){
      Object o = com.getObject();

      // On récupère la classe de o
      Class<?> c = o.getClass();
      try {
        
        // On récupère la méthode supprimer()
        Method m = c.getDeclaredMethod("supprimer", (Class<?>) null);

        // Et si on la trouve, on l'applique
        reussi = (boolean) m.invoke(c.cast(o),(Object) null);
        
      } catch (NoSuchMethodException | 
          IllegalAccessException | 
          IllegalArgumentException | 
          InvocationTargetException e) {
        
        e.printStackTrace();
      }
    }



    else if (com.getAction() == Action.SUPPRIMER_ID){
      String nomClasse = com.getType().getNomClasse();
      try{
        
        Class<?> c = Class.forName(nomClasse);
        Method m = c.getDeclaredMethod("supprimer", (new Integer(0)).getClass());
        reussi = (boolean) m.invoke(null,com.getID());
      
      } catch(ClassNotFoundException | 
          NoSuchMethodException | 
          IllegalAccessException | 
          IllegalArgumentException | 
          InvocationTargetException e){
        
        e.printStackTrace();
      }

    }


    return reussi;
  }
  
  // On supprime cet avertissement, car on vérifie bien l'égalité des types
  @SuppressWarnings("unchecked")
  public void envoyerIDs (TypeBackupable type){
    Class<?> c = null;
    try{
     
      c = Class.forName(type.getNomClasse());
      Method m = c.getDeclaredMethod("ids", (Class<?>) null);
      Set<Integer> ids = Collections.EMPTY_SET;
      
      if (m.getReturnType() == ids.getClass()) ids = (Set<Integer>) m.invoke(null, (Object) null);
      
      // TODO Envoyer les ids
    } catch (ClassNotFoundException | 
        IllegalAccessException | 
        IllegalArgumentException | 
        InvocationTargetException e){
      e.printStackTrace();
    } catch (NoSuchMethodException e){
      System.err.println("Erreur: la classe " + c.getName() + " n'implémente pas la méthode static Set<Integer> supprimer()");
      e.printStackTrace();
    }
  }
  
  public void envoyerAll(TypeBackupable type){
    Class<?> c = null;
    try{
     
      c = Class.forName(type.getNomClasse());
      Method m = c.getDeclaredMethod("getAll", (Class<?>) null);
      Set<?> all = (Set<?>) m.invoke(null, (Object) null);
      
      // TODO Envoyer all
    } catch (ClassNotFoundException | 
        IllegalAccessException | 
        IllegalArgumentException | 
        InvocationTargetException e){
      e.printStackTrace();
    } catch (NoSuchMethodException e){
      System.err.println("Erreur: la classe " + c.getName() + " n'implémente pas la méthode static Set<Integer> supprimer()");
      e.printStackTrace();
    }  }
  
}
