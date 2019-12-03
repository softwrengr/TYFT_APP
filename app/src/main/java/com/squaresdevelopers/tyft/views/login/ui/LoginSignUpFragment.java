package com.squaresdevelopers.tyft.views.login.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.squaresdevelopers.tyft.R;
import com.squaresdevelopers.tyft.views.login.LoginActivity;
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
        onback(view);
        initViews();
        return view;
    }

    private void initViews(){
        ButterKnife.bind(this,view);

        btnUserOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.putStringValueInEditor(getActivity(),"type","Vendor");
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        btnUserTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.putStringValueInEditor(getActivity(),"type","Tyft");
                startActivity(new Intent(getActivity(), LoginActivity.class));

            }
        });
    }

    private void onback(View view) {

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    getActivity().finishAffinity();
                    return true;
                }
                return false;
            }
        });

    }
}
