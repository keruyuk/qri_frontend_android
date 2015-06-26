package com.keruyuk.qr_i;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.keruyuk.qr_i.R;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.keruyuk.qr_i.library.UserFunction;
import com.keruyuk.qr_i.library.databaseHandler;


public class LoginActivity extends ActionBarActivity {
	
	TextView t;
	Button btnLogin;
    EditText inputUsername;
    EditText inputPassword;
    TextView loginErrorMsg;
    
    private static String KEY_SUCCESS = "success";
    private static String KEY_ID = "id";
    private static String KEY_UID = "uid";
    private static String KEY_EMAIL = "email";
    private static String KEY_USERNAME = "uname";
    private static String KEY_FIRSTNAME = "fname";
    private static String KEY_LASTNAME = "lname";
    private static String KEY_PHONE = "phone";
    private static String KEY_DOB = "dob";
    private static String KEY_GENDER = "gender";
    private static String KEY_CITY = "city";
    private static String KEY_JOIN_DATE = "joinDate";
    private static String KEY_SALT = "salt";
    private static String KEY_ACTIVATED = "activated";
    private static String KEY_ERROR = "error";
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_login);
        
        inputUsername = (EditText) findViewById(R.id.edLoginUsername);
        inputPassword = (EditText) findViewById(R.id.edLoginPassword);       
        btnLogin = (Button) findViewById(R.id.btnLogin);        
        loginErrorMsg = (TextView) findViewById(R.id.loginErrorMsg);
		
		
		
		btnLogin.setOnClickListener(new View.OnClickListener() {
            
			@Override
			public void onClick(View v) {
                if (  ( !inputUsername.getText().toString().isEmpty()) && ( !inputPassword.getText().toString().isEmpty()) )
                {
                    //NetAsync(v);
                	Toast.makeText(getApplicationContext(),
                            "Access Violation at DCA347BB", Toast.LENGTH_SHORT).show();
                }
                else if ( ( !inputUsername.getText().toString().isEmpty()) )
                {
                    Toast.makeText(getApplicationContext(),
                            "Password belum diisi", Toast.LENGTH_SHORT).show();
                }
                else if ( ( !inputPassword.getText().toString().isEmpty()) )
                {
                    Toast.makeText(getApplicationContext(),
                            "Username belum diisi", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Username dan Password belum diisi", Toast.LENGTH_SHORT).show();
                }
            }
        });
		
		t = (TextView) findViewById(R.id.lblRegister);
		
		t.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(i);
				//Toast.makeText(getApplicationContext(),
                //        "Access Violation at DCA347BB", Toast.LENGTH_SHORT).show();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class NetCheck extends AsyncTask<Void, Void, Boolean>
    {
        private ProgressDialog nDialog;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(LoginActivity.this);
            nDialog.setMessage("Loading..");
            nDialog.setTitle("Checking Network");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL("http://10.127.127.1/qri_ads/project_api/");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(3000);
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {
                        return true;
                    }
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
			return false;
		}
		
		@Override
        protected void onPostExecute(Boolean th){
            if(th == true){
                nDialog.dismiss();
                new ProcessLogin().execute();
            }
            else{
                nDialog.dismiss();
                loginErrorMsg.setText("Error in Network Connection");
            }
        }
    }
	
	private class ProcessLogin extends AsyncTask<String, Object, JSONObject> {
        private ProgressDialog pDialog;
        String username,password;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            inputUsername = (EditText) findViewById(R.id.edLoginUsername);
            inputPassword = (EditText) findViewById(R.id.edLoginPassword);
            username = inputUsername.getText().toString();
            password = inputPassword.getText().toString();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Logging in ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        
		@Override
		protected JSONObject doInBackground(String... args) {
			// TODO Auto-generated method stub
			UserFunction userFunction = new UserFunction();
            JSONObject json = userFunction.loginUser(username, password);
            return json;
		}
		
		@Override
        protected void onPostExecute(JSONObject json) {
			
			SharedPreferences sharedPreferences;
			
            try {
               if (json.getString(KEY_SUCCESS) != null) {
                    String res = json.getString(KEY_SUCCESS);
                    if(Integer.parseInt(res) == 1){
                        pDialog.setMessage("Loading User Space");
                        pDialog.setTitle("Getting Data");
                        databaseHandler db = new databaseHandler(getApplicationContext());
                        JSONObject json_user = json.getJSONObject("member");
                        /**
                         * Clear all previous data in SQlite database.
                         **/
                        UserFunction logout = new UserFunction();
                        logout.logoutUser(getApplicationContext());
                        db.addUser(json_user.getString(KEY_ID), json_user.getString(KEY_UID), json_user.getString(KEY_EMAIL), json_user.getString(KEY_USERNAME), 
                        		json_user.getString(KEY_FIRSTNAME),json_user.getString(KEY_LASTNAME), json_user.getString(KEY_PHONE),
                        		json_user.getString(KEY_DOB), json_user.getString(KEY_GENDER), json_user.getString(KEY_CITY), 
                        		json_user.getString(KEY_JOIN_DATE), json_user.getString(KEY_SALT));
                       /**
                        *If JSON array details are stored in SQlite it launches the User Panel.
                        **/
                        
                        sharedPreferences = getSharedPreferences("globalPref", Context.MODE_PRIVATE);
                		Editor editor = sharedPreferences.edit();
                		editor.putBoolean("loggedIn", true);
                		editor.commit();
                		
                        Intent upanel = new Intent(getApplicationContext(), MainActivity.class);
                        upanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pDialog.dismiss();
                        startActivity(upanel);
                        /**
                         * Close Login Screen
                         **/
                        finish();
                    }else{
                        pDialog.dismiss();
                        loginErrorMsg.setText("Incorrect username/password");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
       }
	}
	
	public void NetAsync(View view){
        new NetCheck().execute();
    }
}
