package com.example.dms;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdminDashboard extends AppCompatActivity {

    String SMS_PERMISSION = Manifest.permission.SEND_SMS;
    String PHONE_STATE_PERMISSION = Manifest.permission.READ_PHONE_STATE;
    final int   REQUEST_CODE=111;
    ImageView adddisaster,weather_forecast,send_sms,send_awarness_sms;
    String user_contact;
    String[] totalNumber;
    boolean isPermissionGranted= false;
    boolean isPermissionGrantedLoc = false;
    String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    int LOCATION_REQUEST_CODE = 123;
    Location location;
    LatLng latLng;
    ImageView log_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
    send_awarness_sms=findViewById(R.id.awarenesssms);
    adddisaster = findViewById(R.id.addDisaster);
       // SharedPreferences  sharedPreferences= getSharedPreferences("userInfo", Context.MODE_PRIVATE);

log_btn=findViewById(R.id.logout_button);
        log_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                SharedPreferences  sharedPreferences= getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                finish();

                Intent i = new Intent(getApplicationContext(),LoginPanel.class);
                startActivity(i);

            }
        });

    send_awarness_sms.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            if (isPermissionGrantedLoc) {
//                if (location!=null) {
//                    Send_Sms(latLng);
//                }
//                else
//                {
//                    Toast.makeText(getApplicationContext(),"Fetching User Contacts",Toast.LENGTH_LONG).show();
//                }
//            }


            Intent i=new Intent(getApplicationContext(),AwarenessSMS.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

        }
    });



        adddisaster.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(AdminDashboard.this,DisasterAdd.class);
            startActivity(i);
        }
    });
    }




    public void Send_Sms(LatLng lng)
    {
        String lat = String.valueOf(lng.latitude);
        String lngt = String.valueOf(lng.longitude);
        String loc = "Hello There I Am In Dangerous Zone Kindly Help Me Out. Location : https://www.google.com/maps/place/"+lat+","+lngt;
        String msg = "";//+loc;
        String message = msg;

        if (isPermissionGranted)
        {
            for (int i=0;i<totalNumber.length;i++)
            {
                SmsManager smsManager = SmsManager.getDefault();

                smsManager.sendTextMessage(totalNumber[i],null,loc,null,null);


                Toast.makeText(getApplicationContext(),"SMS Sent To :"+ totalNumber[i],Toast.LENGTH_LONG).show();
            }

        }
        else
        {
            checkPermission();
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
//    public void fetchContacts()
//    {
//        String GET_CONTACTS = "https://disaster-ms.000webhostapp.com/WebApi/FetchUserContacts.php";
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_CONTACTS,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject object = new JSONObject(response);
//                            JSONArray array = object.getJSONArray("val");
//                            for (int i=0;i<array.length();i++)
//                            {
//                                JSONObject  jsonObject = array.getJSONObject(i);
//
//                                user_contact = jsonObject.getString("other_number");
//                                totalNumber = new String[]{user_contact};
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
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
