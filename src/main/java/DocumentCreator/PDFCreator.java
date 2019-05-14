package DocumentCreator;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;

import Data.Data;
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
public class PDFCreator {

    private static String pathToOutput;
    private static ReftecReader reader;
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Data data = new Data();
    private static Boolean generated = false;

    public  PDFCreator(String parPathInput, String parPathOutput)throws FileNotFoundException, DocumentException, MalformedURLException, Exception{
        generatePDF(parPathInput, parPathOutput);
        pathToOutput = parPathOutput;
    }

    public void generatePDF(String parPath, String output) throws FileNotFoundException, DocumentException, MalformedURLException, Exception {
        try {
            Document document = new Document();
            reader = new ReftecReader(parPath);
            PdfWriter.getInstance(document, new FileOutputStream(output + "/ResultExtract.pdf"));
            document.open();
            addTitle(document, reader.getREFTECLigne().getNom());
            document.add(this.createTable());
            document.close();
            generated = true;
        }
        catch (Exception e){
            generated = false;
        }
    }

    private static void addTitle(Document document, String parString)
            throws DocumentException {
        Paragraph title = new Paragraph("Extract REFTEC "+ parString + " --- " + new Date(), catFont);
        title.setAlignment(Element.ALIGN_CENTER);
        addEmptyLine(title, 3);
        document.add(title);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public static PdfPTable createTable() throws DocumentException {

        // create 10 column table
        PdfPTable table = new PdfPTable(10);

        // set the width of the table to 100% of page
        table.setWidthPercentage(100);

        //TODO: add real values
        addHeader(table, reader.getREFTECLigne().getNom());

        ArrayList<String[]> list = reader.getREFTECLigne().retrieve();
        int totalNonValide = 0;
        int valide = 0;
        for(String[] tab : list){
            for(int i = 0; i < tab.length; i++){
                if(i == 0)
                    table.addCell(createStationCell(tab[i]));
                else
                    table.addCell(createValueCell(tab[i]));
                try {
                    if (i != tab.length - 1)
                        totalNonValide += Integer.parseInt(tab[i]);
                    else
                        valide = Integer.parseInt(tab[i]);
                }
                catch (NumberFormatException e){}
            }
            table.addCell(createValueCell(totalNonValide+""));
            if(valide == 0)
                table.addCell(createValueCell("0%"));
            else
                table.addCell(createValueCell((valide*100)/(valide+totalNonValide) + "%"));

        }
        int[] tab = reader.getREFTECLigne().getSommeEtapeStations();
        PdfPCell cellDetail = new PdfPCell(new Phrase("--- Détails équipements ---", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.BLACK)));
        cellDetail.setColspan(10);
        Style.headerCellStyle(cellDetail, "NA");
        table.addCell(cellDetail);
        /*for(int i : tab){
            table.addCell(createTotalCell(""+i));
        }
        table.addCell(createTotalCell(totalNonValide+""));
        table.addCell(createTotalCell((valide*100)/(valide+totalNonValide) + "%"));*/

        if(reader.getREFTECLigne().getListeStations().size() == 1){
            addDetailEquipStation(table);
        }
        return table;
    }

    public static void addHeader(PdfPTable parTable, String parLigne){
        Font font = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.BLACK);
        // create header cell
        PdfPCell cellHeader = new PdfPCell(new Phrase("Extract REFTEC ligne : " + parLigne,font));
        PdfPCell cellCodes = new PdfPCell(new Phrase(data.toStringCodesParEquip(),font));
        PdfPCell cellStation = new PdfPCell(new Phrase("Station",font));
        PdfPCell cellNonApp = new PdfPCell(new Phrase("Non Applicable",font));
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

    private static void addDetailEquipStation(PdfPTable parTable){

        try{
        Station station = reader.getREFTECLigne().getListeStations().get(0);
        int totalNonValide = 0;
        int valide = 0;
            for(String s : data.getListeNomBM()){
                totalNonValide = 0;
                valide = 0;

                parTable.addCell(createStationCell(s));
                int[] tempTab = station.getEtapeBM(s);
                for(int i = 0; i < tempTab.length; i++){
                    parTable.addCell(createValueCell(""+tempTab[i]));
                        if (i != 5)
                            totalNonValide += tempTab[i];
                        else
                            valide = tempTab[i];
                }
                //System.out.println("Equipement : " + s + " --- valide : " + valide + " --- non valide : " + totalNonValide);
                parTable.addCell(createValueCell(totalNonValide+""));
                if(valide == 0)
                    parTable.addCell(createValueCell("0%"));
                else
                    parTable.addCell(createValueCell((valide*100)/(valide+totalNonValide) + "%"));
            }
        }
        catch (NumberFormatException e){e.printStackTrace();}

    }

    public static Boolean getGenerated() {
        return generated;
    }
}
