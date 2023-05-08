package mzc.app.modules.report;

import lombok.Getter;
import lombok.Setter;
import mzc.app.model.FixedBill;
import mzc.app.model.ProductHistory;
import mzc.app.modules.pricing.price.ItemPrice;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.Set;

public class PrintTransactionHistory extends PrintToPDF{
    @Getter
    private final float padding = 5;

    @Getter
    public final float margin = 20 * mmToPt;
    @Getter
    private final float rightMargin = PDRectangle.A4.getWidth() - margin;

    @Getter @Setter
    private float lastY = PDRectangle.A4.getHeight() - margin;
    public PrintTransactionHistory() {
        document = new PDDocument();
    }
    public void printPage(FixedBill fixedBill, Set<ProductHistory> productHistories, String customerName) throws IOException {
        lastY = PDRectangle.A4.getHeight() - margin;
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        contentStream = new PDPageContentStream(document, page);
        printTitle();
        printHeader(customerName);

        int i = 0;
        ProductHistory[] productHistoriesTemp = new ProductHistory[productHistories.size()];
        for (ProductHistory productHistory : productHistories) {
            productHistoriesTemp[i] = productHistory;
            i++;
        }
        for (i = 0; i < productHistories.size(); i++) {
            if (lastY < 20 * mmToPt) {
                contentStream.close();
                printContinuationPage(productHistoriesTemp,customerName, i);
                return;
            }
            printEntry(productHistoriesTemp[i]);
        }
        contentStream.close();
    }
    private void printContinuationPage(ProductHistory[] productHistories, String customerName, int index) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        contentStream = new PDPageContentStream(document, page);
        setLastY(PDRectangle.A4.getHeight() - margin);
        printHeader(customerName);
        printEntry(productHistories[index]);
        for (int i = index; i < productHistories.length; i++) {
            if (lastY < 20 * mmToPt) {
                contentStream.close();
                printContinuationPage(productHistories,customerName, i);
                return;
            }
            printEntry(productHistories[i]);
        }
        contentStream.close();
    }
    private void printHeader(String customerName) throws IOException {
        setFont(PDType1Font.HELVETICA_BOLD, 14);
        contentStream.beginText();
        contentStream.newLineAtOffset(margin, lastY - getFontHeight(font, fontSize) - padding);
        contentStream.showText(cutText("No. " + customerName, margin, PDRectangle.A4.getWidth() - margin));
        contentStream.endText();
        setLastY(lastY - getFontHeight(font, fontSize) - padding);
    };
    private void printEntry(ProductHistory productHistory) throws IOException{
        setFont(PDType1Font.HELVETICA, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(margin, lastY - getFontHeight(font, fontSize) - padding);
        contentStream.showText(cutText(productHistory.getName(), margin, PDRectangle.A4.getWidth()/3 * 2));
        contentStream.endText();

        ItemPrice itemPrice = new ItemPrice(productHistory.getAmount(), productHistory.getPriceView());
        String price = cutText(itemPrice.toString(), PDRectangle.A4.getWidth()/3 * 2, PDRectangle.A4.getWidth() - margin);
        float offset = getStringWidth(price, font, fontSize);
        contentStream.beginText();
        contentStream.newLineAtOffset(rightMargin - offset, lastY - getFontHeight(font, fontSize) - padding);
        contentStream.showText(price);
        contentStream.endText();
        setLastY(lastY - getFontHeight(font, fontSize) - padding);
    }

    public void printTitle() throws IOException {
        setFont(PDType1Font.HELVETICA, 20);
        addCenteredText("Riwayat Penjualan", font, fontSize, lastY - getFontHeight(font, fontSize) - padding, true);
        setLastY(lastY - getFontHeight(font, fontSize) - padding);
    }

    public void toPrint(String path) throws IOException {
        document.save(path);
        document.close();
    }
}
