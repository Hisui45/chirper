package models;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String name;
    private String username;
    private String email_address;
    private String password_hash;
    private boolean following;

    public User() {

    }

    public User(int id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }

    public User(int id, String name, String username, boolean isFollowing) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.following = isFollowing;
    }

    public User(String name, String username, String email_address, String password_hash) {
        this.name = name;
        this.username = username;
        this.email_address = email_address;
        this.password_hash = password_hash;
    }

    public User(int id, String name, String username, String email_address, String password_hash) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email_address = email_address;
        this.password_hash = password_hash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return email_address;
    }

    public void setEmailAddress(String email_address) {
        this.email_address = email_address;
    }

    public String getPasswordHash() {
        return password_hash;
    }

    public void setPasswordHash(String password_hash) {
        this.password_hash = password_hash;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

}
