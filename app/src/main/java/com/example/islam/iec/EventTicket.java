package com.example.islam.iec;

/**
 * Created by islam on 10/17/16.
 */
public class EventTicket {

    private String eventName;
    private String regCode;

    public EventTicket(String eventName, String regCode) {
        this.eventName = eventName;
        this.regCode = regCode;
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
}
