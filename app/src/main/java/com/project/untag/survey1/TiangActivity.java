package com.project.untag.survey1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.project.untag.survey1.Adapter.TiangAdapter;
import com.project.untag.survey1.Model.Survey;
import com.project.untag.survey1.Model.Tiang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TiangActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private EditText edIdPel;
    private Button btnSearch,btnAdd;
    private TiangAdapter adapter;
    //private ArrayList<Tiang> tiangArrayList;
    private List<Tiang> tiangArrayList = new ArrayList<>();

    private MultipartJSONRequest request;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiang);
        recyclerView = (RecyclerView) findViewById(R.id.rvTiang);
        edIdPel = (EditText) findViewById(R.id.edIdPel);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        gson = new GsonBuilder().create();
        fetchTiang();


        Log.d("asd", "onResponse: "+tiangArrayList);
        adapter = new TiangAdapter(tiangArrayList,this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TiangActivity.this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchTiang();
                adapter.notifyDataSetChanged();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TiangActivity.this,FormTiang.class));
            }
        });
    }

    public void fetchTiang(){
        String url = Global.GET_DATA_TIANG;
        request = new MultipartJSONRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Kirim Data", response.toString());
                        try {
                            if (Global.isResponseSuccess(response)) {
                                JSONArray jsonArray=response.getJSONArray(Global.JSON_DATA);


                                List<Tiang> items = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<Tiang>>() {
                                }.getType());

                                // adding contacts to contacts list
                                tiangArrayList.clear();
                                tiangArrayList.addAll(items);

                                // refreshing recycler view
                                adapter.notifyDataSetChanged();

                                } else {
                                Toast.makeText(TiangActivity.this, Global.getResponseMessage(response), Toast.LENGTH_SHORT).show();
                            }
                            //setNotLoading();
                        } catch (JSONException je) {

                            Toast.makeText(TiangActivity.this, je.toString(), Toast.LENGTH_SHORT).show();
                            je.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                  Toast.makeText(TiangActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        );


        if (!edIdPel.getText().toString().matches("")) {
            request.addStringParam("IDPel", edIdPel.getText().toString());
        }
        request.setShouldCache(false);
        Log.d("getdata", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(this).addToRequestQueue(request);
    }
}
