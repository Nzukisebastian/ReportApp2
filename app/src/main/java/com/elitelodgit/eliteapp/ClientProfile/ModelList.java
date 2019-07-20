package com.elitelodgit.eliteapp.ClientProfile;

/**
 * Created by SEBASTIAN on 2/1/2018.
 */
public class ModelList {
    String email;
    String phone;
    String name;

    public ModelList() {

    };

    public ModelList(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
