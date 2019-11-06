package com.squaresdevelopers.tyft.views.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.squaresdevelopers.tyft.R;
import com.squaresdevelopers.tyft.views.fragments.loginSignUpViews.LoginSignUpFragment;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((MainActivity) this).getSupportActionBar().hide();


        if (GeneralUtils.getSharedPreferences(this).getBoolean("isLoginUser1", false)) {
            finish();
            startActivity(new Intent(this, TruckMainActivity.class));

        } else if (GeneralUtils.getSharedPreferences(this).getBoolean("isLoginUser2", false)) {
            startActivity(new Intent(this, TyftMainActivity.class));

        } else {
            GeneralUtils.connectFragment(this, new LoginSignUpFragment());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
