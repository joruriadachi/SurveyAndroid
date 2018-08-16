package com.project.untag.survey1;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.project.untag.survey1.API.Global;
import com.project.untag.survey1.API.MultipartJSONRequest;
import com.project.untag.survey1.API.MyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class HomeActivity extends AppCompatActivity {
    ImageView imgSurvey1,imgSurvey2;
    TextView tvTotal,tvTotalIsi;
    private MultipartJSONRequest request;
    SweetAlertDialog AlertDialogSuccess;
    SweetAlertDialog AlertDialogError;
    int total = 0;
    int totalisi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setTitle("Home Survey");

        AlertDialogSuccess = new SweetAlertDialog(this,SweetAlertDialog.SUCCESS_TYPE);
        AlertDialogError = new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE);

        imgSurvey1 = (ImageView)findViewById(R.id.imgSurvey1);
        imgSurvey2 = (ImageView)findViewById(R.id.imgSurvey2);
        tvTotal = (TextView)findViewById(R.id.tvTotal);
        tvTotalIsi = (TextView)findViewById(R.id.tvTotalIsi);

        imgSurvey1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
            }
        });


        imgSurvey2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,Survey2Activity.class));
            }
        });


        //test pull sadasd
        fetchTotal();
    }

    public void fetchTotal(){
        String url = Global.GET_TOTAL_DATA;
        request = new MultipartJSONRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Kirim Data", response.toString());
                        try {
                            if (Global.isResponseSuccess(response)) {
                                JSONObject jsonObject=response.getJSONObject(Global.JSON_DATA);

                                total = jsonObject.getInt("TotalData");
                                totalisi = jsonObject.getInt("TotalDataIsi");

                                tvTotal.setText("Total Data : "+total);
                                tvTotalIsi.setText("Data yang belum diisi : "+totalisi);

                            } else {
                                AlertDialogError.setTitleText("Error").setContentText(Global.getResponseMessage(response)).setConfirmText("OK").show();
                            }
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

        request.setShouldCache(false);
        Log.d("getdata", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(this).addToRequestQueue(request);
    }
}
