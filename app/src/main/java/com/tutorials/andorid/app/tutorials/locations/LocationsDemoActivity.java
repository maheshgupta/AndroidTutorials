package com.tutorials.andorid.app.tutorials.locations;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.tutorials.andorid.app.R;

import java.util.ArrayList;

public class LocationsDemoActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private GoogleApiClient googleApiClient;

    private LocationRequest locationRequest;

    ArrayList<String> locations = new ArrayList<>();
    ArrayAdapter<String> locationAdapter;
    ListView listViewLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations_demo);
        listViewLocations = (ListView) findViewById(R.id.list_view_locations);
        this.locationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, locations);
        listViewLocations.setAdapter(this.locationAdapter);
        this.googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        prepareLocationRequest();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (this.googleApiClient != null && this.googleApiClient.isConnected()) {
            this.googleApiClient.disconnect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.googleApiClient != null && this.googleApiClient.isConnected()) {
            getLocations();
        }
    }


    private void getLocations() {

        LocationServices.FusedLocationApi.requestLocationUpdates(this.googleApiClient, this.locationRequest, this);

        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(this.googleApiClient);
        Log.i("", "Mahesh : getLocations: Longitude : " + lastLocation.getLongitude() + " Latitude : " + lastLocation.getLatitude());
        this.addLocation(lastLocation);
    }

    private void prepareLocationRequest() {
        this.locationRequest = new LocationRequest();
        this.locationRequest.setInterval(1000);
        this.locationRequest.setFastestInterval(10000);
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getLocations();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("", "Mahesh : Location Update : Longitude : " + location.getLongitude() + " Latitude : " + location.getLatitude());
        addLocation(location);
    }


    private void addLocation(Location location) {
        this.locations.add(new String("Lat: " + location.getLatitude() + " Long: " + location.getLongitude()));
        this.locationAdapter.notifyDataSetChanged();
    }

}
