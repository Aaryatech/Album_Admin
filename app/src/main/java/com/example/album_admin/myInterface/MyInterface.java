package com.example.album_admin.myInterface;

//8806042165


import com.example.album_admin.model.Album;
import com.example.album_admin.model.AlbumEnquiry;
import com.example.album_admin.model.Employee;
import com.example.album_admin.model.Info;
import com.example.album_admin.model.Login;
import com.example.album_admin.model.Menu;
import com.example.album_admin.model.MenuConfigure;
import com.example.album_admin.model.SettingValues;
import com.example.album_admin.model.SpCakeAndAlbumEnquiry;
import com.example.album_admin.model.SpecialCake;
import com.example.album_admin.model.Type;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by eis-01 on 11/1/17.
 */

public interface MyInterface {

    @POST("saveAlbumEnquiry")
    Call<AlbumEnquiry> saveAlbumEnquiry(@Body AlbumEnquiry albumEnquiry);

    @POST("updateAlbumEnquiry")
    Call<Info> updateAlbumEnquiry(@Query("enqId") int enqId, @Query("status") int status);


//    @GET("getAllEnquiry")
//    Call<ArrayList<AlbumEnquiry>> getAllEnquiry();


    @GET("getFrnchseEnqAlbmInfo")
    Call<ArrayList<AlbumEnquiry>> getAllEnquiry();

    @GET("getFrnchseEnqAlbmInfoByDate")
    Call<ArrayList<AlbumEnquiry>> getFrnchseEnqAlbmInfoByDate(@Query("fromDate") String fromDate, @Query("toDate") String toDate);


    @GET("getSpCakeCatSuppList")
    Call<ArrayList<SpecialCake>> getSpCakeCatSuppList();

    @GET("getTypeWiseFrNameList")
    Call<ArrayList<Type>> getTypeWiseFrNameList();

    @POST("getMenuForAlbum")
    Call<ArrayList<Menu>> getMenuForAlbum(@Query("catId") int catId, @Query("isSameDay") int isSameDay, @Query("delStatus") int delStatus);

    @POST("getConfigListByFrAndMenu")
    Call<ArrayList<MenuConfigure>> getConfigListByFrAndMenu(@Query("frIds") List<String> frIds, @Query("menuIds") List<String> menuIds);

    @POST("saveAlbum")
    Call<Album> saveAlbum(@Body Album album);

    @POST("updateAlbumId")
    Call<Info> updateAlbumId(@Query("enqId") int enqId, @Query("albmId") int albmId);

    @POST("updateFrConfItemShow")
    Call<Info> updateFrConfItemShow(@Query("items") String items, @Query("settingId") int settingId);

//    @GET("getSpCakeEnaAlbmFrDetail")
//    Call<ArrayList<SpCakeAndAlbumEnquiry>> getSpCakeEnaAlbmFrDetail();

    @GET("getSpCakeEnaAlbmFrDetail")
    Call<ArrayList<SpCakeAndAlbumEnquiry>> getSpCakeEnaAlbmFrDetail(@Query("fromDate") String fromDate, @Query("toDate") String toDate);

    @POST("getLogin")
    Call<JsonObject> getFrLogin(@Query("fr_code") String fr_code, @Query("fr_password") String fr_password);


    @GET("master/allEmployeesByDesg")
    Call<ArrayList<Employee>> allEmployees();

    @POST("master/login")
    Call<Login> doLogin(@Query("dscNumber") String dscNumber);

    @POST("updateAppEmpToken")
    Call<Info> updateAppEmpToken(@Query("empId") int empId, @Query("token") String token,@Query("type") int type );

    @POST("getSettingValueById")
    Call<SettingValues> getSettingValueById(@Query("settingId") int settingId);

    @POST("updateSettingValueById")
    Call<Info> updateSettingValueById(@Query("settingId") int settingId,@Query("value") String value);
}
