package com.example.appgooutec.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appgooutec.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class FragMap extends Fragment {
    private String lat;
    private String lon;
    private String nombreLugar;
    private LatLng location;
    private UiSettings uiSettings;
    private boolean tienepermisos=false;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            int permisosFineLocation=ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
            tienepermisos=permisosFineLocation==PackageManager.PERMISSION_GRANTED;
            String[] permisos= {Manifest.permission.ACCESS_FINE_LOCATION};
            if(tienepermisos){
                Log.i("fb-m","Tiene Permisos de Mapa");
            }else {
                ActivityCompat.requestPermissions(getActivity(), permisos ,1);
            }
            uiSettings = googleMap.getUiSettings();
            uiSettings.setZoomControlsEnabled(true);
            uiSettings.setMyLocationButtonEnabled(true);
            googleMap.setMyLocationEnabled(true);
            googleMap.addMarker(new MarkerOptions().position(location).title(nombreLugar));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15f));




                 }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_frag_map, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lat=getArguments().getString("lat");
        lon=getArguments().getString("lon");
        nombreLugar=getArguments().getString("place");
        location=new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
    }
}