package com.example.demo.dto;

import java.util.List;

import com.example.demo.model.Appointment;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public class PdfRequestDto {
    private String title;
    private List <String> paragraphs;

      public String getTitle() {
        return title;
    }

    public void setTitle(String t) {
        this.title = t;
    }

    public List<String> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<String> paragraphs) {
        this.paragraphs = paragraphs;
    }
}
