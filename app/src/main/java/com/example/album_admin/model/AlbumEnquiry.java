package com.example.album_admin.model;

public class AlbumEnquiry {

    private int enquiryNo;
    private int frId;
    private String custName;
    private String mobileNo;
    private String photo;
    private String enquiryDate;
    private String enquiryDateTime;
    private String approvedDateTime;
    private int approvedUserId;
    private String approvedUserName;
    private int albumId;
    private int status;
    private int delStatus;
    private int noNotifictnFired;
    private String exVar1;
    private String exVar2;
    private String exVar3;
    private int exInt1;
    private int exInt2;
    private int exInt3;


    public AlbumEnquiry(int enquiryNo, int frId, String custName, String mobileNo, String photo, String enquiryDate, String enquiryDateTime, String approvedDateTime, int approvedUserId, String approvedUserName, int albumId, int status, int delStatus, int noNotifictnFired, String exVar1, String exVar2, String exVar3, int exInt1, int exInt2, int exInt3) {
        this.enquiryNo = enquiryNo;
        this.frId = frId;
        this.custName = custName;
        this.mobileNo = mobileNo;
        this.photo = photo;
        this.enquiryDate = enquiryDate;
        this.enquiryDateTime = enquiryDateTime;
        this.approvedDateTime = approvedDateTime;
        this.approvedUserId = approvedUserId;
        this.approvedUserName = approvedUserName;
        this.albumId = albumId;
        this.status = status;
        this.delStatus = delStatus;
        this.noNotifictnFired = noNotifictnFired;
        this.exVar1 = exVar1;
        this.exVar2 = exVar2;
        this.exVar3 = exVar3;
        this.exInt1 = exInt1;
        this.exInt2 = exInt2;
        this.exInt3 = exInt3;
    }

    public int getEnquiryNo() {
        return enquiryNo;
    }

    public void setEnquiryNo(int enquiryNo) {
        this.enquiryNo = enquiryNo;
    }

    public int getFrId() {
        return frId;
    }

    public void setFrId(int frId) {
        this.frId = frId;
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

    public int getApprovedUserId() {
        return approvedUserId;
    }

    public void setApprovedUserId(int approvedUserId) {
        this.approvedUserId = approvedUserId;
    }

    public String getApprovedUserName() {
        return approvedUserName;
    }

    public void setApprovedUserName(String approvedUserName) {
        this.approvedUserName = approvedUserName;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(int delStatus) {
        this.delStatus = delStatus;
    }

    public int getNoNotifictnFired() {
        return noNotifictnFired;
    }

    public void setNoNotifictnFired(int noNotifictnFired) {
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

    public String getExVar3() {
        return exVar3;
    }

    public void setExVar3(String exVar3) {
        this.exVar3 = exVar3;
    }

    public int getExInt1() {
        return exInt1;
    }

    public void setExInt1(int exInt1) {
        this.exInt1 = exInt1;
    }

    public int getExInt2() {
        return exInt2;
    }

    public void setExInt2(int exInt2) {
        this.exInt2 = exInt2;
    }

    public int getExInt3() {
        return exInt3;
    }

    public void setExInt3(int exInt3) {
        this.exInt3 = exInt3;
    }

    @Override
    public String toString() {
        return "AlbumEnquiry{" +
                "enquiryNo=" + enquiryNo +
                ", frId=" + frId +
                ", custName='" + custName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", photo='" + photo + '\'' +
                ", enquiryDate='" + enquiryDate + '\'' +
                ", enquiryDateTime='" + enquiryDateTime + '\'' +
                ", approvedDateTime='" + approvedDateTime + '\'' +
                ", approvedUserId=" + approvedUserId +
                ", approvedUserName='" + approvedUserName + '\'' +
                ", albumId=" + albumId +
                ", status=" + status +
                ", delStatus=" + delStatus +
                ", noNotifictnFired=" + noNotifictnFired +
                ", exVar1='" + exVar1 + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                ", exVar3='" + exVar3 + '\'' +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exInt3=" + exInt3 +
                '}';
    }
}
