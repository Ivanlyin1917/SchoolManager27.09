package com.example.myapplication.fragments.Setting;


import android.content.SharedPreferences;
import android.os.Bundle;


import android.preference.PreferenceFragment;
import com.example.myapplication.R;


import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

public class SettingFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.setting_preferences);
        SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
        sp.registerOnSharedPreferenceChangeListener(this);
        PreferenceScreen ps =getPreferenceScreen();
        int count= ps.getPreferenceCount();
        for(int i=0; i<count; i++){
         Preference preference=ps.getPreference(i);
         if(preference instanceof EditTextPreference){
             String number= sp.getString(preference.getKey(),"");
             setPreferenceLable(preference,number);

         }
        }
    }
    private void setPreferenceLable(Preference preference, String value){
        if(preference instanceof EditTextPreference){
            preference.setSummary(value);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference= findPreference(key);
        if(preference instanceof EditTextPreference){
            String number= sharedPreferences.getString(preference.getKey(),"");
            setPreferenceLable(preference,number);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
