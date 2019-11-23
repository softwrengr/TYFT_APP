package com.squaresdevelopers.tyft.repository;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.squaresdevelopers.tyft.dataModels.sellerProfileModels.SellerProfileDataModel;
import com.squaresdevelopers.tyft.dataModels.sellerProfileModels.SellerProfileResponseModel;
import com.squaresdevelopers.tyft.networking.ApiClient;
import com.squaresdevelopers.tyft.networking.ApiInterface;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TruckProfileRepository {
    private Context context;
    public MutableLiveData<Integer> progressBar;
    private ArrayList<SellerProfileDataModel> truckList = new ArrayList<>();
    private MutableLiveData<List<SellerProfileDataModel>> getTruckProfile = new MutableLiveData<>();

    public TruckProfileRepository(Context context, MutableLiveData<Integer> progressBar) {
        this.context = context;
        this.progressBar = progressBar;
        apiCallTruckProfile();
    }

    private void apiCallTruckProfile() {
        String id = GeneralUtils.getTruckUserID(context);
        Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
        progressBar.setValue(0);
        ApiInterface services = ApiClient.getApiClient().create(ApiInterface.class);
        Call<SellerProfileResponseModel> userLogin = services.getUser2Profile(Integer.parseInt(id));
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
                    truckList.add(response.body().getData());
                    getTruckProfile.setValue(truckList);
                }
            }

            @Override
            public void onFailure(Call<SellerProfileResponseModel> call, Throwable t) {
                progressBar.setValue(8);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public MutableLiveData<List<SellerProfileDataModel>> getUserData() {
        return getTruckProfile;
    }
}
