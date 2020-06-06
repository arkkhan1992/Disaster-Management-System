package com.example.dms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.fromResource;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private GoogleMap mMap;
    final String getLocationsAPI = "http://192.168.0.104/WebApi/fetchLocations.php";
    private FusedLocationProviderClient mFusedLocationClient;
    private Polyline currentPolyline;
    boolean isPermissionGranted = false;
    String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    int LOCATION_REQUEST_CODE = 123;
    Location location, locationDrop;
    LatLng latLng;
    MarkerOptions currentMarker;
    MarkerOptions dropMarker;
    TextView cal_distance;
    float distance;
    TextView distance_km;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        cal_distance = findViewById(R.id.distance_km);


    }


    public void getCurrentLocation() {
        if (isPermissionGranted) {
            mFusedLocationClient = new FusedLocationProviderClient(this);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            final Task task = mFusedLocationClient.getLastLocation();

            task.addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    if (task.isComplete()) {

                        SharedPreferences sharedPreferences = getSharedPreferences("location", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        location = (Location) task.getResult();
                        latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        //editor.putLong("current",latLng);
                    }
                }
            });
        } else {
            checkCurrentLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            isPermissionGranted = true;
        } else {
            isPermissionGranted = false;
        }
        return;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        // LatLng johar = new LatLng(24.924016, 67.138213);
        //  mMap.addMarker(new MarkerOptions().position(johar).title("Marker in Sydney"));
        //      mMap.moveCamera(CameraUpdateFactory.newLatLng(johar));
        //       mMap.animateCamera(CameraUpdateFactory.zoomTo(25f));
        getLocations();


        if (isPermissionGranted) {
            getCurrentLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            currentMarker = new MarkerOptions().position(latLng);


        } else {
            checkCurrentLocation();

        }


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mFusedLocationClient = new FusedLocationProviderClient(getApplicationContext());

          //      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
           //         return TODO;
             //   }
              //  final Task task = smFusedLocationClient.getLastLocation();

          //      task.addOnSuccessListener(new OnSuccessListener() {
            //        @Override
              //      public void onSuccess(Object o) {
                //        if (task.isComplete())
                  //      {
                    //        location = (Location) task.getResult();
                      //      latLng = new LatLng(location.getLatitude(),location.getLongitude());
                     //   }
                   // }
               // });

//                                    Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(),"Current"+location,Toast.LENGTH_LONG).show();

                if (latLng!=null) {


                    currentMarker = new MarkerOptions().position(latLng);
                    dropMarker = new MarkerOptions().position(marker.getPosition());


                    new FetchURL(MapsActivity.this).execute(getUrl(currentMarker.getPosition(), dropMarker.getPosition(), "driving"), "driving");

                    Location temp = new Location("Drop");
                    temp.setLatitude(latLng.latitude);
                    temp.setLongitude(latLng.longitude);

//                    float[] rs = null;
//                    location.distanceBetween(latLng.latitude,latLng.longitude,marker.getPosition().latitude,
//                            marker.getPosition().longitude,rs);
//
//                    Toast.makeText(getApplicationContext(),String.valueOf(rs),Toast.LENGTH_LONG).show();

                    distance(latLng.latitude,latLng.longitude,marker.getPosition().latitude,
                            marker.getPosition().longitude);

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please wait while map is loading",Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });


    }

    public double distance(Double latitude, Double longitude, double e, double f) {
        double d2r = Math.PI / 180;

        double dlong = (longitude - f) * d2r;
        double dlat = (latitude - e) * d2r;
        double a = Math.pow(Math.sin(dlat / 2.0), 2) + Math.cos(e * d2r)
                * Math.cos(latitude * d2r) * Math.pow(Math.sin(dlong / 2.0), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = 6367 * c;
        float finalDistance = (float) d;
        String formattedValue = String.format("%.2f",finalDistance);

        //Toast.makeText(getApplicationContext(),String.valueOf(formattedValue)+" KM",Toast.LENGTH_LONG).show();

        cal_distance.setText("Total Distance:  "+formattedValue+" KM ");


        return d;

    }

    public void checkCurrentLocation(){
        String[]permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this, FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(this,COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED) {

            isPermissionGranted = true;
            mMap.setMyLocationEnabled(true);


        }
        else
        {
            ActivityCompat.requestPermissions(this,permissions,LOCATION_REQUEST_CODE);
        }
    }

public void getLocations(){
    StringRequest stringRequest = new StringRequest(Request.Method.GET, getLocationsAPI,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("locations");
                        for (int i=0;i<jsonArray.length();i++){
                            final JSONObject object = jsonArray.getJSONObject(i);
                            if (object.getString("type").equals("disaster_zone")){
                                final double lat = object.getDouble("lat");
                                final double lng = object.getDouble("long");

                                LatLng dropLatLng = new LatLng(lat,lng);
//                                dropMarker = new MarkerOptions().position(dropLatLng);
                                mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(object.getString("lat")),Double.valueOf(object.getString("long")))).title(object.getString("location_name")));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.valueOf(object.getString("lat")),Double.valueOf(object.getString("long")))));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(12f));

                            }
                            else if(object.getString("type").equals("Help_zone"))
                            {
//
                                int height = 100;
                                int width = 80;
                                BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.fire);
                                Bitmap b=bitmapdraw.getBitmap();
                                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                                mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(object.getString("lat")),Double.valueOf(object.getString("long")))).title(object.getString("location_name"))).setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.valueOf(object.getString("lat")),Double.valueOf(object.getString("long")))));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(12f));
                                final double lat = object.getDouble("lat");
                                final double lng = object.getDouble("long");

                                LatLng dropLatLng = new LatLng(lat,lng);
  //                              dropMarker = new MarkerOptions().position(dropLatLng);


                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }






                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });
    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(stringRequest);
}

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }



    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);

    }
}
