package com.squaresdevelopers.tyft.views.fragments.loginSignUpViews;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.squaresdevelopers.tyft.R;
import com.squaresdevelopers.tyft.views.activities.LoginActivity;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginSignUpFragment extends Fragment {
    @BindView(R.id.btn_user_one)
    Button btnUserOne;
    @BindView(R.id.btn_user_two)
    Button btnUserTwo;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login_sign_up, container, false);

        initViews();
        return view;
    }

    private void initViews(){
        ButterKnife.bind(this,view);

        btnUserOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.putStringValueInEditor(getActivity(),"type","user1");
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        btnUserTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.putStringValueInEditor(getActivity(),"type","user2");
                startActivity(new Intent(getActivity(), LoginActivity.class));

            }
        });
    }
}
