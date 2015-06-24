package com.keruyuk.qr_i.library;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.content.Context;

public class UserFunction {
    private JSONParser jsonParser;
    //URL of the PHP API
    private static String loginURL = "http://10.127.127.1/qri_ads/project_api/";
    private static String registerURL = "http://10.127.127.1/qri_ads/project_api/";
    private static String forpassURL = "http://10.127.127.1/qri_ads/project_api/";
    private static String chgpassURL = "http://10.127.127.1/qri_ads/project_api/";
    private static String login_tag = "login";
    private static String register_tag = "register";
    private static String forpass_tag = "forpass";
    private static String chgpass_tag = "chgpass";
    // constructor
    public UserFunction(){
        jsonParser = new JSONParser();
    }
    /**
     * Function to Login
     **/
    public JSONObject loginUser(String username, String password){
        // Building Parameters
        List params = new ArrayList();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        return json;
    }
    /**
     * Function to change password
     **/
    public JSONObject chgPass(String newpas, String email){
        List params = new ArrayList();
        params.add(new BasicNameValuePair("tag", chgpass_tag));
        params.add(new BasicNameValuePair("newpas", newpas));
        params.add(new BasicNameValuePair("email", email));
        JSONObject json = jsonParser.getJSONFromUrl(chgpassURL, params);
        return json;
    }
    /**
     * Function to reset the password
     **/
    public JSONObject forPass(String forgotpassword){
        List params = new ArrayList();
        params.add(new BasicNameValuePair("tag", forpass_tag));
        params.add(new BasicNameValuePair("forgotpassword", forgotpassword));
        JSONObject json = jsonParser.getJSONFromUrl(forpassURL, params);
        return json;
    }
     /**
      * Function to  Register
      **/
    public JSONObject registerUser(String email, String uname, String fname, String lname, String phone,   
    		String dob, String gender, String city, String password){
        // Building Parameters
        List params = new ArrayList();
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("uname", uname));
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("fname", fname));
        params.add(new BasicNameValuePair("lname", lname));
        params.add(new BasicNameValuePair("phone", phone));
        params.add(new BasicNameValuePair("dob", dob));
        params.add(new BasicNameValuePair("gender", gender));
        params.add(new BasicNameValuePair("city", city));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(registerURL,params);
        return json;
    }
    /**
     * Function to logout user
     * Resets the temporary data stored in SQLite Database
     * */
    public boolean logoutUser(Context context){
        databaseHandler db = new databaseHandler(context);
        db.resetTables();
        return true;
    }
    
    public String truncate(final String content, final int lastIndex) {
        String result = content.substring(0, lastIndex);
        if (content.charAt(lastIndex) != ' ') {
            result = result.substring(0, result.lastIndexOf(" "));
        }
        return result;
    }
}