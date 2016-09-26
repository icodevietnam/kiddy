package com.coursework.com.coursework.domain;

public class Report {
    private Integer id;
    private String name;
    private String description;
    private String kiddyId;


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

    public String getKiddyId() {
        return kiddyId;
    }

    public void setKiddyId(String kiddyId) {
        this.kiddyId = kiddyId;
    }
}
