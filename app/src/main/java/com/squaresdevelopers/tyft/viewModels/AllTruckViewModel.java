package com.squaresdevelopers.tyft.viewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;

import com.squaresdevelopers.tyft.dataModels.tyftUserDataModels.AvailableUserModel;
import com.squaresdevelopers.tyft.repository.AllTrucksRepository;

import java.util.List;

public class AllTruckViewModel extends AndroidViewModel {
    private SavedStateHandle mState;
    private AllTrucksRepository userRepository;

    private MutableLiveData<List<AvailableUserModel>> userMutableLiveData;

    public AllTruckViewModel(@NonNull Application application, SavedStateHandle savedStateHandle) {
        super(application);
        mState = savedStateHandle;
        userRepository = new AllTrucksRepository(application);
        userMutableLiveData = userRepository.getAllWords();

    }


    public MutableLiveData<List<AvailableUserModel>> getData() {
        return userMutableLiveData;
    }

}
