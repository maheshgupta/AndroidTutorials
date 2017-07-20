package com.tutorials.andorid.app.model;

public class Address
{
    private String street;

    public String getStreet() { return this.street; }

    public void setStreet(String street) { this.street = street; }

    private String suite;

    public String getSuite() { return this.suite; }

    public void setSuite(String suite) { this.suite = suite; }

    private String city;

    public String getCity() { return this.city; }

    public void setCity(String city) { this.city = city; }

    private String zipcode;

    public String getZipcode() { return this.zipcode; }

    public void setZipcode(String zipcode) { this.zipcode = zipcode; }

    private Geo geo;

    public Geo getGeo() { return this.geo; }

    public void setGeo(Geo geo) { this.geo = geo; }
}