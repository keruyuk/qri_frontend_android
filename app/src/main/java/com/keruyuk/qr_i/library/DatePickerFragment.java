package com.keruyuk.qr_i.library;

import java.util.Calendar;

import com.keruyuk.qr_i.RegisterActivity;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.os.Bundle;

public final class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
	
	private int y, m, d = 0;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	final Calendar c = Calendar.getInstance();
    	int year  = c.get(Calendar.YEAR);
    	int month = c.get(Calendar.MONTH);
    	int day   = c.get(Calendar.DAY_OF_MONTH);
         // Create a new instance of DatePickerDialog and return it
         return new DatePickerDialog(getActivity(), (RegisterActivity)getActivity(), year, month, day);
    }
 
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
    	y = year;
    	m = month+1;
    	d = day;
    }
    
   /*public String setDate {
	   if (y == 0 || m == 0 || d == 0) {
		   return "";
	   }
	   else {
		   return (d + "/" + m + "/" + y);
	   }
   }*/
}