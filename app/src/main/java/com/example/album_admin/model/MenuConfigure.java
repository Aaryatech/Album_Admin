package com.example.album_admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuConfigure {

    @SerializedName("settingId")
    @Expose
    private Integer settingId;
    @SerializedName("frId")
    @Expose
    private Integer frId;
    @SerializedName("menuId")
    @Expose
    private Integer menuId;
    @SerializedName("catId")
    @Expose
    private Integer catId;
    @SerializedName("subCatId")
    @Expose
    private Integer subCatId;
    @SerializedName("settingType")
    @Expose
    private Integer settingType;
    @SerializedName("fromTime")
    @Expose
    private String fromTime;
    @SerializedName("toTime")
    @Expose
    private String toTime;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("itemShow")
    @Expose
    private String itemShow;
    @SerializedName("delStatus")
    @Expose
    private Integer delStatus;

    public Integer getSettingId() {
        return settingId;
    }

    public void setSettingId(Integer settingId) {
        this.settingId = settingId;
    }

    public Integer getFrId() {
        return frId;
    }

    public void setFrId(Integer frId) {
        this.frId = frId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public Integer getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(Integer subCatId) {
        this.subCatId = subCatId;
    }

    public Integer getSettingType() {
        return settingType;
    }

    public void setSettingType(Integer settingType) {
        this.settingType = settingType;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getItemShow() {
        return itemShow;
    }

    public void setItemShow(String itemShow) {
        this.itemShow = itemShow;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    @Override
    public String toString() {
        return "MenuConfigure{" +
                "settingId=" + settingId +
                ", frId=" + frId +
                ", menuId=" + menuId +
                ", catId=" + catId +
                ", subCatId=" + subCatId +
                ", settingType=" + settingType +
                ", fromTime='" + fromTime + '\'' +
                ", toTime='" + toTime + '\'' +
                ", day='" + day + '\'' +
                ", date='" + date + '\'' +
                ", itemShow='" + itemShow + '\'' +
                ", delStatus=" + delStatus +
                '}';
    }
}
