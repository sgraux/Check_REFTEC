package Model;

import Data.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class ReftecReader { //Lit un extract REFTEC pour une ligne donnée

    private XSSFWorkbook extract;
    private Ligne REFTECLigne;
    private Data data = new Data();


    public ReftecReader(String parPathToExtract){

        File tempFile = new File(parPathToExtract);

        try {
            System.out.println("--- START ----");
            FileInputStream tempInputStream = new FileInputStream(tempFile);
            System.out.println("--- READING ----");
            extract = new XSSFWorkbook(tempInputStream);
        }
        catch(FileNotFoundException e){
            System.out.println("Fichier non trouvé");
        }
        catch (IOException e){
            System.out.println("io exception");
        }
        this.read();
        REFTECLigne.display();

    }

    public void read(){
        Iterator<Row> rowIterator = extract.getSheetAt(0).iterator();

        rowIterator.next(); //Retire les 5 premières lignes
        rowIterator.next();
        rowIterator.next();
        rowIterator.next();
        rowIterator.next();

        Row currentRow;

        Iterator<Cell> cellIterator;
        Cell currentCell;

        int countCell = 0;
        boolean initLigne = false;

        String lieu = "";
        String familleBM = "";
        String statut = "";


        int testCountRows = 0;//DEBUG

        while (rowIterator.hasNext()){
            testCountRows++;
            currentRow = rowIterator.next();
            cellIterator = currentRow.cellIterator();

            while (cellIterator.hasNext()){
                currentCell = cellIterator.next();
                switch (countCell){
                    case 2://Ligne
                        if(!initLigne){
                            this.REFTECLigne = new Ligne(currentCell.getStringCellValue());
                            initLigne = true;
                        }
                        break;
                    case 3://Lieu
                        lieu = currentCell.getStringCellValue();
                        break;
                    case 7://Famile BM
                        familleBM = currentCell.getStringCellValue();
                        break;
                    case 14://Statut du workflow
                        statut = currentCell.getStringCellValue();
                        break;
                }
                countCell++;
            }
            REFTECLigne.manageLieu(lieu,familleBM,statut);
            countCell = 0;
        }

        Collections.sort(REFTECLigne.getListeStations(), new Comparator<Station>() {
            public int compare(Station s1, Station s2)
            {

                return  s1.getNom().compareTo(s2.getNom());
            }
        });
        System.out.println(testCountRows);
    }

    public XSSFWorkbook getExtract() {
        return extract;
    }

    public void setExtract(XSSFWorkbook extract) {
        this.extract = extract;
    }

    public Ligne getREFTECLigne() {
        return REFTECLigne;
    }

    public void setREFTECLigne(Ligne REFTECLigne) {
        this.REFTECLigne = REFTECLigne;
    }

}
