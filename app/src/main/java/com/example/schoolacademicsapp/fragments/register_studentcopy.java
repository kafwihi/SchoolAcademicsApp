package com.example.schoolacademicsapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import java.util.Calendar;
import android.widget.DatePicker;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.app.DatePickerDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolacademicsapp.LoginActivity;
import com.example.schoolacademicsapp.MainActivity;
import com.example.schoolacademicsapp.R;
import com.example.schoolacademicsapp.RegisterActivity;
import com.example.schoolacademicsapp.helper.SQLiteHandler;
import com.example.schoolacademicsapp.helper.SessionManager;

import com.example.schoolacademicsapp.helper.Alerts;
import org.json.JSONException;
import org.json.JSONObject;


public class register_studentcopy extends Fragment implements OnItemSelectedListener{
    private static final String TAG = register_studentcopy.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private Button btnRegister;
    private EditText inputRegno;
    private EditText inputFirstName;
    private EditText inputMidName;
    private EditText inputLastName;
    private EditText inputPhone;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private String regno,fname,mname,lname,level,stream,dob,phone,response;
    private DatePicker picker;
    private DatePickerDialog datePickerDialog;
    private EditText inputDate;

    Alerts alerts ;
    private RequestQueue queue;
    private String current_date;
    private Spinner spin_stream,spin_level;
    private String ACCESS_TOKEN;
    public register_studentcopy() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
     View root = inflater.inflate(R.layout.fragment_register_student, container, false);

        inputRegno=(EditText) root.findViewById(R.id.regno);
        alerts = new Alerts(getActivity());
        inputFirstName=(EditText) root.findViewById(R.id.fname);
        inputMidName=(EditText) root.findViewById(R.id.mname);
        inputLastName=(EditText) root.findViewById(R.id.lname);
        inputPhone=(EditText) root.findViewById(R.id.phone);
        //picker=(DatePicker) root.findViewById(R.id.dob);
        inputDate = (EditText) root.findViewById(R.id.dob);
        spin_stream = (Spinner) root.findViewById(R.id.stream);
        //spin_stream.setOnItemSelectedListener(this);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        spin_level = (Spinner) root.findViewById(R.id.level);
        //spin_level.setOnItemSelectedListener(this);

        // List<String> streams = new ArrayList<String>();
        ArrayList<MyStreams> streams = new ArrayList<>();
        ArrayList<MyLevels> levels = new ArrayList<>();

        streams.add(new MyStreams("NORTH"));
        streams.add(new MyStreams("EAST"));
        streams.add(new MyStreams("SOUTH"));
        streams.add(new MyStreams("WEST"));

        levels.add(new MyLevels("ONE"));
        levels.add(new MyLevels("TWO"));
        levels.add(new MyLevels("THREE"));
        levels.add(new MyLevels("FOUR"));
        ArrayAdapter<MyStreams> adapter_stream =
                new ArrayAdapter<MyStreams>(getActivity(),  android.R.layout.simple_spinner_dropdown_item, streams);
        adapter_stream.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spin_stream.setAdapter(adapter_stream);

        ArrayAdapter<MyLevels> adapter_level =
                new ArrayAdapter<MyLevels>(getActivity(),  android.R.layout.simple_spinner_dropdown_item, levels);
        adapter_level.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spin_level.setAdapter(adapter_level);
        // session manager
        session = new SessionManager(getActivity());

        if (!session.isLoggedIn()) {
            logoutUser();
        }


        ACCESS_TOKEN = session.getUsertoken();
     //   txtEmail.setText(session.getUseremail());
        //Toast.makeText(getActivity(), "Your Token ! "+ACCESS_TOKEN, Toast.LENGTH_LONG).show();



        final Button but = root.findViewById(R.id.btnRegister);
        inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
int month =monthOfYear+1;
                                 current_date =""+dayOfMonth+"-"+month+"-"+year;
                                inputDate.setText(current_date);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                //private String regno,fname,mname,lname,level,stream,dob,phone,response;
                regno = inputRegno.getText().toString().trim();
                fname = inputFirstName.getText().toString().trim();
                mname = inputMidName.getText().toString().trim();
                lname = inputLastName.getText().toString().trim();

