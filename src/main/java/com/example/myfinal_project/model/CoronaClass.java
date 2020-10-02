package com.example.myfinal_project.model;

public class CoronaClass {
    private String description;
    private String title;
    private String url;



    public CoronaClass(String title, String description, String url) {
        this.description = description;
        this.title = title;
        this.url = url;

    }
    public String getUrl() {
        return url;
    }


    public String getTitle() {
        return title;
    }



    public String getDescription() {
        return description;
    }


}
