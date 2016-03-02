package com.example.carlos.saisbrurystest.model;

public class ItemDetails {

    private String name ;
    private String price;
    private int imageNumber;

    public ItemDetails(String name, String price) {

        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public int getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(int imageNumber) {
        this.imageNumber = imageNumber;
    }


}
