package com.example.dms;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Userhelp extends AppCompatActivity {

    String SMS_PERMISSION = Manifest.permission.SEND_SMS;
    String PHONE_STATE_PERMISSION = Manifest.permission.READ_PHONE_STATE;
    final int   REQUEST_CODE=111;
    ImageView btnhelp,btndisadter,btnlocation;
    ImageView logOutUser;
    Dialog dialog;
    int  TIMER= 3000;
    ImageView CloseDialog;
    TextView message,success,txt_username;
    boolean isPermissionGranted= false;
    String father_number;
    String mother_number;
    String other_number;
    String getUserName;
String[] totalNumber;

    Location location;
    LatLng latLng;
    private FusedLocationProviderClient mFusedLocationClient;

    boolean isPermissionGrantedLoc = false;
    String FINE_LOCATION =Manifest.permission.ACCESS_FINE_LOCATION;
    String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    int LOCATION_REQUEST_CODE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhelp);
        setContentView(R.layout.activity_userhelp);
        btnhelp=findViewById(R.id.btnhelp);
        btndisadter=findViewById(R.id.btndistter);
        txt_username=findViewById(R.id.txt_username);

        SharedPreferences  sharedPreferences= getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        txt_username.setText(sharedPreferences.getString("userName",""));


        logOutUser = findViewById(R.id.logout_button);


        logOutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences  sharedPreferences= getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                sharedPreferences.edit().remove("userName").commit();
                Intent i = new Intent(Userhelp.this,LoginPanel.class);
                startActivity(i);

            }
        });

//        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        getUserName = sharedPreferences.getString("userName","");

        btndisadter.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(getApplicationContext(),MapsActivity.class);
        startActivity(i);
    }
});

        fetchContacts();



        btnhelp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkCurrentLocation();
                getCurrentLocation();
                if (isPermissionGrantedLoc) {
                    if (location!=null) {
                        Send_Sms(latLng);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Wait While We Enable SMS Service For You",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    checkCurrentLocation();
                }
                }

        });

    }
    public void Send_Sms(LatLng lng) {
        String lat = String.valueOf(lng.latitude);
        String lngt = String.valueOf(lng.longitude);
        String loc = "Hello There I Am In Dangerous Zone Kindly Help Me Out. Location : https://www.google.com/maps/place/" + lat + "," + lngt;
        String msg = "";//+loc;
        String message = msg;

        if (isPermissionGranted) {

            if (totalNumber.length > 0) {

                for (int i = 0; i < totalNumber.length; i++) {
                    SmsManager smsManager = SmsManager.getDefault();

                    smsManager.sendTextMessage(totalNumber[i], null, loc, null, null);


                    Toast.makeText(getApplicationContext(), "SMS Sent To :" + totalNumber[i], Toast.LENGTH_LONG).show();
//                ShowMessageDialog();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        dialog.dismiss();
//
//                    }
//
//                },TIMER);
                }



        } else {
            Toast.makeText(getApplicationContext(), "Error While Getting Contacts, Try Again Later", Toast.LENGTH_LONG).show();
        }
    }
        else
        {
        checkPermission();
        }
    }

    public void getCurrentLocation(){
        if (isPermissionGrantedLoc)
        {
            mFusedLocationClient = new FusedLocationProviderClient(this);

            final Task task = mFusedLocationClient.getLastLocation();

            task.addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    if (task.isComplete())
                    {
                        location = (Location) task.getResult();

                        if (location !=null) {
                            latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        }
                    }
                }
            });
        }
        else
        {
            checkCurrentLocation();
        }
    }
    public void checkCurrentLocation(){
        String[]permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.SEND_SMS};

        if (ContextCompat.checkSelfPermission(this, FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED)

        {

            isPermissionGrantedLoc = true;
        //    mMap.setMyLocationEnabled(true);

            checkPermission();

        }
        else
        {
            ActivityCompat.requestPermissions(this,permissions,LOCATION_REQUEST_CODE);
        }
    }


    public void checkPermission(){


        String[] permissions = {Manifest.permission.SEND_SMS,Manifest.permission.READ_PHONE_STATE};

        if (ActivityCompat.checkSelfPermission(this,SMS_PERMISSION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,PHONE_STATE_PERMISSION)==PackageManager.PERMISSION_GRANTED)
        {
            isPermissionGranted = true;
        }

        else
        {
            ActivityCompat.requestPermissions(this,permissions,REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length>0 && (grantResults[0]) == PackageManager.PERMISSION_GRANTED)
        {
            isPermissionGranted = true;

        }
        else
        {
            isPermissionGranted =false;
        }
        return;

    }


    public void fetchContacts()
    {


        String GET_CONTACTS = "http://192.168.0.104/WebApi/fetchContacts.php?user="+getUserName;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_CONTACTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("val");
                            for (int i=0;i<array.length();i++)
                            {
                                JSONObject  jsonObject = array.getJSONObject(i);
                                father_number = jsonObject.getString("father_num");
                                mother_number = jsonObject.getString("mother_num");
                                other_number = jsonObject.getString("other_number");
                            totalNumber = new String[]{father_number, mother_number,other_number};

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


//
//    public  void ShowMessageDialog()
//    {
//
//        dialog.setContentView(R.layout.message_popup);
//        CloseDialog =dialog.findViewById(R.id.close);
//        message =dialog.findViewById(R.id.message_sent);
//        success=dialog.findViewById(R.id.succesfuly);
//
//        CloseDialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.show();
//
//
//    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        setResult(RESULT_OK, new Intent().putExtra("EXIT", true));
                        moveTaskToBack(true);
                        finish();
                    }

                }).create().show();


    }
}

