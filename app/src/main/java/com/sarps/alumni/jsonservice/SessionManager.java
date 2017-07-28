package com.sarps.alumni.jsonservice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.sarps.alumni.model.User;


/**
 * Created by pws-418 on 6/8/2016.
 */

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "Session Manager";


    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String USER_NAME ="username";
    private static final String USER_EMAIL ="username";
    private static final String USER_PROFILE ="profile_img";
    private static final String USER_TYPE ="user_type";
    private static final String PREF_KEY_TWITTER_LOGIN = "is_twitter_loggedin";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveUserInfo(String result) {
        editor.putString("userinfo",result).commit();
    }

    public void clearUserInfo()
    {
        editor.remove("userinfo");
    }

    public void savePidInfo(String result) {
        editor.putString("userinfosync",result).commit();
    }

    public String getUserInfo() {
        return pref.getString("userinfo",null);
    }
    public String getPidInfo() {
        return pref.getString("userinfosync",null);
    }


    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();
        // After logout redirect user to Login Activity
//        Intent i = new Intent(_context,Login.class);
//        // Closing all the Activities
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        // Add new Flag to start new Activity
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        // Staring Login Activity
//        _context.startActivity(i);
    }

    public void saveUser(User user)
    {
        editor.putString(USER_NAME,user.getName()).commit();
        editor.putString(USER_EMAIL,user.getEmail()).commit();
        editor.putString(USER_PROFILE,user.getProfile_img()).commit();
        editor.putString(USER_TYPE,user.getType()).commit();
    }

    public User getUser()
    {
        String username = pref.getString(USER_NAME,null);
        String useremail = pref.getString(USER_EMAIL,null);
        String userprofile = pref.getString(USER_PROFILE,null);
        String usertype = pref.getString(USER_TYPE,null);

        User user = new User(username,useremail,userprofile,usertype);

        return user;
    }

    public void setTwitterLogin(boolean value)
    {
        editor.putBoolean(PREF_KEY_TWITTER_LOGIN,value);
    }

    public boolean getTwitterLogin()
    {
        return pref.getBoolean(PREF_KEY_TWITTER_LOGIN,false);
    }
}