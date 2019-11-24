package com.squaresdevelopers.tyft.views.signup;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.squaresdevelopers.tyft.R;
import com.squaresdevelopers.tyft.views.login.LoginActivity;
import com.squaresdevelopers.tyft.views.signup.ui.TyftSignUpFragment;
import com.squaresdevelopers.tyft.views.signup.ui.TruckSignUpFragment;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;

public class TruckSignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_signup);
        ((AppCompatActivity)this).getSupportActionBar().hide();

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            String strUserType = bundle.getString("userType");
            if (strUserType.equals("user1")) {
                GeneralUtils.connectFragment(this, new TruckSignUpFragment());
            } else {
                GeneralUtils.connectFragment(this, new TyftSignUpFragment());
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TruckSignupActivity.this, LoginActivity.class));
    }
}
