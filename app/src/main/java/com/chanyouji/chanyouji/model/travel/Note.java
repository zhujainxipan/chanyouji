package com.chanyouji.chanyouji.model.travel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by annuo on 2015/6/15.
 */
public class Note implements Serializable {
    private int id;
    private int roworder;
    private String layout;
    private int col;
    private String description;
    private int updated_at;
    private Photo photo;

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

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(int updated_at) {
        this.updated_at = updated_at;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public void pareJSON(JSONObject object) {
        try {
            description = object.getString("description");
            id = object.getInt("id");
            JSONObject jsonObject = object.optJSONObject("photo");
            if (jsonObject != null) {
                photo = new Photo();
                photo.pareJSON(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
