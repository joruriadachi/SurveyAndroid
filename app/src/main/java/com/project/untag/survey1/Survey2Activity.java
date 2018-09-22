package com.project.untag.survey1;

import android.app.DatePickerDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.untag.survey1.API.Global;
import com.project.untag.survey1.API.MultipartJSONRequest;
import com.project.untag.survey1.API.MyRequest;
import com.project.untag.survey1.Model.Survey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Survey2Activity extends AppCompatActivity {

    EditText edTanggal,edNomor,edIdPel,edStandM;
    Spinner spWilayah;
    Button btnSave;
    ImageButton btnSearch;
    private Survey mCurrentSurvey;
    private Gson gson;
    final Calendar kalender = Calendar.getInstance();

    ArrayList<String> arrWIlayah;
    ArrayAdapter<String> adapter_wilayah;

    private MultipartJSONRequest request;


    SweetAlertDialog AlertDialogSuccess;
    SweetAlertDialog AlertDialogError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey2);

        spWilayah = (Spinner)findViewById(R.id.spWilayah);
        edNomor = (EditText)findViewById(R.id.edNomor);
        edIdPel = (EditText)findViewById(R.id.edID_P);
        btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        btnSave = (Button)findViewById(R.id.btnSave);
        edTanggal = (EditText)findViewById(R.id.edTanggal) ;
        edStandM = (EditText) findViewById(R.id.edStandM);



        Date currentTime = Calendar.getInstance().getTime();
        String fDate = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);
        edTanggal.setText(fDate);

        arrWIlayah = new ArrayList<String>();
        gson = new GsonBuilder().create();
        init();
        fetchWilayah();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataSurvey();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSurvey2();
            }
        });
    }


    void saveSurvey2(){
        String url = Global.ADD_NEW_SURVEY2;
        request = new MultipartJSONRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Kirim Data", response.toString());
                        if (Global.isResponseSuccess(response)) {
                            Toast.makeText(Survey2Activity.this, Global.getResponseMessage(response), Toast.LENGTH_SHORT).show();

                        } else {
//                            AlertDialogError.setTitleText("Error").setContentText(Global.getResponseMessage(response)).setConfirmText("OK").show();
                            Toast.makeText(Survey2Activity.this, "Gagal", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(MainActivity.this, Global.getResponseMessage(response), Toast.LENGTH_SHORT).show();
//                            hideProgressBar();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                AlertDialogError.setTitleText("Error").setContentText(error.toString()).setConfirmText("OK").show();
//                        Toast.makeText(MainActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
//                hideProgressBar();
            }
        }

        );

        request.addStringParam("Id_Pel", edIdPel.getText().toString());
        request.addStringParam("StandKWH2", edStandM.getText().toString());
        request.addStringParam("Tanggal2", edTanggal.getText().toString());

        request.setShouldCache(false);
        Log.d("savedata", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(this).addToRequestQueue(request);
    }


    public void init(){
        arrWIlayah.add("==WILAYAH==");
        adapter_wilayah = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        arrWIlayah);
        adapter_wilayah.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWilayah.setAdapter(adapter_wilayah);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                kalender.set(Calendar.YEAR, year);
                kalender.set(Calendar.MONTH, monthOfYear);
                kalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        edTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Survey2Activity.this, date, kalender
                        .get(Calendar.YEAR), kalender.get(Calendar.MONTH),
                        kalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edTanggal.setText(sdf.format(kalender.getTime()));
    }

    public void getDataSurvey(){
        String url = Global.GET_DATA_SURVEY;

        request = new MultipartJSONRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Kirim Data", response.toString());
                        try {
                            if (Global.isResponseSuccess(response)) {

                                JSONObject jsonObject = response.getJSONObject(Global.JSON_DATA);
                                Log.d("", "onResponse: "+jsonObject.toString());

                                mCurrentSurvey = gson.fromJson(jsonObject.toString(),Survey.class);

                                setform();
                            } else {
//                                AlertDialogError.setTitleText("Error").setContentText(Global.getResponseMessage(response)).setConfirmText("OK").show();
                                Toast.makeText(Survey2Activity.this, Global.getResponseMessage(response), Toast.LENGTH_SHORT).show();
                            }
                            //setNotLoading();
                        } catch (JSONException je) {
                            AlertDialogError.setTitleText("Error").setContentText(je.toString()).setConfirmText("OK").show();
//                            Toast.makeText(MainActivity.this, je.toString(), Toast.LENGTH_SHORT).show();
                            je.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialogError.setTitleText("Error").setContentText(error.toString()).setConfirmText("OK").show();
//                Toast.makeText(MainActivity.this,error.toString(), Toast.LENGTH_SHORT).show();

            }
        }

        );

        request.addStringParam("No", edNomor.getText().toString());
        request.addStringParam("Id_Pel", edIdPel.getText().toString());

        request.setShouldCache(false);
        Log.d("getdata", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(this).addToRequestQueue(request);
    }

    public void setform(){

    }


    public void fetchWilayah(){
        String url = Global.GET_DATA_WILAYAH;
        request = new MultipartJSONRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Kirim Data", response.toString());
                        try {
                            if (Global.isResponseSuccess(response)) {
                                JSONArray jsonArray=response.getJSONArray(Global.JSON_DATA);
                                for (int i=0;i<jsonArray.length();i++){
                                    Log.d("asd", "onResponse: "+jsonArray.getString(i));
                                    arrWIlayah.add(jsonArray.getString(i));
                                }

                            } else {
                                AlertDialogError.setTitleText("Error").setContentText(Global.getResponseMessage(response)).setConfirmText("OK").show();
                            }
                            //setNotLoading();
                        } catch (JSONException je) {
                            AlertDialogError.setTitleText("Error").setContentText(je.toString()).setConfirmText("OK").show();
                            je.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialogError.setTitleText("Error").setContentText(error.toString()).setConfirmText("OK").show();
            }
        }

        );

        request.addStringParam("No", edNomor.getText().toString());
        request.addStringParam("Id_Pel", edIdPel.getText().toString());

        request.setShouldCache(false);
        Log.d("getdata", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(this).addToRequestQueue(request);
    }


}
