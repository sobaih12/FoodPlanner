package com.example.comedo.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RootArea {
    @SerializedName("meals")
    public List<AreaModel> areas;

    public RootArea() {

    }

    public List<AreaModel> getAreas() {
        return areas;
    }

    public void setAreas(List<AreaModel> areas) {
        this.areas = areas;
    }
}
