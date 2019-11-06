package com.squaresdevelopers.tyft.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squaresdevelopers.tyft.dataModels.tyftUserDataModels.AvailableUserModel;

import java.util.ArrayList;
import java.util.List;

public class TyftUserRepository {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private ArrayList<AvailableUserModel> userList = new ArrayList<>();
    private MutableLiveData<List<AvailableUserModel>> mAllWords = new MutableLiveData<>();


    public TyftUserRepository(Application application) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Seller_Location");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    AvailableUserModel model = postSnapshot.getValue(AvailableUserModel.class);
                    userList.add(model);
                    mAllWords.setValue(userList);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    public MutableLiveData<List<AvailableUserModel>> getAllWords() {
        return mAllWords;
    }
}
