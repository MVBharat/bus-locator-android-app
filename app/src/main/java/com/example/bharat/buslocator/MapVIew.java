package com.example.bharat.buslocator;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.IOException;
import java.util.List;

public class MapVIew extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    GoogleMap mGoogleMap;
    GoogleApiClient mGoogleApiClinet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (GoogleServicesAvailable()) {
            Toast.makeText(this, "Connected to GPS", Toast.LENGTH_LONG).show();
            setContentView(R.layout.map_layout);
            initMap();
        } else {

        }

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private boolean GoogleServicesAvailable() {

        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't connect to play services", Toast.LENGTH_LONG).show();
        }

        return false;
    }


    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

    }

    public void onMapReady(GoogleMap googlemap) {
        mGoogleMap = googlemap;


        if (mGoogleMap != null) {

           mGoogleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(Marker marker) {

                    Geocoder gc= new Geocoder(MapVIew.this);
                    LatLng ll = marker.getPosition();
                    double lat=ll.latitude;
                    double lng=ll.longitude;
                    List<Address> list=null;
                    try {
                         list=gc.getFromLocation(lat,lng,1);
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                    Address add=list.get(0);
                    marker.setTitle(add.getLocality());
                 marker.showInfoWindow();

                }
            });

            mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {

                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    View v = getLayoutInflater().inflate(R.layout.mapinfo_window, null);

                    TextView tvlocality = (TextView) v.findViewById(R.id.tv_locality);
                    TextView tvlat = (TextView) v.findViewById(R.id.tv_lat);
                    TextView tvlng = (TextView) v.findViewById(R.id.tv_lng);
                    TextView tvsnippet = (TextView) v.findViewById(R.id.tv_snippet);

                    LatLng ll = marker.getPosition();
                    tvlocality.setText(marker.getTitle());
                    tvlat.setText("Latitude: " + ll.latitude);
                    tvlng.setText("Longitude: " + ll.longitude);
                    tvsnippet.setText(marker.getSnippet());

                    return v;
                }
            });
        }

            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
      //      goToLocationZom(15.7777762, 74.4634341, 20);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleApiClinet = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClinet.connect();

        }


    private void goToLocationZom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mGoogleMap.moveCamera(update);
    }

    public void geoLocate(View view) throws IOException {
        EditText et = (EditText) findViewById(R.id.editText);
        String location = et.getText().toString();

        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(location, 1);

        Address address = list.get(0);
        String locality = address.getLocality();

        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

        double lat = address.getLatitude();
        double lng = address.getLongitude();
        goToLocationZom(lat, lng, 15);

        setMarker(locality, lat, lng);

    }

    Circle circle;
    Marker marker1;
    Marker marker2;
    Polygon line;

    private void setMarker(String locality, double lat, double lng) {
     /*   if(marker!=null){
            removeEverything();
        }
*/        MarkerOptions options = new MarkerOptions()
                .title(locality)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.buslocatoricon))
                .position(new LatLng(lat,lng))
                .snippet("Your Bus is Here")
                .draggable(true);

        if(marker1==null) {
            marker1 = mGoogleMap.addMarker(options);
        }else if(marker2==null){
            marker2 = mGoogleMap.addMarker(options);
            drawLine();
        }else{
            removeEverything();
            marker1=mGoogleMap.addMarker(options);
        }
        circle = drawCircle(new LatLng(lat, lng));

    }

    private void drawLine() {
        PolygonOptions options= new PolygonOptions()
                                    .add(marker1.getPosition())
                                    .add(marker2.getPosition());
                                    /*.color(Color.BLUE)
                                    .width(4);*/
        line=mGoogleMap.addPolygon(options);
    }

    private Circle drawCircle(LatLng latLng) {

        CircleOptions options = new CircleOptions()
                            .center(latLng)
                            .radius(500)
                            .fillColor(0x33FF0000)
                            .strokeColor(Color.BLUE)
                            .strokeWidth(3);
        return mGoogleMap.addCircle(options);
    }

    private void removeEverything(){
        marker1.remove();
        marker1=null;
        marker2.remove();
        marker2=null;
        circle.remove();
        circle=null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mapTypeNone:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.mapTypeNormal:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeSatellite:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapTypeTerrain:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mapTypeHybrid:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;

            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    LocationRequest mLocaitonRequest;

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocaitonRequest = LocationRequest.create();
        mLocaitonRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocaitonRequest.setInterval(1000);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClinet, mLocaitonRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if(location==null){
            Toast.makeText(getApplicationContext(),"Can't get current locaiton",Toast.LENGTH_LONG).show();
        }
        else {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,15);
            mGoogleMap.animateCamera(update);
        }
    }
}
















        /*
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);

        if(mGoogleMap !=null){
            mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    View v= getLayoutInflater().inflate(R.layout.mapinfo_window,null);

                    TextView tvlocality = (TextView) v.findViewById(R.id.tv_locality);
                    TextView tvlat = (TextView) v.findViewById(R.id.tv_lat);
                    TextView tvlng = (TextView) v.findViewById(R.id.tv_lng);
                    TextView tvsnippet = (TextView) v.findViewById(R.id.tv_snippet);

                    LatLng ll = marker.getPosition();
                    tvlocality.setText(marker.getTitle());
                    tvlat.setText("Latitude: " + ll.latitude);
                    tvlng.setText("Longitude: "+ ll.longitude);
                    tvsnippet.setText(marker.getSnippet());

                    return v;
                }
            });
        }

        //goToLocationZom(15.775405,74.4680547,20);
        //goToLocation(15.775405,74.4680547);
      /*

        mGoogleApiClinet = new GoogleApiClient.Builder(this)
                                .addApi(LocationServices.API)
                                .addConnectionCallbacks(this)
                                .addOnConnectionFailedListener(this)
                                .build();

    }

    private void goToLocation(double lat, double lng) {
        LatLng ll=new LatLng(lat,lng);
        CameraUpdate update= CameraUpdateFactory.newLatLng(ll);
        mGoogleMap.moveCamera(update);
    }

    private void goToLocationZom(double lat, double lng, float zoom) {
        LatLng ll=new LatLng(lat,lng);
        CameraUpdate update= CameraUpdateFactory.newLatLngZoom(ll,zoom);
        mGoogleMap.moveCamera(update);
    }

    Marker marker;

    public void geoLocate(View view) throws IOException {
        EditText et=(EditText)findViewById(R.id.editText);
        String location= et.getText().toString();

        Geocoder gc = new Geocoder(this);
        List<Address> list=gc.getFromLocationName(location,1);

        Address address= list.get(0);
        String locality=address.getLocality();

        Toast.makeText(this,locality,Toast.LENGTH_LONG).show();

        double lat = address.getLatitude();
        double lng = address.getLongitude();
        goToLocationZom(lat,lng,15);

        setMarker(locality, lat, lng);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mapTypeNone:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.mapTypeNormal:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeSatellite:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapTypeTerrain:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mapTypeHybrid:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;

            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setMarker(String locality, double lat, double lng) {
        if(marker !=null){
            marker.remove();
        }

        MarkerOptions options = new MarkerOptions()
                .title(locality)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.b))
                .position(new LatLng(lat,lng))
                .snippet("Your Bus is Here");
        marker = mGoogleMap.addMarker(options);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
    */

