package com.elitelodgit.eliteapp.CompanyReports;

public class CompanyReportModelList {

    String scene;
    String image;
    String executive;
    String evidence;
    String findings;
    String document;
    String subject;
    String observation;
    String conclusion;
    String recommendation;

    public CompanyReportModelList() {

    }

    public CompanyReportModelList(String scene, String image, String executive, String evidence, String findings, String document, String subject, String observation, String conclusion, String recommendation) {
        this.scene = scene;
        this.image = image;
        this.executive = executive;
        this.evidence = evidence;
        this.findings = findings;
        this.document = document;
        this.subject = subject;
        this.observation = observation;
        this.conclusion = conclusion;
        this.recommendation = recommendation;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getExecutive() {
        return executive;
    }

    public void setExecutive(String executive) {
        this.executive = executive;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public String getFindings() {
        return findings;
    }

    public void setFindings(String findings) {
        this.findings = findings;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}
