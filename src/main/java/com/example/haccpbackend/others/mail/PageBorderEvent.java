package com.example.haccpbackend.others.mail;

import com.itextpdf.text.pdf.*;
import com.itextpdf.text.*;

public class PageBorderEvent extends PdfPageEventHelper {
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte canvas = writer.getDirectContent();
        Rectangle rect = document.getPageSize();

        // Définir les marges internes de la bordure
        float margin = 20f;

        // Définir la couleur et l’épaisseur du trait
        canvas.setColorStroke(BaseColor.BLACK);
        canvas.setLineWidth(0.5f); // léger

        // Dessiner un rectangle (bordure)
        canvas.rectangle(
                rect.getLeft(margin),
                rect.getBottom(margin),
                rect.getWidth() - 2 * margin,
                rect.getHeight() - 2 * margin
        );
        canvas.stroke();
    }
}
