package com.wisam.app.iec;

/**
 * Created by islam on 10/17/16.
 */
public class EventTicket {

    private String eventName;
    private String regCode;
    private String eventID;

    public EventTicket(String eventName, String eventID, String regCode) {
        this.eventName = eventName;
        this.regCode = regCode;
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }
}
