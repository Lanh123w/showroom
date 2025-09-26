package com.example.forddealer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CarColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer priceAdjustment;
    private String colorCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriceAdjustment() {
        return priceAdjustment;
    }

    public void setPriceAdjustment(Integer priceAdjustment) {
        this.priceAdjustment = priceAdjustment;
    }
    

    public void setColorCode(String colorCode) {
    this.colorCode = colorCode;
    }

    public String getColorCode() {
    return colorCode;
    }

}
