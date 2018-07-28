package com.project.untag.survey1;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.untag.survey1.API.Global;
import com.project.untag.survey1.API.MultipartJSONRequest;
import com.project.untag.survey1.API.MyRequest;
import com.project.untag.survey1.Global.Util;
import com.project.untag.survey1.Model.Survey;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    Spinner spKWH, spKontaktor, spBatas, spMCB, spSwitch, spGround, spKondisi, spKeterangan, spWilayah;
    EditText edWatt, edDaya, edTanggal, edNomor, edIdPel, edProvinsi, edKabupatenKota, edNama, edAlamat, edKecamatan, edWilayah, edJumlah, edCospi, edAmpere, edStandM, edLat, edLng, edJumlahMCB, edVoltAmpere;
    ImageView imgPhoto;
    ImageButton btnSave, btnSearch, btnNewSave;
    Drawable placeholder;
    ArrayList<String> arrWIlayah;

    ArrayAdapter<CharSequence> adapter_kwh, adapter_kontaktor, adapter_batas, adapter_mcb, adapter_switchs, adapter_ground, adapter_kondisi, adapter_keterangan, adapter_survey;
    ArrayAdapter<String> adapter_wilayah;
    GPSTracker gpsTracker;
    private FusedLocationProviderClient mFusedLocationClient;

    String stringLatitude, stringLongitude;
    private Survey mCurrentSurvey;
    private Gson gson;

    final Calendar kalender = Calendar.getInstance();
    private MultipartJSONRequest request;
    private ProgressBar progressBar;
    private ScrollView scSurvey;
    private String fileImagePath = "";
    SweetAlertDialog AlertDialogSuccess;
    SweetAlertDialog AlertDialogError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(getApplication(), "Gk AKtip", Toast.LENGTH_SHORT).show();
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

