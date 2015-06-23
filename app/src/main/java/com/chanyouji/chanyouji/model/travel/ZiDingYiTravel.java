package com.chanyouji.chanyouji.model.travel;

/**
 * Created by annuo on 2015/6/17.
 */

/**
 * 根据已有的数据结构拼接出自己需要的数据结构
 */
public class ZiDingYiTravel {

    //只有不等于空，我们才存放
    //注意有很多值是不存在的

    //日志的id
    private int id;
    //第几天
    private int day;
    //日期
    private String tripdate;
    //日志描述（""）
    private String description;
    //日志图片
    private String imgUrl;
    //地点（port+loc）（null）
    private String entryname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getTripdate() {
        return tripdate;
    }

    public void setTripdate(String tripdate) {
        this.tripdate = tripdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getEntryname() {
        return entryname;
    }

    public void setEntryname(String entryname) {
        this.entryname = entryname;
    }
}
