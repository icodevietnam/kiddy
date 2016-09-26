package com.coursework.com.coursework.domain;

public class Bird {
    private Integer id;
    private String birdName;
    private String location;
    private String date;
    private String time;
    private String watcherName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBirdName() {
        return birdName;
    }

    public void setBirdName(String birdName) {
        this.birdName = birdName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWatcherName() {
        return watcherName;
    }

    public void setWatcherName(String watcherName) {
        this.watcherName = watcherName;
    }
}
