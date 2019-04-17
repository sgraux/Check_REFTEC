package DocumentCreator;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;

import Model.ReftecReader;
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

    private final String pathToOutput = "OutputDir";
    private static ReftecReader reader;
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);

    public  PDFCreator(String parPath)throws FileNotFoundException, DocumentException, MalformedURLException, Exception{
        generatePDF(parPath);
    }

    public void generatePDF(String parPath) throws FileNotFoundException, DocumentException, MalformedURLException, Exception {
        Document document = new Document();
        reader = new ReftecReader(parPath);
        PdfWriter.getInstance(document, new FileOutputStream(pathToOutput + "/ResultExtract.pdf"));
        document.open();
        addTitle(document, reader.getREFTECLigne().getNom());
        document.add(this.createTable());
        document.close();
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

        // create 6 column table
        PdfPTable table = new PdfPTable(7);

        // set the width of the table to 100% of page
        table.setWidthPercentage(100);

        //TODO: add real values
        addHeader(table, reader.getREFTECLigne().getNom());

        ArrayList<String[]> list = reader.getREFTECLigne().retrieve();
        for(String[] tab : list){
            for(int i = 0; i < tab.length; i++){
                if(i == 0)
                    table.addCell(createValueCell(tab[i], true));
                else
                    table.addCell(createValueCell(tab[i], false));
            }
        }
        int[] tab = reader.getREFTECLigne().getSommeEtapeStations();
        table.addCell(createValueCell("Totaux", true));
        for(int i : tab){
            table.addCell(createValueCell(""+i, true));
        }

        return table;
    }

    public static void addHeader(PdfPTable parTable, String parLigne){
        Font font = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.BLACK);
        // create header cell
        PdfPCell cellHeader = new PdfPCell(new Phrase("Extract REFTEC ligne : " + parLigne,font));
        PdfPCell cellStation = new PdfPCell(new Phrase("Station",font));
        PdfPCell cellNonApp = new PdfPCell(new Phrase("Non Applicable",font));
        PdfPCell cellE0 = new PdfPCell(new Phrase("Etape 0",font));
        PdfPCell cellE1 = new PdfPCell(new Phrase("Etape 1",font));
        PdfPCell cellE2 = new PdfPCell(new Phrase("Etape 2",font));
        PdfPCell cellE3 = new PdfPCell(new Phrase("Etape 3",font));
        PdfPCell cellValide = new PdfPCell(new Phrase("Validé",font));

        cellHeader.setColspan(7);
        cellStation.setColspan(1);
        cellNonApp.setColspan(1);
        cellE0.setColspan(1);
        cellE1.setColspan(1);
        cellE2.setColspan(1);
        cellE3.setColspan(1);
        cellValide.setColspan(1);

        // set style
        Style.headerCellStyle(cellHeader, 1);
        Style.headerCellStyle(cellStation, 0);
        Style.headerCellStyle(cellNonApp, 0);
        Style.headerCellStyle(cellE0, 0);
        Style.headerCellStyle(cellE1, 0);
        Style.headerCellStyle(cellE2, 0);
        Style.headerCellStyle(cellE3, 0);
        Style.headerCellStyle(cellValide, 0);

        // add to table
        parTable.addCell(cellHeader);
        parTable.addCell(cellStation);
        parTable.addCell(cellNonApp);
        parTable.addCell(cellE0);
        parTable.addCell(cellE1);
        parTable.addCell(cellE2);
        parTable.addCell(cellE3);
        parTable.addCell(cellValide);
    }

    // create cells
    private static PdfPCell createLabelCell(String text){
        // font
        Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.DARK_GRAY);

        // create cell
        PdfPCell cell = new PdfPCell(new Phrase(text,font));

        // set style
        Style.labelCellStyle(cell);
        return cell;
    }

    // create cells
    private static PdfPCell createValueCell(String text, boolean parStation){
        // font
        Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);

        // create cell
        PdfPCell cell = new PdfPCell(new Phrase(text,font));

        // set style
        Style.valueCellStyle(cell, parStation);
        return cell;
    }
}
