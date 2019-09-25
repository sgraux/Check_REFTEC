package DocumentCreator;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;

import Data.Data;
import Model.EquipementSI;
import Model.Ligne;
import Model.ReftecReader;
import Model.Station;
import com.itextpdf.text.*;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.ArrayList;
import java.util.Date;

/**Genere le PDF qui presente tous les graphes.
 * @author Sean Graux
 * @version 1.0
 */
public class PDFCreator { //Classe qui créer le pdf contenant le tableau de suivi

    private static ReftecReader reader;
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Data data = new Data();
    private static Boolean generated = false;
    private static String fileName;

    public  PDFCreator(String parPathInput, String parPathOutput, String parVerificationStep, String parMode)throws FileNotFoundException, DocumentException, MalformedURLException, Exception{
            generatePDF(parPathInput, parPathOutput, parVerificationStep, parMode);
    }

    //Créer le pdf à l'emplacement indiqué par output
    public void generatePDF(String parPath, String output, String parVerificationStep, String parMode) throws FileNotFoundException, DocumentException, MalformedURLException, Exception {
        try {
            Document document = new Document();
            reader = new ReftecReader(parPath);
            String title;

            if(parMode.equals("Mode station")) {
                String extractName = reader.getREFTECLigne().getNom() + "_" + reader.getREFTECLigne().getListeStations().get(0).getNom();
                this.fileName = "/Extract_" + extractName + "_" + parVerificationStep + ".pdf";
                title = extractName + " --- " + parVerificationStep;
            }
            else {
                Ligne ligneCourante = reader.getREFTECLigne();
                if(parMode.equals("Mode ligne")) {
                    this.fileName = "/Extract_Mode Ligne_" + ligneCourante.getNom() + ".pdf";
                    title = "Mode ligne --- " + ligneCourante.getNom();
                }
                else{
                    this.fileName = "/Extract_Mode Ligne Détails_" + ligneCourante.getNom() + ".pdf";
                    title = "Mode ligne Détails --- " + ligneCourante.getNom();
                }
            }

            //Ouverture du document et appel de la méthode d'ajout du contenu
            PdfWriter.getInstance(document, new FileOutputStream(output + fileName));
            document.open();
            addTitle(document, title);
            document.add(this.createTable(parMode));

            //Ajout d'une précision sur la colone non applicable
            document.add(new Paragraph(""));
            document.add(new Phrase("(*) La colonne \"non applicable\" est affichée à titre informatif. Elle n'est pas prise en compte dans le ratio de validation."));

            document.close();
            generated = true;
        }
        catch (Exception e){
            generated = false;
        }
    }

    //Ajoute un titre au pdf passé en argument
    private static void addTitle(Document document, String parString)
            throws DocumentException {
        Paragraph title = new Paragraph(parString, catFont);
        title.setAlignment(Element.ALIGN_CENTER);
        addEmptyLine(title, 3);
        document.add(title);
    }

