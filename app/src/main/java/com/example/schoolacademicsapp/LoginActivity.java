package com.example.schoolacademicsapp;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


import com.example.schoolacademicsapp.R;
import com.example.schoolacademicsapp.helper.SQLiteHandler;
import com.example.schoolacademicsapp.helper.SessionManager;

public class LoginActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    private String name,email,password,response;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //setSupportActionBar(toolbar);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        //db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
             /* Intent intent = new Intent(getApplicationContext(),
                        DashboardActivity.class);
                startActivity(intent);
                finish();*/

                email = inputEmail.getText().toString().trim();
                password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    pDialog.setMessage("Loging In ...");
                    showDialog();
                    //String url = "http://192.198.0.21/android_login_api/login.php";
                    String url = "http://bddb60ae.ngrok.io/authentication";
                        StringRequest postRequest = new StringRequest(Method.POST, url,

                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d(TAG,"Response 1 "+response.toString());
                                        hideDialog();
                                        try{
                                            JSONObject json =  new JSONObject(response);
                                            Log.d(TAG,"Response 2 "+json);

                                            JSONObject user = json.getJSONObject("user");
                                            String error = user.getString("id");
                                            //JSONObject access = json.getJSONObject("accessToken");

                                            String token =  json.getString("accessToken");
                                            Log.d(TAG,"Response id "+error+" token "+token);

                                            if(error!="null"){
                                                // Create login session
                                                session.setLogin(true);
                                                String name = user.getString("firstname") +" "+user.getString("lastname");
                                                String email = user.getString("email");

                                                session.setParams(name,email,token);

                                                Toast.makeText(getApplicationContext(), "Welcome! "+name, Toast.LENGTH_LONG).show();

                                                // Launch login activity
                                                try{
                                                    Thread.sleep(Toast.LENGTH_LONG);
                                                    LoginActivity.this.finish();
                                                    Intent intent = new Intent(
                                                            LoginActivity.this,
                                                            MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                catch (InterruptedException ex){
                                                    ex.printStackTrace();
                                                }

                                            }else
                                            {
                                                // Error occurred in registration. Get the error
                                                // message
                                                // String errorMsg = jObj.getString("error_msg");
                                                Toast.makeText(getApplicationContext(),
                                                        "error", Toast.LENGTH_LONG).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Log.e(TAG, "Login Error: " + error.getMessage());
                                    Toast.makeText(getApplicationContext(),
                                            "Sorry, Access Denied!!!", Toast.LENGTH_LONG).show();
                                    hideDialog();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("strategy", "local");
                            params.put("email", email);
                            params.put("password", password);
                            return params;
                        }
                    };

                    queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(postRequest);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }

            }
            //login end
        });
        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                // Intent i = new Intent(getBaseContext(), Register.class);
                Intent i = new Intent(view.getContext(), RegisterActivity.class);
                view.getContext().startActivity(i);
                //startActivity(i);
                finish();

            }
        });

    }
    /**
     * Function to login user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */

    /*private void checkLogin(final String email,
                            final String password)
    {
        String url = "http://45101233.ngrok.io/android_login_api/login.php";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG,"Response "+response.toString());
                        hideDialog();
                        try{
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            if(!error){
                                //user successfull stored in mysql
                                Toast.makeText(getApplicationContext(), "Welcome Home!", Toast.LENGTH_LONG).show();

                                // Launch login activity
                                try{
                                    Thread.sleep(Toast.LENGTH_LONG);
                                    LoginActivity.this.finish();
                                    Intent intent = new Intent(
                                            LoginActivity.this,
                                            MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                catch (InterruptedException ex){
                                    ex.printStackTrace();
                                }


                            }else
                            {
                                // Error occurred in registration. Get the error
                                // message
                                String errorMsg = jObj.getString("error_msg");
                                Toast.makeText(getApplicationContext(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e(TAG, "Login Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(postRequest);
    }*/

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}