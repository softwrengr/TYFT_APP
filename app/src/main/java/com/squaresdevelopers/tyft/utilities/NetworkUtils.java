package com.squaresdevelopers.tyft.utilities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squaresdevelopers.tyft.R;

import java.util.List;

public class NetworkUtils {

    public static void grantPermession(Context context){

        Dexter.withActivity((Activity) context)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }

        }).check();
    }


    public static void showImage(Activity context,String url,String name){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_layout);
        ImageView imageView = dialog.findViewById(R.id.iv_show);
        TextView tvName = dialog.findViewById(R.id.tv_name);
        Glide.with(context).load(url).into(imageView);
        tvName.setText(name);
        dialog.show();
    }

}