                dob = inputDate.getText().toString().trim();
                phone = inputPhone.getText().toString().trim();
                level = spin_level.getSelectedItem().toString();
                stream = spin_stream.getSelectedItem().toString();

                //picker.setSpinnersShown(true);

                if (!fname.isEmpty() && !lname.isEmpty() &&!regno.isEmpty()) {
                    pDialog.setMessage("Registering ...");
                    showDialog();
                    //String url = "http://192.168.0.21/android_login_api/register.php";
                    String url = "http://bddb60ae.ngrok.io/admission";
                    //http://9602a31b.ngrok.io/users
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,

                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {
                                    //Log.d(TAG,"Response 1 "+response.toString());
                                    hideDialog();
                                    try{
                                        JSONObject json =  new JSONObject(response);
                                        //Log.d(TAG,"Response 2 "+json);

                                        //JSONObject json = (JSONObject) new JSONTokener(response).nextValue();
                                        String error = json.getString("regno");

                                        if(error!="null"){
                                           // Toast.makeText(getActivity(), "Successfully Registered!", Toast.LENGTH_LONG).show();
                                            String message = "Successfully Registered!";
                                            alerts.showDialog(message);

                                            emptyField();
                                            return;
                                        }//else if(error=="null")
                                        {
                                              //Toast.makeText(getActivity(),"Record Exists!", Toast.LENGTH_LONG).show();
                                            String message = "Record Exists!";
                                            alerts.showDialog(message);

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
                                    Toast.makeText(getActivity(),
                                            error.getMessage(), Toast.LENGTH_LONG).show();
                                    hideDialog();
                                }
                            }
                    ) {

                        //headers and access token
                       /* @Override
                        public String getBodyContentType(){
                            return "application/json; charset=UTF-8";
                        }*/
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError{
                            Map<String, String> header = new HashMap<String, String>();
                            header.put("Accept", "application/json");
                            //header.put("Content-Type", "application/json");

                            //header.put("token", ACCESS_TOKEN);
                            header.put("Authorization","Bearer: "+ ACCESS_TOKEN);
                            return header;
                        }
                        //raw data passed here
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String>  params = new HashMap<String, String>();
                            //private String regno,fname,mname,lname,level,stream,dob,phone,response;

                            params.put("regno", regno);
                            params.put("firstname", fname);
                            params.put("midname", mname);
                            params.put("lastname", lname);
                            params.put("dob", dob);
                            params.put("contact", phone);
                            params.put("level", level);
                            params.put("stream", stream);
                            params.put("progress", "running");
                            return params;
                        }
                    };

                    queue = Volley.newRequestQueue(getActivity());
                    queue.add(postRequest);

                } else {
                    Toast.makeText(getActivity(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        return root;

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       level = parent.getItemAtPosition(position).toString();
       stream = parent.getItemAtPosition(position).toString();

        Toast.makeText(parent.getContext(), "Selected: level " + level+" stream "+stream, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    private void emptyField() {
        inputRegno.setText("");
        inputFirstName.setText("");
        inputMidName.setText("");
        inputLastName.setText("");
        inputPhone.setText("");

    }

    private void logoutUser() {
        session.setLogin(false);
        session.Logout();
        // Launching the login activity
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
//        finish();
    }
}


 class MyStreams {
    private String stream;

    public MyStreams() {
    }

    public MyStreams(String contact_name) {
        this.stream = contact_name;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String contact_name) {
        this.stream = contact_name;
    }
    @Override
    public String toString() {
        return stream;
    }
}

class MyLevels {
    private String stream;

    public MyLevels() {
    }

    public MyLevels(String contact_name) {
        this.stream = contact_name;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String contact_name) {
        this.stream = contact_name;
    }
    @Override
    public String toString() {
        return stream;
    }



}

