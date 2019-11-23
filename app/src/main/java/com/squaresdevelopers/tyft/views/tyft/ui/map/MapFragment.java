package com.squaresdevelopers.tyft.views.tyft.ui.map;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squaresdevelopers.tyft.R;
import com.squaresdevelopers.tyft.dataModels.locationDataModel.GetSellerLocation;
import com.squaresdevelopers.tyft.dataModels.sellerProfileModels.SellerProfileResponseModel;
import com.squaresdevelopers.tyft.networking.ApiClient;
import com.squaresdevelopers.tyft.networking.ApiInterface;
import com.squaresdevelopers.tyft.utilities.AlertUtils;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;
import com.squaresdevelopers.tyft.utilities.GetLocation;
import com.squaresdevelopers.tyft.utilities.InternetUtils;
import com.squaresdevelopers.tyft.utilities.NetworkUtils;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    AlertDialog alertDialog;
    View view;
    MapView mapView;
    private GoogleMap googleMap;

    TextView tvUsername;
    ImageView ivOne, ivTwo;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    LatLng apiLatLng;
    GetLocation getLocation;

    String strImageOne, strImageTwo, strName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);
        mapView = view.findViewById(R.id.mapView);
        getLocation = new GetLocation();
        getLocation.getLocation(getActivity());

        initUI();
        if (InternetUtils.isNetworkConnected(getActivity())) {
            mapView.onCreate(savedInstanceState);
            mapView.onResume();
            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
                mapView.getMapAsync(this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), "No Internet Connection Available", Toast.LENGTH_SHORT).show();
        }

        showSellerLocation();


        return view;
    }

    private void initUI() {

    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        LatLng latLng = new LatLng(Double.parseDouble(GeneralUtils.getUserLatitude(getActivity())),
                Double.parseDouble(GeneralUtils.getUserLongitude(getActivity())));
        googleMap.addMarker(new MarkerOptions().position(latLng).title("My Current Location").snippet("This is My Current Location")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, calculateZoomLevel(16));
        googleMap.animateCamera(cameraUpdate);


    }

    private void showSellerLocation() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Seller_Location");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()) {
                    snackBar("No truck exist this time");
                } else {
                    googleMap.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        GetSellerLocation model = postSnapshot.getValue(GetSellerLocation.class);

                        String id = model.getStrID();
                        String date = model.getDate();
                        String startTime = model.getStartTime();
                        String endTime = model.getEndTime();
                        String lat = model.getStrLatitude();
                        String lng = model.getStrLongitude();

                        try {
                            calculateTiming(id, date, startTime, endTime, lat, lng);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void calculateTiming(String id, String date, String startTime, String endTime, String latitude, String longitude) throws ParseException {

        SimpleDateFormat dfDate = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

        Date c = Calendar.getInstance().getTime();
        String formattedDate = dfDate.format(c);

        try {
            //date matched
            if (dfDate.parse(date).equals(dfDate.parse(formattedDate))) {
                Date date_from = formatter.parse(startTime);
                Date date_to = formatter.parse(endTime);

                String currentTime = formatter.format(Calendar.getInstance().getTime());
                Date dateNow = formatter.parse(currentTime);

                //if time matched
                if (date_from.before(dateNow) && date_to.after(dateNow)) {
                    showMarker(latitude, longitude, id, true);
                } else {
                    showMarker(latitude, longitude, id, false);
                }
            }
            //date not match
            else {
                showMarker(latitude, longitude, id, false);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public void showMarker(String lat, String lng, final String id, boolean checkLocation) {

        Marker markerName;
        double latt = Double.parseDouble(lat);
        double lngg = Double.parseDouble(lng);
        apiLatLng = new LatLng(latt, lngg);

        if (!checkLocation) {
            markerName = googleMap.addMarker(new MarkerOptions().position(apiLatLng).snippet(id));
            markerName.remove();

        }
        if (checkLocation) {
            MarkerOptions marker = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.truck));
            marker.snippet(id);
            marker.position(apiLatLng);
            marker.visible(true);
            googleMap.addMarker(marker);

        }

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                showUser2Dialog(marker.getSnippet(), marker.getPosition());
                return false;

            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

    }

    private void showUser2Dialog(String id, LatLng markerLatLng) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.user2_dialog);
        // tvUserEmail = dialog.findViewById(R.id.tv_user2_email);
        Button btnGetDirection = dialog.findViewById(R.id.btn_get_direction);
        tvUsername = dialog.findViewById(R.id.tv_user2_username);
        ivOne = dialog.findViewById(R.id.iv_user2_one);
        ivTwo = dialog.findViewById(R.id.iv_user2_two);
        alertDialog = AlertUtils.createProgressDialog(getActivity());
        alertDialog.show();
        apiCallUser2Profile(id);
        dialog.show();


        ivOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkUtils.showImage(getActivity(), strImageOne, strName);
            }
        });

        ivTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkUtils.showImage(getActivity(), strImageTwo, strName);
            }
        });

        btnGetDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps.mytracks?saddr=" +
                                String.valueOf(markerLatLng.latitude) + "," +
                                String.valueOf(markerLatLng.longitude) +
                                "&daddr=" + GeneralUtils.getUserLatitude(getActivity()) +
                                "," + GeneralUtils.getUserLongitude(getActivity())));
                startActivity(intent);
            }
        });
        dialog.show();
    }


    private void apiCallUser2Profile(String id) {
        ApiInterface services = ApiClient.getApiClient().create(ApiInterface.class);
        Call<SellerProfileResponseModel> userLogin = services.getUser2Profile(Integer.parseInt(id));
        userLogin.enqueue(new Callback<SellerProfileResponseModel>() {
            @Override
            public void onResponse(Call<SellerProfileResponseModel> call, Response<SellerProfileResponseModel> response) {
                alertDialog.dismiss();
                if (response.body() == null) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else if (response.body().getSuccess()) {
                    // tvUserEmail.setText(response.body().getData().getEmail());
                    strName = response.body().getData().getTextField();
                    strImageOne = response.body().getData().getImage1();
                    strImageTwo = response.body().getData().getImage2();

                    tvUsername.setText(strName);
                    Glide.with(getActivity())
                            .load(strImageOne)
                            .placeholder(R.drawable.profile)
                            .into(ivOne);
                    Glide.with(getActivity())
                            .load(strImageTwo)
                            .placeholder(R.drawable.profile)
                            .into(ivTwo);
                }
            }

            @Override
            public void onFailure(Call<SellerProfileResponseModel> call, Throwable t) {
                alertDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private float calculateZoomLevel(int screenWidth) {
        double equatorLength = 7000; // in meters
        double widthInPixels = screenWidth;
        double metersPerPixel = equatorLength / 256;
        int zoomLevel = 14;
        while ((metersPerPixel * widthInPixels) > 2000) {
            metersPerPixel /= 2;
            ++zoomLevel;
        }
        return zoomLevel;
    }


    private void snackBar(String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG);

    }


}