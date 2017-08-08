package com.tutorials.andorid.app.retrofit.jsonplaceholder;


import com.tutorials.andorid.app.model.Album;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AlbumsService {

    @GET("/albums?userId=1")
    Call<ArrayList<Album>> getAlbumsForUser(@Query("userId") int userid);

}
