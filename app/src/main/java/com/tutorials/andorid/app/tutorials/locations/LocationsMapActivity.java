package com.tutorials.andorid.app.tutorials.locations;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tutorials.andorid.app.R;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class LocationsMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 340;
    private GoogleMap mMap;

    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;

    private static final int MILLI_SECS = 1000;
    private static final int SEC = 1 * MILLI_SECS;
    private static final int GPS_INTERVAL = 1 * SEC;
    private static final int FASTEST_INTERVAL = 15 * SEC;
    private Marker lastMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_location);
        this.googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        prepareLocationRequest();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void prepareLocationRequest() {
        this.locationRequest = new LocationRequest();
        this.locationRequest.setInterval(GPS_INTERVAL);
        this.locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        this.locationRequest.setFastestInterval(FASTEST_INTERVAL);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.googleApiClient.disconnect();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void startForLocations() {
        if (hasLocationPermissions()) {
            if (this.googleApiClient.isConnected()) {
                startLocationUpdates();
            }
        } else {
            requestForPermissions();
        }
    }

    private void startLocationUpdates() {
        @SuppressLint("MissingPermission") PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                this.googleApiClient, this.locationRequest, this);
        @SuppressLint("MissingPermission") Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(this.googleApiClient);
        if (lastLocation!=null) {
            showLocationOnMap(lastLocation.getLatitude(), lastLocation.getLongitude());
        }
    }

    private void showLocationOnMap(double latitude, double longitude) {
        if (this.mMap != null) {
//            mMap.moveCamera();
            if (this.lastMarker!=null){
                this.lastMarker.remove();
            }
            LatLng latLng = new LatLng(latitude, longitude);
            CameraUpdate cameraLatLng = CameraUpdateFactory.newLatLng(latLng);
            mMap.moveCamera(cameraLatLng);
            mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 1000, null);

            this.lastMarker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.bitmap_announcement)));
            this.lastMarker.setTitle("Last Message");

        }
    }


    private boolean hasLocationPermissions() {
        int permissionFineLocation = ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);
        int permissionCoarseLocation = ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION);
        return permissionFineLocation == PackageManager.PERMISSION_GRANTED && permissionCoarseLocation == PackageManager.PERMISSION_GRANTED;
    }

    private void requestForPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setCompassEnabled(true);
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        startForLocations();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (hasLocationPermissions()) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            showLocationOnMap(location.getLatitude(), location.getLongitude());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            startLocationUpdates();
        }
    }
}
