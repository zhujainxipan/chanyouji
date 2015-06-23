package com.chanyouji.chanyouji.model.travel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by annuo on 2015/6/15.
 */
public class TravelDetail implements Serializable  {
    private int id;
    private String name;
    private int photoscount;
    private String startdate;
    private String enddate;
    private int level;
    private boolean privacy;
    private int frontcoverphotoid;
    private int viewscount;
    private int commentscount;
    private int likescount;
    private int favoritescount;
    private String state;
    private String source;
    private String serial_id;
    private String serialposition;
    private int serialnextid;
    private int updatedat;
    private List<TripDay> tripdays;
    private Tip tip;
    private User user;
    private String uploadtoken;
    private boolean currentuserfavorite;
    private String password;
    private String frontcoverphotourl;
    private List<NotesComment> notes_likes_comments;

    public void pareJSON(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            id = jsonObject.getInt("id");
            favoritescount = jsonObject.getInt("favorites_count");
            commentscount = jsonObject.getInt("comments_count");
            tripdays = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray("trip_days");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                TripDay tripDay = new TripDay();
                tripDay.pareJSON(object);
                tripdays.add(tripDay);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isPrivacy() {
        return privacy;
    }

    public void setPrivacy(boolean privacy) {
        this.privacy = privacy;
    }

    public int getFrontcoverphotoid() {
        return frontcoverphotoid;
    }

    public void setFrontcoverphotoid(int frontcoverphotoid) {
        this.frontcoverphotoid = frontcoverphotoid;
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

    public int getFavoritescount() {
        return favoritescount;
    }

    public void setFavoritescount(int favoritescount) {
        this.favoritescount = favoritescount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSerial_id() {
        return serial_id;
    }

    public void setSerial_id(String serial_id) {
        this.serial_id = serial_id;
    }

    public String getSerialposition() {
        return serialposition;
    }

    public void setSerialposition(String serialposition) {
        this.serialposition = serialposition;
    }

    public int getSerialnextid() {
        return serialnextid;
    }

    public void setSerialnextid(int serialnextid) {
        this.serialnextid = serialnextid;
    }

    public int getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(int updatedat) {
        this.updatedat = updatedat;
    }

    public List<TripDay> getTripdays() {
        return tripdays;
    }

    public void setTripdays(List<TripDay> tripdays) {
        this.tripdays = tripdays;
    }

    public Tip getTip() {
        return tip;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUploadtoken() {
        return uploadtoken;
    }

    public void setUploadtoken(String uploadtoken) {
        this.uploadtoken = uploadtoken;
    }

    public boolean isCurrentuserfavorite() {
        return currentuserfavorite;
    }

    public void setCurrentuserfavorite(boolean currentuserfavorite) {
        this.currentuserfavorite = currentuserfavorite;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFrontcoverphotourl() {
        return frontcoverphotourl;
    }

    public void setFrontcoverphotourl(String frontcoverphotourl) {
        this.frontcoverphotourl = frontcoverphotourl;
    }

    public List<NotesComment> getNotes_likes_comments() {
        return notes_likes_comments;
    }

    public void setNotes_likes_comments(List<NotesComment> notes_likes_comments) {
        this.notes_likes_comments = notes_likes_comments;
    }
}
