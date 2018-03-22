package com.example.vankien.dating.Views.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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

import com.example.vankien.dating.Controllers.MapController;
import com.example.vankien.dating.Controllers.MapControllerCallback;
import com.example.vankien.dating.Models.PeopleAround;
import com.example.vankien.dating.R;
import com.example.vankien.dating.Utils.ImageUtils;
import com.example.vankien.dating.Views.Activity.DetailActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.HashMap;

public class MapFragment extends Fragment implements MapControllerCallback {
    MapView mMapView;
    GoogleMap googleMap;
    Location myLocation;
    LocationManager mLocationManager;
    HashMap<MarkerOptions,String> markerOptions;

    MapController controller;
    String id;
    View rootView;

    ArrayList<PeopleAround> peopleArounds;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("TAG","Map OnCreateView");
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        markerOptions = new HashMap<>();
        peopleArounds = new ArrayList<>();

        initMap(savedInstanceState);

        return rootView;
    }
    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        controller = MapController.getShareInstance();
        if (visible) {
            Log.e("Map Screen","load data");
            controller.callback = this;
            controller.requestPeopleAround(id);
        }else{
            controller.callback = null;
        }
    }

    void initMap(Bundle savedInstanceState){
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
        updateMyLocationIntoFirebase();
        LatLng mylatlng = new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
        CameraPosition position = new CameraPosition.Builder().target(mylatlng).zoom(15).bearing(19).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));

        googleMap.addMarker(new MarkerOptions().position(mylatlng).title("I'm here").snippet("Do you see me?"));
        if (peopleArounds.size()>0){
            loadImageIntoMap();
        }
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

    @Override
    public void getAroundPeopleSuccess(ArrayList<PeopleAround> peopleArounds) {
        this.peopleArounds.clear();
        this.peopleArounds.addAll(peopleArounds);
        Log.e("Map Screen","callback");
        loadImageIntoMap();
    }


    private void loadImageIntoMap(){
        if (mMapView != null){
            for (PeopleAround people: peopleArounds){
                loadUserIntoMap(people);
            }
        }

    }

    /** Hàm này để load avatar và gắn vào marker
     * @param people object bao gồm avatar trong đó
     */
    private void loadUserIntoMap(final PeopleAround people){
        if (!people.getId().equals(id)){
            final LatLng mylatlng = people.getAddressLatLng();
            String avatar = people.getAvatarUrl();
            Uri uri = Uri.parse(avatar);
            Picasso.with(getContext()).load(uri).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Bitmap squareBitmap = ImageUtils.cropToSquare(bitmap);
                    Bitmap resized = Bitmap.createScaledBitmap(squareBitmap, 50, 50, true);
                    Bitmap circle = ImageUtils.getCircleBitmap(resized,true);
                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(circle);
                    MarkerOptions marker = new MarkerOptions().icon(bitmapDescriptor).position(mylatlng).title(people.getName()).snippet(people.getAddress());
                    markerOptions.put(marker,people.getId());
                    googleMap.addMarker(marker);
                }
                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        }
    }


    /** Khi người dùng sử dụng gps để định vị thì
     * hàm này sẽ lấy vị trí đó và update lên database
     */
    private void updateMyLocationIntoFirebase(){
        FirebaseDatabase.getInstance().getReference().child("Profile").child(id).child("latitude").setValue(myLocation.getLatitude());
        FirebaseDatabase.getInstance().getReference().child("Profile").child(id).child("longitude").setValue(myLocation.getLongitude());
    }
}
