package com.example.android.popularmovies.settings;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.android.popularmovies.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        // Add Preferences.
        addPreferencesFromResource(R.xml.preferences);
    }

}
