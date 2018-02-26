package com.example.vankien.dating.Views.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vankien.dating.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {
    MapView mMapView;
    GoogleMap googleMap;
    Location myLocation;
    LocationManager mLocationManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("TAG","Map OnCreateView");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                if(myLocation != null){
                    addMarkerMyLocationOnMap();
                }
            }
        });
        return rootView;
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG","Map OnCreate");
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("TAG","Not have permission");
            return;
        }
        final Location loc = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(loc == null){
            final LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    myLocation = location;
                    Log.e("TAG Location",""+location.getLatitude()+location.getLongitude());
                    if(googleMap != null){
                        addMarkerMyLocationOnMap();
                    }
                }
                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }
                @Override
                public void onProviderEnabled(String s) {

                }
                @Override
                public void onProviderDisabled(String s) {

                }
            };
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 500, locationListener);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 500, locationListener);
        }else{
            myLocation = loc;
            Log.e("TAG Location",""+loc.getLatitude()+loc.getLongitude());
            if(googleMap != null){
                addMarkerMyLocationOnMap();
            }
        }
    }

    private void addMarkerMyLocationOnMap(){
        LatLng mylatlng = new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
        CameraPosition position = new CameraPosition.Builder().target(mylatlng).zoom(15).bearing(19).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));

        googleMap.addMarker(new MarkerOptions().position(mylatlng).title("I'm here").snippet("Do you see me?"));
    }
































    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
