package com.example.myapplication.fragments.Setting;


import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import com.example.myapplication.R;
import android.support.v7.preference.PreferenceFragmentCompat;

public class SettingFragment extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
addPreferencesFromResource(R.xml.setting_preferences);
    }
}
