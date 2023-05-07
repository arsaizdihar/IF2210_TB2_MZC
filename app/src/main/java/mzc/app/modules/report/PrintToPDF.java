package mzc.app.modules.report;

import lombok.Getter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

abstract public class PrintToPDF {
    @Getter
    protected @NotNull PDDocument document;
    @Getter
    protected @NotNull PDPageContentStream contentStream;
    @Getter
    protected @NotNull PDType1Font font = PDType1Font.TIMES_ROMAN;
    @Getter
    protected int fontSize = 11;
    @Getter
    protected final float mmToPt = 2.83465f;

    protected void setFont(PDType1Font font, int fontSize) throws IOException {
        contentStream.setFont(font, fontSize);
        this.font = font;
        this.fontSize = fontSize;
    }

    public String cutText(String text, float x, float borderX) throws IOException {
        float padding = 5;
        float dotDotWidth = getStringWidth("...", font, fontSize)  + padding;
        float textWidth = getStringWidth(text, font, fontSize);

        if (textWidth < borderX - x) {
            return text;
        }
        if (borderX - x < dotDotWidth) {
            return "";
        }

        for (int i = 0; (textWidth + x + dotDotWidth > borderX); i++) {
            text = text.substring(0, text.length() - 1);
            textWidth = getStringWidth(text, font, fontSize);
        }

        return text + "...";
    }
    float getStringWidth(String text, PDFont font, int fontSize) throws IOException {
        return font.getStringWidth(text) / 1000 * fontSize;
    }

    float getFontHeight(PDFont font, int fontSize) throws IOException {
        return font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
    }

    public void addCenteredText(String text, PDType1Font font, int fontSize, float ty, boolean isPortrait) throws IOException {
        setFont(font, fontSize);
        float stringWidth = getStringWidth(text, font, fontSize);
        float centeredPosition = (PDRectangle.A4.getHeight() - stringWidth) / 2;
        if (isPortrait) {
            centeredPosition = (PDRectangle.A4.getWidth() - stringWidth) / 2;
        }
        contentStream.beginText();
        contentStream.newLineAtOffset(centeredPosition, ty);
        contentStream.showText(text);
        contentStream.endText();
    }
}
