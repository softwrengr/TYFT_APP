package com.squaresdevelopers.tyft.views.forgotPasswordViews.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squaresdevelopers.tyft.R;
import com.squaresdevelopers.tyft.dataModels.fogotDataModel.ChangePasswordModel;
import com.squaresdevelopers.tyft.databinding.FragmentChangePasswordBinding;
import com.squaresdevelopers.tyft.views.login.ui.LoginFragment;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;
import com.squaresdevelopers.tyft.viewModels.ChangePasswordViewModel;

public class ChangePasswordFragment extends Fragment {

    private ChangePasswordViewModel changePasswordViewModel;
    FragmentChangePasswordBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_change_password, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        changePasswordViewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setChangePasswordViewModel(changePasswordViewModel);

        changePasswordViewModel.newPassword().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s.isEmpty()) {
                    binding.etPassword.setError("Set your password");
                }
            }
        });

        changePasswordViewModel.confirm.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s.isEmpty()) {
                    binding.etConfirmPassword.setError("Confirm your password");
                }
            }
        });

        changePasswordViewModel.changePassword().observe(this, new Observer<ChangePasswordModel>() {
            @Override
            public void onChanged(@Nullable ChangePasswordModel changePasswordModel) {
                GeneralUtils.connectFragmentWithBack(getActivity(), new LoginFragment());
            }
        });

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.connectFragment(getActivity(), new CodeVerificationFragment());
            }
        });
    }
}
