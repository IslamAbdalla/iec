package com.wisam.app.iec;

/**
 * Created by islam on 10/20/16.
 */
public class Project {
    private String name;
    private String link;
    private String image;
    private String id;

    public Project(String imageURL, String name, String url, String id) {
        this.image = imageURL;
        this.name = name;
        this.link = url;
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
