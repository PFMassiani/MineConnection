package interaction;

import bdd_communication.DAO;

class DAEvtParticulier extends DAO<Evenement> {

  Evenement evenement;
 //-----------------------------------------------------------------------------------------------------------------------
 // CONSTRUCTEURS ---------------------------------------------------------------------------------------------------------
 // -----------------------------------------------------------------------------------------------------------------------
  
  public DAEvtParticulier(Evenement evt) {
    super("evt_" + evt.getID());
    evenement = evt;
    setChampPrimaire("id_etudiant");
  }

  @Override
  public Evenement chercher(int id) {
    // TODO Auto-generated method stub
    return null;
  }
  
  public boolean update(){
    return update(evenement);
  }

}
