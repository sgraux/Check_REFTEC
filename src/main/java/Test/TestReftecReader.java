package Test;

import DocumentCreator.PDFCreator;
import Model.ReftecReader;
import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

public class TestReftecReader {

    public static void main(String[] args) throws FileNotFoundException, DocumentException, MalformedURLException, Exception{
        //ReftecReader reader = new ReftecReader("reftec-liste-des-equipements_20190412-135015.xlsx");
        PDFCreator pdfCreator = new PDFCreator("C:\\Users\\Sean\\Documents\\Project\\Check_REFTEC\\InputDir\\extract.xlsx", "C:\\Users\\Sean\\Documents\\Project\\Check_REFTEC\\OutputDir");
    }
}
