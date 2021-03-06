package com.example.album_admin.model;

public class Album {

    private int albumId;

    private String albumCode;

    private String albumName;

    private String photo1;
    private String photo2;

    private float minWt;
    private float maxWt;

    private int delStatus;

    private int spId;

    private String flavourId;
    private int isActive;
    private int isVisibleToAlbum;
    private String albumDesc;

    public Album(int albumId, String albumCode, String albumName, String photo1, String photo2, float minWt, float maxWt, int delStatus, int spId, String flavourId, int isActive, int isVisibleToAlbum, String albumDesc) {
        this.albumId = albumId;
        this.albumCode = albumCode;
        this.albumName = albumName;
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.minWt = minWt;
        this.maxWt = maxWt;
        this.delStatus = delStatus;
        this.spId = spId;
        this.flavourId = flavourId;
        this.isActive = isActive;
        this.isVisibleToAlbum = isVisibleToAlbum;
        this.albumDesc = albumDesc;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
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

    public float getMinWt() {
        return minWt;
    }

    public void setMinWt(float minWt) {
        this.minWt = minWt;
    }

    public float getMaxWt() {
        return maxWt;
    }

    public void setMaxWt(float maxWt) {
        this.maxWt = maxWt;
    }

    public int getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(int delStatus) {
        this.delStatus = delStatus;
    }

    public int getSpId() {
        return spId;
    }

    public void setSpId(int spId) {
        this.spId = spId;
    }

    public String getFlavourId() {
        return flavourId;
    }

    public void setFlavourId(String flavourId) {
        this.flavourId = flavourId;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getIsVisibleToAlbum() {
        return isVisibleToAlbum;
    }

    public void setIsVisibleToAlbum(int isVisibleToAlbum) {
        this.isVisibleToAlbum = isVisibleToAlbum;
    }

    public String getAlbumDesc() {
        return albumDesc;
    }

    public void setAlbumDesc(String albumDesc) {
        this.albumDesc = albumDesc;
    }

    @Override
    public String toString() {
        return "Album{" +
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
                ", albumDesc='" + albumDesc + '\'' +
                '}';
    }
}
