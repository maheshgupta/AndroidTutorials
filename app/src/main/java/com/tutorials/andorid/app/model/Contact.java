package com.tutorials.andorid.app.model;

/**
 * Created by mahesh on 8/1/17.
 */

public class Contact {
    public String id;
    public String displayName;
    public Boolean hasPhoneNumber;
    public String phoneNumber;

    public Contact(String id, String displayName, String phoneNumber, String hasPhoneNumber) {
        this.id = id;
        this.displayName = displayName;
        this.hasPhoneNumber = Boolean.parseBoolean(hasPhoneNumber);
        this.phoneNumber = phoneNumber;
    }
}