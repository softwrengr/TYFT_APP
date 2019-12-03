package com.squaresdevelopers.tyft.views.truck;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.squaresdevelopers.tyft.R;
import com.squaresdevelopers.tyft.views.truck.ui.TruckHomeFragment;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;

public class TruckMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.setTitle(GeneralUtils.getUserType(this));

        GeneralUtils.connectFragment(this, new TruckHomeFragment());
    }

}
