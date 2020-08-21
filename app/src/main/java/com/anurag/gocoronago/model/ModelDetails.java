package com.anurag.gocoronago.model;

public class ModelDetails {
    private String loc;
    private int totalConfirmed;
    private int deaths;
    private int dischared;


    public ModelDetails(String loc, int totalConfirmed, int deaths, int dischared) {
        this.loc = loc;
        this.totalConfirmed = totalConfirmed;
        this.deaths = deaths;
        this.dischared = dischared;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public int getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(int totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getDischared() {
        return dischared;
    }

    public void setDischared(int dischared) {
        this.dischared = dischared;
    }

    @Override
    public String toString() {
        return "ModelDetails{" +
                "loc='" + loc + '\'' +
                ", totalConfirmed=" + totalConfirmed +
                ", deaths=" + deaths +
                ", dischared=" + dischared +
                '}';
    }
}
