package hwaldschmidt.larpchartool.charactersheetwriter;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import hwaldschmidt.larpchartool.domain.Chara;
import hwaldschmidt.larpchartool.domain.Visit;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CharacterSheetWriter for data of one char as pdf file. Uses "itext".
 *
 * @author Heiko Waldschmidt
 */
@Service
public class PdfCharacterSheetWriter implements CharacterSheetWriter {

    private static Font h1Font = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font textBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private static Font textFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL);
    private static Logger logger = LoggerFactory.getLogger(PdfCharacterSheetWriter.class.getName());

    public String createCharacterSheet(Chara chara, List<Visit> visits, int condays) throws IOException {
        logger.trace("creating character sheet");
        String filename = System.getProperty("java.io.tmpdir") + chara.getName() + ".pdf";
        try {
            Document document = new Document(PageSize.A4, 36.0F, 36.0F, 36.0F, 60.0F);
            final PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
            Footer footer = new Footer();
            footer.setFooterText("Created by LARPCharTool");
            writer.setPageEvent(footer);
            document.open();
            addMetaData(document);
            addChardata(document, chara, visits, condays);
            document.close();
        } catch (DocumentException e) {
            logger.error("error creating document for " + chara.getName(), e);
            throw new IOException(e);
        } catch (IOException e){
            logger.error("error creating document for " + chara.getName(), e);
            throw e;
        }
        return filename;
    }

    private static void addMetaData(Document document) {
        document.addTitle("Character Sheet");
        document.addSubject("");
        document.addKeywords("LARP, Char");
        document.addAuthor("");
        document.addCreator("Heiko Waldschmidts LARPCharTool");
    }

    private static void addChardata(Document document, Chara chara, List<Visit> visits, int condays) throws DocumentException {
        addBasicCharData(document, chara, condays);
        addVisitTable(document, visits);
    }

    private static void addBasicCharData(Document document, Chara chara, int condays) throws DocumentException {
        Paragraph formattedPageTitle = new Paragraph();
        Paragraph pageTitle = new Paragraph(chara.getName(), h1Font);
        formattedPageTitle.add(pageTitle);
        addEmptyLine(formattedPageTitle, 1);
        document.add(formattedPageTitle);

        PdfPTable table = new PdfPTable(2);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);

        PdfPCell cell = new PdfPCell(new Phrase("Condays:", textBoldFont));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(Integer.toString(condays), textFont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        document.add(table);
    }

    private static void addVisitTable(Document document, List<Visit> visits) throws DocumentException {
        Paragraph formattedTableTitle = new Paragraph();
        Paragraph tableTitle = new Paragraph("Visited Conventions", h1Font);
        formattedTableTitle.add(tableTitle);
        addEmptyLine(formattedTableTitle, 1);
        document.add(formattedTableTitle);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.getDefaultCell().setUseAscender(true);
        table.getDefaultCell().setUseDescender(true);

        addVisitTableHeader(table);
        addVisitTableData(table, visits);
        document.add(table);
    }

    private static void addVisitTableHeader(PdfPTable table){

        PdfPCell pdfPHeaderCell = new PdfPCell(new Phrase("Convention", textBoldFont));
        pdfPHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPHeaderCell.setColspan(1);
        pdfPHeaderCell.setRowspan(1);
        table.addCell(pdfPHeaderCell);

        pdfPHeaderCell = new PdfPCell(new Phrase("Start", textBoldFont));
        pdfPHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPHeaderCell.setColspan(1);
        pdfPHeaderCell.setRowspan(1);
        table.addCell(pdfPHeaderCell);

        pdfPHeaderCell = new PdfPCell(new Phrase("End", textBoldFont));
        pdfPHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPHeaderCell.setColspan(1);
        pdfPHeaderCell.setRowspan(1);
        table.addCell(pdfPHeaderCell);

        pdfPHeaderCell = new PdfPCell(new Phrase("Condays", textBoldFont));
        pdfPHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPHeaderCell.setColspan(1);
        pdfPHeaderCell.setRowspan(1);
        table.addCell(pdfPHeaderCell);

        table.setHeaderRows(1);
    }

    private static void addVisitTableData(PdfPTable table, List<Visit> visits){
        for (Visit visit: visits){
            PdfPCell pdfPCell1 = new PdfPCell(new Phrase(visit.getConvention().getTitle()));
            pdfPCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell1.setColspan(1);
            pdfPCell1.setRowspan(1);
            table.addCell(pdfPCell1);

            PdfPCell pdfPCell2 = new PdfPCell(new Phrase(visit.getConvention().getStart().toString()));
            pdfPCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell2.setColspan(1);
            pdfPCell2.setRowspan(1);
            table.addCell(pdfPCell2);

            PdfPCell pdfPCell3 = new PdfPCell(new Phrase(visit.getConvention().getEnd().toString()));
            pdfPCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell3.setColspan(1);
            pdfPCell3.setRowspan(1);
            table.addCell(pdfPCell3);

            PdfPCell pdfPCell4 = new PdfPCell(new Phrase(visit.getCondays().toString()));
            pdfPCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell4.setColspan(1);
            pdfPCell4.setRowspan(1);
            table.addCell(pdfPCell4);
        }
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    /**
     * from https://stackoverflow.com/questions/8009874/itext-pagenumber-display-in-pdf
     */
    private class Footer extends PdfPageEventHelper {
        /** The footerText text. */
        String footerText;
        /** The template with the total number of pages. */
        PdfTemplate total;

        /**
         * Allows us to change the content of the footerText.
         *
         * @param footerText
         *            The new footerText String
         */
        public void setFooterText(String footerText) {
            this.footerText = footerText;
        }

        /**
         * Creates the PdfTemplate that will hold the total number of pages.
         *
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(com.itextpdf.text.pdf.PdfWriter,
         *      com.itextpdf.text.Document)
         */
        public void onOpenDocument(PdfWriter writer, Document document) {
            total = writer.getDirectContent().createTemplate(30, 16);
        }

        /**
         * Adds a footerText to every page
         *
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(com.itextpdf.text.pdf.PdfWriter,
         *      com.itextpdf.text.Document)
         */
        public void onEndPage(PdfWriter writer, Document document) {
            PdfPTable table = new PdfPTable(3);
            try {
                table.setWidths(new int[] { 24, 24, 2 });
                table.setTotalWidth(527);
                table.setLockedWidth(true);
                table.getDefaultCell().setFixedHeight(20);
                table.getDefaultCell().setBorder(Rectangle.BOTTOM);
                table.addCell(footerText);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(String.format("Page %d of", writer.getPageNumber()));
                PdfPCell cell = new PdfPCell(Image.getInstance(total));
                cell.setBorder(Rectangle.BOTTOM);
                table.addCell(cell);
                table.writeSelectedRows(0, -1, 34, 50, writer.getDirectContent());
            } catch (DocumentException de) {
                throw new ExceptionConverter(de);
            }
        }

        /**
         * Fills out the total number of pages before the document is closed.
         *
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(com.itextpdf.text.pdf.PdfWriter,
         *      com.itextpdf.text.Document)
         */
        public void onCloseDocument(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(
                    total,
                    Element.ALIGN_LEFT,
                    new Phrase(String.valueOf(writer.getPageNumber())),
                    2, 2, 0
            );
        }
    }
}
