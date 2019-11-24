package com.squaresdevelopers.tyft.repository;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

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

public class AllTrucksRepository {
    private Context context;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private ArrayList<AvailableUserModel> userList = new ArrayList<>();
    private MutableLiveData<List<AvailableUserModel>> mAllTrucks = new MutableLiveData<>();


    public AllTrucksRepository(Application application) {
        context = application.getApplicationContext();
        getDataFromFirebase();
    }

    private void getDataFromFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Seller_Location");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    AvailableUserModel model = postSnapshot.getValue(AvailableUserModel.class);
                    userList.add(model);
                    mAllTrucks.setValue(userList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "sorry there are some error from firebase", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public MutableLiveData<List<AvailableUserModel>> getAllWords() {
        return mAllTrucks;
    }
}
