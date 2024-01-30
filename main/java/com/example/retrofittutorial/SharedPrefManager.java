package com.example.retrofittutorial;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.retrofittutorial.ModelResponse.User;

public class SharedPrefManager {

//    sharedpreference file name

    private static String Shared_Preference_Name = "SharedPreferenceName";
    private SharedPreferences sharedPreferences;
    Context context;
    //    editor is used when adding data in the shardpreference
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        this.context = context;
    }


    public void saveUser(User user) {

        sharedPreferences = context.getSharedPreferences(Shared_Preference_Name, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("id", user.getId());
        editor.putString("username", user.getUsername());
        editor.putString("email", user.getEmail());
//        for loggin status of user
        editor.putBoolean("logged", true);
        editor.apply();
    }

//    check user logged in or not

    public boolean isLoggedInUser() {
        sharedPreferences = context.getSharedPreferences(Shared_Preference_Name, Context.MODE_PRIVATE);
//        return true if value set true in sharedpref otherwise return false
        return sharedPreferences.getBoolean("logged", false);
    }

//    getting user detail from User

    public User getUser() {
        sharedPreferences = context.getSharedPreferences(Shared_Preference_Name, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("username", null),
                sharedPreferences.getString("email", null));
    }

    public void logoutUser(){
        sharedPreferences = context.getSharedPreferences(Shared_Preference_Name, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}