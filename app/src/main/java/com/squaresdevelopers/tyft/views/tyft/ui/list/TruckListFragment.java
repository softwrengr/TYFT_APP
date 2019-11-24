package com.squaresdevelopers.tyft.views.tyft.ui.list;

import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squaresdevelopers.tyft.R;
import com.squaresdevelopers.tyft.adapters.TyftUserAdapter;
import com.squaresdevelopers.tyft.dataModels.tyftUserDataModels.AvailableUserModel;
import com.squaresdevelopers.tyft.viewModels.AllTruckViewModel;

import java.util.List;


public class TruckListFragment extends Fragment {

    AllTruckViewModel userViewModel;
    TyftUserAdapter tyftUserAdapter;
    LinearLayoutManager layoutManager;
    RecyclerView rvUsers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // View view  = DataBindingUtil.inflate(inflater,R.layout.fragment_tyft_users,container,false);
        View view = inflater.inflate(R.layout.fragment_tyft_users, container, false);
        rvUsers = view.findViewById(R.id.rv_users);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


         userViewModel = new ViewModelProvider(this, new SavedStateVMFactory(this))
                .get(AllTruckViewModel.class);

        userViewModel.getData().observe(this, new Observer<List<AvailableUserModel>>() {
            @Override
            public void onChanged(@Nullable List<AvailableUserModel> availableUserModels) {
                layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rvUsers.setLayoutManager(layoutManager);
                tyftUserAdapter = new TyftUserAdapter(getActivity(), availableUserModels);
                rvUsers.setAdapter(tyftUserAdapter);
            }
        });

    }

}
