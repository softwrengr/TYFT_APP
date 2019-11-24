package com.squaresdevelopers.tyft.views.signup.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squaresdevelopers.tyft.R;
import com.squaresdevelopers.tyft.dataModels.sellerSignUpModels.SellerSignUpResponseModel;
import com.squaresdevelopers.tyft.networking.ApiClient;
import com.squaresdevelopers.tyft.networking.ApiInterface;
import com.squaresdevelopers.tyft.networking.BaseNetworking;
import com.squaresdevelopers.tyft.utilities.AlertUtils;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;
import com.squaresdevelopers.tyft.utilities.NetworkUtils;
import com.squaresdevelopers.tyft.views.login.ui.LoginFragment;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class TruckSignUpFragment extends Fragment {
    AlertDialog alertDialog;
    View view;
    @BindView(R.id.iv_seller_one)
    ImageView ivSellerOne;
    @BindView(R.id.iv_seller_two)
    ImageView ivSellerTwo;
    @BindView(R.id.et_seller_textfield)
    EditText etTextfield;
    @BindView(R.id.et_seller_email)
    EditText etEmail;
    @BindView(R.id.et_seller_password)
    EditText etPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.btn_seller_signup)
    Button btnSellerSignup;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    String strEmail, strPassword, strTextField;
    private File file1, file2;
    private boolean aBoolean = false, bBoolean = false;
    final int CAMERA_CAPTURE = 10;
    final int RESULT_LOAD_IMAGE = 20;
    private boolean valid = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, view);
        NetworkUtils.grantPermession(getActivity());
        initUI();
        return view;
    }

    private void initUI() {
        BaseNetworking.grantPermission(getActivity());

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        btnSellerSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    alertDialog = AlertUtils.createProgressDialog(getActivity());
                    alertDialog.show();
                    apiCallSellerSignUp();
                }
            }
        });

        ivSellerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aBoolean = true;
                bBoolean = false;
                cameraBuilder();
            }
        });

        ivSellerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bBoolean = true;
                aBoolean = false;
                cameraBuilder();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

    }


    public void cameraIntent() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(captureIntent, CAMERA_CAPTURE);
    }

    public void galleryIntent() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    //open camera view
    public void cameraBuilder() {
        android.app.AlertDialog.Builder pictureDialog = new android.app.AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Open");
        String[] pictureDialogItems = {
                "\tGallery",
                "\tCamera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                galleryIntent();
                                break;
                            case 1:
                                cameraIntent();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && null != data) {
            Uri selectedImageUri = data.getData();
            String imagepath = getPath(selectedImageUri);
            if (aBoolean) {
                file1 = new File(imagepath);
                try {
                    file1 = new Compressor(getActivity()).compressToFile(file1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (bBoolean) {
                file2 = new File(imagepath);
                try {
                    file2 = new Compressor(getActivity()).compressToFile(file2);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        } else if (resultCode == RESULT_OK && requestCode == CAMERA_CAPTURE && data != null) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();

            thumbnail.compress(Bitmap.CompressFormat.PNG, 90, bytes);
            if (aBoolean) {
                file1 = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".png");
                FileOutputStream fo;
                try {
                    file1.createNewFile();
                    fo = new FileOutputStream(file1);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ivSellerOne.setImageBitmap(thumbnail);
            } else if (bBoolean) {

                file2 = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".png");
                FileOutputStream fo;
                try {
                    file2.createNewFile();
                    fo = new FileOutputStream(file2);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ivSellerTwo.setImageBitmap(thumbnail);

            }
        }
    }

    @SuppressLint("SetTextI18n")
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(projection[0]);
        String filePath = cursor.getString(columnIndex);
        if (aBoolean) {
            ivSellerOne.setImageBitmap(BitmapFactory.decodeFile(filePath));
        } else if (bBoolean) {
            ivSellerTwo.setImageBitmap(BitmapFactory.decodeFile(filePath));

        }
        return cursor.getString(column_index);

    }

    private void apiCallSellerSignUp() {
        ApiInterface services = ApiClient.getApiClient().create(ApiInterface.class);
        RequestBody requestFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
        final MultipartBody.Part fileBody1 = MultipartBody.Part.createFormData("image1", file1.getName(), requestFile1);
        RequestBody fileName1 = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

        RequestBody requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
        final MultipartBody.Part fileBody2 = MultipartBody.Part.createFormData("image2", file2.getName(), requestFile2);
        RequestBody fileName2 = RequestBody.create(MediaType.parse("text/plain"), "upload_test");


        RequestBody emailBody = RequestBody.create(MediaType.parse("multipart/form-data"), strEmail);
        RequestBody passBody = RequestBody.create(MediaType.parse("multipart/form-data"), strPassword);
        RequestBody textBody = RequestBody.create(MediaType.parse("multipart/form-data"), strTextField);

        retrofit2.Call<SellerSignUpResponseModel> sellerSignUp = services.sellerSignUp(emailBody,
                fileBody1, fileName1, fileBody2, fileName2, passBody, textBody);
        sellerSignUp.enqueue(new Callback<SellerSignUpResponseModel>() {
            @Override
            public void onResponse(retrofit2.Call<SellerSignUpResponseModel> call, Response<SellerSignUpResponseModel> response) {
                alertDialog.dismiss();

                if (response.body() == null) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else if (response.body().getSuccess()) {
                    getActivity().finish();
                    GeneralUtils.connectFragment(getActivity(), new LoginFragment());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<SellerSignUpResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validate() {
        valid = true;
        strEmail = etEmail.getText().toString();
        strTextField = etTextfield.getText().toString();
        strPassword = etPassword.getText().toString();


        if (strEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            etEmail.setError("Please enter a valid email");
            valid = false;
        } else {
            etEmail.setError(null);
        }

        if (strPassword.isEmpty() || strPassword.length() < 6) {
            etPassword.setError("Please enter a strong password");
            valid = false;
        } else {
            etPassword.setError(null);
        }

        if (strTextField.isEmpty()) {
            etTextfield.setError("Please enter data");
            valid = false;
        } else {
            etTextfield.setError(null);
        }

        if (file1 == null) {
            Toast.makeText(getActivity(), "please select first images", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            Log.d("error", "all is okay");
        }

        if (file2 == null) {
            Toast.makeText(getActivity(), "please select second images", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("error", "all is okay");
        }


        return valid;
    }


}
