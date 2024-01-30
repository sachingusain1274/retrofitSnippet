package com.example.retrofittutorial;

import com.example.retrofittutorial.ModelResponse.DeleteAccountResponse;
import com.example.retrofittutorial.ModelResponse.FetchUsersResponse;
import com.example.retrofittutorial.ModelResponse.LoginResponse;
import com.example.retrofittutorial.ModelResponse.RegisterResponse;
import com.example.retrofittutorial.ModelResponse.UpdatePasswordResponse;
import com.example.retrofittutorial.ModelResponse.UpdateUserResponse;
import com.example.retrofittutorial.ModelResponse.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("register.php")
//    RegisterResponse is a type of response coming fromm api
    Call<RegisterResponse> registerUser(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("newLogin.php")
    Call<LoginResponse> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );


    @GET("fetchusers.php")
    Call<FetchUsersResponse> fetchUsers();

    @FormUrlEncoded
    @POST("updateuser.php")
    Call<UpdateUserResponse> updateUser(
            @Field("id") int id,
            @Field("username") String username,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("updatepassword.php")
    Call<UpdatePasswordResponse> updatePassword(
            @Field("email") String email,
            @Field("current") String currentPassword,
            @Field("new") String newPassword
    );


    @FormUrlEncoded
    @POST("deleteaccount.php")
    Call<DeleteAccountResponse> deleteAccount(
            @Field("id") int id
    );



}
