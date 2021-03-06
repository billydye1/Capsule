package ca.allanwang.capsule.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;

import org.greenrobot.eventbus.Subscribe;

import ca.allanwang.capsule.library.activities.CapsuleActivityFrame;
import ca.allanwang.capsule.library.changelog.ChangelogDialog;
import ca.allanwang.capsule.library.event.CClickEvent;
import ca.allanwang.capsule.library.event.SnackbarEvent;
import ca.allanwang.capsule.library.interfaces.CDrawerItem;
import ca.allanwang.capsule.library.item.DrawerItem;
import ca.allanwang.capsule.library.logging.CLog;
import ca.allanwang.capsule.sample.fragments.FragmentSample;
import ca.allanwang.capsule.sample.fragments.FragmentSampleNoFab;
import ca.allanwang.capsule.sample.fragments.RippleFragment;
import ca.allanwang.capsule.sample.fragments.SampleSwipeRecyclerFragment;
import ca.allanwang.capsule.sample.fragments.ViewPagerFragmentSample;
import ca.allanwang.capsule.sample.helpers.SamplePrefs;

/**
 * Created by Allan Wang on 2016-08-21.
 */
public class MainActivity extends CapsuleActivityFrame {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        cCoordinatorLayout.setScrollAllowed(false);
        addCollapsingToolbarView(R.layout.toolbar_view);
//        new CustomizeToolbar().setHeight(70);
        new CustomizeToolbar().hideTitleOnExpand().setHeight(200).withClickEvents(true);
        collapseAppBar(false);
        onVersionUpdate(BuildConfig.VERSION_CODE, () -> ChangelogDialog.show(MainActivity.this, R.xml.changelog));
    }

    @Subscribe
    public void onCClick(CClickEvent event) {
        if (event.view == cToolbar)
            snackbar(new SnackbarEvent("Toolbar clicked", Snackbar.LENGTH_SHORT));
    }

    @Override
    public void switchFragment(Fragment fragment) {
        if (fragment instanceof SampleSwipeRecyclerFragment) expandAppBar();
        else collapseAppBar();
        super.switchFragment(fragment);
    }

    /**
     * @return desired header
     * Sets up account header
     * will not be added if null
     */
    @Nullable
    @Override
    protected AccountHeaderBuilder getAccountHeaderBuilder() {
        return new AccountHeaderBuilder().withActivity(this)
                .withHeaderBackground(R.color.colorPrimary)
                .withSelectionFirstLine(s(R.string.app_name))
                .withSelectionSecondLine(BuildConfig.VERSION_NAME)
                .withProfileImagesClickable(false)
                .withResetDrawerOnProfileListClick(false)
                .addProfiles(
                        new ProfileDrawerItem().withIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                )
                .withSelectionListEnabled(false)
                .withSelectionListEnabledForSingleProfile(false);
    }

    /**
     * Sets up array of drawer items
     *
     * @return array of drawer items
     */
    @Override
    protected CDrawerItem[] getDrawerItems() {
        return new CDrawerItem[]{
                new DrawerItem(R.string.home, GoogleMaterial.Icon.gmd_dashboard, true, FragmentSample::new),
                new DrawerItem(R.string.viewpager, GoogleMaterial.Icon.gmd_view_column, true, ViewPagerFragmentSample::new),
                new DrawerItem(R.string.refresh_recycler, GoogleMaterial.Icon.gmd_refresh, true, SampleSwipeRecyclerFragment::new),
                new DrawerItem(R.string.basic_with_fab, GoogleMaterial.Icon.gmd_domain, true, FragmentSample::new),
                new DrawerItem(R.string.basic_no_fab, GoogleMaterial.Icon.gmd_extension, true, FragmentSampleNoFab::new),
                new DrawerItem(R.string.ripple, GoogleMaterial.Icon.gmd_blur_circular, true, RippleFragment::new)
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_changelog) {
            ChangelogDialog.show(this, R.xml.changelog, null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int switchTheme() {
        if (new SamplePrefs(this).switchTheme()) {
            setTheme(R.style.CapsuleAppTheme);
            return R.style.CapsuleAppTheme;
        } else {
            setTheme(R.style.AppTheme_Dark);
            return R.style.AppTheme_Dark;
        }
    }
}
