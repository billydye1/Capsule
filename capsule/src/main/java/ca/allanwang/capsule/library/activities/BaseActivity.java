package ca.allanwang.capsule.library.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

import java.util.Locale;

import ca.allanwang.capsule.library.interfaces.CActivityCore;
import ca.allanwang.capsule.library.logging.CLogTree;
import ca.allanwang.capsule.library.logging.CallbackLogTree;
import ca.allanwang.capsule.library.utils.EventUtils;
import timber.log.Timber;

/**
 * Created by Allan Wang on 2016-08-21.
 * <p>
 * The core's core
 */
abstract class BaseActivity extends AppCompatActivity implements CActivityCore {

    private CLogTree cTree;

    protected static String s(@NonNull Context context, @StringRes int id) {
        return context.getString(id);
    }

    /**
     * onCreate for capsule
     *
     * @param savedInstanceState
     */
    @CallSuper
    protected void capsuleOnCreate(Bundle savedInstanceState) {
        enableCLog();
    }

    /**
     * If you need to skip Capsule's initialization for later
     *
     * @param savedInstanceState
     */
    protected void preCapsuleOnCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected String s(@StringRes int id) {
        if (id == 0) return "placeholder";
        return getString(id);
    }

    protected String sf(@StringRes int id, Object... o) {
        return String.format(Locale.CANADA, s(id), o);
    }

    protected void plantDebugLog(boolean isDebug, CallbackLogTree tree) {
        if (isDebug) Timber.plant(tree);
    }

    protected void enableCLog() {
        if (cTree == null) {
            cTree = new CLogTree();
            Timber.plant(cTree);
        }
    }

    protected void disableCLog() {
        if (cTree != null) {
            Timber.uproot(cTree);
            cTree = null;
        }
    }

    protected void postEvent(Object event) {
        EventUtils.post(event);
    }

    public void reload() {
        finish();
        overridePendingTransition(0, 0); //No transitions
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}
