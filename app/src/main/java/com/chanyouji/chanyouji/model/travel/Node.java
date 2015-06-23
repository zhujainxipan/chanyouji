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
public class Node implements Serializable {
    private int id;
    private int roworder;
    private int score;
    private String comment;
    private List<Tip> tips;
    private Memo memo;
    private int entryid;
    private float lat;
    private float lng;
    private String entrytype;
    private boolean userentry;
    private String entryname;
    private String attractiontype;
    private int updatedat;
    private List<Note> notes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoworder() {
        return roworder;
    }

    public void setRoworder(int roworder) {
        this.roworder = roworder;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Tip> getTips() {
        return tips;
    }

    public void setTips(List<Tip> tips) {
        this.tips = tips;
    }

    public Memo getMemo() {
        return memo;
    }

    public void setMemo(Memo memo) {
        this.memo = memo;
    }

    public int getEntryid() {
        return entryid;
    }

    public void setEntryid(int entryid) {
        this.entryid = entryid;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public String getEntrytype() {
        return entrytype;
    }

    public void setEntrytype(String entrytype) {
        this.entrytype = entrytype;
    }

    public boolean isUserentry() {
        return userentry;
    }

    public void setUserentry(boolean userentry) {
        this.userentry = userentry;
    }

    public String getEntryname() {
        return entryname;
    }

    public void setEntryname(String entryname) {
        this.entryname = entryname;
    }

    public String getAttractiontype() {
        return attractiontype;
    }

    public void setAttractiontype(String attractiontype) {
        this.attractiontype = attractiontype;
    }

    public int getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(int updatedat) {
        this.updatedat = updatedat;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void pareJSON(JSONObject jsonObject) {
        try {
            entryname = jsonObject.getString("entry_name");
            JSONArray jsonArray = jsonObject.getJSONArray("notes");
            notes = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Note note = new Note();
                JSONObject object = (JSONObject) jsonArray.get(i);
                note.pareJSON(object);
                notes.add(note);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
