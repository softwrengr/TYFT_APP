package com.squaresdevelopers.tyft.views.forgotPasswordViews.forgotPassFragments;

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
import com.squaresdevelopers.tyft.dataModels.fogotDataModel.VerifyCodeModel;
import com.squaresdevelopers.tyft.databinding.FragmentCodeVerificationBinding;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;
import com.squaresdevelopers.tyft.viewModels.CodeVerificationViewModel;


public class CodeVerificationFragment extends Fragment {

    private CodeVerificationViewModel codeVerificationViewModel;
    FragmentCodeVerificationBinding codeVerificationBinding;


    public static CodeVerificationFragment newInstance() {
        return new CodeVerificationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        codeVerificationBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_code_verification, container, false);
        return codeVerificationBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        codeVerificationViewModel = ViewModelProviders.of(this).get(CodeVerificationViewModel.class);
        codeVerificationBinding.setLifecycleOwner(this);
        codeVerificationBinding.setCodeVerificationViewModel(codeVerificationViewModel);

        codeVerificationViewModel.code.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if(s.isEmpty()){
                    codeVerificationBinding.etCode.setError("please enter your code");
                }
            }
        });

        codeVerificationViewModel.getVerify().observe(this, new Observer<VerifyCodeModel>() {
            @Override
            public void onChanged(@Nullable VerifyCodeModel verifyCodeModel) {

                if(verifyCodeModel.getSuccess()){
                    GeneralUtils.putStringValueInEditor(getActivity(),"token",verifyCodeModel.getData().getApiToken());
                    GeneralUtils.connectFragmentWithBack(getActivity(), new ChangePasswordFragment());
                }
            }
        });

        codeVerificationBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.connectFragment(getActivity(), new EmailVerificationFragment());
            }
        });
    }
}
