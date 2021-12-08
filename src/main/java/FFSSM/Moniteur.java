/**
 * @(#) Moniteur.java
 */
package FFSSM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Moniteur extends Plongeur {

    public int numeroDiplome;
    private ArrayList<Embauche> emploie;
    private Club presidentClub;
    private ArrayList<Plongee> plongees;

    public Moniteur(String numeroINSEE, String nom, String prenom, String adresse, String telephone, LocalDate naissance, int numeroDiplome) {
        super(numeroINSEE, nom, prenom, adresse, telephone, naissance);
        this.numeroDiplome = numeroDiplome;
        this.emploie = new ArrayList<>();
        this.plongees = new ArrayList<>();
    }

    /**
     * Si ce moniteur n'a pas d'embauche, ou si sa dernière embauche est terminée,
     * ce moniteur n'a pas d'employeur.
     * @return l'employeur actuel de ce moniteur sous la forme d'un Optional
     */
    public Optional<Club> employeurActuel() {
         // DONE: Implémenter cette méthode
        if (emploie.isEmpty() || emploie.get(emploie.size()-1).estTerminee()){
            return Optional.empty();
        }else{
            return Optional.ofNullable(emploie.get(emploie.size()-1).getEmployeur()) ;
        }
    }
    
    /**
     * Enregistrer une nouvelle embauche pour cet employeur
     * @param employeur le club employeur
     * @param debutNouvelle la date de début de l'embauche
     */
    public void nouvelleEmploie(Club employeur, LocalDate debutNouvelle) {   
         // DONE: Implémenter cette méthode
        emploie.add(new Embauche(debutNouvelle, this, employeur));

    }

    public List<Embauche> emplois() {
        return emploie;
    }

    public void terminerEmbauche(LocalDate fin) throws Exception {

        //TODO Verifier que la date de fin est apres celle de debut
        if (!emploie.isEmpty()){
            emploie.get(emploie.size()-1).terminer(fin);
        }else{
            throw new Exception("Le moniteur n'a pas d'embauche");
        }
    }

}