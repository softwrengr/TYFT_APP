package com.squaresdevelopers.tyft.viewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.squaresdevelopers.tyft.dataModels.fogotDataModel.ForgotModel;
import com.squaresdevelopers.tyft.networking.ApiClient;
import com.squaresdevelopers.tyft.networking.ApiInterface;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordViewModel extends AndroidViewModel {

    public MutableLiveData<Integer> progressBar = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();

    private MutableLiveData<ForgotModel> userMutableLiveData;

    public ForgotPasswordViewModel(@NonNull Application application) {
        super(application);
        progressBar.setValue(8);
    }

    public MutableLiveData<String> getEmailAddress(){
        return email;
    }

    public MutableLiveData<ForgotModel> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClick(final View view) {
        progressBar.setValue(0);

        ApiInterface apiService = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ForgotModel> forgotResponse = apiService.sentEmail(email.getValue());

        forgotResponse.enqueue(new Callback<ForgotModel>() {
            @Override
            public void onResponse(Call<ForgotModel> call, Response<ForgotModel> response) {
                progressBar.setValue(8);

                if (response.body() == null) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        showToast("invalid");
                    } catch (Exception e) {
                        showToast("error");
                    }
                } else  {
                    GeneralUtils.putStringValueInEditor(getApplication().getApplicationContext(),"user_email",email.getValue());
                    showToast(response.body().getMessage());
                    ForgotModel model = new ForgotModel();
                    model.setMessage(response.body().getMessage());
                    model.setSuccess(response.body().getSuccess());
                    userMutableLiveData.setValue(model);
                }

            }

            @Override
            public void onFailure(Call<ForgotModel> call, Throwable t) {
                progressBar.setValue(8);
                showToast(t.getMessage());

            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(getApplication().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

}
