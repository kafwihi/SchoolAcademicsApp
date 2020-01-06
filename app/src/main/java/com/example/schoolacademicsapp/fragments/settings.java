
package com.example.schoolacademicsapp.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.schoolacademicsapp.R;
/*

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.example.charateristics.R;
import com.example.charateristics.App.AppConfig;
import com.example.charateristics.App.AppController;
import com.example.charateristics.helper.SQLiteHandler;
import com.example.charateristics.helper.SessionManager;
import com.example.charateristics.MainActivity;

//import com.example.charateristics.Activity.LoginActivity;
*/
public class settings extends AppCompatActivity{
    //private static final String TAG = LoginActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
   /* private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private String name,email,password,response;
    private RequestQueue queue;*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);

        inputFullName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoinScreen);
    }
/*

    private void registerUser(final String email,
                              final String password)
    {
        pDialog.setMessage("Registering ...");
        showDialog();
        String url = "http://192.168.0.21/android_login_api/register.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,

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
                                Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                                // Launch login activity
                                try{
                                    Thread.sleep(Toast.LENGTH_LONG);
                                    Register.this.finish();
                                    Intent intent = new Intent(
                                            Register.this,
                                            LoginActivity.class);
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
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(postRequest);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    */
}
