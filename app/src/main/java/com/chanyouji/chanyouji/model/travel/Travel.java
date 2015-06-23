package com.chanyouji.chanyouji.model.travel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by annuo on 2015/6/15.
 */
public class Travel implements Serializable {
    private int id;
    private String name;
    private int photoscount;
    private String startdate;
    private String enddate;
    private int days;
    private int level;
    private int viewscount;
    private int commentscount;
    private int likescount;
    private String source;
    private String frontcoverphotourl;
    private boolean featured;
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhotoscount() {
        return photoscount;
    }

    public void setPhotoscount(int photoscount) {
        this.photoscount = photoscount;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getViewscount() {
        return viewscount;
    }

    public void setViewscount(int viewscount) {
        this.viewscount = viewscount;
    }

    public int getCommentscount() {
        return commentscount;
    }

    public void setCommentscount(int commentscount) {
        this.commentscount = commentscount;
    }

    public int getLikescount() {
        return likescount;
    }

    public void setLikescount(int likescount) {
        this.likescount = likescount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFrontcoverphotourl() {
        return frontcoverphotourl;
    }

    public void setFrontcoverphotourl(String frontcoverphotourl) {
        this.frontcoverphotourl = frontcoverphotourl;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void pareJSON(JSONObject jsonObject) {
        try {
            id = jsonObject.getInt("id");
            name = jsonObject.getString("name");
            photoscount = jsonObject.getInt("photos_count");
            days = jsonObject.getInt("days");
            startdate = jsonObject.getString("start_date");
            frontcoverphotourl = jsonObject.getString("front_cover_photo_url");
            user = new User();
            user.pareJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
