package com.squaresdevelopers.tyft.viewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.squaresdevelopers.tyft.dataModels.sellerProfileModels.SellerProfileDataModel;
import com.squaresdevelopers.tyft.repository.SellerRepository;


import java.util.List;

public class SellerViewModel extends AndroidViewModel {
    public MutableLiveData<Integer> progressBar = new MutableLiveData<>();
    private MutableLiveData<String> email;
    private MutableLiveData<String> name;
    private SellerRepository sellerRepository;

    private MutableLiveData<List<SellerProfileDataModel>> getData;

    public SellerViewModel(@NonNull Application application) {
        super(application);
        sellerRepository = new SellerRepository(application,progressBar);
        email = sellerRepository.getEmail();
        name = sellerRepository.getName();
        getData = sellerRepository.getUserData();
    }

    public MutableLiveData<List<SellerProfileDataModel>> getData() {
        return getData;
    }

    public MutableLiveData<String> getEmail(){
        return email;
    }

    public MutableLiveData<String> getName(){
        return name;
    }
}
