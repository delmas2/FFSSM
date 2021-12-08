package FFSSM;


import java.time.LocalDate;
import java.util.Objects;

public class Embauche {

    private LocalDate debut;

    private LocalDate fin;

    private final Moniteur employe;

    private final Club employeur;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Embauche embauche = (Embauche) o;
        return Objects.equals(debut, embauche.debut) && Objects.equals(fin, embauche.fin) && Objects.equals(employe, embauche.employe) && Objects.equals(employeur, embauche.employeur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(debut, fin, employe, employeur);
    }

    public Embauche(LocalDate debut, Moniteur employe, Club employeur) {
        this.debut = debut;
        this.employe = employe;
        this.employeur = employeur;
    }

    /**
     * Termine cette embauche
     * @param dateFin la date à laquelle cette embauche est terminée
     */
    public void terminer(LocalDate dateFin) throws Exception {
        if (!this.estTerminee()){
            this.fin = dateFin;
        }else{
            throw new Exception("Le moniteur n'a pas d'embauche en cours");
        }
    }
    
    /**
     * Est-ce que cette embauche est terminée ?
     * @return vrai si terminée, faux sinon.
     */
    public boolean estTerminee() {
        return (fin != null);
    }
    /**
     * Get the value of employeur
     *
     * @return the value of employeur
     */
    public Club getEmployeur() {
        return employeur;
    }

    /**
     * Get the value of employe
     *
     * @return the value of employe
     */
    public Moniteur getEmploye() {
        return employe;
    }

    /**
     * Get the value of fin
     *
     * @return the value of fin
     */
    public LocalDate getFin() {
        return fin;
    }

    /**
     * Set the value of fin
     *
     * @param fin new value of fin
     */
    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    /**
     * Get the value of debut
     *
     * @return the value of debut
     */
    public LocalDate getDebut() {
        return debut;
    }

    
}