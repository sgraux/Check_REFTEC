package Model;

import Data.Data;

import java.util.ArrayList;
import java.util.Iterator;

public class Station {

    private String nom;
    private ArrayList<EquipementSI> listeEquipementSI;
    private final Data data = new Data();

    public Station(String parNom){
        this.nom = parNom;
        listeEquipementSI = new ArrayList<EquipementSI>();
    }

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
}
