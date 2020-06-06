package com.example.dms;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisasterAdd extends AppCompatActivity implements OnMapReadyCallback{
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    GoogleMap map;
    SupportMapFragment supportMapFragment;
    SearchView searchView;
    Spinner spinner;
    final String addRec = "http://192.168.0.104/WebApi/addRecord.php";
    String type;
    Button addDMS;
    LatLng latLng;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_add);

        spinner=findViewById(R.id.spinnerType);

        List<String> list=new ArrayList<String>();
        list.add("Select DMS Type");
        list.add("disaster_zone");
        list.add("Help_zone");
        ArrayAdapter arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0)
                {
                    //String type=String.valueOf(position);
                    //Toast.makeText(AdminDashboardMap.this, "", Toast.LENGTH_SHORT).show();
                    type = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), "Selected: " + type, Toast.LENGTH_LONG).show();


                }
                else
                {
                    Toast.makeText(DisasterAdd.this, "Select DMS Type First", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);

        searchView=findViewById(R.id.search_location);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList=null;
                if (location !=null || !location.equals(""))
                {
                    map.clear();
                    Geocoder geocoder=new Geocoder(getApplicationContext());
                    try {
                        addressList=geocoder.getFromLocationName(location,1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    latLng =new LatLng(address.getLatitude(),address.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng).title("Double Tap here to remove this marker"));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));

//            MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions.snippet("Tap here to remove this marker");
//
//            // Setting title for the InfoWindow
//            markerOptions.title("Marker Demo");

//         //   map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//
//                @Override
//                public void onInfoWindowClick(Marker marker) {
//                    marker.remove();
//                }
//            });


                    TextView locationTv = (TextView) findViewById(R.id.latlongLocation);

                    locationTv.setText("Latitude: " + latLng.latitude+ " , Longitude: " + latLng.longitude);



                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        supportMapFragment.getMapAsync(this);


        addDMS = findViewById(R.id.Add_DMS);

        addDMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vv = searchView.getQuery().toString();
                addDisasterHelp(vv,latLng.latitude,latLng.longitude,type);

            }
        });


    }
    //
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                Place place = Autocomplete.getPlaceFromIntent(data);
//                Log.i(null, "Place: " + place.getName() + ", " + place.getId());
//            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
//                // TODO: Handle the error.
//                Status status = Autocomplete.getStatusFromIntent(data);
//                Log.i(null, status.getStatusMessage());
//            } else if (resultCode == RESULT_CANCELED) {
//                // The user canceled the operation.
//            }
//        }
//
//    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map= googleMap;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }



//    public void onLocationChanged(Location location) {
//        TextView locationTv = (TextView) findViewById(R.id.latlongLocation);
//        double latitude = location.getLatitude();
//        double longitude = location.getLongitude();
//        LatLng latLng = new LatLng(latitude, longitude);
//        googleMap.addMarker(new MarkerOptions().position(latLng));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
//        locationTv.setText("Latitude:" + latitude + ", Longitude:" + longitude);
//    }



    public void addDisasterHelp(final String name, final double lat, final double lng, final String type){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, addRec,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> post = new HashMap<>();
                post.put("location",name);
                post.put("lat",String.valueOf(lat));
                post.put("long",String.valueOf(lng));
                post.put("type",type);
                return post;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
