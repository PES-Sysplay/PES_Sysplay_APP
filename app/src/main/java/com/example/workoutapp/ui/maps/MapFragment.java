package com.example.workoutapp.ui.maps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.R;
import com.example.workoutapp.ui.home.ActivityListAdapter;
import com.example.workoutapp.ui.home.ActivityDetail;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    List<Marker> markers = new ArrayList<>();
    List<Activitat> activity_list = new ArrayList<>();
    LocationRequest mLocationRequest;
    FusedLocationProviderClient mFusedLocationClient;
    boolean location = false;
    GoogleMap mGoogleMap;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @SuppressLint("RestrictedApi")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_maps, container, false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setShowHideAnimationEnabled(false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();

        CheckPermissionLocation();
        return root;
    }

    public void initMap() {

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }
    void CheckPermissionLocation(){
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            location = true;
            initMap();
        }
        //no ha donat permissos mai o no han estat donats encara
        else {
            //Request Location Permission
            checkLocationPermission();
        }

    }

    @SuppressLint("MissingPermission") //the permission are already ask
    @Override
    public void onMapReady(@NotNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        //mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(0); // two minute interval
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if(location){
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            mGoogleMap.setMyLocationEnabled(true);

            ActivitiesDisplay(googleMap);
        }
        else {
            ActivitiesDisplay(googleMap);

        }
    }

    void ActivitiesDisplay(GoogleMap googleMap){
        int i = 0;

        activity_list = ActivityListAdapter.getInstance(getContext(), new ArrayList<>()).copyInfo();

        while(i < activity_list.size()){
            String[] locations = activity_list.get(i).getLocation().split(", ");
            LatLng sydney = new LatLng(Double.parseDouble(locations[0]), Double.parseDouble(locations[1]));
            markers.add(googleMap.addMarker(new MarkerOptions().position(sydney).title(activity_list.get(i).getName())));

            ++i;
        }
        if(!location) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Marker marker : markers) {
                builder.include(marker.getPosition());
            }
            LatLngBounds bounds = builder.build();
            int padding = 250; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            googleMap.animateCamera((cu),2000,null);
        }
        googleMap.setOnMarkerClickListener(marker -> {
            for(int ii =0; ii<activity_list.size(); ++ii){

                if (marker.equals(markers.get(ii))) {

                    CoordinatorLayout rootlayout = getActivity().findViewById(R.id.coordinatorLayout);
                    Snackbar snackbar = Snackbar.make(rootlayout, activity_list.get(ii).getName(), Snackbar.LENGTH_INDEFINITE).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    int finalIi = ii;
                    snackbar.setAction("IR A LA ACTIVIDAD", new View.OnClickListener() {
                        int j = finalIi;
                        @Override
                        public void onClick(View v) {

                            Context context = v.getContext();
                            Intent intent = new Intent(context, ActivityDetail.class);
                            intent.putExtra("Position recycler", j);
                            intent.putExtra("From", "map");
                            context.startActivity(intent);
                        }
                    });
                    snackbar.setActionTextColor(Color.parseColor("#7f5aa0"));

                    snackbar.show();
                }
            }

            return false;
        });
    }
    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }
                //Place current location marker
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());




                /*MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current Position");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);*/

                //move map camera
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15),2000,null);
            }
        }
    };

    private void checkLocationPermission() {
        //
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {


                        location = true;
                    }

                } else {

                    location = false;
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();

                }
                initMap();
            }

        }
    }

}




