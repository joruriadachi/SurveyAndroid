package com.project.untag.survey1.API;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ikko on 05/05/2018.
 */

public class Global {
    public static final String BASE_URL = "http://192.168.43.224/SurveyAPI/";
    public static final String JSON_META = "Meta";
    public static final String PARAM_MESSAGE_CODE = "Code";
    public static final String JSON_MESSAGE = "Message";
    public static final String JSON_DATA = "Data";


    public static final String ADD_SURVEY = BASE_URL+"/survey/savesurvey";
    public static final String ADD_NEW_SURVEY = BASE_URL+"/survey/savenewsurvey";
    public static final String GET_DATA_SURVEY = BASE_URL+"/survey/getdatasurvey";
    public static final String GET_DATA_WILAYAH = BASE_URL+"/survey/getwilayah";
    public static final String GET_TOTAL_DATA = BASE_URL+"/survey/gettotalwilayah";


    public static boolean isResponseSuccess(JSONObject jsonResponse) {
        try {
            if (jsonResponse.getJSONObject(JSON_META).getInt(PARAM_MESSAGE_CODE) == 200) {
                return true;
            }
        } catch (JSONException je) {
            je.printStackTrace();
            return false;
        }
        return false;
    }

    public static final String getResponseMessage(JSONObject jsonResponse) {
        try {
            return jsonResponse.getJSONObject(JSON_META).getString(JSON_MESSAGE);
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return "Parsing failed";
    }
}
