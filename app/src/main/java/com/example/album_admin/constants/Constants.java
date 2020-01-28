package com.example.album_admin.constants;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.album_admin.myInterface.MyInterface;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by eis-01 on 11/1/17.
 */

public class Constants {


    public static OkHttpClient client=new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Accept", "application/json")
                            .method(original.method(), original.body())
                            .build();


                    Response response = chain.proceed(request);

                    // Inresponse=response.body().string();
                    // Customize or return the response
                    return response;
                }
            })
            .readTimeout(1000, TimeUnit.SECONDS)
            .connectTimeout(1000, TimeUnit.SECONDS)
            .writeTimeout(1000, TimeUnit.SECONDS)
            .build();


    public static Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("http://107.180.95.11:8080/webapi/")
            //.baseUrl("http://192.168.2.7:8096/")
            //.baseUrl("http://132.148.148.215:8080/bakerywebapi/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build();

    public static MyInterface myInterface =retrofit.create(MyInterface.class);


    public static Retrofit retrofit1 = new Retrofit.Builder()
           .baseUrl("http://107.180.95.11:8080/SecurityAppApi/")
          //  .baseUrl("http://192.168.2.7:8096/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build();

    public static MyInterface myInterface1 = retrofit1.create(MyInterface.class);


    public static boolean isOnline(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            Toast.makeText(context, "No Internet Connection ! ", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    public static String FOLDER_NAME="GFPL_MON_ALBUM1";
    public static final String IMAGE_URL="http://107.180.95.11:8080/uploads/ALBUM/";
    //public static final String IMAGE_URL="http://192.168.2.7:8096/uploads/ALBUM/";




}
