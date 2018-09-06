package com.project.untag.survey1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
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
import com.project.untag.survey1.Adapter.LampuAdapter;
import com.project.untag.survey1.Model.Lampu;
import com.project.untag.survey1.Model.Tiang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LampuActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LampuAdapter adapter;
    private List<Lampu> arrLampu = new ArrayList<>();;

    private MultipartJSONRequest request;
    private Gson gson;
    Tiang mCurrentTiang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lampu);

        recyclerView = (RecyclerView) findViewById(R.id.rvLampu);
        String strObj = getIntent().getStringExtra("objTiang");

        Gson gsonforobj = new Gson();
        mCurrentTiang = gsonforobj.fromJson(strObj, Tiang.class);

        gson = new GsonBuilder().create();
        fetchLampu();

        initRecycler();

    }

    public void initRecycler(){
        Log.d("asd", "onResponse: "+arrLampu);
        adapter = new LampuAdapter(arrLampu,this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LampuActivity.this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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


                                List<Lampu> items = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<Lampu>>() {
                                }.getType());

                                // adding contacts to contacts list
                                arrLampu.clear();
                                arrLampu.addAll(items);
                                initRecycler();
                                // refreshing recycler view
                                adapter.notifyDataSetChanged();

                            } else {
                                Toast.makeText(LampuActivity.this, Global.getResponseMessage(response), Toast.LENGTH_SHORT).show();
                            }
                            //setNotLoading();
                        } catch (JSONException je) {

                            Toast.makeText(LampuActivity.this, je.toString(), Toast.LENGTH_SHORT).show();
                            je.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LampuActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        );

        request.setShouldCache(false);

        request.addStringParam("IDTiang", mCurrentTiang.getIDTiang().toString());
        Log.d("getdata", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(this).addToRequestQueue(request);
    }
}
