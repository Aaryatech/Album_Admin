package com.example.album_admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SettingValues {
    @SerializedName("settingId")
    @Expose
    private Integer settingId;
    @SerializedName("settingKey")
    @Expose
    private String settingKey;
    @SerializedName("settingValue1")
    @Expose
    private String settingValue1;
    @SerializedName("settingValue2")
    @Expose
    private String settingValue2;
    @SerializedName("exVarchar1")
    @Expose
    private String exVarchar1;
    @SerializedName("exVarchar2")
    @Expose
    private String exVarchar2;
    @SerializedName("exVarchar3")
    @Expose
    private String exVarchar3;
    @SerializedName("delStatus")
    @Expose
    private Integer delStatus;

    public Integer getSettingId() {
        return settingId;
    }

    public void setSettingId(Integer settingId) {
        this.settingId = settingId;
    }

    public String getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(String settingKey) {
        this.settingKey = settingKey;
    }

    public String getSettingValue1() {
        return settingValue1;
    }

    public void setSettingValue1(String settingValue1) {
        this.settingValue1 = settingValue1;
    }

    public String getSettingValue2() {
        return settingValue2;
    }

    public void setSettingValue2(String settingValue2) {
        this.settingValue2 = settingValue2;
    }

    public String getExVarchar1() {
        return exVarchar1;
    }

    public void setExVarchar1(String exVarchar1) {
        this.exVarchar1 = exVarchar1;
    }

    public String getExVarchar2() {
        return exVarchar2;
    }

    public void setExVarchar2(String exVarchar2) {
        this.exVarchar2 = exVarchar2;
    }

    public String getExVarchar3() {
        return exVarchar3;
    }

    public void setExVarchar3(String exVarchar3) {
        this.exVarchar3 = exVarchar3;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    @Override
    public String toString() {
        return "SettingValues{" +
                "settingId=" + settingId +
                ", settingKey='" + settingKey + '\'' +
                ", settingValue1='" + settingValue1 + '\'' +
                ", settingValue2='" + settingValue2 + '\'' +
                ", exVarchar1='" + exVarchar1 + '\'' +
                ", exVarchar2='" + exVarchar2 + '\'' +
                ", exVarchar3='" + exVarchar3 + '\'' +
                ", delStatus=" + delStatus +
                '}';
    }
}
