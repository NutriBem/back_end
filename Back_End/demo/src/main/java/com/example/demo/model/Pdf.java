package com.example.demo.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

public class Pdf implements Serializable{
    private String titulo;
    private String conteudo;
    private byte[] arquivo;

    public Pdf (){};

    public Pdf(String titulo, String conteudo, byte[] arquivo){
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.arquivo = arquivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pdf pdf = (Pdf) o;
        return Objects.equals(titulo, pdf.titulo) && 
               Objects.equals(conteudo, pdf.conteudo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, conteudo);
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    
}
