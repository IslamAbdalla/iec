package com.example.islam.iec;

/**
 * Created by islam on 10/20/16.
 */
public class Project {
    private String name;
    private String url;
    private String imageURL;

    public Project(String imageURL, String name, String url) {
        this.imageURL = imageURL;
        this.name = name;
        this.url = url;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
