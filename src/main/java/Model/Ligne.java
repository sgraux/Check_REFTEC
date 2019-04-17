package Model;

import java.util.ArrayList;

public class Ligne {

    private String nom;
    private ArrayList<Station> listeStations;

    public Ligne(String parNom){
        this.nom = parNom;
        listeStations = new ArrayList<Station>();
    }

    public void manageLieu(String parLieu, String parFamilleBM, String parStatut){
        boolean found = false;

        for(Station s : listeStations){
            if(s.getNom().equals(parLieu)){
                s.manageBM(parFamilleBM, parStatut);
                found = true;
            }
        }

        if(!found){
            Station s = new Station(parLieu);
            s.manageBM(parFamilleBM, parStatut);
            listeStations.add(s);
        }

    }

    public void display(){
        String toDisplay = " --- " + nom + " --- \nStation | Non Applicable | Etape 0 | Etape 1 | Etape 2 | Etape 3 | Validé |\n";
        for(Station s : listeStations){
            toDisplay += s.toString() + "\n";
        }
        System.out.println(toDisplay);
    }

    public ArrayList<String[]> retrieve(){
        ArrayList<String[]> list = new ArrayList<String[]>();
        int[] tempSomme = {};
        String[] tempString;
        for(Station s : listeStations){
            tempString = new String[7];
            tempSomme = s.getSommeEtapeEquip();
            tempString[0] = s.getNom();
            tempString[1] = ""+tempSomme[0];
            tempString[2] = ""+tempSomme[1];
            tempString[3] = ""+tempSomme[2];
            tempString[4] = ""+tempSomme[3];
            tempString[5] = ""+tempSomme[4];
            tempString[6] = ""+tempSomme[5];
            list.add(tempString);
        }
        return list;
    }

    public int[] getSommeEtapeStations(){ //retourne un tableau contenant le nombre d'équipements de la ligne pour chaque étape
        int[] tabSommeEtapeStations = new int[6];
        int[] currentSommeEtapeStation;

        for(int i = 0; i < listeStations.size(); i++){
            currentSommeEtapeStation = listeStations.get(i).getSommeEtapeEquip();
            for(int j = 0; j < 6; j++){
                tabSommeEtapeStations[j] += currentSommeEtapeStation[j];
            }
        }
        return tabSommeEtapeStations;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<Station> getListeStations() {
        return listeStations;
    }

    public void setListeStations(ArrayList<Station> listeStations) {
        this.listeStations = listeStations;
    }
}
