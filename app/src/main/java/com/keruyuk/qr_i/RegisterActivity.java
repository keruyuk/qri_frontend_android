package com.keruyuk.qr_i;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;

import com.keruyuk.qr_i.R;

import org.json.JSONException;
import org.json.JSONObject;

import com.keruyuk.qr_i.library.DatePickerFragment;
import com.keruyuk.qr_i.library.UserFunction;
import com.keruyuk.qr_i.library.databaseHandler;

import android.widget.DatePicker;

public class RegisterActivity extends FragmentActivity implements OnDateSetListener {
	private static String KEY_SUCCESS = "success";
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
    private static String KEY_ERROR = "error";
    
    private String dob;
    private Boolean confirm = false;
    
    EditText inputEmail;
    EditText inputUsername;
    EditText inputPassword;
    EditText inputRePassword;
    EditText inputFirstName;
    EditText inputLastName;
    EditText inputPhone;
    EditText inputDob;
    EditText inputGender;
    EditText inputCity;
    Button btnRegister;
    TextView registerErrorMsg;
    Spinner spinCity;
    
    final Context context = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//getActionBar().setIcon(R.drawable.qri_login_small);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		spinCity = (Spinner) findViewById(R.id.spinCity);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,  R.array.city, android.R.layout.simple_expandable_list_item_1);
		spinCity.setAdapter(adapter);
		
