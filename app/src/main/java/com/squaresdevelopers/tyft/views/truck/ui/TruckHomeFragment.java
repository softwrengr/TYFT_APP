package com.squaresdevelopers.tyft.views.truck.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squaresdevelopers.tyft.R;
import com.squaresdevelopers.tyft.dataModels.locationDataModel.SellerLocationModel;
import com.squaresdevelopers.tyft.dataModels.sellerProfileModels.SellerProfileDataModel;
import com.squaresdevelopers.tyft.databinding.FragmentSellerHomeBinding;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;
import com.squaresdevelopers.tyft.utilities.GetLocation;
import com.squaresdevelopers.tyft.utilities.LocaleUtilities;
import com.squaresdevelopers.tyft.viewModels.SellerViewModel;
import com.squaresdevelopers.tyft.views.login.LoginActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TruckHomeFragment extends Fragment {

    private GetLocation getLocation;
    private SellerViewModel sellerViewModel;
    private FragmentSellerHomeBinding binding;
    private String strLanguage,strDate;
    private int sellerID;
    private DatabaseReference databaseReference;
    private SimpleDateFormat currentDate;
    private boolean checkData = true;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleUtilities.loadLocale(getActivity(),
                GeneralUtils.getLanguage(getActivity()));

        sellerID = GeneralUtils.getSellerId(getActivity());
        checkData = GeneralUtils.checkValueInDatabase(getActivity());
        currentDate = new SimpleDateFormat("dd-MM-yyyy");

        Date c = Calendar.getInstance().getTime();
        strDate = currentDate.format(c);

        getLocation = new GetLocation();
        getLocation.getLocation(getActivity());


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_seller_home, container, false);
        onback(binding.getRoot());
        FirebaseApp.initializeApp(getActivity());
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
                        "truck_name", sellerProfileDataModels.get(0).getTextField());

                saveSellerData();
                updatingLocation();
            }
        });


        binding.layoutMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.connectFragmentWithBack(getActivity(), new TruckMapFragment());
            }
        });

        binding.layoutSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.connectFragmentWithBack(getActivity(), new TruckTimingFragment());
            }
        });

        binding.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.connectFragmentWithBack(getActivity(), new TruckEditProfileFragment());
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
        LinearLayout llEnglish = dialog.findViewById(R.id.ll_english);
        LinearLayout llSpanish = dialog.findViewById(R.id.ll_spanish);
        CheckBox cbEnglish = dialog.findViewById(R.id.checkbox_english);
        CheckBox cbSpanish = dialog.findViewById(R.id.checkbox_spanish);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvDone = dialog.findViewById(R.id.tv_done);

        String checkLanguage = GeneralUtils.getLanguage(getActivity());
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
                LocaleUtilities.changeLanguage(getActivity(),strLanguage);
                getActivity().recreate();
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

    private void saveSellerData() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Seller_Location").child(String.valueOf(sellerID));
        SellerLocationModel model = new SellerLocationModel(
                String.valueOf(sellerID),
                strDate,
                GeneralUtils.getStartTime(getActivity()),
                GeneralUtils.getEndTime(getActivity()),
                GeneralUtils.getUserLatitude(getActivity()),
                GeneralUtils.getUserLongitude(getActivity()),
                GeneralUtils.getUserImage1(getActivity()),
                GeneralUtils.getUserImage2(getActivity()),
                GeneralUtils.getTruckName(getActivity()));

        databaseReference.setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                GeneralUtils.putBooleanValueInEditor(getActivity(),"check_value",false);
            }
        });
    }

    private void updatingLocation() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("strLatitude", GeneralUtils.getUserLatitude(getActivity()));
        result.put("strLongitude",GeneralUtils.getUserLongitude(getActivity()));
        FirebaseDatabase.getInstance().getReference().child("Seller_Location").child(String.valueOf(sellerID)).updateChildren(result);
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