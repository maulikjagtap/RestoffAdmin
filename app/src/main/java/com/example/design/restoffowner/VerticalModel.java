package com.example.design.restoffowner;

import java.util.ArrayList;

public class VerticalModel {

    String title;
    ArrayList<Horizontalmodel> horizontalmodelArrayList;

    public VerticalModel(String title, ArrayList<Horizontalmodel> horizontalmodelArrayList) {
        this.title = title;
        this.horizontalmodelArrayList = horizontalmodelArrayList;
    }

    public VerticalModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Horizontalmodel> getHorizontalmodelArrayList() {
        return horizontalmodelArrayList;
    }

    public void setHorizontalmodelArrayList(ArrayList<Horizontalmodel> horizontalmodelArrayList) {
        this.horizontalmodelArrayList = horizontalmodelArrayList;
    }
}
