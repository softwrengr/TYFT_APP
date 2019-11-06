package com.squaresdevelopers.tyft.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.squaresdevelopers.tyft.R;
import com.squaresdevelopers.tyft.views.fragments.loginSignUpViews.LoginFragment;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ((AppCompatActivity)this).getSupportActionBar().hide();

        GeneralUtils.connectFragment(this, new LoginFragment());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
