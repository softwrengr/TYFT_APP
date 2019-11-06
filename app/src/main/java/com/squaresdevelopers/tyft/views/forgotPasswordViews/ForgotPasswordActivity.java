package com.squaresdevelopers.tyft.views.forgotPasswordViews;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.squaresdevelopers.tyft.R;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;
import com.squaresdevelopers.tyft.views.forgotPasswordViews.forgotPassFragments.EmailVerificationFragment;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        GeneralUtils.connectFragmentWithBack(this,new EmailVerificationFragment());
    }
}
