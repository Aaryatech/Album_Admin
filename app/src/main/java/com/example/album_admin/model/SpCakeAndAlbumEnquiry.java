package com.example.album_admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpCakeAndAlbumEnquiry {

    @SerializedName("albumId")
    @Expose
    private Integer albumId;
    @SerializedName("albumCode")
    @Expose
    private String albumCode;
    @SerializedName("albumName")
    @Expose
    private String albumName;
    @SerializedName("photo1")
    @Expose
    private String photo1;
    @SerializedName("photo2")
    @Expose
    private String photo2;
    @SerializedName("minWt")
    @Expose
    private Integer minWt;
    @SerializedName("maxWt")
    @Expose
    private Integer maxWt;
    @SerializedName("delStatus")
    @Expose
    private Integer delStatus;
    @SerializedName("spId")
    @Expose
    private Integer spId;
    @SerializedName("flavourId")
    @Expose
    private String flavourId;
    @SerializedName("isActive")
    @Expose
    private Integer isActive;
    @SerializedName("isVisibleToAlbum")
    @Expose
    private Integer isVisibleToAlbum;
    @SerializedName("enquiryNo")
    @Expose
    private Integer enquiryNo;
    @SerializedName("custName")
    @Expose
    private String custName;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("enquiryDate")
    @Expose
    private String enquiryDate;
    @SerializedName("enquiryDateTime")
    @Expose
    private String enquiryDateTime;
    @SerializedName("approvedDateTime")
    @Expose
    private String approvedDateTime;
    @SerializedName("approvedUserId")
    @Expose
    private Integer approvedUserId;
    @SerializedName("approvedUserName")
    @Expose
    private String approvedUserName;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("noNotifictnFired")
    @Expose
    private Integer noNotifictnFired;
    @SerializedName("exVar1")
    @Expose
    private String exVar1;
    @SerializedName("exVar2")
    @Expose
    private String exVar2;
    @SerializedName("exInt1")
    @Expose
    private Integer exInt1;
    @SerializedName("exInt2")
    @Expose
    private Integer exInt2;
    @SerializedName("frId")
    @Expose
    private Integer frId;
    @SerializedName("frName")
    @Expose
    private String frName;
    @SerializedName("albumDesc")
    @Expose
    private String albumDesc;

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getAlbumCode() {
        return albumCode;
    }

    public void setAlbumCode(String albumCode) {
        this.albumCode = albumCode;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public Integer getMinWt() {
        return minWt;
    }

    public void setMinWt(Integer minWt) {
        this.minWt = minWt;
    }

    public Integer getMaxWt() {
        return maxWt;
    }

    public void setMaxWt(Integer maxWt) {
        this.maxWt = maxWt;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public String getFlavourId() {
        return flavourId;
    }

    public void setFlavourId(String flavourId) {
        this.flavourId = flavourId;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getIsVisibleToAlbum() {
        return isVisibleToAlbum;
    }

    public void setIsVisibleToAlbum(Integer isVisibleToAlbum) {
        this.isVisibleToAlbum = isVisibleToAlbum;
    }

    public Integer getEnquiryNo() {
        return enquiryNo;
    }

    public void setEnquiryNo(Integer enquiryNo) {
        this.enquiryNo = enquiryNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEnquiryDate() {
        return enquiryDate;
    }

    public void setEnquiryDate(String enquiryDate) {
        this.enquiryDate = enquiryDate;
    }

    public String getEnquiryDateTime() {
        return enquiryDateTime;
    }

    public void setEnquiryDateTime(String enquiryDateTime) {
        this.enquiryDateTime = enquiryDateTime;
    }

    public String getApprovedDateTime() {
        return approvedDateTime;
    }

    public void setApprovedDateTime(String approvedDateTime) {
        this.approvedDateTime = approvedDateTime;
    }

    public Integer getApprovedUserId() {
        return approvedUserId;
    }

    public void setApprovedUserId(Integer approvedUserId) {
        this.approvedUserId = approvedUserId;
    }

    public String getApprovedUserName() {
        return approvedUserName;
    }

    public void setApprovedUserName(String approvedUserName) {
        this.approvedUserName = approvedUserName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNoNotifictnFired() {
        return noNotifictnFired;
    }

    public void setNoNotifictnFired(Integer noNotifictnFired) {
        this.noNotifictnFired = noNotifictnFired;
    }

    public String getExVar1() {
        return exVar1;
    }

    public void setExVar1(String exVar1) {
        this.exVar1 = exVar1;
    }

    public String getExVar2() {
        return exVar2;
    }

    public void setExVar2(String exVar2) {
        this.exVar2 = exVar2;
    }

    public Integer getExInt1() {
        return exInt1;
    }

    public void setExInt1(Integer exInt1) {
        this.exInt1 = exInt1;
    }

    public Integer getExInt2() {
        return exInt2;
    }

    public void setExInt2(Integer exInt2) {
        this.exInt2 = exInt2;
    }

    public Integer getFrId() {
        return frId;
    }

    public void setFrId(Integer frId) {
        this.frId = frId;
    }

    public String getFrName() {
        return frName;
    }

    public void setFrName(String frName) {
        this.frName = frName;
    }

    public String getAlbumDesc() {
        return albumDesc;
    }

    public void setAlbumDesc(String albumDesc) {
        this.albumDesc = albumDesc;
    }

    @Override
    public String toString() {
        return "SpCakeAndAlbumEnquiry{" +
                "albumId=" + albumId +
                ", albumCode='" + albumCode + '\'' +
                ", albumName='" + albumName + '\'' +
                ", photo1='" + photo1 + '\'' +
                ", photo2='" + photo2 + '\'' +
                ", minWt=" + minWt +
                ", maxWt=" + maxWt +
                ", delStatus=" + delStatus +
                ", spId=" + spId +
                ", flavourId='" + flavourId + '\'' +
                ", isActive=" + isActive +
                ", isVisibleToAlbum=" + isVisibleToAlbum +
                ", enquiryNo=" + enquiryNo +
                ", custName='" + custName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", photo='" + photo + '\'' +
                ", enquiryDate='" + enquiryDate + '\'' +
                ", enquiryDateTime='" + enquiryDateTime + '\'' +
                ", approvedDateTime='" + approvedDateTime + '\'' +
                ", approvedUserId=" + approvedUserId +
                ", approvedUserName='" + approvedUserName + '\'' +
                ", status=" + status +
                ", noNotifictnFired=" + noNotifictnFired +
                ", exVar1='" + exVar1 + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", frId=" + frId +
                ", frName='" + frName + '\'' +
                ", albumDesc='" + albumDesc + '\'' +
                '}';
    }
}
