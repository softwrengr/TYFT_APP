package com.squaresdevelopers.tyft.viewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.squaresdevelopers.tyft.dataModels.fogotDataModel.ChangePasswordModel;
import com.squaresdevelopers.tyft.networking.ApiClient;
import com.squaresdevelopers.tyft.networking.ApiInterface;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordViewModel extends AndroidViewModel {

    public MutableLiveData<Integer> progressBar = new MutableLiveData<>();
    public MutableLiveData<String> newPassword = new MutableLiveData<>();
    public MutableLiveData<String> confirm = new MutableLiveData<>();

    private MutableLiveData<ChangePasswordModel> changePasswordMutableLiveData;

    public ChangePasswordViewModel(@NonNull Application application) {
        super(application);
        progressBar.setValue(8);
    }

    public MutableLiveData<String> newPassword() {
        return newPassword;
    }

    public MutableLiveData<String> confirmPassword() {
        return confirm;
    }

    public MutableLiveData<ChangePasswordModel> changePassword() {

        if (changePasswordMutableLiveData == null) {
            changePasswordMutableLiveData = new MutableLiveData<ChangePasswordModel>();
        }
        return changePasswordMutableLiveData;

    }

    public void onClick(final View view) {
        progressBar.setValue(0);

        String token = GeneralUtils.getApiToken(getApplication().getApplicationContext());

        ApiInterface apiService = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ChangePasswordModel> loginresponse = apiService.changePassword(newPassword.getValue(), token, confirm.getValue());

        loginresponse.enqueue(new Callback<ChangePasswordModel>() {
            @Override
            public void onResponse(Call<ChangePasswordModel> call, Response<ChangePasswordModel> response) {
                progressBar.setValue(0);

                if (response.body() == null) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        showToast("invalid");
                    } catch (Exception e) {
                        showToast("error");
                    }
                } else {
                    showToast(response.body().getMessage());
                    ChangePasswordModel model = new ChangePasswordModel();
                    model.setMessage(response.body().getMessage());
                    model.setSuccess(response.body().getSuccess());
                    changePasswordMutableLiveData.setValue(model);


                }


            }

            @Override
            public void onFailure(Call<ChangePasswordModel> call, Throwable t) {
                progressBar.setValue(0);
                showToast(t.getMessage());

            }
        });


    }


    private void showToast(String msg) {
        Toast.makeText(getApplication().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

}
