package com.everysoft.cardockspeakerphone;

import com.everysoft.cardockspeakerphone.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class CarDockSpeakerphonePreferenceActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
}
