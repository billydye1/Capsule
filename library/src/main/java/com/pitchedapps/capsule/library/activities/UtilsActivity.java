package com.pitchedapps.capsule.library.activities;

import android.support.annotation.NonNull;

import com.pitchedapps.capsule.library.interfaces.CCallback;
import com.pitchedapps.capsule.library.utils.CPrefs;

/**
 * Created by Allan Wang on 2016-10-30.
 *
 * Activity containing some useful methods
 */

abstract class UtilsActivity extends ViewActivity {

    protected void onVersionUpdate(int newVersionCode, @NonNull CCallback callback) {
        CPrefs prefs = new CPrefs(this);
        if (newVersionCode > prefs.getVersionCode()) {
            callback.onResult();
        }
        prefs.setVersionCode(newVersionCode);
    }
}