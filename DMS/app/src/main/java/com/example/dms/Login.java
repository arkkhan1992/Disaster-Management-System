package com.example.dms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    TextView textView;
    EditText email,pass;
    String sendEm;
    Button login;
    final String URL_LOGIN = "http://192.168.0.104/WebApi/login.php";
    //ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textView=findViewById(R.id.txtsign);
        email=findViewById(R.id.txtemail);
        //imageView=findViewById(R.id.navigatetosignup);
        pass=findViewById(R.id.txtpass);
        login=findViewById(R.id.btnlogin);

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(android.view.View view) {
//                Intent i = new Intent(Login.this,MainActivity.class);
//                startActivity(i);
//            }
//        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendEm = email.getText().toString();
               LoginUser(email.getText().toString(),pass.getText().toString());

            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
             startActivity(i);
            }
        });

    }


    public void LoginUser(final String email, final String password){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("done"))
                        {
                            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userName",email.toString());
                            editor.commit();

                            Intent i = new Intent(Login.this,Userhelp.class);
                            i.putExtra("un",sendEm);
                            startActivity(i);
                            Toast.makeText(getApplicationContext(),"Login Successfully",Toast.LENGTH_LONG).show();
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
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


}
