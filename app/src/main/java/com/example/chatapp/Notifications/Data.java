package com.example.chatapp.Notifications;

public class Data {
    String user;
    int icon;
    String title;
    String body;
    String sented;

    public Data() {
    }

    public Data(String user, int icon, String title, String body, String sented) {
        this.user = user;
        this.icon = icon;
        this.title = title;
        this.body = body;
        this.sented = sented;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSented() {
        return sented;
    }

    public void setSented(String sented) {
        this.sented = sented;
    }
}
