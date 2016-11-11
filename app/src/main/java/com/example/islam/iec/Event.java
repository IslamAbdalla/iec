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
    private String id;

    private String image;
    private @DrawableRes int imageId;
    private String location;
    private String locationText;
    private Boolean upcoming;
    private Boolean isSeparator;
    public ArrayList<Project> projects;
    public PrefManager.ProjectState votedState;

    public Event(String title, String id, String location,String locationText, String date, String description, String image, Boolean upcoming) {
        this.name = title;
        this.id = id;
        this.location = location;
        this.locationText = locationText;
        this.date = date;
        this.description = description;
        this.image = image;
        this.upcoming = upcoming;
        this.isSeparator = false;
        votedState = PrefManager.ProjectState.UNKNOWN;
    }
    public Event(String string){
        this.name = null;
        this.id = null;
        this.location = null;
        this.locationText = null;
        this.date = null;
        this.description = null;
        this.image = null;
        this.upcoming = null;
        this.isSeparator = true;
    }

    public void setProjects (ArrayList<Project> mProjects) {
        projects = mProjects;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean isSeparator() {
        return isSeparator;
    }

    public static class Project implements Parcelable {
        private String name;
        private String image;
        private String link;
        private String id;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
            dest.writeString(this.id);
        }

        public Project(String imageURL, String name, String url, String id) {
            this.image = imageURL;
            this.name = name;
            this.link = url;
            this.id = id;
        }

        protected Project(Parcel in) {
            this.name = in.readString();
            this.image = in.readString();
            this.link = in.readString();
            this.id = in.readString();
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
        dest.writeString(this.id);
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
        this.id = in.readString();
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
