package com.squaresdevelopers.tyft.views.tyft.ui.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This will be done soon");
    }

    public LiveData<String> getText() {
        return mText;
    }
}