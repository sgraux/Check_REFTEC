package Data;

public class Data { //Classe contenant les données utilisées par l'outil

    private final int numeroColonneLigne = 3;
    private final int getNumeroColonneLieu = 4;
    private final int getNumeroColonneFamille = 8;

    //Liste des codes de famille d'équipement
    private final String[] listeBM = {"ARFO","CEAS", "PEAS", "DEAS","PEAL","ARTS", "MITS", "RTIP", "RTCL", "TSONO","ARVS", "CAVS", "RAVS", "MOVS","SONO", "SONOR", "PUPI", "PUSO", "HPSO","IVOY", "IVDO", "PUIN", "BUIN", "CAIN", "BMIN", "MPIN","MISC", "SCESU","CNI", "FUJ", "KON", "O.K", "OTI", "SCH", "SPEC", "THY", "PALE","A000","A001", "A002", "A003", "A004", "A005", "A007","BL", "BR", "CI", "GC", "GLE", "GR", "GVP", "PB", "PBH", "PTEBV", "PTEC", "PTLBV", "PTLC", "RM", "VR", "VRA", "GS"};

    //Liste des noms de famille d'équipement
    private final String[] listeNomBM = {"Armoires Fortes", "Centrales d'alarmes", "Telesonorisation", "Caméras", "Sonorisation", "Interphones", "Superviseur", "Commande à distance escalier mécanique et trottoir roulant", "Commande à distance ascenseur", "Commande à distance grilles et fermeture automatique"};

    //Listes des codes d'équipement par famille
    //Une liste de code par famille
    private final String[] centrales = {"CEAS", "PEAS", "DEAS","PEAL"};
    private final String[] telesono = {"ARTS", "MITS", "RTIP", "RTCL", "TSONO"};
    private final String[] video = {"ARVS", "CAVS", "RAVS", "MOVS"};
    private final String[] son = {"SONO", "SONOR", "PUPI", "PUSO", "HPSO"};
    private final String[] phones = {"IVOY", "IVDO", "PUIN", "BUIN", "CAIN", "BMIN", "MPIN"};
    private final String[] superviseur = {"MISC", "SCESU"};
    private final String[] escalierMecaniqueEtTrottoir = {"CNI", "FUJ", "KON", "O.K", "OTI", "SCH", "SPEC", "THY", "PALE"};
    private final String[] ascenseur = {"A000","A001", "A002", "A003", "A004", "A005", "A007"};
    private final String[] grillesEtFermeture = {"BL", "BR", "CI", "GC", "GLE", "GR", "GVP", "PB", "PBH", "PTEBV", "PTEC", "PTLBV", "PTLC", "RM", "VR", "VRA", "GS"};

    private final int[] m01RGB ={255,255,77};
    //private final String[] m02RGB;
    private final int[] m03RGB = {179,179,0};
    private final int[] m04RGB = {217,102,255};
    /*private final String[] m05RGB;
    private final String[] m06RGB;
    private final String[] m07RGB;
    private final String[] m08RGB;
    private final String[] m09RGB;
    private final String[] m10RGB;
    private final String[] m11RGB;
    private final String[] m12RGB;*/
    private final int[] m13RGB ={153,255,255};


    public int[] getRGB(String parLigne){ //Retourne la couleur associé à la ligne passée en argument
        if(parLigne.equals("M01")) return m01RGB;
        else if(parLigne.equals("M03")) return m03RGB;
        else if(parLigne.equals("M04")) return m04RGB;
        else if(parLigne.equals("M13")) return m13RGB;
        else return null;
    }

    public boolean estDansBM(String parBM){// indique si le code passé en argument fait partie de la liste des codes surveillés
        for(int i = 0; i < listeBM.length; i++){
            if(listeBM[i].equals(parBM)) return true;
        }
        return false;
    }


    //Getter, Setter et ToString
    public String toStringCodes(){
        String res = "[ ";
        for(int i = 0; i < listeBM.length; i++){
            if(i == listeBM.length-1)
                res += listeBM[i] + " ";
            else
                res += listeBM[i] + ", ";
        }
        res += "]";
        return res;
    }

    public String toStringCodesParEquip(){
        String res = "Armoires fortes  : ARFO \nCentrales d'alarme:";
        for(String s : centrales){
            res += s + " ";
        }

        res += "\nTélésonorisation : ";
        for(String s : telesono){
            res += s + " ";
        }

        res += "\nCaméras : ";
        for(String s : video){
            res += s + " ";
        }

        res += "\nSuperviseur :";
        for(String s : superviseur){
            res += s + " ";
        }

        res += "\nEscaliers mécaniques et trottoirs : ";
        for(String s : escalierMecaniqueEtTrottoir){
            res += s + " ";
        }

        res += "\nAscenseur : ";
        for(String s : ascenseur){
            res += s + " ";
        }
        res += "\nGrilles et Fermetures : ";
        for(String s : grillesEtFermeture){
            res += s + " ";
        }

        return res;
    }

    public int getNumeroColonneLigne() {
        return numeroColonneLigne;
    }

    public int getGetNumeroColonneLieu() {
        return getNumeroColonneLieu;
    }

    public int getGetNumeroColonneFamille() {
        return getNumeroColonneFamille;
    }

    public String[] getListeBM() {
        return listeBM;
    }

    public String[] getCentrales() {
        return centrales;
    }

    public String[] getTelesono() {
        return telesono;
    }

    public String[] getVideo() {
        return video;
    }

    public String[] getSon() {
        return son;
    }

    public String[] getPhones() {
        return phones;
    }

    public String[] getSuperviseur() {
        return superviseur;
    }

    public String[] getEscalierMecaniqueEtTrottoir() {
        return escalierMecaniqueEtTrottoir;
    }

    public String[] getAscenseur() {
        return ascenseur;
    }

    public String[] getGrillesEtFermeture() {
        return grillesEtFermeture;
    }

    public int[] getM01RGB() {
        return m01RGB;
    }

    public int[] getM03RGB() {
        return m03RGB;
    }

    public int[] getM04RGB() {
        return m04RGB;
    }

    public int[] getM13RGB() {
        return m13RGB;
    }

    public String[] getListeNomBM() {
        return listeNomBM;
    }
}
