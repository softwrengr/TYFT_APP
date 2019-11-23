package com.squaresdevelopers.tyft.views.forgotPasswordViews.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squaresdevelopers.tyft.R;
import com.squaresdevelopers.tyft.dataModels.fogotDataModel.ForgotModel;
import com.squaresdevelopers.tyft.databinding.FragmentForgotBinding;
import com.squaresdevelopers.tyft.views.login.ui.LoginFragment;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;
import com.squaresdevelopers.tyft.viewModels.ForgotPasswordViewModel;

public class EmailVerificationFragment extends Fragment {
    private ForgotPasswordViewModel forgotPasswordViewModel;
    private FragmentForgotBinding activityMainBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        activityMainBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_forgot, container, false);
        onback(activityMainBinding.getRoot());
        return activityMainBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        forgotPasswordViewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel.class);
        activityMainBinding.setLifecycleOwner(this);
        activityMainBinding.setForgotPasswordViewModel(forgotPasswordViewModel);

        forgotPasswordViewModel.getEmailAddress().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s.isEmpty()) {
                    activityMainBinding.etEmail.setError("please enter your email");
                }
            }
        });


        forgotPasswordViewModel.getUser().observe(this, new Observer<ForgotModel>() {
            @Override
            public void onChanged(@Nullable ForgotModel forgotModel) {
                if (forgotModel.getSuccess()) {
                    GeneralUtils.connectFragmentWithBack(getActivity(), new CodeVerificationFragment());
                }

            }
        });


        activityMainBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.connectFragment(getActivity(), new LoginFragment());
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
                    getActivity().onBackPressed();
                    return true;
                }
                return false;
            }
        });

    }

}