		spinCity.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int pos, long arg3) {
				// TODO Auto-generated method stub
				inputCity.setText(parent.getSelectedItem().toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				inputCity.setText("");
			}
		});
		
		inputEmail = (EditText) findViewById(R.id.edEmail);
		//textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
		//	    getResources().getDimension(R.dimen.textsize));
		inputEmail.setTextSize(TypedValue.COMPLEX_UNIT_PX, 
				getResources().getDimension(R.dimen.textsize_small));
		inputUsername = (EditText) findViewById(R.id.edUsername);
		inputUsername.setTextSize(TypedValue.COMPLEX_UNIT_PX, 
				getResources().getDimension(R.dimen.textsize_small));
		inputPassword = (EditText) findViewById(R.id.edPassword);
		inputPassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, 
				getResources().getDimension(R.dimen.textsize_small));
		inputRePassword = (EditText) findViewById(R.id.edRePassword);
		inputRePassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, 
				getResources().getDimension(R.dimen.textsize_small));
		inputFirstName = (EditText) findViewById(R.id.edFirstName);
		inputFirstName.setTextSize(TypedValue.COMPLEX_UNIT_PX, 
				getResources().getDimension(R.dimen.textsize_small));
		inputLastName = (EditText) findViewById(R.id.edLastName);
		inputLastName.setTextSize(TypedValue.COMPLEX_UNIT_PX, 
				getResources().getDimension(R.dimen.textsize_small));
		inputPhone = (EditText) findViewById(R.id.edPhone);
		inputPhone.setTextSize(TypedValue.COMPLEX_UNIT_PX, 
				getResources().getDimension(R.dimen.textsize_small));
		inputCity = (EditText) findViewById(R.id.edHidenCity);
		inputCity.setTextSize(TypedValue.COMPLEX_UNIT_PX, 
				getResources().getDimension(R.dimen.textsize_small));
		inputGender = (EditText) findViewById(R.id.edHidenGender);
		inputGender.setTextSize(TypedValue.COMPLEX_UNIT_PX, 
				getResources().getDimension(R.dimen.textsize_small));
		btnRegister = (Button) findViewById(R.id.btnRegister);
		inputDob = (EditText) findViewById(R.id.edDob);
		registerErrorMsg = (EditText) findViewById(R.id.registerErrorMsg);
		
		View.OnTouchListener Spinner_OnTouch = new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_UP) {
					showDatePickerDialog(v);
				}
				return true;
			}
		};
		
		View.OnKeyListener Spinner_OnKey = new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
					showDatePickerDialog(v);
					return true;
				}
				else {
					return false;
				}
			}
		};
		
		inputDob.setOnTouchListener(Spinner_OnTouch);
		inputDob.setOnKeyListener(Spinner_OnKey);
		
		
		
		btnRegister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(final View v) {
				// TODO Auto-generated method stub
				if (( !inputUsername.getText().toString().equals("")) && 
						( !inputPassword.getText().toString().equals("")) && 
						( !inputFirstName.getText().toString().equals("")) && 
						( !inputLastName.getText().toString().equals("")) && 
						( !inputEmail.getText().toString().equals("")) &&
						( !inputDob.getText().toString().equals("")) &&
						( !inputPhone.getText().toString().equals("")) &&
						( !inputGender.getText().toString().equals("")) &&
						( inputPassword.getText().toString().equals(inputPassword.getText().toString()))) 
				{
					if (( inputUsername.getText().toString().length() > 4 ) && 
							( inputPassword.getText().toString().length() > 5 ) &&
							( inputPassword.getText().toString().equals(inputPassword.getText().toString()))) 
					{
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
						alertDialogBuilder.setTitle("QR-i - Konfirnasi");
						alertDialogBuilder
							.setMessage("Apakah Data Yang Anda Masukkan Sudah Benar?")
							.setCancelable(false)
							.setPositiveButton("Ya",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// if this button is clicked, close
									// current activity
									NetAsync(v);
									
								}
							 })
							.setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									confirm = false;
									dialog.cancel();
								}
							});
						AlertDialog alertDialog = alertDialogBuilder.create();

						alertDialog.show();
						
                    }
                    else {
                        if (inputUsername.getText().toString().length() < 5 ) {
                        	Toast.makeText(getApplicationContext(),
                        			"Panjang Username minimal 5 karakter", Toast.LENGTH_SHORT).show();
                        	inputUsername.setFocusable(true);
                        	inputUsername.requestFocus();
                        }
                        else if ( inputPassword.getText().toString().length() < 6 ) {
                        	Toast.makeText(getApplicationContext(),
                        			"Panjang Password minimal 6 karakter", Toast.LENGTH_LONG).show();
                        	inputPassword.setFocusable(true);
                        	inputPassword.requestFocus();
                        }
                        else if (!inputPassword.getText().toString().equals(inputPassword.getText().toString())) {
                        	Toast.makeText(getApplicationContext(),
                        			"Password yang anda masukkan tidak sama, silakan ulangi kembali", Toast.LENGTH_LONG).show();
                        	inputPassword.setFocusable(true);
                        	inputPassword.requestFocus();
                        }
                    }
					
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Data yang dimasukkan belum lengkap", Toast.LENGTH_SHORT).show();
                }
			}
		});
	}

	@Override 
	public boolean onPrepareOptionsMenu(Menu menu) {
	    MenuItem settingsItem = menu.findItem(R.id.action_settings);
	    settingsItem.setVisible(false);
	    return false; 
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
	
	public void onRBGenderClicked(View view) {
		Boolean checked = ((RadioButton) view).isChecked();
		switch(view.getId()) {
		
		case R.id.radioMale:
			if (checked) {
				inputGender.setText("L");
				break;
			}
				
		case R.id.radioFemale:
			if (checked) {
				inputGender.setText("P");
				break;
			}				
		}
		
	}
	
	public void showDatePickerDialog(View view) {
			    
		DatePickerFragment newFragment = new DatePickerFragment();
	    newFragment.show(this.getFragmentManager(), "datePicker");
	    
	}
	
	public void onDateSet(DatePicker view, int year, int month, int day) {
		month = month + 1;
		String strMonth = null;
		String strDay = null;
		
		if (month < 10) {
			strMonth = "0" + Integer.toString(month);
			
		}
		else {
			strMonth = Integer.toString(month);
		}
		
		if (day < 10) {
			strDay = "0" + Integer.toString(day);
		}
		else {
			strDay = Integer.toString(day);
		}
	    ((EditText) findViewById(R.id.edDob)).setText(strDay + "/" + strMonth + "/" + year);
	}
	
	
	public void NetAsync(View view){
        new NetCheck().execute();
    }
	
	
	private class NetCheck extends AsyncTask<Void, Void, Boolean> {

		private ProgressDialog nDialog;
	  	    
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
	        nDialog = new ProgressDialog(RegisterActivity.this);
	        nDialog.setMessage("Loading..");
	        nDialog.setTitle("Checking Network");
	        nDialog.setIndeterminate(false);
	        nDialog.setCancelable(true);
	        nDialog.show();
		};

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			boolean result;
			
			result = false;
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL("http://10.127.127.1/qri_ads/project_api/");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(3000);
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {
                        result = true;
                    }
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return result;
		};
		
		@Override
		protected void onPostExecute(Boolean th) {
			if(th == true){
	        	nDialog.dismiss();
	            new ProcessRegister().execute();
	        }
	        else{
	            nDialog.dismiss();
	            //registerErrorMsg.setText("Error in Network Connection");
	            Toast.makeText(getApplicationContext(),
            			"Koneksi gagal, periksa kembali koneksi anda", Toast.LENGTH_SHORT).show();
	        }
		}
		
	}
	
	private class ProcessRegister extends AsyncTask<String, Object, JSONObject> {
		
		private ProgressDialog pDialog;
        String email,password,fname,lname,uname, phone, dob, gender, city;
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            inputUsername = (EditText) findViewById(R.id.edUsername);
            inputPassword = (EditText) findViewById(R.id.edPassword);
            
            fname = inputFirstName.getText().toString();
            lname = inputLastName.getText().toString();
            email = inputEmail.getText().toString();
            uname= inputUsername.getText().toString();
            password = inputPassword.getText().toString();
            phone = inputPhone.getText().toString();
            city = inputCity.getText().toString();
            gender = inputGender.getText().toString();
            dob = inputDob.getText().toString();
            Date dobDate = null;
			try {
				dobDate = new SimpleDateFormat("dd/MM/yyyy").parse(dob);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dob = new SimpleDateFormat("yyyy-MM-dd").format(dobDate);
            
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setTitle("Contacting Server");
            pDialog.setMessage("Registering ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

		@Override
		protected JSONObject doInBackground(String... arg) {
			// TODO Auto-generated method stub
			UserFunction userFunction = new UserFunction();
	        JSONObject json = userFunction.registerUser(email, uname, fname, lname, phone, dob, gender, city, password);
	        return json;
		}
		
		@Override
		protected void onPostExecute(JSONObject json) {
			/**
			* Checks for success message.
			**/
			try {
				if (json.getString(KEY_SUCCESS) != null) {
				    registerErrorMsg.setText("");
				    String res = json.getString(KEY_SUCCESS);
				    String red = json.getString(KEY_ERROR);
				    if(Integer.parseInt(res) == 1){
					    pDialog.setTitle("Getting Data");
					    pDialog.setMessage("Loading Info");
					    registerErrorMsg.setText("Successfully Registered");
					    databaseHandler db = new databaseHandler(getApplicationContext());
					    JSONObject json_user = json.getJSONObject("user");
					    /**
					    * Removes all the previous data in the SQlite database
					    **/
					    //UserFunction logout = new UserFunction();
					    //logout.logoutUser(getApplicationContext());
					    /*db.addUser(json_user.getString(KEY_UID), json_user.getString(KEY_EMAIL), json_user.getString(KEY_USERNAME), json_user.getString(KEY_FIRSTNAME),
					    		json_user.getString(KEY_LASTNAME), json_user.getString(KEY_PHONE), json_user.getString(KEY_DOB), json_user.getString(KEY_GENDER), json_user.getString(KEY_CITY), 
					    		json_user.getString(KEY_JOIN_DATE));
					    /**
					    * Stores registered data in SQlite Database
					    * Launch Registered screen
					    
					    Intent registered = new Intent(getApplicationContext(), RegisterActivity.class);
					    /**
					    * Close all views before launching Registered screen
					    
					    registered.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					    startActivity(registered);
					    finish();*/
					    
					    pDialog.dismiss();
					    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
						alertDialogBuilder.setTitle("QR-i - Informasi");
						alertDialogBuilder
							.setMessage("Registrasi Berhasil, Silakan Login Untuk Mengaktifkan Akun Anda")
							.setCancelable(false)
							.setNeutralButton("OK", new DialogInterface.OnClickListener () {

								@Override
								public void onClick(DialogInterface dialog,int id) {
									// TODO Auto-generated method stub
									/*Intent login = new Intent(getApplicationContext(), LoginActivity.class);
									login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(login);*/
									finish();
								}
								
							});
						AlertDialog alertDialog = alertDialogBuilder.create();

						alertDialog.show();
				    }
					    else if (Integer.parseInt(red) ==2){
					    	pDialog.dismiss();
					        registerErrorMsg.setText("User already exists");
					    }
					    else if (Integer.parseInt(red) ==3){
					        pDialog.dismiss();
					        registerErrorMsg.setText("Invalid Email id");
					    }
					}
			        else {
			        	pDialog.dismiss();
			            registerErrorMsg.setText("Error occured in registration");
			        }
			} 
			catch (JSONException e) {
				e.printStackTrace();
			}
		}

		public void NetAsync(View view){
			new NetCheck().execute();
		}
	
	
	
	
	}
}

