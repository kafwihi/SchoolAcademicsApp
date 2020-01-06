package com.example.schoolacademicsapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

/*
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
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

import com.example.charateristics.Activity.LoginActivity;
*/
public class register_student extends Activity{
    //private static final String TAG = LoginActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    //private SessionManager session;
    //private SQLiteHandler db;
    private String name,email,password,response;
    //private RequestQueue queue;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register_student);

        inputFullName = (EditText) findViewById(R.id.fname);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoinScreen);

    }

}
