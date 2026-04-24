package com.example.lab7_starsgallery.beans;

public class Star {
    private int id;
    private String name;
    private String imageUrl;
    private float star;
    private static int autoIncrement = 0;

    public Star(String name, String imageUrl, float star) {
        this.id = ++autoIncrement;
        this.name = name;
        this.imageUrl = imageUrl;
        this.star = star;
    }

    public int getId()           { return id; }
    public String getName()      { return name; }
    public String getImageUrl()  { return imageUrl; }
    public float getStar()       { return star; }

    public void setName(String name)         { this.name = name; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setStar(float star)          { this.star = star; }
}
