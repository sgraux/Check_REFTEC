package DocumentCreator;

import Data.Data;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;

public class Style {//Gère le style des cellules du tableau

    private static Data data = new Data();

    public static void headerCellStyle(PdfPCell cell, String parLigne){

        // alignment
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        // padding
        cell.setPaddingTop(0f);
        cell.setPaddingBottom(7f);
        if(parLigne.equals("NA")) cell.setBackgroundColor(new BaseColor(128,128,128));
        else{
            int[] rgb = data.getRGB(parLigne);
            cell.setBackgroundColor(new BaseColor(rgb[0],rgb[1],rgb[2]));
        }

        // background color
        /*if(parLigne){
            cell.setBackgroundColor(new BaseColor(255,255,77));
        }
        else if(parLigne == 3){
            cell.setBackgroundColor(new BaseColor(179,179,0));
        }
        else if(parLigne == 4){
            cell.setBackgroundColor(new BaseColor(217,102,255));
        }
        else if(parLigne == 13){
            cell.setBackgroundColor(new BaseColor(153,255,255));
        }
        else
            cell.setBackgroundColor(new BaseColor(128,128,128));*/

        // border
        cell.setBorder(0);
        cell.setBorderWidthBottom(2f);

    }
    public static void labelCellStyle(PdfPCell cell){
        // alignment
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        // padding
        cell.setPaddingLeft(3f);
        cell.setPaddingTop(0f);

        // background color
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);

        // border
        cell.setBorder(0);
        cell.setBorderWidthBottom(1);
        cell.setBorderColorBottom(BaseColor.GRAY);

        // height
        cell.setMinimumHeight(18f);
    }

    public static void valueCellStyle(PdfPCell cell){
        // alignment
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        // padding
        cell.setPaddingTop(0f);
        cell.setPaddingBottom(5f);

        // border
        cell.setBorder(0);
        cell.setBorderWidthBottom(0.5f);

        // height
        cell.setMinimumHeight(18f);

        //if(parStation) cell.setBackgroundColor(new BaseColor(200,200,200));
    }

    public static void stationCellStyle(PdfPCell cell) {
        // alignment
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        // padding
        cell.setPaddingTop(0f);
        cell.setPaddingBottom(5f);

        // border
        cell.setBorder(0);
        cell.setBorderWidthRight(0.5f);
        cell.setBorderWidthLeft(0.5f);
        cell.setBorderWidthBottom(0.5f);

        // height
        cell.setMinimumHeight(18f);

        cell.setBackgroundColor(new BaseColor(200, 200, 200));
    }
}