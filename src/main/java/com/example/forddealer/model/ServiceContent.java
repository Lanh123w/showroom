package com.example.forddealer.model;

import jakarta.persistence.*;

@Entity
public class ServiceContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String icon;
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
