package com.wisam.app.iec;

/**
 * Created by islam on 10/15/16.
 */
public class User {

    private String name;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String job;

    public User(String name, String username, String password, String address, String email, String job, String phone) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.email = email;
        this.job = job;
        this.phone = phone;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
