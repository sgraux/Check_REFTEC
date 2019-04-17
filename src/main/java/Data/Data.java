package Data;

public class Data {

    private final int numeroColonneLigne = 3;
    private final int getNumeroColonneLieu = 4;
    private final int getNumeroColonneFamille = 8;
    private final String[] listeBM = {"CEAS", "PEAS", "DEAS","PEAL","ARTS", "MITS", "RTIP", "RTCL", "TSONO","ARVS", "CAVS", "RAVS", "MOVS","SONO", "SONOR", "PUPI", "PUSO", "HPSO","IVOY", "IVDO", "PUIN", "BUIN", "CAIN", "BMIN", "MPIN"};


    public boolean estDansBM(String parBM){
        for(int i = 0; i < listeBM.length; i++){
            if(listeBM[i].equals(parBM)) return true;
        }
        return false;
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
}
