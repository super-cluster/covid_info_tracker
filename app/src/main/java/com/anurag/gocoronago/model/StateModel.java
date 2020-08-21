package com.anurag.gocoronago.model;

public class StateModel {
    String state,statecode,district,confirmed,active,recovered,deceased;

    public StateModel(String state, String statecode) {
        this.state = state;
        this.statecode = statecode;
    }

    public StateModel( String district, String confirmed, String active, String recovered, String deceased) {
        this.district = district;
        this.confirmed = confirmed;
        this.active = active;
        this.recovered = recovered;
        this.deceased = deceased;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }
}
