package com.squaresdevelopers.tyft.viewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;

import com.squaresdevelopers.tyft.dataModels.tyftUserDataModels.AvailableUserModel;
import com.squaresdevelopers.tyft.repository.TyftUserRepository;

import java.util.List;

public class TyftUserViewModel extends AndroidViewModel {
    private SavedStateHandle mState;
    private TyftUserRepository userRepository;

    private MutableLiveData<List<AvailableUserModel>> userMutableLiveData;

    public TyftUserViewModel(@NonNull Application application,SavedStateHandle savedStateHandle) {
        super(application);
        mState = savedStateHandle;
        userRepository = new TyftUserRepository(application);
        userMutableLiveData = userRepository.getAllWords();

    }


    public MutableLiveData<List<AvailableUserModel>> getData() {
        return userMutableLiveData;
    }

}
