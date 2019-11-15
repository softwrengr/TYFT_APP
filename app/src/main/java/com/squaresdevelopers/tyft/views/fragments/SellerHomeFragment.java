package com.squaresdevelopers.tyft.views.fragments;

import android.app.Dialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.squaresdevelopers.tyft.R;
import com.squaresdevelopers.tyft.dataModels.sellerProfileModels.SellerProfileDataModel;
import com.squaresdevelopers.tyft.databinding.FragmentSellerHomeBinding;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;
import com.squaresdevelopers.tyft.utilities.GetLocation;
import com.squaresdevelopers.tyft.viewModels.SellerViewModel;
import com.squaresdevelopers.tyft.views.activities.LoginActivity;

import java.util.List;
import java.util.Locale;

public class SellerHomeFragment extends Fragment {

    private GetLocation getLocation;

    private SellerViewModel sellerViewModel;
    private FragmentSellerHomeBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_seller_home, container, false);
        getLocation = new GetLocation();
        getLocation.getLocation(getActivity());
        loadLocale();
        return binding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sellerViewModel = ViewModelProviders.of(this).get(SellerViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setSellerViewModel(sellerViewModel);

        sellerViewModel.getData().observe(this, new Observer<List<SellerProfileDataModel>>() {
            @Override
            public void onChanged(List<SellerProfileDataModel> sellerProfileDataModels) {

                Glide.with(getActivity())
                        .load(sellerProfileDataModels.get(0).getImage1()).into(binding.ivSellerOne);
                Glide.with(getActivity())
                        .load(sellerProfileDataModels.get(0).getImage2()).into(binding.ivSellerTwo);

                GeneralUtils.putStringValueInEditor(getActivity(),
                        "image1", sellerProfileDataModels.get(0).getImage1());
                GeneralUtils.putStringValueInEditor(getActivity(),
                        "image2", sellerProfileDataModels.get(0).getImage2());
                GeneralUtils.putStringValueInEditor(getActivity(),
                        "user2_email", sellerProfileDataModels.get(0).getEmail());
                GeneralUtils.putStringValueInEditor(getActivity(),
                        "user2_text", sellerProfileDataModels.get(0).getTextField());
            }
        });


        binding.layoutMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.connectFragmentWithBack(getActivity(), new SellerMapFragment());
            }
        });

        binding.layoutSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.connectFragmentWithBack(getActivity(), new SetTimingFragment());
            }
        });

        binding.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.connectFragmentWithBack(getActivity(), new SellerEditProfileFragment());
            }
        });

        binding.layoutSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLanguages();
            }
        });

        binding.layoutSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                GeneralUtils.putStringValueInEditor(getActivity(), "type", "user1");
                GeneralUtils.putBooleanValueInEditor(getContext(), "isLoginUser1", false);
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }


    private void selectLanguages() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.language_layout);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvDone = dialog.findViewById(R.id.tv_done);

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("es");
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

    private void setLocale(String language) {

        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(config,
                getActivity().getBaseContext().getResources().getDisplayMetrics());

        GeneralUtils.putStringValueInEditor(getActivity(), "language", language);
    }

    private void loadLocale() {
        String language = GeneralUtils.getLanguage(getActivity());
        setLocale(language);
    }

}