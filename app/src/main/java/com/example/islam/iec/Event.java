package com.example.islam.iec;

import android.location.Location;
import android.support.annotation.DrawableRes;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by islam on 9/15/16.
 */
public class Event {
    private String name;
    private String date;
    private String description;
    private String image;
    private @DrawableRes int imageId;
    private String location;
    private String locationText;
    private Boolean upcoming;
    private Boolean isSeparator;
    private ArrayList<Project> projects;

    public Event(String title, String location,String locationText, String date, String description, @DrawableRes int imageId, Boolean upcoming) {
        this.name = title;
        this.location = location;
        this.locationText = locationText;
        this.date = date;
        this.description = description;
        this.imageId = imageId;
        this.upcoming = upcoming;
        this.isSeparator = false;
    }
    public Event(String string){
        this.name = null;
        this.location = null;
        this.locationText = null;
        this.date = null;
        this.description = null;
        this.imageId = 0;
        this.upcoming = null;
        this.isSeparator = true;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public @DrawableRes int getImageId() {
        return imageId;
    }

    public void setImageId( @DrawableRes int imageId) {
        this.imageId = imageId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationText() {
        return locationText;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
    }

    public Boolean getUpcoming() {
        return upcoming;
    }

    public void setUpcoming(Boolean upcoming) {
        this.upcoming = upcoming;
    }

    public Boolean isSeparator() {
        return isSeparator;
    }

    private class Project {
        private String name;
        private String image;
        private String link;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

    }
}
