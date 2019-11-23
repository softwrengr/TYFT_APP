package com.squaresdevelopers.tyft.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.squaresdevelopers.tyft.dataModels.sellerProfileModels.SellerProfileDataModel;
import com.squaresdevelopers.tyft.repository.TruckProfileRepository;
import java.util.List;

public class TruckViewModel extends AndroidViewModel {

    public MutableLiveData<Integer> progressBar= new MutableLiveData<>();
    public MutableLiveData<List<SellerProfileDataModel>> getTruckProfile;
    TruckProfileRepository truckProfileRepository;


    public TruckViewModel(@NonNull Application application) {
        super(application);
        truckProfileRepository = new TruckProfileRepository(application,progressBar);
        getTruckProfile = truckProfileRepository.getUserData();

    }

    public MutableLiveData<List<SellerProfileDataModel>> getTruckData() {
        return getTruckProfile;
    }
}
