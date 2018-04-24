package com.example.vankien.dating.Views.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.vankien.dating.Controllers.MapController;
import com.example.vankien.dating.Interface.MapDelegate;
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
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class MapFragment extends Fragment implements MapDelegate {

    MapView mMapView;
    GoogleMap googleMap;
    Location myLocation;
    LocationManager mLocationManager;
    HashMap<Marker, String> markers;
    Marker myMarker;
    MapController controller;
    String id;
    View rootView;
    LocationListener locationListener;

    Circle circleGoogleMap;

    ArrayList<PeopleAround> peopleArounds;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("TAG", "Map OnCreateView");
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        markers = new HashMap<>();
        peopleArounds = new ArrayList<>();

        initMap(savedInstanceState);

        return rootView;
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        controller = MapController.getShareInstance();
        if (visible) {
            Log.e("Map Screen", "load data");
            controller.delegate = this;
            controller.requestPeopleAround(id,true);
            getLocation();
        } else {
            controller.delegate = null;
        }
    }

    void initMap(Bundle savedInstanceState) {
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
                addActionWhenTapOnMarker();
                if (myLocation != null) {
                    addMarkerMyLocationOnMap();

                }
            }
        });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG", "Map OnCreate");
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        getLocation();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        final Location loc = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(loc == null){
            locationListener = new LocationListener() {
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
        if (myMarker != null){
            myMarker.remove();
        }
        myMarker = googleMap.addMarker(new MarkerOptions().position(mylatlng).title("I'm here").snippet("Do you see me?"));
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySetting",MODE_PRIVATE);
        int radius = sharedPreferences.getInt("distance",0);
        if (circleGoogleMap != null) {
            circleGoogleMap.remove();
        }
        circleGoogleMap = googleMap.addCircle(new CircleOptions()
                .center(mylatlng)
                .radius(radius*1000)
                .strokeColor(getActivity().getResources().getColor(R.color.greenCircle))
                .strokeWidth(2)
                .fillColor(0x5500ff00));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                circleGoogleMap.getCenter(), getZoomLevel(circleGoogleMap)));

        if (peopleArounds.size()>0){
            loadImageIntoMap();
        }
    }
    public int getZoomLevel(Circle circle) {
        int zoomLevel = 11;
        if (circle != null) {
            double radius = circle.getRadius() + circle.getRadius() / 2;
            double scale = radius / 500;
            zoomLevel = (int) (16 - Math.log(scale) / Math.log(2));
        }
        return zoomLevel;
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
        Log.e("Map Screen","delegate");
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
            final Uri uri = Uri.parse(avatar);
            Picasso.with(getContext()).load(uri).resize(100,100).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Bitmap squareBitmap = ImageUtils.cropToSquare(bitmap);
                    Bitmap resized = Bitmap.createScaledBitmap(squareBitmap, 80, 80, true);
                    Bitmap circle = ImageUtils.getCircleBitmap(resized,true);
                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(circle);
                    MarkerOptions markerOption = new MarkerOptions().icon(bitmapDescriptor).position(mylatlng).title(people.getName()).snippet(people.getAddress());
                    Log.e("Map Screen: id",people.getId());
                    if (circleGoogleMap != null){
                        float[] distance = new float[2];

                        Location.distanceBetween(mylatlng.latitude, mylatlng.longitude, circleGoogleMap.getCenter().latitude,circleGoogleMap.getCenter().longitude,distance);

                        if ( distance[0] <= circleGoogleMap.getRadius()) {
                            Marker marker = googleMap.addMarker(markerOption);
                            markers.put(marker,people.getId());
                        } else {

                        }
                    }
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

    private void addActionWhenTapOnMarker(){
        if(googleMap != null){
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    if (marker != myMarker){
                        String id = markers.get(marker);
                        Intent intent = new Intent(getActivity(),DetailActivity.class);
                        Log.e("Map Screen",id);
                        intent.putExtra("UserID",id);
                        startActivity(intent);
                    }
                }
            });
        }
    }


}
