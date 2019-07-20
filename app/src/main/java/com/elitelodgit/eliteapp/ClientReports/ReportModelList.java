package com.elitelodgit.eliteapp.ClientReports;

public class ReportModelList {
    String nature;
    String date;
    String image;
    String time;
    String place;
    String parties;
    String insurance;
    String policy;
    String reference;

    public ReportModelList() {

    };

    public ReportModelList(String nature, String date, String image, String time, String place, String parties, String insurance, String policy, String reference) {
        this.nature = nature;
        this.date = date;
        this.image = image;
        this.time = time;
        this.place = place;
        this.parties = parties;
        this.insurance = insurance;
        this.policy = policy;
        this.reference = reference;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getParties() {
        return parties;
    }

    public void setParties(String parties) {
        this.parties = parties;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
