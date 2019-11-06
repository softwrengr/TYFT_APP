package com.squaresdevelopers.tyft.repository;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.google.android.material.snackbar.Snackbar;
import com.squaresdevelopers.tyft.dataModels.editProfileModels.EditProfileDataModel;
import com.squaresdevelopers.tyft.dataModels.loginModels.LoginResponseModel;
import com.squaresdevelopers.tyft.dataModels.sellerProfileModels.SellerProfileDataModel;
import com.squaresdevelopers.tyft.dataModels.sellerProfileModels.SellerProfileResponseModel;
import com.squaresdevelopers.tyft.dataModels.tyftUserDataModels.AvailableUserModel;
import com.squaresdevelopers.tyft.networking.ApiClient;
import com.squaresdevelopers.tyft.networking.ApiInterface;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerRepository {
    private Context context;
    public MutableLiveData<Integer> progressBar;
    private MutableLiveData<String> email = new MutableLiveData<>();
    private MutableLiveData<String> name = new MutableLiveData<>();
    private ArrayList<SellerProfileDataModel> userList = new ArrayList<>();
    private MutableLiveData<List<SellerProfileDataModel>> getUserData = new MutableLiveData<>();

    public SellerRepository(Application application,MutableLiveData<Integer> progressBar) {
        this.context = application;
        this.progressBar = progressBar;
        apiCallProfile();
    }

    public MutableLiveData<List<SellerProfileDataModel>> getUserData() {
        return getUserData;
    }

    public MutableLiveData<String> getEmail(){
        return email;
    }

    public MutableLiveData<String> getName(){
        return name;
    }

    private void apiCallProfile() {
        progressBar.setValue(0);
        ApiInterface services = ApiClient.getApiClient().create(ApiInterface.class);
        Call<SellerProfileResponseModel> userLogin = services.getUser2Profile(GeneralUtils.getSellerId(context));
        userLogin.enqueue(new Callback<SellerProfileResponseModel>() {
            @Override
            public void onResponse(Call<SellerProfileResponseModel> call, Response<SellerProfileResponseModel> response) {
                progressBar.setValue(8);
                if (response.body() == null) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else  {
                    name.setValue(response.body().getData().getUserType());
                    email.setValue(response.body().getData().getEmail());
                    userList.add(response.body().getData());
                    getUserData.setValue(userList);
                }
            }

            @Override
            public void onFailure(Call<SellerProfileResponseModel> call, Throwable t) {
                progressBar.setValue(8);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
