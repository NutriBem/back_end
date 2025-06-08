package com.example.demo.controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.io.source.ByteArrayOutputStream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.example.demo.dto.PdfRequestDto;

@RestController
public class PdfController {
    @PostMapping("generate-pdf")
    public ResponseEntity<byte[]> generatePdf(@RequestBody PdfRequestDto resquest) throws Exception{

        ByteArrayOutputStream arrayBytes = new ByteArrayOutputStream(); // capturar dados de saída em um array de bytes em memória dps converter em string ou outro format

        try(
            PdfWriter writer = new PdfWriter(arrayBytes);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf)){

            //Titulo
            document.add(new Paragraph(resquest.getTitle())
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold()
                    .setFontSize(20));

            //paragrafo
            for (String para : resquest.getParagraphs()) {
                document.add(new Paragraph(para)
                        .setMarginTop(10)
                        .setFontSize(12));
            }
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "documento.pdf");
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(arrayBytes.toByteArray());
    }    
}
