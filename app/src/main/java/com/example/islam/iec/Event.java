package com.example.islam.iec;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by islam on 9/15/16.
 */
public class Event implements Parcelable{
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

    public Event(String title, String location,String locationText, String date, String description, String image, Boolean upcoming) {
        this.name = title;
        this.location = location;
        this.locationText = locationText;
        this.date = date;
        this.description = description;
        this.image = image;
        this.upcoming = upcoming;
        this.isSeparator = false;
    }
    public Event(String string){
        this.name = null;
        this.location = null;
        this.locationText = null;
        this.date = null;
        this.description = null;
        this.image = null;
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

    private static class Project implements Parcelable {
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


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeString(this.image);
            dest.writeString(this.link);
        }

        public Project() {
        }

        protected Project(Parcel in) {
            this.name = in.readString();
            this.image = in.readString();
            this.link = in.readString();
        }

        public static final Creator<Project> CREATOR = new Creator<Project>() {
            @Override
            public Project createFromParcel(Parcel source) {
                return new Project(source);
            }

            @Override
            public Project[] newArray(int size) {
                return new Project[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.date);
        dest.writeString(this.description);
        dest.writeString(this.image);
        dest.writeInt(this.imageId);
        dest.writeString(this.location);
        dest.writeString(this.locationText);
        dest.writeValue(this.upcoming);
        dest.writeValue(this.isSeparator);
        dest.writeList(this.projects);
    }

    protected Event(Parcel in) {
        this.name = in.readString();
        this.date = in.readString();
        this.description = in.readString();
        this.image = in.readString();
        this.imageId = in.readInt();
        this.location = in.readString();
        this.locationText = in.readString();
        this.upcoming = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.isSeparator = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.projects = new ArrayList<Project>();
        in.readList(this.projects, Project.class.getClassLoader());
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
