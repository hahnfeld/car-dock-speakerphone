package com.everysoft.cardockspeakerphone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;

public class CarDockSpeakerphoneBroadcastReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String intent_action = intent.getAction();
		if (intent_action.equals(Intent.ACTION_DOCK_EVENT)) {
			int dock_state = intent.getIntExtra(Intent.EXTRA_DOCK_STATE, 0);
			switch (dock_state) {
			case Intent.EXTRA_DOCK_STATE_CAR:
				setSpeakerphoneOn(context, true);
				setDockedStatus(context, true);
				break;
			case Intent.EXTRA_DOCK_STATE_UNDOCKED:
				setSpeakerphoneOn(context, false);
				setDockedStatus(context, false);
				break;
			}
		}
		else if (intent_action.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
			String phone_state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
			if (phone_state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK) && getDockedStatus(context)) {
				setSpeakerphoneOn(context, true);
			}
		}
	}
	
	void setSpeakerphoneOn(Context context, boolean on) {
		AudioManager audio_manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		if (!audio_manager.isBluetoothA2dpOn() && !audio_manager.isBluetoothScoOn() && !audio_manager.isWiredHeadsetOn()) {
			audio_manager.setSpeakerphoneOn(on);
		}
	}
	
	void setDockedStatus(Context context, boolean docked) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor pref_edit = prefs.edit();
		pref_edit.putBoolean("docked", docked);
		pref_edit.commit();
	}

	boolean getDockedStatus(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getBoolean("docked", false);
	}
}
