package com.example.dms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AwarenessSMS extends AppCompatActivity {
    EditText msg;
    ImageView send_awareness_msg;
    Dialog dialog;
    String getUser_contact;
    String[] totalNumbers;
    ArrayList<String> contacts;

    String SMS_PERMISSION = Manifest.permission.SEND_SMS;
    String PHONE_STATE_PERMISSION = Manifest.permission.READ_PHONE_STATE;
    final int   REQUEST_CODE=111;
    boolean isPermissionGranted= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awareness_sms);
   msg = findViewById(R.id.input_awareness_msg);
   send_awareness_msg = findViewById(R.id.send_awareness);


       fetchContacts();

    contacts = new ArrayList<String>();
    send_awareness_msg.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        checkPermission();

        String aware_msg=msg.getText().toString();
        String msg=aware_msg.trim().toString();
        Toast.makeText(AwarenessSMS.this, aware_msg.toString(), Toast.LENGTH_SHORT).show();

        String message = send_awareness_msg.toString();

        SmsManager smsManager = SmsManager.getDefault();
           // int numbers=totalNumber.length;

        for(int i=0; i < contacts.size(); i++) {
            if (contacts.get(i).length()>=11)
            {
                smsManager.sendTextMessage( contacts.get(i),null, aware_msg, null, null);
                Toast.makeText(getApplicationContext(),"SMS Sent To :"+ String.valueOf(contacts.get(i)),Toast.LENGTH_LONG).show();

            }





        }


    }
});

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height= dm.heightPixels;
        getWindow().setLayout((int)(width*.9),(int)(height*.8));
        ImageView CloseDialog =findViewById(R.id.close);
        //msg.setFilters(new InputFilter[]{new });
        CloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



//   send.setOnClickListener(new View.OnClickListener() {
//       @Override
//       public void onClick(View v) {
//
//       }
//   });
    }

    public void fetchContacts()
    {


        String GET_CONTACTS = "http://192.168.0.104/WebApi/FetchUserContacts.php";
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

                               // getUser_contact = jsonObject.getString("contact_no");
                                //totalNumbers = new String[]{getUser_contact};
                                //String totalnum=getUser_contact;
//                       totalNumbers[i] = String.valueOf(new String[]{jsonObject.getString("contact_no")});
                contacts.add(i,jsonObject.getString("contact_no"));
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

}
