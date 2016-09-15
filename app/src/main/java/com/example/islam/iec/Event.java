package com.example.islam.iec;

import android.location.Location;
import android.support.annotation.BoolRes;
import android.support.annotation.DrawableRes;

import java.util.Date;

/**
 * Created by islam on 9/15/16.
 */
public class Event {
    private String title;
    private Date date;
    private String description;
    private @DrawableRes int imageId;
    private Location location;
    private String locationText;
    private Boolean upcoming;
    private Boolean isSeparator;

    public Event(String title, Location location,String locationText, Date date, String description, @DrawableRes int imageId, Boolean upcoming) {
        this.title = title;
        this.location = location;
        this.locationText = locationText;
        this.date = date;
        this.description = description;
        this.imageId = imageId;
        this.upcoming = upcoming;
        this.isSeparator = false;
    }
    public Event(String string){
        this.title = null;
        this.location = null;
        this.locationText = null;
        this.date = null;
        this.description = null;
        this.imageId = 0;
        this.upcoming = null;
        this.isSeparator = true;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
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
}
