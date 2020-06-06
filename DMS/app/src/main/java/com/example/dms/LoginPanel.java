package com.example.dms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.regex.Pattern;
import java.util.zip.Inflater;

import static com.example.dms.R.layout.activity_login_panel;

public class LoginPanel extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    TextView textView, togglePass;
    EditText email, pass;
    Button login;
    int TIMER = 3000;
    final String URL_LOGIN = "http://192.168.0.104/WebApi/login.php";
    ImageView imageView;
    Dialog dialog;
    ImageView CloseDialog;
    TextView loginFailed, invalid_user;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_login_panel);


        togglePass = findViewById(R.id.toggle_password);
        togglePass.setVisibility(View.GONE);
        textView = findViewById(R.id.txtsign);
        email = findViewById(R.id.txtemail);
        String email_user = email.getText().toString();
        imageView = findViewById(R.id.navigatetosignup);
        pass = findViewById(R.id.txtpass);
        login = findViewById(R.id.btnlogin);


        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        String validateUserRole = sharedPreferences.getString("userRole", "");
        String validateUser = sharedPreferences.getString("userName", "");
        if (validateUserRole.equals("1")) {
            Intent i2 = new Intent(LoginPanel.this, AdminDashboard.class);
            startActivity(i2);

        } else if (validateUserRole.equals("2") && !validateUser.equals("")) {
            Intent i2 = new Intent(LoginPanel.this, Userhelp.class);
            startActivity(i2);

        } else {

        }


        togglePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (togglePass.getText() == "SHOW") {
                    togglePass.setText("HIDE");
                    pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pass.setSelection(pass.length());
                } else {
                    togglePass.setText("SHOW");
                    pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pass.setSelection(pass.length());
                }
            }
        });
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (pass.getText().length() > 0) {
                    togglePass.setVisibility(View.VISIBLE);

                } else {
                    togglePass.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (email.getText().toString().equals("") ) {
                    email.setError("Please Enter Username");

                }
                else if(pass.getText().toString().equals(""))
                {
                    pass.setError("Please Enter Password");
                }
                    else {

                    LoginUser(email.getText().toString(), pass.getText().toString());

                }

            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Intent ii = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(ii);
            }
        });


    }

    public void LoginUser(final String email, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("1")) {
                            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userName", email);
                            editor.putString("userRole", response);
                            editor.commit();
                            Intent i2 = new Intent(LoginPanel.this, AdminDashboard.class);
                            startActivity(i2);


//                            Toast.makeText(getApplicationContext(),"Login Successfully",Toast.LENGTH_LONG).show();
                        } else if (response.trim().equals("2")) {
//                                validateUsername();
//                                confirmInput();
//                                validatePassword();
                            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userName", email);
                            editor.putString("userRole", response);
                            editor.commit();
                            Intent i2 = new Intent(LoginPanel.this, Userhelp.class);
                            startActivity(i2);


                        } else {
                            // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                           // ShowMessageDialog();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //dialog.dismiss();

                                }

                            }, TIMER);


                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                //ShowMessageDialog();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        dialog.dismiss();
//
//                    }
//
//                }, TIMER);


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("email", email);
                map.put("password", password);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void AdminLogin() {
        if (email.getText().toString().equals("admin") & pass.getText().toString().equals("admin")) {

            final ProgressDialog progressDialog = new ProgressDialog(LoginPanel.this);
            progressDialog.setMessage("Logging In");
            progressDialog.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    Intent i = new Intent(LoginPanel.this, AdminDashboard.class);
                    startActivity(i);

                }

            }, TIMER);
        } else {
            Toast.makeText(this, "Login Failed..!", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean validateUsername() {
        String usernameInput = email.getText().toString().trim();

        if (usernameInput.isEmpty()) {
            email.setError("Field can't be empty");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = pass.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            pass.setError("Field can't be empty");
            return false;
        }
        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            pass.setError("Password too weak");
            return false;
        }
           else {
            pass.setError(null);
            return true;
        }
    }



    public void confirmInput() {
        if (!validateUsername() | !validatePassword()) {
            return;
        }

        String input =email.getText().toString();
        input += "\n";

        Toast.makeText(this, input , Toast.LENGTH_SHORT).show();
    }



//    public  void ShowMessageDialog()
//    {
//        View view=getLayoutInflater().inflate(R.layout.login_failed,null);
//
//        CloseDialog =view.findViewById(R.id.close);
//        loginFailed=view.findViewById(R.id.Login_failed);
//        //dialog.setContentView(R.layout.login_failed);
//
//        invalid_user =view.findViewById(R.id.Invalied_user);
//        invalid_user.setText("Invalid Username or Password");
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

}
