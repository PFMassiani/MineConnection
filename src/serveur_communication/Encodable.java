package serveur_communication;

public abstract class Encodable {
  public abstract byte[] encoder();
  // Retourne un tableau de byte construit sous la forme suivante:
  // [type d'objet (0 à 3)][informations nécessaires au constructeur]
  // Où le second crochet est propre à chaque objet
  
  // Différents types d'objet:
  // * 0: Etudiant
  // * 1: Association
  // * 2: Evenement
  // * 3: PAPS
  
  public abstract <T extends Encodable> T decoder();
  
  
}
