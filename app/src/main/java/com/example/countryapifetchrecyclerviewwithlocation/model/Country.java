package com.example.countryapifetchrecyclerviewwithlocation.model;

import com.google.gson.annotations.SerializedName;

public class Country {
    private @SerializedName("name")String countryName;
    private @SerializedName("alpha2Code") String countryCode;
    private @SerializedName("capital") String countryCapital;
    private @SerializedName("population") String countryPopulation;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCapital() {
        return countryCapital;
    }

    public void setCountryCapital(String countryCapital) {
        this.countryCapital = countryCapital;
    }

    public String getCountryPopulation() {
        return countryPopulation;
    }

    public void setCountryPopulation(String countryPopulation) {
        this.countryPopulation = countryPopulation;
    }

    @Override
    public String toString() {
        return "Country{" +
                "countryName='" + countryName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", countryCapital='" + countryCapital + '\'' +
                ", countryPopulation='" + countryPopulation + '\'' +
                '}';
    }
}
