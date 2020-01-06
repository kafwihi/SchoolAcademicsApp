
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

public class RegisterActivity extends Activity{
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFirstName;

    private EditText inputLastName;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private String fname,lname,email,password,response;
    private RequestQueue queue;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputFirstName = (EditText) findViewById(R.id.fname);
        inputLastName = (EditText) findViewById(R.id.lname);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoinScreen);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        //db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegisterActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                 fname = inputFirstName.getText().toString().trim();
                lname = inputLastName.getText().toString().trim();

                email = inputEmail.getText().toString().trim();
                 password = inputPassword.getText().toString().trim();

                if (!fname.isEmpty() && !lname.isEmpty() &&!email.isEmpty() && !password.isEmpty()) {
                    pDialog.setMessage("Registering ...");
                    showDialog();
                    //String url = "http://192.168.0.21/android_login_api/register.php";
                   String url = "http://bddb60ae.ngrok.io/users";
                   //http://9602a31b.ngrok.io/users
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

                                        //JSONObject json = (JSONObject) new JSONTokener(response).nextValue();
                                         String error = json.getString("id");
                                        Log.d(TAG,"Response error3 "+error);

                                          if(error!="null"){
                                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now! "+error, Toast.LENGTH_LONG).show();

                                        // Launch login activity
                                        //RegisterActivity.this.finish();
                                        Intent intent = new Intent(
                                                RegisterActivity.this,
                                                LoginActivity.class);
                                        startActivity(intent);
                                        finish();
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

                                    Log.e(TAG, "Registration Error: " + error.getMessage());
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
                            params.put("firstname", fname);
                            params.put("lastname", lname);
                            params.put("previlage", "admin");
                            params.put("email", email);
                            params.put("password", password);
                            return params;
                        }
                    };

                    queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(postRequest);

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), LoginActivity.class);
                view.getContext().startActivity(i);
                finish();
            }
        });

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
