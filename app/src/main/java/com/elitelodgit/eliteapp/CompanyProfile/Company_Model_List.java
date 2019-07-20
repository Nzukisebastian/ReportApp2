package com.elitelodgit.eliteapp.CompanyProfile;

public class Company_Model_List {
    String companyname,regno,email,telno;

    public Company_Model_List() {

    };

    public Company_Model_List(String companyname, String regno, String email, String telno) {
        this.companyname = companyname;
        this.regno = regno;
        this.email = email;
        this.telno = telno;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }
}
