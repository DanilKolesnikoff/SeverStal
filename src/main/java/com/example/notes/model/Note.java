package com.example.notes.model;
import jakarta.persistence.*;
import jakarta.persistence.Lob;

import java.util.Base64;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @Lob
    private byte[] image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageBase64() {
        if (image == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(image);
    }
}