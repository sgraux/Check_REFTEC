package Model;

import java.util.ArrayList;

public class Ligne {//Décris une ligne

    private String nom; //Nom de la ligne
    private ArrayList<Station> listeStations;//Liste des stations présentent sur la ligne

    public Ligne(String parNom){
        this.nom = parNom;
        listeStations = new ArrayList<Station>();
    }

    //Gère les stations
    public void manageLieu(String parLieu, String parFamilleBM, String parStatut){
        boolean found = false;

        for(Station s : listeStations){//Si la station fait partie de la liste, on lui passe l'équipement à gérer
            if(s.getNom().equals(parLieu)){
                s.manageBM(parFamilleBM, parStatut);
                found = true;
            }
        }

        if(!found){//Sinon, on créer la station et on lui passe l'équipement à gérer
            Station s = new Station(parLieu);
            s.manageBM(parFamilleBM, parStatut);
            listeStations.add(s);
        }

    }

    //ToString avec affichage
    public void display(){
        String toDisplay = " --- " + nom + " --- \nStation | Non Applicable | Etape 0 | Etape 1 | Etape 2 | Etape 3 | Validé |\n";
        for(Station s : listeStations){
            toDisplay += s.toString() + "\n";
        }
        System.out.println(toDisplay);
    }

    //Donne une liste contenant les informations de chaque station de la ligne
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

    //Getters et setters
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
