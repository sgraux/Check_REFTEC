package Model;

public class EquipementSI {

    private String codeBM;
    private int nbNonApplicable;
    private int nbE0;
    private int nbE1;
    private int nbE2;
    private int nbE3;
    private int nbValide;

    public EquipementSI(String parCodeBM){
        codeBM = parCodeBM;
        nbNonApplicable = 0;
        nbE0 = 0;
        nbE1 = 0;
        nbE2 = 0;
        nbE3 = 0;
        nbValide = 0;
    }

    public void manageStatut(String parStatut){
        if(parStatut.equals("Non Applicable")) nbNonApplicable++;
        else if(parStatut.equals("Etape 0")) nbE0++;
        else if(parStatut.equals("Etape 1")) nbE1++;
        else if(parStatut.equals("Etape 2")) nbE2++;
        else if(parStatut.equals("Etape 3")) nbE3++;
        else if(parStatut.equals("Validé")) nbValide++;
    }

    public String getCodeBM() {
        return codeBM;
    }

    public void setCodeBM(String codeBM) {
        this.codeBM = codeBM;
    }

    public int getNbNonApplicable() {
        return nbNonApplicable;
    }

    public void setNbNonApplicable(int nbNonApplicable) {
        this.nbNonApplicable = nbNonApplicable;
    }

    public int getNbE0() {
        return nbE0;
    }

    public void setNbE0(int nbE0) {
        this.nbE0 = nbE0;
    }

    public int getNbE1() {
        return nbE1;
    }

    public void setNbE1(int nbE1) {
        this.nbE1 = nbE1;
    }

    public int getNbE2() {
        return nbE2;
    }

    public void setNbE2(int nbE2) {
        this.nbE2 = nbE2;
    }

    public int getNbE3() {
        return nbE3;
    }

    public void setNbE3(int nbE3) {
        this.nbE3 = nbE3;
    }

    public int getNbValide() {
        return nbValide;
    }

    public void setNbValide(int nbValide) {
        this.nbValide = nbValide;
    }
}
