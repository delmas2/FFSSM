package FFSSM;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import FFSSM.Club;
import FFSSM.Moniteur;
import FFSSM.Plongeur;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ldelma05
 */
public class FFSSMJUnitTest {
   Moniteur Brice;
    Plongeur Paul;
    Plongeur Arnaud;
    Club club;
 
 @BeforeEach
    public void setUp() {

        Brice = new Moniteur("DB8183394", "DeNice", "Brice", "7 Rue de la Libération 81100 Castres", "0782330560", LocalDate.of(2001, 9,27), 54142);
        Paul = new Plongeur("OP5071374","Ortega", "Paul", "A côte de Chicken Times 81100 Castres ", "0748520130", LocalDate.of(2000, 11,20));
        Arnaud = new Plongeur("BA534387","Bonnafous", "Arnaud", "Pareil que le moniteur 81100 Castres ", "0767889425", LocalDate.of(2000, 05,2));
        club = new Club(Brice,"Posseidon", "0254863398");

    }

    
    @Test
    public void testAjouteLicence() throws Exception {

        Licence licence1 = new Licence(Brice, "57AFK", LocalDate.of(2021, 12,2), club);
        Licence licence2 = new Licence(Brice, "BELLA4", LocalDate.of(2021, 6,4), club);
        Licence licence3 = new Licence(Paul, "GUGU4", LocalDate.of(2020, 11,3), club);

        assertThrows(Exception.class, () -> {
            Brice.derniereLicence();
        });

        Brice.ajouteLicence("57AFK", LocalDate.of(2021, 12,2), club);

        assertEquals(Brice.derniereLicence(), licence1);

        Brice.ajouteLicence("BELLA4", LocalDate.of(2021, 6,4), club);

        assertEquals(Brice.derniereLicence(), licence2);
    }


    @Test
    public void testNouvelEmbauche() {
        Embauche emb1 = new Embauche(LocalDate.of(2020, 6,4), Brice, club);
        Embauche emb2 = new Embauche(LocalDate.of(2020, 3,3), Brice, club);



        ArrayList<Embauche> listEmbauches = new ArrayList<>();
        listEmbauches.add(emb1);
        listEmbauches.add(emb2);

        Brice.nouvelleEmploie(club, LocalDate.of(2020, 6,4));
        Brice.nouvelleEmploie(club, LocalDate.of(2020, 3,3));

        assertEquals(listEmbauches, Brice.emplois());
    }

    @Test
    public void testTerminerEmbauche() throws Exception {

        assertThrows(Exception.class, () -> {
            Brice.terminerEmbauche(LocalDate.now());
        });

        Brice.nouvelleEmploie(club, LocalDate.of(2020, 6,4));

        Brice.terminerEmbauche(LocalDate.now());

        assertTrue(Brice.emplois().get(Brice.emplois().size()-1).estTerminee());
    }

    @Test
    public void testEmployeurActuel() throws Exception {

        //TODO regler ça
        assertEquals(Brice.employeurActuel(), Optional.empty());  

        Brice.nouvelleEmploie(club, LocalDate.of(2020, 6,4));

        assertEquals(Brice.employeurActuel(), Optional.of(club));

        Club club2 = new Club(Brice,"Posseidon", "0346798763");

        Brice.nouvelleEmploie(club, LocalDate.of(2020, 6,4));
        Brice.terminerEmbauche(LocalDate.now());
        Brice.nouvelleEmploie(club2, LocalDate.now());

        assertEquals(Brice.employeurActuel(), Optional.of(club2));

        Brice.terminerEmbauche(LocalDate.now());

        assertEquals(Brice.employeurActuel(), Optional.empty()); 

    }

  
    public void testEstTerminee() throws Exception {

        
        Brice.nouvelleEmploie(club, LocalDate.of(2004, 8, 10));
        assertFalse(Brice.emplois().get(Brice.emplois().size()-1).estTerminee());


     
        Brice.terminerEmbauche(LocalDate.of(2014, 8, 10));
        assertTrue(Brice.emplois().get(Brice.emplois().size()-1).estTerminee());



        Club club2 = new Club(Brice,"Posseidon", "0346798763");
        Brice.nouvelleEmploie(club2, LocalDate.of(2020, 5, 8));
        Brice.emplois().get(Brice.emplois().size()-1).terminer(LocalDate.of(2021, 2, 1));

        assertTrue(Brice.emplois().get(Brice.emplois().size()-1).estTerminee());
        assertEquals(Brice.emplois().get(Brice.emplois().size()-1).getFin(),LocalDate.of(2021, 2, 1) );

    }

   

