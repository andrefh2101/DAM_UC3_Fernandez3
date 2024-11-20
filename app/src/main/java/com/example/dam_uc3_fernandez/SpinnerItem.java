package com.example.dam_uc3_fernandez;

public class SpinnerItem {
    private String text;
    private int imageResource;

    // Constructor
    public SpinnerItem(String text, int imageResource) {
        this.text = text;
        this.imageResource = imageResource;
    }

    // Métodos getters
    public String getText() {
        return text;
    }

    public int getImageResource() {
        return imageResource;
    }
}
