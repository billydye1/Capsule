package com.pitchedapps.capsule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.pitchedapps.capsule.library.event.CFabEvent;
import com.pitchedapps.capsule.library.event.SnackbarEvent;
import com.pitchedapps.capsule.library.fragments.CapsuleFragment;
import com.pitchedapps.capsule.library.utils.ColourUtils;

/**
 * Created by Allan Wang on 2016-08-21.
 */
public class FragmentSample extends CapsuleFragment {

    @Override
    public int getTitleId() {
        return R.string.sample_fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_sample, container, false);
        FrameLayout frame = (FrameLayout) v.findViewById(R.id.fragment_main);
        frame.setBackgroundColor(ColourUtils.randomLightColor());
//        AnimUtils.rootCircularReview(v);
        v.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar(new SnackbarEvent("TEST"));
            }
        });
        return v;
    }

    @Nullable
    @Override
    protected CFabEvent updateFab() {
        return new CFabEvent(GoogleMaterial.Icon.gmd_3d_rotation, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar(new SnackbarEvent("Hi"));
            }
        });
    }
}
