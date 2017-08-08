package com.tutorials.andorid.app.retrofit.jsonplaceholder;

import com.tutorials.andorid.app.model.user.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UsersService {
    @GET("/users")
    Call<List<User>> getUsers();

    @GET("/users/{userid}")
    Call<User> getUser(@Path("userid") int userid);
}
