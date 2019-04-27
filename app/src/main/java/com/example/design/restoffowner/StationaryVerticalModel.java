package com.example.design.restoffowner;

import java.util.ArrayList;

public class StationaryVerticalModel {
   private  String title;
    private ArrayList<StationaryIteamModel> stationaryIteamModelArrayList;

    public StationaryVerticalModel(String title, ArrayList<StationaryIteamModel> stationaryIteamModelArrayList) {
        this.title = title;
        this.stationaryIteamModelArrayList = stationaryIteamModelArrayList;
    }

    public StationaryVerticalModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<StationaryIteamModel> getStationaryIteamModelArrayList() {
        return stationaryIteamModelArrayList;
    }

    public void setStationaryIteamModelArrayList(ArrayList<StationaryIteamModel> stationaryIteamModelArrayList) {
        this.stationaryIteamModelArrayList = stationaryIteamModelArrayList;
    }
}
