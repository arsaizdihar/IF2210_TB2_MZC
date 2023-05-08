package mzc.app.modules.report;

import lombok.Getter;
import lombok.Setter;
import mzc.app.model.FixedBill;
import mzc.app.model.ProductHistory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

public class PrintReport extends PrintToPDF{
    @Getter
    private final float[] xTable = {10 * mmToPt, 40 * mmToPt, 140 * mmToPt, 190 * mmToPt, 230 * mmToPt, 260 * mmToPt, 277 * mmToPt};
    @Getter
    final float[] headerMargin = {10 * mmToPt, 210 * mmToPt};

    public PrintReport() {
        document = new PDDocument();
    }

    public void toPrint(String path) throws IOException {
        document.save(path);
        document.close();
    }

    public void setNewPage(FixedBill bill, Set<ProductHistory> productHistories) throws IOException {
        PDPage page = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
        document.addPage(page);
        contentStream = new PDPageContentStream(document, page);
        setTitle(bill.getId());
        var yt = 500f;
        setHeader(bill, yt);
        yt -= PDType1Font.TIMES_ROMAN.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * 16 + 5;
        setHeaderColumn(yt);
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 11);
        ProductHistory[] productHistoriesTemp = new ProductHistory[productHistories.size()];
        int index = 0;
        for (ProductHistory productHistory : productHistories) {
            productHistoriesTemp[index] = productHistory;
            index++;
        }
        index = 0;
        for (ProductHistory productHistory : productHistories) {
            yt -= 50;
            if (yt < 20 * mmToPt) {
                contentStream.close();
                setContinuationPage(bill, productHistoriesTemp, index);
                return;
            }
            index++;
            setContent(productHistory, yt);
        }
        contentStream.close();
    }
    public void setContinuationPage(FixedBill bill, ProductHistory[] productHistories, int index) throws IOException {
        PDPage page = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
        document.addPage(page);
        contentStream = new PDPageContentStream(document, page);
        var yt = PDRectangle.A4.getWidth() - 20 * mmToPt - 20;
        setHeader(bill, yt);
        yt -= PDType1Font.TIMES_ROMAN.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * 16 + 5;
        setHeaderColumn(yt);
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 11);
        for (int i = index; i < productHistories.length; i++) {
            yt -= 50;
            if (yt < 20 * mmToPt) {
                contentStream.close();
                setContinuationPage(bill, productHistories, i);
                return;
            }
            setContent(productHistories[i], yt);
        }
        contentStream.close();
    }

    public void setTitle(long billId) throws IOException {
        addCenteredText("Faktur Penjualan " + billId + "#", PDType1Font.TIMES_BOLD, 20, 550, false);
    }

    public void setHeader(FixedBill bill, float ty) throws IOException {
        float tempTy = ty;
        setFont(PDType1Font.TIMES_BOLD, 16);
        var informasiCustomer = new String[]{
                "Nomor Pelanggan: " + bill.getCustomerId()
        };
        var infoBill = new String[]{
                "Nomor Faktur: " + bill.getId(),
//                "Tanggal: " + bill.getCreatedAt().toString()
        };
        for (int i = 0; i < 1; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(headerMargin[0], tempTy);
            contentStream.showText(informasiCustomer[i]);
            contentStream.endText();
            tempTy -= getFontHeight(font, fontSize) + 5;
        }
        tempTy = ty;
        for (int i = 0; i < 1; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(headerMargin[1], tempTy);
            contentStream.showText(infoBill[i]);
            contentStream.endText();
            tempTy -= getFontHeight(font, fontSize) + 5;
        }
    }

    public void setHeaderColumn(float ty) throws IOException {
        String[] headerTable = {"Gambar", "Nama Barang", "Kategori", "Harga Beli", "Harga Jual", "Kuantitas"};
        contentStream.setFont(PDType1Font.TIMES_BOLD, 12);

        for (int i = 0; i < headerTable.length; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(getXTable()[i], ty);
            contentStream.showText(headerTable[i]);
            contentStream.endText();
        }
    }

    public void setContent(ProductHistory productHistory, float ty) throws IOException {
        setFont(PDType1Font.TIMES_ROMAN, 11);
        String[] contentTable = new String[6];
        contentTable[0] = "Gambar";
        contentTable[1] = productHistory.getName();
        contentTable[2] = productHistory.getCategory();
        contentTable[3] = productHistory.getBuyPriceView().toString();
        contentTable[4] = productHistory.getPriceView().toString();
        contentTable[5] = productHistory.getAmount().toString();

        if (productHistory.getImagePath() != null && !productHistory.getImagePath().equals("")) {
            PDImageXObject pdImage = PDImageXObject.createFromFile(productHistory.getImagePath(), document);
            contentStream.drawImage(pdImage, getXTable()[0], ty, 40, 40);
        }


        for (int i = 1; i < 6; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(getXTable()[i], ty);
            contentStream.showText(cutText(contentTable[i], getXTable()[i], getXTable()[i + 1]));
            contentStream.endText();
        }
    }
}
