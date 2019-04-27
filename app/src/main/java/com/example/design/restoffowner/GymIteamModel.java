package com.example.design.restoffowner;

class GymIteamModel {
    private  String name;
    private String description;
    private  String price;
   private String myuri;

    public GymIteamModel(String name, String description, String price, String myuri) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.myuri = myuri;
    }

    public GymIteamModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMyuri() {
        return myuri;
    }

    public void setMyuri(String myuri) {
        this.myuri = myuri;
    }
}

