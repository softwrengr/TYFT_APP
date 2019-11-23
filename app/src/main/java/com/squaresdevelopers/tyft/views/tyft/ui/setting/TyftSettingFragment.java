package com.squaresdevelopers.tyft.views.tyft.ui.setting;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.squaresdevelopers.tyft.R;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;
import com.squaresdevelopers.tyft.utilities.LocaleUtilities;
import com.squaresdevelopers.tyft.views.login.LoginActivity;

public class TyftSettingFragment extends Fragment implements View.OnClickListener {
    private String strLanguage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleUtilities.loadLocale(getActivity(),
                GeneralUtils.getLanguage(getActivity()));
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        LinearLayout layoutSetting = root.findViewById(R.id.layout_setting);
        LinearLayout layoutLogout = root.findViewById(R.id.layout_signout);

        layoutLogout.setOnClickListener(this);
        layoutSetting.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_signout:
                signOut();
            break;
            case R.id.layout_setting:
                selectLanguages();
            break;
        }
    }

    private void signOut(){
        getActivity().finish();
        GeneralUtils.putStringValueInEditor(getActivity(),"type","user2");
        GeneralUtils.putBooleanValueInEditor(getActivity(), "isLoginUser2", false);
        startActivity(new Intent(getActivity(), LoginActivity.class));
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
}