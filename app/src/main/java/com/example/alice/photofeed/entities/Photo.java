package com.example.alice.photofeed.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by alice on 7/5/16.
 * Data  to  save in FireBase
 *
 *
 */

public class Photo {

    @JsonIgnore
    private  String id;

    @JsonIgnore
    private boolean publishedByMe;

    private String url;
    private String email;
    private double latitud;
    private double longitud;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isPublishedByMe() {
        return publishedByMe;
    }

    public void setPublishedByMe(boolean publishedByMe) {
        this.publishedByMe = publishedByMe;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
