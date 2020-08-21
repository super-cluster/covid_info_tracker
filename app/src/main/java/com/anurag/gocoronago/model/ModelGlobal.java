package com.anurag.gocoronago.model;

public class ModelGlobal {
    String Country,TotalConfirmed,TotalRecovered,TotalDeaths;

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getTotalConfirmed() {
        return TotalConfirmed;
    }

    public void setTotalConfirmed(String totalConfirmed) {
        TotalConfirmed = totalConfirmed;
    }

    public String getTotalRecovered() {
        return TotalRecovered;
    }

    public void setTotalRecovered(String totalRecovered) {
        TotalRecovered = totalRecovered;
    }

    public String getTotalDeaths() {
        return TotalDeaths;
    }

    public void setTotalDeaths(String totalDeaths) {
        TotalDeaths = totalDeaths;
    }

    public ModelGlobal(String country, String totalConfirmed, String totalRecovered, String totalDeaths) {
        Country = country;
        TotalConfirmed = totalConfirmed;
        TotalRecovered = totalRecovered;
        TotalDeaths = totalDeaths;
    }
}
