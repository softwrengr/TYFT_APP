package com.squaresdevelopers.tyft.views.login.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squaresdevelopers.tyft.R;
import com.squaresdevelopers.tyft.views.tyft.TyftMainActivity;
import com.squaresdevelopers.tyft.views.truck.TruckMainActivity;
import com.squaresdevelopers.tyft.views.signup.TruckSignupActivity;
import com.squaresdevelopers.tyft.databinding.FragmentLoginBinding;
import com.squaresdevelopers.tyft.dataModels.loginModels.LoginResponseModel;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;
import com.squaresdevelopers.tyft.utilities.GetLocation;
import com.squaresdevelopers.tyft.viewModels.LoginViewModel;
import com.squaresdevelopers.tyft.views.forgotPasswordViews.ForgotPasswordActivity;


public class LoginFragment extends Fragment {
    String  strUserType;
    GetLocation getLocation;
    private LoginViewModel loginViewModel;
    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_login, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLocation = new GetLocation();
        getLocation.getLocation(getActivity());
        strUserType = GeneralUtils.getUserType(getActivity());

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginViewModel);


        loginViewModel.getEmailAddress().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if(s.isEmpty()){
                   binding.etEmail.setError("Enter your Email Address");
                }
            }
        });

        loginViewModel.getPassword().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if(s.isEmpty()){
                   binding.etPassword.setError("Enter your password");
                }
            }
        });


        loginViewModel.getUser().observe(this,new Observer<LoginResponseModel>() {
            @Override
            public void onChanged(@Nullable LoginResponseModel loginUser) {

                if (loginUser.getSuccess()) {
                    if (loginUser.getMessage().equals("Vendor Successfully logged In")) {
                        GeneralUtils.putStringValueInEditor(getContext(), "token", loginUser.getData().getApiToken());
                        GeneralUtils.putIntValueInEditor(getContext(), "seller_id", loginUser.getData().getId());
                        GeneralUtils.putBooleanValueInEditor(getContext(), "isLoginUser1", true);
                        getActivity().finish();
                        startActivity(new Intent(getActivity(), TruckMainActivity.class));

                    }
                    if (loginUser.getMessage().equals("Tyft Successfully logged In")) {
                        GeneralUtils.putBooleanValueInEditor(getContext(), "isLoginUser2", true);
                        GeneralUtils.putIntValueInEditor(getContext(), "seller_id", loginUser.getData().getId());
                        getActivity().finish();
                        startActivity(new Intent(getActivity(), TyftMainActivity.class));

                    }
                } else if(!loginUser.getSuccess()) {
                    Toast.makeText(getActivity(), loginUser.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("userType",strUserType);
                startActivity(new Intent(getActivity(), TruckSignupActivity.class).putExtras(bundle));
            }
        });

        binding.forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(getActivity(), ForgotPasswordActivity.class));
            }
        });

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.connectFragment(getActivity(),new LoginSignUpFragment());
            }
        });
    }
}
