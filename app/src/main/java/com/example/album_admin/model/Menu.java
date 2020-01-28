package com.example.album_admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Menu {

    @SerializedName("menuId")
    @Expose
    private Integer menuId;
    @SerializedName("menuTitle")
    @Expose
    private String menuTitle;
    @SerializedName("menuDesc")
    @Expose
    private String menuDesc;
    @SerializedName("menuImage")
    @Expose
    private String menuImage;
    @SerializedName("selectedMenuImage")
    @Expose
    private String selectedMenuImage;
    @SerializedName("isSameDayApplicable")
    @Expose
    private String isSameDayApplicable;
    @SerializedName("mainCatId")
    @Expose
    private Integer mainCatId;
    @SerializedName("delStatus")
    @Expose
    private Integer delStatus;
    private boolean isChecked;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }

    public String getMenuImage() {
        return menuImage;
    }

    public void setMenuImage(String menuImage) {
        this.menuImage = menuImage;
    }

    public String getSelectedMenuImage() {
        return selectedMenuImage;
    }

    public void setSelectedMenuImage(String selectedMenuImage) {
        this.selectedMenuImage = selectedMenuImage;
    }

    public String getIsSameDayApplicable() {
        return isSameDayApplicable;
    }

    public void setIsSameDayApplicable(String isSameDayApplicable) {
        this.isSameDayApplicable = isSameDayApplicable;
    }

    public Integer getMainCatId() {
        return mainCatId;
    }

    public void setMainCatId(Integer mainCatId) {
        this.mainCatId = mainCatId;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuId=" + menuId +
                ", menuTitle='" + menuTitle + '\'' +
                ", menuDesc='" + menuDesc + '\'' +
                ", menuImage='" + menuImage + '\'' +
                ", selectedMenuImage='" + selectedMenuImage + '\'' +
                ", isSameDayApplicable='" + isSameDayApplicable + '\'' +
                ", mainCatId=" + mainCatId +
                ", delStatus=" + delStatus +
                ", isChecked=" + isChecked +
                '}';
    }
}