//        if (!Util.isConnectingToInternet(this)) {
////            AlertDialogError.setTitleText("Error").setContentText("Anda tidak sedang terhubung dengan Internet").setConfirmText("OK").show();
//            Toast.makeText(this, "Anda tidak sedang terhubung dengan Internet", Toast.LENGTH_SHORT).show();
//            return;
//        }


        gson = new GsonBuilder().create();
        gpsTracker = new GPSTracker(this);

        AlertDialogSuccess = new SweetAlertDialog(this,SweetAlertDialog.SUCCESS_TYPE);
        AlertDialogError = new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE);

        placeholder = getResources().getDrawable(R.drawable.placeholder_square);

        spKWH = (Spinner)findViewById(R.id.spKWH);
        spKontaktor = (Spinner)findViewById(R.id.spKontaktor);
        spBatas = (Spinner)findViewById(R.id.spBatas);
        spMCB = (Spinner)findViewById(R.id.spMCB);
        spSwitch = (Spinner)findViewById(R.id.spSwitch);
        spGround = (Spinner)findViewById(R.id.spGround);
        spKondisi = (Spinner)findViewById(R.id.spKondisi);
        spWilayah = (Spinner)findViewById(R.id.spWilayah);
        spKeterangan = (Spinner)findViewById(R.id.spKeterangan);
        edTanggal = (EditText)findViewById(R.id.edTanggal);
        imgPhoto = (ImageView)findViewById(R.id.imgPhoto);
        btnSave = (ImageButton)findViewById(R.id.btnSave);
        btnNewSave = (ImageButton)findViewById(R.id.btnNewSave);
        btnSearch = (ImageButton)findViewById(R.id.btnSearch);
        scSurvey = (ScrollView)findViewById(R.id.scSurvey);
        edVoltAmpere = (EditText)findViewById(R.id.edVolAmpere);
        edDaya = (EditText)findViewById(R.id.edDaya);
        edWatt = (EditText)findViewById(R.id.edWatt);

        edNomor = (EditText)findViewById(R.id.edNomor);
        edIdPel = (EditText)findViewById(R.id.edID_P);
        edProvinsi = (EditText)findViewById(R.id.edProvinsi);
        edKabupatenKota = (EditText)findViewById(R.id.edKabupaten);
        edNama = (EditText)findViewById(R.id.edNama);
        edAlamat = (EditText)findViewById(R.id.edAlamat);
        edKecamatan = (EditText)findViewById(R.id.edKecamatan);
        edWilayah = (EditText)findViewById(R.id.edWilayah);
        edJumlah = (EditText)findViewById(R.id.edJumlah);
        edJumlahMCB = (EditText)findViewById(R.id.edJumlahMCB);
        edCospi = (EditText)findViewById(R.id.edCospi);
        edAmpere = (EditText)findViewById(R.id.edAmpere);
        edStandM = (EditText)findViewById(R.id.edStandM);
        edLat = (EditText)findViewById(R.id.edLat);
        edLng = (EditText)findViewById(R.id.edLng);

        arrWIlayah = new ArrayList<String>();

        progressBar = (ProgressBar) findViewById(R.id.prb_load_more);
        hideProgressBar();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSurvey();
            }
        });

        btnNewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNewSurvey();
            }
        });

        AlertDialogError.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                //Dismiss your alert here then you will be able to call it again
                sweetAlertDialog.dismiss();
            }
        });

        AlertDialogSuccess.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                //Dismiss your alert here then you will be able to call it again
                sweetAlertDialog.dismiss();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spWilayah.getSelectedItemPosition() == 0 || edNomor.getText().toString() == "" || edNomor.getText().toString() == null){
                    AlertDialogError.setTitleText("Error").setContentText("Silankan Pilih WIlayah dan No Urut/ID Pelanggan terlebih dahulu").setConfirmText("OK").show();
                }else{
                    getDataSurvey();
                }
            }
        });

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

        Date currentTime = Calendar.getInstance().getTime();
        String fDate = new SimpleDateFormat("MM/dd/yyyy").format(currentTime);
        edTanggal.setText(fDate);

        init();
        fetchWilayah();

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
                                
                                hideProgressBar();
                            } else {
                                AlertDialogError.setTitleText("Error").setContentText(Global.getResponseMessage(response)).setConfirmText("OK").show();
//                                Toast.makeText(MainActivity.this, Global.getResponseMessage(response), Toast.LENGTH_SHORT).show();
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
                hideProgressBar();
            }
        }

        );

        request.addStringParam("No", edNomor.getText().toString());
        request.addStringParam("Id_Pel", edIdPel.getText().toString());

        request.setShouldCache(false);
        Log.d("getdata", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(this).addToRequestQueue(request);
    }

    public void init(){


        ArrayAdapter<CharSequence> adapter_kwh = ArrayAdapter.createFromResource(this, R.array.kwh, android.R.layout.simple_spinner_item);
        adapter_kwh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter_kontaktor = ArrayAdapter.createFromResource(this, R.array.kontaktor, android.R.layout.simple_spinner_item);
        adapter_kontaktor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter_batas = ArrayAdapter.createFromResource(this, R.array.batas, android.R.layout.simple_spinner_item);
        adapter_batas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter_mcb = ArrayAdapter.createFromResource(this, R.array.mcb, android.R.layout.simple_spinner_item);
        adapter_mcb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter_switchs = ArrayAdapter.createFromResource(this, R.array.switchs, android.R.layout.simple_spinner_item);
        adapter_switchs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter_ground = ArrayAdapter.createFromResource(this, R.array.ground, android.R.layout.simple_spinner_item);
        adapter_ground.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter_kondisi = ArrayAdapter.createFromResource(this, R.array.kondisi, android.R.layout.simple_spinner_item);
        adapter_kondisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter_keterangan = ArrayAdapter.createFromResource(this, R.array.keterangan, android.R.layout.simple_spinner_item);
        adapter_keterangan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter_survey = ArrayAdapter.createFromResource(this,R.array.survey, android.R.layout.simple_spinner_dropdown_item);
        adapter_survey.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        arrWIlayah.add("==WILAYAH==");
        adapter_wilayah = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        arrWIlayah);
        adapter_wilayah.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                kalender.set(Calendar.YEAR, year);
                kalender.set(Calendar.MONTH, monthOfYear);
                kalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        spKWH.setAdapter(adapter_kwh);
        spKontaktor.setAdapter(adapter_kontaktor);
        spBatas.setAdapter(adapter_batas);
        spMCB.setAdapter(adapter_mcb);
        spSwitch.setAdapter(adapter_switchs);
        spGround.setAdapter(adapter_ground);
        spKondisi.setAdapter(adapter_kondisi);
        spKeterangan.setAdapter(adapter_keterangan);
        adapter_wilayah.setNotifyOnChange(true);
        spWilayah.setAdapter(adapter_wilayah);

        edTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, date, kalender
                        .get(Calendar.YEAR), kalender.get(Calendar.MONTH),
                        kalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                //TODO: do what you have to...
                                imgPhoto.setImageURI(r.getUri());
                                Log.d("Ikko", "onPickResult: "+"file://"+r.getPath());
                                fileImagePath = r.getPath();


                            }
                        })
                        .setOnPickCancel(new IPickCancel() {
                            @Override
                            public void onCancelClick() {
                                //TODO: do what you have to if user clicked cancel

                            }
                        }).show(MainActivity.this.getSupportFragmentManager());
            }
        });

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edTanggal.setText(sdf.format(kalender.getTime()));
    }

    void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
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
                                hideProgressBar();
                            } else {
                                AlertDialogError.setTitleText("Error").setContentText(Global.getResponseMessage(response)).setConfirmText("OK").show();
//                                Toast.makeText(MainActivity.this, Global.getResponseMessage(response), Toast.LENGTH_SHORT).show();
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
                hideProgressBar();
            }
        }

        );

        request.addStringParam("No", edNomor.getText().toString());
        request.addStringParam("Id_Pel", edIdPel.getText().toString());
        request.addStringParam("Wilayah",spWilayah.getSelectedItem().toString());

        request.setShouldCache(false);
        Log.d("getdata", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(this).addToRequestQueue(request);
    }

    public void setform(){
        edNomor.setText(mCurrentSurvey.getNoUrut());
        edIdPel.setText(mCurrentSurvey.getIDPel());
        edProvinsi.setText(mCurrentSurvey.getProvinsi());
        edKabupatenKota.setText(mCurrentSurvey.getKabupatenKota());
        edNama.setText(mCurrentSurvey.getNama());
        edAlamat.setText(mCurrentSurvey.getAlamat());
        edKecamatan.setText(mCurrentSurvey.getKecamatan());
        edWilayah.setText(mCurrentSurvey.getWilayah());
        edTanggal.setText(mCurrentSurvey.getTanggal1());
        edJumlah.setText(mCurrentSurvey.getJumlahLampu());
        edJumlahMCB.setText(mCurrentSurvey.getJumlahMCB());
        edCospi.setText(mCurrentSurvey.getCosPhi());
        edAmpere.setText(mCurrentSurvey.getAmphere());
        edStandM.setText(mCurrentSurvey.getStandKWH1());
        edVoltAmpere.setText(mCurrentSurvey.getVoltAmpere());
        edDaya.setText(mCurrentSurvey.getDaya());
        edWatt.setText(mCurrentSurvey.getWatt());

        selectSpinnerValue(spKWH,mCurrentSurvey.getKWhMeter());
        selectSpinnerValue(spKontaktor,mCurrentSurvey.getKontaktor());
        selectSpinnerValue(spBatas,mCurrentSurvey.getPembatasDaya());
        selectSpinnerValue(spMCB,mCurrentSurvey.getMCB());
        selectSpinnerValue(spSwitch,mCurrentSurvey.getSwitchs());
        selectSpinnerValue(spGround,mCurrentSurvey.getGrounding());
        selectSpinnerValue(spKondisi,mCurrentSurvey.getKondisiBox());
        selectSpinnerValue(spKeterangan,mCurrentSurvey.getKeterangan());



        imgPhoto.setImageDrawable(placeholder);
    }

    private void selectSpinnerValue(Spinner spinner, String myString)
    {
        int index = 0;
        for(int i = 0; i < spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).toString().equals(myString)){
                spinner.setSelection(i);
                break;
            }
        }
    }

    public void sendSurvey(){
        String url = Global.ADD_SURVEY;
        showProgressBar();

        request = new MultipartJSONRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Kirim Data", response.toString());
                        if (Global.isResponseSuccess(response)) {
                            Snackbar snackbar = Snackbar
                                    .make(scSurvey, Global.getResponseMessage(response), Snackbar.LENGTH_LONG);

                            snackbar.show();
                            hideProgressBar();
                        } else {
                            AlertDialogError.setTitleText("Error").setContentText(Global.getResponseMessage(response)).setConfirmText("OK").show();
//                            Toast.makeText(gpsTracker, "", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(MainActivity.this, Global.getResponseMessage(response), Toast.LENGTH_SHORT).show();
                            hideProgressBar();
                        }
                    }
                }
                , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialogError.setTitleText("Error").setContentText(error.toString()).setConfirmText("OK").show();
