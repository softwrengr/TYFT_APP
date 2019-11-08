package com.squaresdevelopers.tyft.views.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.squaresdevelopers.tyft.R;
import com.squaresdevelopers.tyft.views.fragments.TyftUsersFragment;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TyftMainActivity extends AppCompatActivity {

    @BindView(R.id.bottombar)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tyft_main);
        ButterKnife.bind(this);

        ((AppCompatActivity)this).getSupportActionBar().show();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorPrimaryDark)));

        bottomNavigationView.setOnNavigationItemSelectedListener(myNavigationListener);

        if(savedInstanceState == null){
            GeneralUtils.connectTyftFragment(TyftMainActivity.this,new com.squaresdevelopers.tyft.views.fragments.MapFragment());
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener myNavigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.map:
                    GeneralUtils.connectTyftFragment(TyftMainActivity.this,new com.squaresdevelopers.tyft.views.fragments.MapFragment());
                    return true;
//                case R.id.users:
//                    GeneralUtils.connectTyftFragment(TyftMainActivity.this,new TyftUsersFragment());
//                    return true;
                case R.id.logout:
                    finish();
                    GeneralUtils.putStringValueInEditor(TyftMainActivity.this,"type","user2");
                    GeneralUtils.putBooleanValueInEditor(TyftMainActivity.this, "isLoginUser2", false);
                    startActivity(new Intent(TyftMainActivity.this, LoginActivity.class));
                    return true;

            }
            return false;
        }
    };



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
