package com.chanyouji.chanyouji.model.travel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by annuo on 2015/6/15.
 */
public class Advert  implements Serializable {
    private String position;
    private String imageurl;
    private String adverttype;
    private String content;
    private boolean rotation;


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getAdverttype() {
        return adverttype;
    }

    public void setAdverttype(String adverttype) {
        this.adverttype = adverttype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRotation() {
        return rotation;
    }

    public void setRotation(boolean rotation) {
        this.rotation = rotation;
    }

    public void parseJSON(JSONObject jsonObject) {
        try {
            imageurl = jsonObject.getString("image_url");
            content = jsonObject.getString("content");
            rotation = jsonObject.getBoolean("rotation");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
