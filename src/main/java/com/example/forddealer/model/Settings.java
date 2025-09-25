package com.example.forddealer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Settings {
    @Id
    private Integer id;

    private String logoPath;
    private String bannerPath;
    private String footerText;
    @Lob
    @Column(name = "about_content", columnDefinition = "TEXT")
    private String aboutContent;
    private String hotline;
    private String email;
    private String address;
    private String zalo;
    private String website;

 


    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getLogoPath() { return logoPath; }
    public void setLogoPath(String logoPath) { this.logoPath = logoPath; }

    public String getBannerPath() { return bannerPath; }
    public void setBannerPath(String bannerPath) { this.bannerPath = bannerPath; }

    public String getFooterText() { return footerText; }
    public void setFooterText(String footerText) { this.footerText = footerText; }

    public String getAboutContent() { return aboutContent; }
    public void setAboutContent(String aboutContent) { this.aboutContent = aboutContent; }

    public String getHotline() { return hotline; }
    public void setHotline(String hotline) { this.hotline = hotline; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getZalo() { return zalo; }
    public void setZalo(String zalo) { this.zalo = zalo; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

  
}
