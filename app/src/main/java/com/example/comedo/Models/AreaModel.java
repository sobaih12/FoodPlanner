package com.example.comedo.Models;

import java.util.HashMap;

public class AreaModel {
    private String strArea;
    private String thumbnail;

    public AreaModel() {
    }

    public AreaModel(String strArea) {
        this.strArea = strArea;
        this.thumbnail = getThumbnail();
    }

    public String getThumbnail() {
        setThumbnail();
        return thumbnail;
    }

    public void setThumbnail() {
        HashMap<String, String> countryCode = new HashMap<>();
        countryCode.put("Vietnamese", "vn");
        countryCode.put("Turkish", "tr");
        countryCode.put("Tunisian", "tn");
        countryCode.put("Thai", "th");
        countryCode.put("Spanish", "es");
        countryCode.put("Russian", "ru");
        countryCode.put("American", "us");
        countryCode.put("British", "gb");
        countryCode.put("Canadian", "ca");
        countryCode.put("Chinese", "cn");
        countryCode.put("Croatian", "hr");
        countryCode.put("Dutch", "de");
        countryCode.put("Egyptian", "eg");
        countryCode.put("French", "fr");
        countryCode.put("Greek", "gr");
        countryCode.put("Indian", "in");
        countryCode.put("Irish", "ie");
        countryCode.put("Italian", "it");
        countryCode.put("Jamaican", "jm");
        countryCode.put("Japanese", "jp");
        countryCode.put("Kenyan", "ke");
        countryCode.put("Malaysian", "my");
        countryCode.put("Mexican", "mx");
        countryCode.put("Moroccan", "ma");
        countryCode.put("Polish", "pl");
        countryCode.put("Portuguese", "pt");
        countryCode.put("Unknown","ps");
        countryCode.put("Filipino","ph");
        this.thumbnail = "https://www.themealdb.com/images/icons/flags/big/64/" + countryCode.get(strArea) + ".png";
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    @Override
    public String toString() {
        return "Area{" +
                "strArea='" + strArea + '\'' +
                '}';
    }
}