//                        Toast.makeText(MainActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
                        hideProgressBar();
                    }
                }

        );

        request.addStringParam("No", edNomor.getText().toString());
        request.addStringParam("Id_Pel", edIdPel.getText().toString());
        request.addStringParam("Kd_Provinsi", edProvinsi.getText().toString());
        request.addStringParam("Kd_Kabupaten", edKabupatenKota.getText().toString());
        request.addStringParam("Kd_Kecamatan", edKecamatan.getText().toString());
        request.addStringParam("Nama", edNama.getText().toString());
        request.addStringParam("Alamat", edAlamat.getText().toString());
//        request.addStringParam("Kec", edKecamatan.getText().toString());
        request.addStringParam("Wilayah", edWilayah.getText().toString());
        request.addStringParam("Lat", edLat.getText().toString());
        request.addStringParam("Long", edLng.getText().toString());
        request.addStringParam("Tanggal", edTanggal.getText().toString());
        request.addStringParam("Jumlah", edJumlah.getText().toString());
        request.addStringParam("Kwh", spKWH.getSelectedItem().toString());
        request.addStringParam("Batas", spBatas.getSelectedItem().toString());
        request.addStringParam("Kontaktor", spKontaktor.getSelectedItem().toString());
        request.addStringParam("Mcb", spMCB.getSelectedItem().toString());
        request.addStringParam("JumlahMCB", edJumlahMCB.getText().toString());
        request.addStringParam("Switch", spSwitch.getSelectedItem().toString());
        request.addStringParam("Ground", spGround.getSelectedItem().toString());
        request.addStringParam("Kondisi", spKondisi.getSelectedItem().toString());
        request.addStringParam("Cospi", edCospi.getText().toString());
        request.addStringParam("Amp", edAmpere.getText().toString());
        request.addStringParam("Stand", edStandM.getText().toString());
        request.addStringParam("Ket", spKeterangan.getSelectedItem().toString());
        request.addStringParam("VoltAmpere", edVoltAmpere.getText().toString());
        request.addStringParam("Daya", edDaya.getText().toString());
        request.addStringParam("Watt", edWatt.getText().toString());

        if (fileImagePath != null || !fileImagePath.isEmpty()) {
            Log.d("ikko", "sendSurvey: "+fileImagePath);
            request.addFile("Foto", fileImagePath);
        }


        request.setShouldCache(false);
        Log.d("savedata", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(this).addToRequestQueue(request);

    }

    public void sendNewSurvey(){
        String url = Global.ADD_NEW_SURVEY;
        showProgressBar();

        request = new MultipartJSONRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Kirim Data", response.toString());
                        if (Global.isResponseSuccess(response)) {
                            Snackbar snackbar = Snackbar
                                    .make(scSurvey, Global.getResponseMessage(response), Snackbar.LENGTH_LONG);

                            snackbar.show();
                            hideProgressBar();
                        } else {
                            AlertDialogError.setTitleText("Error").setContentText(Global.getResponseMessage(response)).setConfirmText("OK").show();
//                            Toast.makeText(gpsTracker, "", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(MainActivity.this, Global.getResponseMessage(response), Toast.LENGTH_SHORT).show();
                            hideProgressBar();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialogError.setTitleText("Error").setContentText(error.toString()).setConfirmText("OK").show();
//                        Toast.makeText(MainActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
                hideProgressBar();
            }
        }

        );

        request.addStringParam("No", edNomor.getText().toString());
        request.addStringParam("Id_Pel", edIdPel.getText().toString());
        request.addStringParam("Kd_Provinsi", edProvinsi.getText().toString());
        request.addStringParam("Kd_Kabupaten", edKabupatenKota.getText().toString());
        request.addStringParam("Kd_Kecamatan", edKecamatan.getText().toString());
        request.addStringParam("Nama", edNama.getText().toString());
        request.addStringParam("Alamat", edAlamat.getText().toString());
