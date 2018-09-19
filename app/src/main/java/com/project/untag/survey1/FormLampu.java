package com.project.untag.survey1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.project.untag.survey1.Model.JenisTiang;
import com.project.untag.survey1.Model.Lampu;
import com.project.untag.survey1.Model.Tiang;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FormLampu extends AppCompatActivity {
    Button btnSave;
    EditText edJumlah;
    Spinner spLampu;
    Tiang mCurrentTiang;
    ImageView imgKondisiLampu;

    private ArrayList<String> arrLampu;
    private HashMap<String, String> hashidLampu;
    ArrayAdapter<String> spinnerArrayLampuAdapter;

    private Gson gson;
    private MultipartJSONRequest request;
    private String fileImagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_lampu);

        btnSave = (Button)findViewById(R.id.btnSave);
        edJumlah = (EditText) findViewById(R.id.edJumlahLampu);
        spLampu = (Spinner) findViewById(R.id.spLampu);
        imgKondisiLampu = (ImageView)findViewById(R.id.imgKondisiLampu);

        String strObj = getIntent().getStringExtra("objTiang");

        Gson gsonforobj = new Gson();
        mCurrentTiang = gsonforobj.fromJson(strObj, Tiang.class);


        gson = new GsonBuilder().create();

        arrLampu = new ArrayList<>();
        hashidLampu = new HashMap<>();

        fetchLampu();



        init();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLampuPJU();
            }
        });

        imgKondisiLampu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                //TODO: do what you have to...
                                imgKondisiLampu.setImageURI(r.getUri());
                                Log.d("Ikko", "onPickResult: "+"file://"+r.getPath());
                                fileImagePath = r.getPath();


                            }
                        })
                        .setOnPickCancel(new IPickCancel() {
                            @Override
                            public void onCancelClick() {
                                //TODO: do what you have to if user clicked cancel

                            }
                        }).show(FormLampu.this.getSupportFragmentManager());
            }
        });
    }

    void saveLampuPJU(){
        String url = Global.GET_ADD_DATA_LAMPU_PJU;
        request = new MultipartJSONRequest(Request.Method.POST, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Kirim Data", response.toString());
                        try {
                            if (Global.isResponseSuccess(response)) {
                                JSONArray jsonArray=response.getJSONArray(Global.JSON_DATA);


                                Toast.makeText(FormLampu.this, "Sukses", Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(FormLampu.this, Global.getResponseMessage(response), Toast.LENGTH_SHORT).show();
                            }
                            //setNotLoading();
                        } catch (JSONException je) {

                            Toast.makeText(FormLampu.this, je.toString(), Toast.LENGTH_SHORT).show();
                            je.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FormLampu.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        );


        request.addStringParam("IDTiang", mCurrentTiang.getIDTiang().toString());
        request.addStringParam("Jumlah", edJumlah.getText().toString());

        String id_lampu = spLampu.getSelectedItem().toString();


        request.addStringParam("IDLampu", hashidLampu.get(id_lampu));

        if (fileImagePath != null || !fileImagePath.isEmpty()) {
            Log.d("ikko", "sendSurvey: "+fileImagePath);
            request.addFile("FotoLampu", fileImagePath);
        }

        request.setShouldCache(false);
        Log.d("getdata", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(this).addToRequestQueue(request);
    }

    public void fetchLampu(){
        String url = Global.GET_DATA_LAMPU;
        request = new MultipartJSONRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Kirim Data", response.toString());
                        try {
                            if (Global.isResponseSuccess(response)) {
                                JSONArray jsonArray=response.getJSONArray(Global.JSON_DATA);


                                Log.d("Kirim Data2", String.valueOf(gson.fromJson(jsonArray.getString(0),JenisTiang.class)));

                                arrLampu.add("Pilih Jenis Tiang");
                                if (jsonArray.length() > 0){
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        Lampu objLampu = gson.fromJson(jsonArray.getString(i),Lampu.class);
                                        arrLampu.add(objLampu.getJenisLampu().getNmJenisLampu().toString()+"- W:"+objLampu.getWattLampu()+"- V"+objLampu.getVALampu());
                                        hashidLampu.put(objLampu.getJenisLampu().getNmJenisLampu().toString()+"- W:"+objLampu.getWattLampu()+"- V"+objLampu.getVALampu(), String.valueOf(objLampu.getIDLampu()));
                                    }
                                    spinnerArrayLampuAdapter.notifyDataSetChanged();
                                }


                            } else {
                                Toast.makeText(FormLampu.this, Global.getResponseMessage(response), Toast.LENGTH_SHORT).show();
                            }
                            //setNotLoading();
                        } catch (JSONException je) {

                            Toast.makeText(FormLampu.this, je.toString(), Toast.LENGTH_SHORT).show();
                            je.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FormLampu.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        );
        request.setShouldCache(false);
        Log.d("getdata", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(this).addToRequestQueue(request);
    }

    void init(){
        spinnerArrayLampuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                arrLampu);
        spinnerArrayLampuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLampu.setAdapter(spinnerArrayLampuAdapter);
    }
}
