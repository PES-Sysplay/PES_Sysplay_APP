package com.example.workoutapp.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.R;
import com.example.workoutapp.ui.home.ActivityListAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {


    List<Activitat> activity_list = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                activity_list = ActivityListAdapter.getInstance(root.getContext(), new ArrayList<>()).copyInfo();
                Log.d("hola",String.valueOf(activity_list.size()));
                for(int i = 0; i < activity_list.size(); ++i){
                    String[] locations = activity_list.get(i).getLocation().split(", ");
                    Log.d("hola", locations[1]);
                    LatLng sydney = new LatLng(Double.parseDouble(locations[0]), Double.parseDouble(locations[1]));
                    googleMap.addMarker(new MarkerOptions().position(sydney).title(activity_list.get(i).getName()));

                }
                /*googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        MarkerOptions markerOptions = new MarkerOptions();

                        markerOptions.position(latLng);
                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                        googleMap.clear();

                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                latLng,10
                        ));
                        googleMap.addMarker(markerOptions);
                    }
                });*/
            }
        });
        return root;
    }
}