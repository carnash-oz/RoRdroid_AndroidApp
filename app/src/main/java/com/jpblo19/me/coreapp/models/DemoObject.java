package com.jpblo19.me.coreapp.models;

/**
 * Created by jpblo19 on 5/16/16.
 */
public class DemoObject {

    private float gpslatitud;
    private float gpslongitud;

    private String title;
    private String info;
    private String address;

    public DemoObject(){
        gpslatitud = 0.0f;
        gpslongitud = 0.0f;

        title = "";
        info = "";
        address = "";
    }

    public float getGpslatitud() {
        return gpslatitud;
    }

    public void setGpslatitud(float gpslatitud) {
        this.gpslatitud = gpslatitud;
    }

    public float getGpslongitud() {
        return gpslongitud;
    }

    public void setGpslongitud(float gpslongitud) {
        this.gpslongitud = gpslongitud;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
