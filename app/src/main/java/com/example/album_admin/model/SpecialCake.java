package com.example.album_admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecialCake {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private Object message;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("spId")
    @Expose
    private Integer spId;
    @SerializedName("spName")
    @Expose
    private String spName;
    @SerializedName("spHsncd")
    @Expose
    private String spHsncd;
    @SerializedName("spUom")
    @Expose
    private String spUom;
    @SerializedName("spCess")
    @Expose
    private Integer spCess;
    @SerializedName("cutSection")
    @Expose
    private Integer cutSection;
    @SerializedName("isTallySync")
    @Expose
    private Integer isTallySync;
    @SerializedName("delStatus")
    @Expose
    private Integer delStatus;
    @SerializedName("uomId")
    @Expose
    private Integer uomId;
    @SerializedName("spCode")
    @Expose
    private String spCode;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getSpHsncd() {
        return spHsncd;
    }

    public void setSpHsncd(String spHsncd) {
        this.spHsncd = spHsncd;
    }

    public String getSpUom() {
        return spUom;
    }

    public void setSpUom(String spUom) {
        this.spUom = spUom;
    }

    public Integer getSpCess() {
        return spCess;
    }

    public void setSpCess(Integer spCess) {
        this.spCess = spCess;
    }

    public Integer getCutSection() {
        return cutSection;
    }

    public void setCutSection(Integer cutSection) {
        this.cutSection = cutSection;
    }

    public Integer getIsTallySync() {
        return isTallySync;
    }

    public void setIsTallySync(Integer isTallySync) {
        this.isTallySync = isTallySync;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public Integer getUomId() {
        return uomId;
    }

    public void setUomId(Integer uomId) {
        this.uomId = uomId;
    }

    public String getSpCode() {
        return spCode;
    }

    public void setSpCode(String spCode) {
        this.spCode = spCode;
    }

    @Override
    public String toString() {
        return "SpecialCake{" +
                "error=" + error +
                ", message=" + message +
                ", id=" + id +
                ", spId=" + spId +
                ", spName='" + spName + '\'' +
                ", spHsncd='" + spHsncd + '\'' +
                ", spUom='" + spUom + '\'' +
                ", spCess=" + spCess +
                ", cutSection=" + cutSection +
                ", isTallySync=" + isTallySync +
                ", delStatus=" + delStatus +
                ", uomId=" + uomId +
                ", spCode='" + spCode + '\'' +
                '}';
    }
}
