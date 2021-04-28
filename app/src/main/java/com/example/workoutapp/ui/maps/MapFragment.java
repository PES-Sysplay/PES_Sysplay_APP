package com.example.workoutapp.ui.maps;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.R;
import com.example.workoutapp.ui.home.ActivityListAdapter;
import com.example.workoutapp.ui.home.ScrollingActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {

    List<Marker> markers = new ArrayList<>();
    List<Activitat> activity_list = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {


            int i = 0;

            @Override
            public void onMapReady(GoogleMap googleMap) {

                activity_list = ActivityListAdapter.getInstance(root.getContext(), new ArrayList<>()).copyInfo();

                while(i < activity_list.size()){
                    String[] locations = activity_list.get(i).getLocation().split(", ");
                    LatLng sydney = new LatLng(Double.parseDouble(locations[0]), Double.parseDouble(locations[1]));
                     markers.add(googleMap.addMarker(new MarkerOptions().position(sydney).title(activity_list.get(i).getName())));

                    ++i;
                }

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (Marker marker : markers) {
                    builder.include(marker.getPosition());
                }
                LatLngBounds bounds = builder.build();
                int padding = 250; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                googleMap.moveCamera(cu);
                googleMap.animateCamera(cu);

                googleMap.setOnMarkerClickListener(marker -> {
                    i = 0;
                    while (i < activity_list.size()) {

                        if (marker.equals(markers.get(i))) {
                            //Log.d("ENTRA AL IFFFF", String.valueOf(i));

                            CoordinatorLayout rootlayout = root.findViewById(R.id.coordinatorLayout);
                            Snackbar snackbar = Snackbar.make(rootlayout, activity_list.get(i).getName(), Snackbar.LENGTH_INDEFINITE).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                            snackbar.setAction("IR A LA ACTIVIDAD", new View.OnClickListener() {
                                int j = i;
                                @Override
                                public void onClick(View v) {

                                    Context context = v.getContext();
                                    Intent intent = new Intent(context, ScrollingActivity.class);
                                    intent.putExtra("Position recycler", j);
                                    intent.putExtra("From", "map");
                                    context.startActivity(intent);
                                }
                            });
                            snackbar.setActionTextColor(Color.parseColor("#7f5aa0"));

                            snackbar.show();
                        }
                        ++i;
                    }

                    return false;
                });

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


        });
        return root;
    }
}