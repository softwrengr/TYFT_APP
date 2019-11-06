package com.squaresdevelopers.tyft.viewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.squaresdevelopers.tyft.dataModels.loginModels.LoginResponseModel;
import com.squaresdevelopers.tyft.networking.ApiClient;
import com.squaresdevelopers.tyft.networking.ApiInterface;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;


import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    public MutableLiveData<Integer> progressBar = new MutableLiveData<>();
    public MutableLiveData<String> EmailAddress = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();
    private MutableLiveData<LoginResponseModel> userMutableLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        progressBar.setValue(8);
    }

    public MutableLiveData<String> getEmailAddress(){
        return EmailAddress;
    }

    public MutableLiveData<String> getPassword(){
        return Password;
    }

    public MutableLiveData<LoginResponseModel> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClick(final View view) {
        progressBar.setValue(0);
        String strUserType = GeneralUtils.getUserType(getApplication().getApplicationContext());

        ApiInterface apiService = ApiClient.getApiClient().create(ApiInterface.class);
        final Call<LoginResponseModel> loginresponse = apiService.userLogin(EmailAddress.getValue(), Password.getValue(),strUserType);
        loginresponse.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                progressBar.setValue(8);
                if (response.body() == null) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getApplication().getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                        Snackbar.make(view,jObjError.getString("message"),Snackbar.LENGTH_LONG).show();
                    } catch (Exception e) {
                    }
                } else {
                   LoginResponseModel loginResponseModel  = new LoginResponseModel();
                   loginResponseModel.setSuccess(response.body().getSuccess());
                   loginResponseModel.setMessage(response.body().getMessage());
                   loginResponseModel.setResponseCode(response.body().getResponseCode());
                   loginResponseModel.setData(response.body().getData());
                   userMutableLiveData.setValue(loginResponseModel);
                }

            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                Toast.makeText(getApplication().getApplicationContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}