    //Ajoute une ligne vide dans le document passé en argument
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    //Ajoute le tableau contenant toutes les informations du suivi
    public static PdfPTable createTable(String parMode) throws DocumentException {

        // create 10 column table
        PdfPTable table = new PdfPTable(10);

        // set the width of the table to 100% of page
        table.setWidthPercentage(100);

        addHeader(table, reader.getREFTECLigne().getNom());

        //Récupère les données lues par le REFTEC Reader
        ArrayList<String[]> list = reader.getREFTECLigne().retrieve();
        int totalNonValide = 0;
        int valide = 0;

        int compteur = 0;
        int sommePourcentage = 0;

        //Parcours la liste des données et les place dans le tableau
        for(String[] tab : list){
            totalNonValide = 0;
            valide = 0;
            for(int i = 0; i < tab.length; i++){
                if(i == 0) //Style différent pour le nom de station
                    table.addCell(createStationCell(tab[i]));
                else
                    table.addCell(createValueCell(tab[i]));
                try {
                    if (i != tab.length - 1) {
                        if (i != 1) {
                            totalNonValide += Integer.parseInt(tab[i]);
                        }
                    }
                    else
                        valide = Integer.parseInt(tab[i]);
                }
                catch (NumberFormatException e){}
            }
            table.addCell(createValueCell(totalNonValide+""));

            if(totalNonValide == 0){
                table.addCell(createValueCell("100%"));
                sommePourcentage  += 100;
            }
            else {
                if(valide == 0) {
                    table.addCell(createValueCell("0%"));
                }
                else {
                    table.addCell(createValueCell((float) (valide * 100) / (valide + totalNonValide) + "%"));
                    sommePourcentage += (float) (valide * 100) / (valide + totalNonValide);
                }
            }
            //En mode ligne détails on ajoute tout les détails de toutes les stations de la ligne
            if(parMode.equals("Mode ligne détails")) {
                PdfPCell cellDetail = new PdfPCell(new Phrase("--- Détails équipements ---", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.BLACK)));
                cellDetail.setColspan(10);
                Style.headerCellStyle(cellDetail, "NA");
                table.addCell(cellDetail);
                addDetailEquipStation(table, reader.getREFTECLigne().getListeStations().get(compteur));
                compteur++;
                PdfPCell cellBlank = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.BLACK)));
                cellBlank.setColspan(10);
                Style.headerCellStyle(cellBlank, "NA");
                table.addCell(cellBlank);
            }
        }
        int[] tab = reader.getREFTECLigne().getSommeEtapeStations();

        //en mode station on donne les détails pour la station donnée
        if(parMode.equals("Mode station")){
            PdfPCell cellDetail = new PdfPCell(new Phrase("--- Détails équipements ---", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.BLACK)));
            cellDetail.setColspan(10);
            Style.headerCellStyle(cellDetail, "NA");
            table.addCell(cellDetail);
            addDetailEquipStation(table, reader.getREFTECLigne().getListeStations().get(0));
        }
        else {
            PdfPCell cellRatioLigne = new PdfPCell(new Phrase("--- Ratio complétude des données de supervision "+reader.getREFTECLigne().getNom()+" ---", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.BLACK)));
            cellRatioLigne.setColspan(9);
            Style.headerCellStyle(cellRatioLigne, reader.getREFTECLigne().getNom());
            table.addCell(cellRatioLigne);
            table.addCell(createStationCell( sommePourcentage / reader.getREFTECLigne().getListeStations().size() + "%"));
        }
        return table;
    }

    //Ajoute le un header au tableau (titre, codes de famille d'équipement et entêtes de colonnes)
    public static void addHeader(PdfPTable parTable, String parLigne){
        Font font = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.BLACK);
        // create header cell
        PdfPCell cellHeader = new PdfPCell(new Phrase("Extract REFTEC ligne : " + parLigne,font));
        PdfPCell cellCodes = new PdfPCell(new Phrase(data.toStringCodesParEquip(),font));
        PdfPCell cellStation = new PdfPCell(new Phrase("Station",font));
        PdfPCell cellNonApp = new PdfPCell(new Phrase("Non Applicable (*)",font));
        PdfPCell cellE0 = new PdfPCell(new Phrase("Etape 0",font));
        PdfPCell cellE1 = new PdfPCell(new Phrase("Etape 1",font));
        PdfPCell cellE2 = new PdfPCell(new Phrase("Etape 2",font));
        PdfPCell cellE3 = new PdfPCell(new Phrase("Etape 3",font));
        PdfPCell cellValide = new PdfPCell(new Phrase("Validé",font));
        PdfPCell cellTotalNonValide = new PdfPCell(new Phrase("Total non validé", font));
        PdfPCell cellRatio = new PdfPCell(new Phrase("Ratio validé", font));

        cellHeader.setColspan(10);
        cellCodes.setColspan(10);
        cellStation.setColspan(2);
        cellNonApp.setColspan(1);
        cellE0.setColspan(1);
        cellE1.setColspan(1);
        cellE2.setColspan(1);
        cellE3.setColspan(1);
        cellValide.setColspan(1);
        cellTotalNonValide.setColspan(1);
        cellRatio.setColspan(1);

        // set style
        Style.headerCellStyle(cellHeader, parLigne);
        Style.headerCellStyle(cellCodes, "NA");
        cellCodes.setHorizontalAlignment(Element.ALIGN_LEFT);
        Style.headerCellStyle(cellStation, "NA");
        Style.headerCellStyle(cellNonApp, "NA");
        Style.headerCellStyle(cellE0, "NA");
        Style.headerCellStyle(cellE1, "NA");
        Style.headerCellStyle(cellE2, "NA");
        Style.headerCellStyle(cellE3, "NA");
        Style.headerCellStyle(cellValide, "NA");
        Style.headerCellStyle(cellTotalNonValide, "NA");
        Style.headerCellStyle(cellRatio, "NA");

        // add to table
        parTable.addCell(cellHeader);
        parTable.addCell(cellCodes);
        parTable.addCell(cellStation);
        parTable.addCell(cellNonApp);
        parTable.addCell(cellE0);
        parTable.addCell(cellE1);
        parTable.addCell(cellE2);
        parTable.addCell(cellE3);
        parTable.addCell(cellValide);
        parTable.addCell(cellTotalNonValide);
        parTable.addCell(cellRatio);
    }

    //Création de cellules dans le tableau avec des styles différents
    private static PdfPCell createStationCell(String text){
        // font
        Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);

        PdfPCell cell = new PdfPCell(new Phrase(text,font));
        cell.setColspan(2);

        // set style
        Style.stationCellStyle(cell);
        return cell;
    }

    private static PdfPCell createValueCell(String text){
        // font
        Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);

        // create cell
        PdfPCell cell = new PdfPCell(new Phrase(text,font));

        // set style
        Style.valueCellStyle(cell);
        return cell;
    }

    private static PdfPCell createTotalCell(String text){
        // font
        Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);

        PdfPCell cell = new PdfPCell(new Phrase(text,font));
        cell.setColspan(1);

        // set style
        Style.stationCellStyle(cell);
        return cell;
    }

    //Ajoute les détails par famille d'équipement de la station passée en argument
    private static void addDetailEquipStation(PdfPTable parTable, Station parStation){

        try{
        Station station = parStation;
        int totalNonValide = 0;
        int valide = 0;
            for(String s : data.getListeNomBM()){
                totalNonValide = 0;
                valide = 0;

                parTable.addCell(createStationCell(s));
                int[] tempTab = station.getEtapeBM(s);
                for(int i = 0; i < tempTab.length; i++){
                    parTable.addCell(createValueCell(""+tempTab[i]));
                        if (i != 5) {
                            if (i != 0) {
                                totalNonValide += tempTab[i];
                            }
                        }
                        else
                            valide = tempTab[i];
                }
                parTable.addCell(createValueCell(totalNonValide+""));
                if(totalNonValide == 0){
                    parTable.addCell(createValueCell("100%"));
                }
                else{
                    if (valide == 0)
                        parTable.addCell(createValueCell("0%"));
                    else
                        parTable.addCell(createValueCell((float) (valide * 100) / (valide + totalNonValide) + "%"));
                }
            }
        }
        catch (NumberFormatException e){e.printStackTrace();}

    }

    //Getter
    public static Boolean getGenerated() {
        return generated;
    }

    public static String getFileName() {
        return fileName;
    }
}
