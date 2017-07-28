package com.sarps.alumni;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Sarps on 11/22/2016.
 */
public class LaunchActivity extends AppCompatActivity {
    SharedPreferences pref;
    String pref_email;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref=getSharedPreferences("pref", Context.MODE_PRIVATE);
        pref_email = pref.getString("pref_email", "DEFAULT");
        System.out.println("pref_email :- " + pref_email);
        if (pref_email.equals("pref_email")){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }else{
            startActivity(new Intent(getApplicationContext(),DiyApproachActivity.class));
            finish();
        }
    }
}
