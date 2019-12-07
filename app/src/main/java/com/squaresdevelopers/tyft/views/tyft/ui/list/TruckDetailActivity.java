package com.squaresdevelopers.tyft.views.tyft.ui.list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squaresdevelopers.tyft.R;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;
import com.squaresdevelopers.tyft.utilities.InternetUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TruckDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    @BindView(R.id.iv_user_one)
    ImageView ivOne;
    @BindView(R.id.iv_user_two)
    ImageView ivTwo;
    @BindView(R.id.truck_name)
    TextView truckName;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.btn_navigate)
    Button btnNavigate;
    @BindView(R.id.truck_mapView)
    MapView mapView;

    private GoogleMap googleMap;
    private String truckLat, truckLng, userLat, userLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_detail);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            truckName.setText(bundle.getString("name"));
            tvDate.setText(bundle.getString("date"));
            tvStartTime.setText(bundle.getString("start_time"));
            tvEndTime.setText(bundle.getString("end_time"));

            Glide.with(this).load(bundle.getString("image_one")).into(ivOne);
            Glide.with(this).load(bundle.getString("image_two")).into(ivTwo);

            truckLat = bundle.getString("lat");
            truckLng = bundle.getString("lng");
            userLat = GeneralUtils.getUserLatitude(this);
            userLng = GeneralUtils.getUserLongitude(this);
        }

        if (InternetUtils.isNetworkConnected(this)) {
            mapView.onCreate(savedInstanceState);
            mapView.onResume();
            try {
                MapsInitializer.initialize(this);
                mapView.getMapAsync(this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps.mytracks?saddr=" +
                                truckLat + "," +
                                truckLng +
                                "&daddr=" + userLat +
                                "," + userLng));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker();

        LatLng userLatLng = new LatLng(Double.parseDouble(userLat), Double.parseDouble(userLng));
        googleMap.addMarker(new MarkerOptions().position(userLatLng)
                .title(getResources().getString(R.string.current_location))
                .snippet(getResources().getString(R.string.this_is_your_location)))
                .setIcon(icon);

        BitmapDescriptor truckIcon = BitmapDescriptorFactory.fromResource(R.drawable.truck);
        LatLng truckLatLng = new LatLng(Double.parseDouble(truckLat), Double.parseDouble(truckLng));
        googleMap.addMarker(new MarkerOptions().position(truckLatLng)
                .title("Truck Location"))
                .setIcon(truckIcon);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(truckLatLng, 15);
        googleMap.animateCamera(cameraUpdate);

        Polyline line = googleMap.addPolyline(new PolylineOptions()
                .add(userLatLng, truckLatLng)
                .width(5)
                .color(Color.RED));

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


    @Override
    public void onLowMemory() {
        super.onLowMemory();

    }

}
