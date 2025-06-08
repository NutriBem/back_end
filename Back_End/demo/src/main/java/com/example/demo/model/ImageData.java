package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "IMAGE_DATA") //da porra da tabela
public class ImageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;
    
    @Column(nullable = false)
    private String contentType;

    @Lob
    @Column(columnDefinition = "BLOB", nullable = false) //mudar quando migrar para postgress, H2 = BLOB
    private byte[] data;

    // public Long getId() {
    //     return id;
    // }
    // public void setId(Long id) {
    //     this.id = id;
    // }
    // public byte[] getData() {
    //     return data;
    // }
    // public void setData(byte[] data) {
    //     this.data = data;
    // }
    // public String getFileName() {
    //     return fileName;
    // }
    // public void setFileName(String fileName) {
    //     this.fileName = fileName;
    // }
    // public String getContentType() {
    //     return contentType;
    // }
    // public void setContentType(String contentType) {
    //     this.contentType = contentType;
    // }
}
