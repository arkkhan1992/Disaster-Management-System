package com.example.dms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    Button btnreg;
    TextView textView,togglePass;
    EditText email,password,contact,father_name,mother_name,father_cell_no,mother_cell_no,other_name,other_number;
    final String URL_INSERT="http://192.168.0.104/WebApi/test1.php";
    final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        togglePass=findViewById(R.id.toggle_password);
        togglePass.setVisibility(View.GONE);

        textView=findViewById(R.id.txtlogin);
        email = findViewById(R.id.email_edtxt);
        password = findViewById(R.id.pass_edtxt);
        contact = findViewById(R.id.contact_number);
        father_name = findViewById(R.id.father_edtxt);
        mother_name = findViewById(R.id.mother_edtxt);
        father_cell_no= findViewById(R.id.father_no);
        mother_cell_no= findViewById(R.id.mother_no);
        other_name = findViewById(R.id.other_name);
        other_number = findViewById(R.id.other_no);
        btnreg = findViewById(R.id.reg);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Intent i = new Intent(MainActivity.this,LoginPanel.class);
                startActivity(i);
            }
        });


        togglePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (togglePass.getText()=="SHOW")
                {
                    togglePass.setText("HIDE");
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    password.setSelection(password.length());
                }
                else
                {
                    togglePass.setText("SHOW");
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setSelection(password.length());
                }
            }
        });
    password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (password.getText().length()>0)
                {
                    togglePass.setVisibility(View.VISIBLE);

                }
                else {togglePass.setVisibility(View.GONE);}
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.getText().toString().equals("") ) {
                    email.setError("Please Enter Username");

                }
                else if(password.getText().toString().equals(""))
                {
                    password.setError("Please Enter Password");
                }
                else if(password.getText().toString().length()<=6)
                {
                    password.setError("Please Enter Atleast Any 7 Characters");
                }

                else if (contact.getText().toString().length()>11  || contact.getText().toString().length()<11)
                {                    contact.setError("Please Enter Valid Contact Number");
                }
                else if (father_cell_no.getText().toString().length()>11  || father_cell_no.getText().toString().length()<11)
                {                    father_cell_no.setError("Please Enter Valid Contact Number");
                }
                else if (mother_cell_no.getText().toString().length()>11  || mother_cell_no.getText().toString().length()<11)
                {                    mother_cell_no.setError("Please Enter Valid Contact Number");
                }

                else if (other_number.getText().toString().length()>11  || other_number.getText().toString().length()<11)
                {                    other_number.setError("Please Enter Valid Contact Number");
                }

//                else if (!password.getText().toString().equals(PASSWORD_PATTERN) || password.getText().toString() != PASSWORD_PATTERN )
//                {
//                password.setError("Be between 8 and 40 characters long\n" +
//                        "Contain at least one digit.\n" +
//                        "Contain at least one lower case character.\n" +
//                        "Contain at least one upper case character.\n" +
//                        "Contain at least on special character from [ @ # $ % ! . ].");
//                }




                else {

                    RegisterUser(email.getText().toString(),password.getText().toString(),contact.getText().toString(),father_name.getText().toString(),
                            father_cell_no.getText().toString(),mother_name.getText().toString()
                            ,mother_cell_no.getText().toString(),other_name.getText().toString()
                            ,other_number.getText().toString());

                }

            }
        });

    }

    public void RegisterUser(final String email, final String password, final String userContact, final String fatherName, final String fatherNumber, final String motherName, final String motherNumber, final String otherName, final String otherNo){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("done"))
                        {
                            Toast.makeText(getApplicationContext(),"User Registered",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("email",email);
                map.put("password",password);
                map.put("user_no",userContact);
                map.put("Father_name",fatherName);
                map.put("Father_no",fatherNumber);
                map.put("mother_name",motherName);
                map.put("mother_no",motherNumber);
                map.put("other_name",otherName);
                map.put("other_no",otherNo);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    public void ValidationContact()
    {
        String regexStr = "^[0-9]$";

        String number=contact.getText().toString();
        String father_cell=father_cell_no.getText().toString();
        String mother_cell=mother_cell_no.getText().toString();
        String other_cell=other_number.getText().toString();


        if(contact.getText().toString().length()<10 || number.length()>13 || number.matches(regexStr)==false  ) {
            //Toast.makeText(getApplicationContext(),"Please enter "+"\n"+" valid phone number",Toast.LENGTH_SHORT).show();
            // am_checked=0;
            contact.setError("Please enter "+"\n"+" valid phone number");
        }
        if(father_cell_no.getText().toString().length()<10 || number.length()>13 || number.matches(regexStr)==false  ) {
            //Toast.makeText(getApplicationContext(),"Please enter "+"\n"+" valid phone number",Toast.LENGTH_SHORT).show();
            // am_checked=0;
            father_cell_no.setError("Please enter "+"\n"+" valid phone number");

        }
        if(mother_cell_no.getText().toString().length()<10 || number.length()>13 || number.matches(regexStr)==false  ) {
            //Toast.makeText(getApplicationContext(),"Please enter "+"\n"+" valid phone number",Toast.LENGTH_SHORT).show();
            // am_checked=0;
            mother_cell_no.setError("Please enter "+"\n"+" valid phone number");

        }
        if(other_number.getText().toString().length()<10 || number.length()>13 || number.matches(regexStr)==false  ) {
            //Toast.makeText(getApplicationContext(),"Please enter "+"\n"+" valid phone number",Toast.LENGTH_SHORT).show();
            other_number.setError("Please enter "+"\n"+" valid phone number");

            // am_checked=0;
        }

    }

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }


}
