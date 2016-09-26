package com.coursework.com.coursework.domain;

public class Report {
    private Integer id;
    private String name;
    private String description;
    private String birdId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getBirdId() {
        return birdId;
    }

    public void setBirdId(String birdId) {
        this.birdId = birdId;
    }
}
