package com.project.untag.survey1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.project.untag.survey1.API.Global;
import com.project.untag.survey1.API.MultipartJSONRequest;
import com.project.untag.survey1.API.MyRequest;
import com.project.untag.survey1.Model.JenisKabel;
import com.project.untag.survey1.Model.JenisTiang;
import com.project.untag.survey1.Model.Tiang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FormTiang extends AppCompatActivity {

    Tiang mCurrentTiang;
    GPSTracker gpsTracker;
    String stringLatitude, stringLongitude;
    EditText edLat,edLng;
    Spinner spJenisTiang,spJenisKabel;
    private Gson gson;

    private ArrayList<String> arrTiang;
    private HashMap<String, String> hashidTiang;
    ArrayAdapter<String> spinnerArrayAdapter;

    private ArrayList<String> arrJenisKabel;
    private HashMap<String, String> hashidJenisKabel;
    ArrayAdapter<String> spinnerArrayAdapterJenisKabel;

    private MultipartJSONRequest request;

    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_tiang);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(getApplication(), "GPS Gk AKtip", Toast.LENGTH_SHORT).show();
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Log.d("Ikko Lat", "onSuccess: "+location.getLatitude());
                            Log.d("Ikko Lng", "onSuccess: "+location.getLongitude());
                            stringLatitude = String.valueOf(location.getLatitude());
                            stringLongitude = String.valueOf(location.getLongitude());
                            edLat.setText(stringLatitude);
                            edLng.setText(stringLongitude);
                        }
                    }
                });

        fetchJenisTiang();
        fetchJenisKabel();

        gson = new GsonBuilder().create();
        arrTiang = new ArrayList<>();
        hashidTiang = new HashMap<>();

        arrJenisKabel = new ArrayList<>();
        hashidJenisKabel = new HashMap<>();

        edLat = (EditText) findViewById(R.id.edLatitude);
        edLng = (EditText) findViewById(R.id.edLongitude);
        spJenisKabel = (Spinner) findViewById(R.id.spJenisKabel);
        spJenisTiang = (Spinner) findViewById(R.id.spJenisTiang);

        gpsTracker = new GPSTracker(this);
        Gson gsonforobj = new Gson();
        String strObj = getIntent().getStringExtra("objTiang");
        mCurrentTiang = gsonforobj.fromJson(strObj, Tiang.class);

        if (mCurrentTiang != null){
            initForm();
        }

        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            if (gpsTracker.latitude > 0 && gpsTracker.longitude > 0){
                stringLatitude = String.valueOf(gpsTracker.latitude);
                stringLongitude = String.valueOf(gpsTracker.longitude);

                Log.d("GPS", stringLatitude);
                Log.d("GPS", stringLongitude);

                edLat.setText(stringLatitude);
                edLng.setText(stringLongitude);
            }
            Log.d("GPS", "AKFTIF");

        }else{
            Log.d("GPS", "GK AKFTIF");
            gpsTracker.showSettingsAlert();
        }


        init();
    }

    public void init(){
        //Jenis Tiang
        spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                arrTiang);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJenisTiang.setAdapter(spinnerArrayAdapter);

        //Jenis Kabel
        spinnerArrayAdapterJenisKabel = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                arrJenisKabel);
        spinnerArrayAdapterJenisKabel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJenisKabel.setAdapter(spinnerArrayAdapterJenisKabel);
    }

    public void initForm(){

    }

    public void fetchJenisTiang(){
        String url = Global.GET_DATA_JENIS_TIANG;
        request = new MultipartJSONRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Kirim Data", response.toString());
                        try {
                            if (Global.isResponseSuccess(response)) {
                                JSONArray jsonArray=response.getJSONArray(Global.JSON_DATA);


                                Log.d("Kirim Data2", String.valueOf(gson.fromJson(jsonArray.getString(0),JenisTiang.class)));


                                if (jsonArray.length() > 0){
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JenisTiang objJenisTiang = gson.fromJson(jsonArray.getString(i),JenisTiang.class);
                                        arrTiang.add(objJenisTiang.getNmJenisTiang().toString());
                                        hashidTiang.put(objJenisTiang.getNmJenisTiang().toString(), String.valueOf(objJenisTiang.getIDJenisTiang()));
                                    }
                                    spinnerArrayAdapter.notifyDataSetChanged();
                                }


                            } else {
                                Toast.makeText(FormTiang.this, Global.getResponseMessage(response), Toast.LENGTH_SHORT).show();
                            }
                            //setNotLoading();
                        } catch (JSONException je) {

                            Toast.makeText(FormTiang.this, je.toString(), Toast.LENGTH_SHORT).show();
                            je.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FormTiang.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        );
        request.setShouldCache(false);
        Log.d("getdata", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(this).addToRequestQueue(request);
    }

    public void fetchJenisKabel(){
        String url = Global.GET_DATA_JENIS_KABEL;
        request = new MultipartJSONRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Kirim Data", response.toString());
                        try {
                            if (Global.isResponseSuccess(response)) {
                                JSONArray jsonArray=response.getJSONArray(Global.JSON_DATA);




                                if (jsonArray.length() > 0){
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JenisKabel objJenisKabel = gson.fromJson(jsonArray.getString(i),JenisKabel.class);
                                        arrJenisKabel.add(objJenisKabel.getNmJenisKabel().toString());
                                        hashidJenisKabel.put(objJenisKabel.getNmJenisKabel().toString(), String.valueOf(objJenisKabel.getIDJenisKabel()));
                                    }
                                    spinnerArrayAdapterJenisKabel.notifyDataSetChanged();
                                }


                            } else {
                                Toast.makeText(FormTiang.this, Global.getResponseMessage(response), Toast.LENGTH_SHORT).show();
                            }
                            //setNotLoading();
                        } catch (JSONException je) {

                            Toast.makeText(FormTiang.this, je.toString(), Toast.LENGTH_SHORT).show();
                            je.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FormTiang.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        );
        request.setShouldCache(false);
        Log.d("getdata", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(this).addToRequestQueue(request);
    }
}
