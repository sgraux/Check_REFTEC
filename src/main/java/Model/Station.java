package Model;

import Data.Data;

import java.util.ArrayList;
import java.util.Iterator;

public class Station implements Comparable<Station>{ //Décris une station

    private String nom;
    private ArrayList<EquipementSI> listeEquipementSI;
    private final Data data = new Data();

    public Station(String parNom){
        this.nom = parNom;
        listeEquipementSI = new ArrayList<EquipementSI>();
    }

    //Gère les équipements
    public void manageBM(String parBM, String parStatut){
        if(data.estDansBM(parBM)) {
            boolean found = false;
            for (EquipementSI e : listeEquipementSI) {
                if (e.getCodeBM().equals(parBM)) {
                    e.manageStatut(parStatut);
                    found = true;
                }
            }

            if (!found) {
                EquipementSI e = new EquipementSI(parBM);
                e.manageStatut(parStatut);
                listeEquipementSI.add(e);
            }
        }

    }


    //Getters et Setters
    public int[] getSommeEtapeEquip(){ //Retourne un tableau de taille 6 contenant la somme du nombre d'équipement pour chaque étape
        int[] tabSommeEtapeEquip = new int[6];
        Iterator<EquipementSI> iterator = listeEquipementSI.iterator();
        EquipementSI tempEquip;

        for(int i = 0; i < 6; i++){
            tabSommeEtapeEquip[i] = 0;
        }

        while(iterator.hasNext()){
            tempEquip = iterator.next();
            tabSommeEtapeEquip[0] += tempEquip.getNbNonApplicable();
            tabSommeEtapeEquip[1] += tempEquip.getNbE0();
            tabSommeEtapeEquip[2] += tempEquip.getNbE1();
            tabSommeEtapeEquip[3] += tempEquip.getNbE2();
            tabSommeEtapeEquip[4] += tempEquip.getNbE3();
            tabSommeEtapeEquip[5] += tempEquip.getNbValide();
        }

        return tabSommeEtapeEquip;
    }

    public int[] getEtapeCodeBM(String parCodeBM){
        int[] res = {0, 0, 0, 0, 0, 0};
        for(EquipementSI equip : listeEquipementSI){
            if(equip.getCodeBM().equals(parCodeBM)) return equip.getTabEtape();
        }
        return res;
    }

    public int[] getEtapeBM(String familleBM){
        int[] res = new int[6];
        int[] temp;

        for(int i = 0; i < 6; i++) res[i] = 0;

        if(familleBM.equals("Armoires Fortes")) res = this.getEtapeCodeBM("ARFO");

        else if(familleBM.equals("Centrales d'alarmes")){
            for(int i = 0; i < data.getCentrales().length; i++){
                temp = this.getEtapeCodeBM(data.getCentrales()[i]);
                for(int j = 0; j < temp.length; j ++){
                    res[j] += temp[j];
                }
            }
        }
        else if(familleBM.equals("Telesonorisation")){
            for(int i = 0; i < data.getTelesono().length; i++){
                temp = this.getEtapeCodeBM(data.getTelesono()[i]);
                for(int j = 0; j < temp.length; j ++){
                    res[j] += temp[j];
                }
            }
        }
        else if(familleBM.equals("Caméras")){
            for(int i = 0; i < data.getVideo().length; i++){
                temp = this.getEtapeCodeBM(data.getVideo()[i]);
                for(int j = 0; j < temp.length; j ++){
                    res[j] += temp[j];
                }
            }
        }
        else if(familleBM.equals("Sonorisation")){
            for(int i = 0; i < data.getSon().length; i++){
                temp = this.getEtapeCodeBM(data.getSon()[i]);
                for(int j = 0; j < temp.length; j ++){
                    res[j] += temp[j];
                }
            }
        }

        else if(familleBM.equals("Interphones")){
            for(int i = 0; i < data.getPhones().length; i++){
                temp = this.getEtapeCodeBM(data.getPhones()[i]);
                for(int j = 0; j < temp.length; j ++){
                    res[j] += temp[j];
                }
            }
        }

        else if(familleBM.equals("Superviseur")){
            for(int i = 0; i < data.getSuperviseur().length; i++){
                temp = this.getEtapeCodeBM(data.getSuperviseur()[i]);
                for(int j = 0; j < temp.length; j ++){
                    res[j] += temp[j];
                }
            }
        }

        else if(familleBM.equals("Commande à distance escalier mécanique et trottoir roulant")){
            for(int i = 0; i < data.getEscalierMecaniqueEtTrottoir().length; i++){
                temp = this.getEtapeCodeBM(data.getEscalierMecaniqueEtTrottoir()[i]);
                for(int j = 0; j < temp.length; j ++){
                    res[j] += temp[j];
                }
            }
        }

        else if(familleBM.equals("Commande à distance ascenseur")){
            for(int i = 0; i < data.getAscenseur().length; i++){
                temp = this.getEtapeCodeBM(data.getAscenseur()[i]);
                for(int j = 0; j < temp.length; j ++){
                    res[j] += temp[j];
                }
            }
        }

        else if(familleBM.equals("Commande à distance grilles et fermeture automatique")){
            for(int i = 0; i < data.getGrillesEtFermeture().length; i++){
                temp = this.getEtapeCodeBM(data.getGrillesEtFermeture()[i]);
                for(int j = 0; j < temp.length; j ++){
                    res[j] += temp[j];
                }
            }
        }

        return res;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<EquipementSI> getListeEquipementSI() {
        return listeEquipementSI;
    }

    public void setListeEquipementSI(ArrayList<EquipementSI> listeEquipementSI) {
        this.listeEquipementSI = listeEquipementSI;
    }

    public String toString(){
        int[] temp = getSommeEtapeEquip();
        return nom + "| " + temp[0] + " | " + temp[1] + " | " + temp[2] + " | " + temp[3] + " | " + temp[4] + " |" + temp[5] + " |";
    }

    public int compareTo(Station o) {
        return this.nom.compareTo(o.getNom());
    }
}
