/*******************************************************************************
 * Copyright (c) 2016 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import de.cominto.blaetterkatalog.android.cfl.R;
import de.cominto.blaetterkatalog.android.util.LogHelper;

/**
 * Class OverviewActivity.
 * This activity shows the overview of the available items. It contains a
 * DrawerMenu on Phones and a Master/Detail-Flow on Tablets.
 *
 * @author Jan Grünewald (2016)
 * @version 1.0.0
 */
public class OverviewActivity extends AppCompatActivity {

    private static final String TAG = LogHelper.TAG(OverviewActivity.class);

    private static final String BUNDLE_KEY_DATA = "dataBundle";
    private static final String TAG_FRAGMENT_OVERVIEW_MASTER = "overviewMasterFragment";
    private static final String TAG_FRAGMENT_OVERVIEW_DETAIL = "overviewDetailFragment";


    private Fragment masterFragment;
    private Fragment detailFragment;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    /**
     * Creates a new instance of the master-fragment if none exists.
     * Adds the master-fragment to its container.
     */
    private void setupMasterFragment() {
        masterFragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_OVERVIEW_MASTER);
        if (masterFragment == null) {
            masterFragment = new OverviewMasterFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.overview_master_content, masterFragment, TAG_FRAGMENT_OVERVIEW_MASTER)
                    .commit();
        }
    }

    /**
     * Creates a new instance of the detail-fragment if none exists.
     * Adds the detail-fragment to its container.
     */
    private void setupDetailFragment() {
        detailFragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_OVERVIEW_DETAIL);
        if (detailFragment == null) {
            detailFragment = new OverviewDetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.overview_detail_content, detailFragment, TAG_FRAGMENT_OVERVIEW_DETAIL)
                    .commit();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.overview_drawer_layout) instanceof DrawerLayout) {
            drawerLayout = (DrawerLayout) findViewById(R.id.overview_drawer_layout);
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
            drawerLayout.addDrawerListener(drawerToggle);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (drawerToggle != null) {
            drawerToggle.syncState();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        setupMasterFragment();
        setupDetailFragment();
    }

    public static Intent createStartingIntentWithData(Context context, Bundle data) {
        Intent i = new Intent(context, OverviewActivity.class);
        i.putExtra(BUNDLE_KEY_DATA, data);

        return i;
    }
}
