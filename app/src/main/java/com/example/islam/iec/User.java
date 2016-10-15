package com.example.islam.iec;

/**
 * Created by islam on 10/15/16.
 */
public class User {

    private String username;
    private String email;
    private String phone;
    private String address;
    private String job;

    public User(String address, String email, String job, String phone, String username) {
        this.address = address;
        this.email = email;
        this.job = job;
        this.phone = phone;
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