    @Test
    public void testPlongeesNonConformes() throws Exception {

        Plongee plongee1 = new Plongee(new Site("Danlo", "Cmouillé"),Brice, LocalDate.of(2022, 1, 6),25,2);
        Plongee plongee2 = new Plongee(new Site("Soulo", "YaDPoisson"),Brice, LocalDate.of(2022, 1, 6),25,2);
        Plongee plongee3 = new Plongee(new Site("Ocean Aquatique", "CaTrempe"),Brice, LocalDate.of(2022, 1, 6),25,2);

        Brice.ajouteLicence("57AFK", LocalDate.of(2021, 12,2), club);
        Arnaud.ajouteLicence("BELLA4", LocalDate.of(2021, 6,4), club);
        Paul.ajouteLicence("GUGU4", LocalDate.of(2020, 11,3), club); 

        plongee1.ajouteParticipant(Brice);
        plongee1.ajouteParticipant(Arnaud);

        plongee2.ajouteParticipant(Arnaud);
        plongee2.ajouteParticipant(Paul);  

        plongee3.ajouteParticipant(Paul); 
        plongee3.ajouteParticipant(Brice);

        HashSet<Plongee> plongeesNonConformes = new HashSet<>();

        club.organisePlongee(plongee1);

        assertEquals(club.plongeesNonConformes(), plongeesNonConformes);

        plongeesNonConformes.add(plongee2);
        club.organisePlongee(plongee2);

        assertEquals(club.plongeesNonConformes(), plongeesNonConformes);

        plongeesNonConformes.add(plongee3);
        club.organisePlongee(plongee3);

        assertEquals(club.plongeesNonConformes(), plongeesNonConformes);
    }

    
    @Test
    public void testEstConforme() throws Exception {
        Plongee plongee1 = new Plongee(new Site("Danlo", "Cmouillé"),Brice, LocalDate.of(2022, 1, 6),25,2);

        Brice.ajouteLicence("57AFK", LocalDate.of(2021, 12,2), club);
        Arnaud.ajouteLicence("BELLA4", LocalDate.of(2021, 6,4), club);
        Paul.ajouteLicence("GUGU4", LocalDate.of(2020, 11,3), club);

        plongee1.ajouteParticipant(Brice);
        plongee1.ajouteParticipant(Arnaud);

        ArrayList<Licence> listLicences = new ArrayList<>();

        listLicences.add(Brice.derniereLicence());
        listLicences.add(Arnaud.derniereLicence());

        assertTrue(plongee1.estConforme());  

        plongee1.ajouteParticipant(Paul);
        listLicences.add(Paul.derniereLicence());  

        assertFalse(plongee1.estConforme()); 
        assertEquals(plongee1.getPalanquees(), listLicences); 

    }



    @Test
    public void testEstValide() throws Exception {
        Licence l1 = new Licence(Paul, "L33CH33", LocalDate.of(2010, 1,1), club);

        assertTrue(l1.estValide(LocalDate.of(2010, 1,3)));
        assertFalse(l1.estValide(LocalDate.of(2015, 1,1)));
        assertTrue(l1.estValide(LocalDate.of(2010, 1,1)));
    }

}


