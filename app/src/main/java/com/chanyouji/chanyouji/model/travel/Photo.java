package com.chanyouji.chanyouji.model.travel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by annuo on 2015/6/16.
 */
public class Photo implements Serializable {
    private int id;
    private int imagewidth;
    private int imageheight;
    private int imagefilesize;
    private float exiflat;
    private float exiflng;
    private int exifdatetimeoriginal;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImagewidth() {
        return imagewidth;
    }

    public void setImagewidth(int imagewidth) {
        this.imagewidth = imagewidth;
    }

    public int getImageheight() {
        return imageheight;
    }

    public void setImageheight(int imageheight) {
        this.imageheight = imageheight;
    }

    public int getImagefilesize() {
        return imagefilesize;
    }

    public void setImagefilesize(int imagefilesize) {
        this.imagefilesize = imagefilesize;
    }

    public float getExiflat() {
        return exiflat;
    }

    public void setExiflat(float exiflat) {
        this.exiflat = exiflat;
    }

    public float getExiflng() {
        return exiflng;
    }

    public void setExiflng(float exiflng) {
        this.exiflng = exiflng;
    }

    public int getExifdatetimeoriginal() {
        return exifdatetimeoriginal;
    }

    public void setExifdatetimeoriginal(int exifdatetimeoriginal) {
        this.exifdatetimeoriginal = exifdatetimeoriginal;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void pareJSON(JSONObject jsonObject) throws JSONException {
        url = jsonObject.getString("url");
    }
}
