package com.example.chatapp.Model;

public class User {
    String id;
    String username;
    String imageUrl;
    String status;
    String search;

    public User(String id, String username, String imageUrl,String status ,String search) {
        this.id = id;
        this.username = username;
        this.imageUrl = imageUrl;
        this.status=status;
        this.search=search;
    }

    public User() {
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
