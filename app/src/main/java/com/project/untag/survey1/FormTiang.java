package com.project.untag.survey1;

import android.graphics.Color;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.project.untag.survey1.API.Global;
import com.project.untag.survey1.API.MultipartJSONRequest;
import com.project.untag.survey1.API.MyRequest;
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

    private MultipartJSONRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_tiang);

        fetchJenisTiang();
        fetchJenisKabel();

        gson = new GsonBuilder().create();
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

                edLat.setText(stringLatitude);
                edLng.setText(stringLongitude);
            }


        }else{
            gpsTracker.showSettingsAlert();
        }


        spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                arrTiang);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJenisTiang.setAdapter(spinnerArrayAdapter);
    }

    public void initForm(){

    }

    public void fetchJenisTiang(){
        String url = Global.GET_DATA_JENIS_KABEL;
        request = new MultipartJSONRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Kirim Data", response.toString());
                        try {
                            if (Global.isResponseSuccess(response)) {
                                JSONArray jsonArray=response.getJSONArray(Global.JSON_DATA);
                                arrTiang.clear();
                                hashidTiang.clear();

                                if (jsonArray.length() > 0){
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JenisTiang objJenisTiang = gson.fromJson(jsonArray.getJSONObject(i).toString(),JenisTiang.class);
                                        arrTiang.add(objJenisTiang.getNmJenisTiang());
                                        hashidTiang.put(objJenisTiang.getNmJenisTiang(), String.valueOf(objJenisTiang.getIDJenisTiang()));
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

    }
}
