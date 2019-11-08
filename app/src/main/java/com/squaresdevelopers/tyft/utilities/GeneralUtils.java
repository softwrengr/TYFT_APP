package com.squaresdevelopers.tyft.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.squaresdevelopers.tyft.R;

/**
 * Created by eapple on 18/12/2018.
 */

public class GeneralUtils {

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    public static Fragment connectFragment(Context context,Fragment fragment){
        ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        return fragment;
    }
    public static Fragment connectFragmentWithBack(Context context,Fragment fragment){
        ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack("").commit();
        return fragment;
    }

    public static Fragment connectTyftFragment(Context context,Fragment fragment){
        ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.bottom_container,fragment).commit();
        return fragment;
    }

    public static SharedPreferences.Editor putBooleanValueInEditor(Context context, String key, boolean value) {
        sharedPreferences = getSharedPreferences(context);
        editor = sharedPreferences.edit();
        editor.putBoolean(key, value).commit();
        return editor;
    }

    public static SharedPreferences.Editor putStringValueInEditor(Context context, String key, String value) {
        sharedPreferences = getSharedPreferences(context);
        editor = sharedPreferences.edit();
        editor.putString(key, value).commit();
        return editor;
    }

    public static SharedPreferences.Editor putIntValueInEditor(Context context, String key, int value) {
        sharedPreferences = getSharedPreferences(context);
        editor = sharedPreferences.edit();
        editor.putInt(key, value).commit();
        return editor;
    }



    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("TYFT", 0);
    }

    public static String getUserType(Context context) {
        return getSharedPreferences(context).getString("type","");
    }

    public static String getApiToken(Context context){
        return getSharedPreferences(context).getString("token","");
    }

    public static String getUserEmail(Context context){
        return getSharedPreferences(context).getString("user_email","");
    }

    public static int getSellerId(Context context){
        return getSharedPreferences(context).getInt("seller_id",0);
    }

    public static String getUser2Email(Context context){
        return getSharedPreferences(context).getString("user2_email","");
    }


    public static String getUser2Text(Context context){
        return getSharedPreferences(context).getString("user2_text","");
    }

    public static String getUserImage1(Context context){
        return getSharedPreferences(context).getString("image1","");
    }

    public static String getUserImage2(Context context){
        return getSharedPreferences(context).getString("image2","");
    }

    public static String getUserLatitude(Context context){
        return getSharedPreferences(context).getString("latitude","");
    }
    public static String getUserLongitude(Context context){
        return getSharedPreferences(context).getString("longitude","");
    }

    public static String getLanguage(Context context){
        return getSharedPreferences(context).getString("longitude","");
    }

    public static String getStartTime(Context context){
        return getSharedPreferences(context).getString("startTime","00:00:00");
    }

    public static String getEndTime(Context context){
        return getSharedPreferences(context).getString("endTime","00:00:00");
    }


    public static boolean isLogin(Context context){
        return getSharedPreferences(context).getBoolean("loggedIn",false);
    }

    public static boolean sellerLocation(Context context){
        return getSharedPreferences(context).getBoolean("seller_location",false);
    }


}
