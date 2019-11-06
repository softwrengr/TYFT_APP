package com.squaresdevelopers.tyft.viewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.Toast;


import com.squaresdevelopers.tyft.dataModels.fogotDataModel.VerifyCodeModel;
import com.squaresdevelopers.tyft.networking.ApiClient;
import com.squaresdevelopers.tyft.networking.ApiInterface;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CodeVerificationViewModel extends AndroidViewModel {
    private String email;
    public MutableLiveData<Integer> progressBar = new MutableLiveData<>();
    public MutableLiveData<String> code = new MutableLiveData<>();

    private MutableLiveData<VerifyCodeModel> userMutableLiveData;

    public CodeVerificationViewModel(@NonNull Application application) {
        super(application);
        progressBar.setValue(8);
    }

    public MutableLiveData<String> getCode() {
        return code;
    }

    public MutableLiveData<VerifyCodeModel> getVerify() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClick(final View view) {
        email = GeneralUtils.getUserEmail(getApplication().getApplicationContext());
        progressBar.setValue(0);

        ApiInterface apiService = ApiClient.getApiClient().create(ApiInterface.class);
        Call<VerifyCodeModel> loginresponse = apiService.verifyCode(email, code.getValue());

        loginresponse.enqueue(new Callback<VerifyCodeModel>() {
            @Override
            public void onResponse(Call<VerifyCodeModel> call, Response<VerifyCodeModel> response) {
                progressBar.setValue(0);

                if (response.body() == null) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        showToast("invalid");
                    } catch (Exception e) {
                        showToast("error");
                    }
                } else if (response.body().getSuccess()) {
                    showToast(response.body().getMessage());
                    VerifyCodeModel model = new VerifyCodeModel();
                    model.setMessage(response.body().getMessage());
                    model.setSuccess(response.body().getSuccess());
                    model.setData(response.body().getData());
                    userMutableLiveData.setValue(model);
                }

            }

            @Override
            public void onFailure(Call<VerifyCodeModel> call, Throwable t) {
                progressBar.setValue(0);
                showToast(t.getMessage());

            }
        });

    }

    private void showToast(String msg) {
        Toast.makeText(getApplication().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

}

