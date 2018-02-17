package com.example.android.sunshine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;


public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_general);

        SharedPreferences sharedPreferences= getPreferenceScreen().getSharedPreferences();
        for(int i=0;i<getPreferenceScreen().getPreferenceCount();i++){
            Preference preference=getPreferenceScreen().getPreference(i);
            if(!(preference instanceof CheckBoxPreference)){
                preference.setSummary(sharedPreferences.getString(preference.getKey(),""));
            }
        }

    }

    void setPreferenceSummary(Preference preference,Object value){

        if(preference instanceof ListPreference){
            ListPreference listPreference= (ListPreference) preference;
            int prefIndex=listPreference.findIndexOfValue(value.toString());
            if(prefIndex>=0){
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }else{
            preference.setSummary(value.toString());
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference= findPreference(key);
        if(!(preference instanceof CheckBoxPreference)){
            setPreferenceSummary(preference,sharedPreferences.getString(key,""));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
