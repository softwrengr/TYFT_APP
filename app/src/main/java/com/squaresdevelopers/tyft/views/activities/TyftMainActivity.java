package com.squaresdevelopers.tyft.views.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squaresdevelopers.tyft.R;
import com.squaresdevelopers.tyft.utilities.LocaleUtilities;
import com.squaresdevelopers.tyft.views.fragments.TyftUsersFragment;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TyftMainActivity extends AppCompatActivity {
    private String strLanguage;
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
                case R.id.languages:
                    selectLanguages();
                    return true;
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


    private void selectLanguages() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.language_layout);
        LinearLayout llEnglish = dialog.findViewById(R.id.ll_english);
        LinearLayout llSpanish = dialog.findViewById(R.id.ll_spanish);
        CheckBox cbEnglish = dialog.findViewById(R.id.checkbox_english);
        CheckBox cbSpanish = dialog.findViewById(R.id.checkbox_spanish);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvDone = dialog.findViewById(R.id.tv_done);

        String checkLanguage = GeneralUtils.getLanguage(this);
        if(checkLanguage.equals("en")){
            cbEnglish.setChecked(true);
            cbSpanish.setChecked(false);
        }
        else {
            cbSpanish.setChecked(true);
            cbEnglish.setChecked(false);
        }


        llEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbEnglish.setChecked(true);
                cbSpanish.setChecked(false);
                strLanguage = "en";
            }
        });

        llSpanish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbSpanish.setChecked(true);
                cbEnglish.setChecked(false);
                strLanguage = "es";
            }
        });

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaleUtilities.changeLanguage(TyftMainActivity.this,strLanguage);
                TyftMainActivity.this.recreate();
                dialog.dismiss();

            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