//        request.addStringParam("Kec", edKecamatan.getText().toString());
        request.addStringParam("Wilayah", edWilayah.getText().toString());
        request.addStringParam("Lat", edLat.getText().toString());
        request.addStringParam("Long", edLng.getText().toString());
        request.addStringParam("Tanggal", edTanggal.getText().toString());
        request.addStringParam("Jumlah", edJumlah.getText().toString());
        request.addStringParam("Kwh", spKWH.getSelectedItem().toString());
        request.addStringParam("Batas", spBatas.getSelectedItem().toString());
        request.addStringParam("Kontaktor", spKontaktor.getSelectedItem().toString());
        request.addStringParam("Mcb", spMCB.getSelectedItem().toString());
        request.addStringParam("JumlahMCB", edJumlahMCB.getText().toString());
        request.addStringParam("Switch", spSwitch.getSelectedItem().toString());
        request.addStringParam("Ground", spGround.getSelectedItem().toString());
        request.addStringParam("Kondisi", spKondisi.getSelectedItem().toString());
        request.addStringParam("Cospi", edCospi.getText().toString());
        request.addStringParam("Amp", edAmpere.getText().toString());
        request.addStringParam("Stand", edStandM.getText().toString());
        request.addStringParam("Ket", spKeterangan.getSelectedItem().toString());
        request.addStringParam("VoltAmpere", edVoltAmpere.getText().toString());
        request.addStringParam("Daya", edDaya.getText().toString());
        request.addStringParam("Watt", edWatt.getText().toString());

        if (fileImagePath != null || !fileImagePath.isEmpty()) {
            Log.d("ikko", "sendSurvey: "+fileImagePath);
            request.addFile("Foto", fileImagePath);
        }


        request.setShouldCache(false);
        Log.d("savedata", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(this).addToRequestQueue(request);

    }
}
