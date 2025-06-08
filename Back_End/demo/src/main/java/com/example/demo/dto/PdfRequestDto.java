package com.example.demo.dto;


public class PdfRequestDto {
    private String title;
    private String paragraph;

      public String getTitle() {
        return title;
    }

    public void setTitle(String t) {
        this.title = t;
    }

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }


}
