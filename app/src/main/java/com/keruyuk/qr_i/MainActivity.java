package com.keruyuk.qr_i;

import java.util.HashMap;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Window;

import com.keruyuk.qr_i.library.Contents;
import com.keruyuk.qr_i.library.Crypto;
import com.keruyuk.qr_i.library.QRCodeEncoder;
import com.keruyuk.qr_i.library.UserFunction;
import com.keruyuk.qr_i.library.databaseHandler;


public class MainActivity extends ActionBarActivity {

    SharedPreferences sharedPreferences;
    String encString;
    String strDay;
    String strMonth;
    String salt;
    String uniqueId;
    String memberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHandler db = new databaseHandler(getApplicationContext());
        HashMap member = new HashMap();
        member = db.getMemberDetails();
        if (member.isEmpty()) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            finish();
        }
        else {
            salt = (String) member.get("salt");
            uniqueId = (String) member.get("uid");
            //UserFunction functions = new UserFunction();
            uniqueId = uniqueId.substring(0, 16);
            memberId = (String) member.get("id");
        }

        sharedPreferences = getSharedPreferences("globalPref", Context.MODE_PRIVATE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
