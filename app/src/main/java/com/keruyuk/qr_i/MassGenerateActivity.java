package com.keruyuk.qr_i;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.content.Intent;
import com.keruyuk.qr_i.library.IntentIntegrator;

import com.google.zxing.Result;
import com.google.zxing.MultiFormatReader;
//import com.google.zxing.client.android.CaptureActivity;



public class MassGenerateActivity extends Fragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		
        View rootView = inflater.inflate(R.layout.activity_mass_generate, container, false);
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
        return rootView;
    }
}
