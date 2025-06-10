package com.example.demo.model;

import java.time.LocalDateTime;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne()
    @JoinColumn(name = "fk_paciente")
    private Patient fkPatient;

    @OneToOne
    @JoinColumn(name = "fk_agenda")
    private Agenda fkAgenda;

    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_receptionist", nullable = true)
    private Recepcionist fkReceptionist;

    @Column(name = "appointment_date")
    private LocalDateTime appointmentDate = LocalDateTime.now();

    @Column(name = "appointment_pdf")
    private Pdf pdf;

    //gera e armazena
    public void generatedPdf(String titulo, String contedo){
        byte[] arquivo = generatePdfFile(titulo, contedo);
        this.pdf = new Pdf(titulo, contedo, arquivo);
    }

       private byte[] generatePdfFile(String titulo, String conteudo) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             PdfWriter writer = new PdfWriter(outputStream);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {
            
            document.add(new Paragraph(titulo)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold()
                    .setFontSize(20));
            
            document.add(new Paragraph(conteudo)
                    .setMarginTop(10)
                    .setFontSize(12));
            
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }

    public Pdf getPdf() {
        return pdf;
    }
}