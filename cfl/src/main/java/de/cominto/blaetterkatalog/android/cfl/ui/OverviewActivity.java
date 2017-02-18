/*******************************************************************************
 * Copyright (c) 2016 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.ui;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import de.cominto.blaetterkatalog.android.cfl.CFL;
import de.cominto.blaetterkatalog.android.cfl.CFLConfiguration;
import de.cominto.blaetterkatalog.android.cfl.R;
import de.cominto.blaetterkatalog.android.cfl.model.DataSource;
import de.cominto.blaetterkatalog.android.cfl.model.CFLDataSourceEntry;
import de.cominto.blaetterkatalog.android.cfl.model.CFLSection;
import de.cominto.blaetterkatalog.android.cfl.model.atom.OverviewDisplayItemProvider;
import de.cominto.blaetterkatalog.android.cfl.realm.RealmCFLSection;
import de.cominto.blaetterkatalog.android.cfl.service.CFLDataChangedListener;
import de.cominto.blaetterkatalog.android.cfl.service.CFLDataReceiver;
import de.cominto.blaetterkatalog.android.cfl.service.CFLUpdateService;
import de.cominto.blaetterkatalog.android.util.LogHelper;
import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;
import io.realm.RealmResults;
import timber.log.Timber;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Class OverviewActivity.
 * This activity shows the overview of the available items. It contains a
 * DrawerMenu on Phones and a Master/Detail-Flow on Tablets.
 *
 * @author Jan Grünewald (2016)
 * @version 1.0.0
 */
public class OverviewActivity extends AppCompatActivity implements OverviewDisplayItemProvider, CFLDataChangedListener {

    private static final String TAG = LogHelper.TAG(OverviewActivity.class);

    private static final String BUNDLE_KEY_DATA = "dataBundle";
    private static final String TAG_FRAGMENT_OVERVIEW_MASTER = "overviewMasterFragment";
    private static final String TAG_FRAGMENT_OVERVIEW_DETAIL = "overviewDetailFragment";


    private Fragment masterFragment;
    private Fragment detailFragment;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private OverviewRecyclerViewAdapter rvAdapter;

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final List<OverviewDisplayItem> overviewDisplayItems = new ArrayList<>();

    @Inject
    CFLConfiguration cflConfiguration;

    @Inject
    Realm realm;

    @Inject
    @Named("ext_image_dir")
    File imageDir;

    @Override
    public void onCFLDataChanged() {
        Timber.d("Data changed.");

        if (cflConfiguration != null && realm != null) {
            String[] activeSectionIds = new String[cflConfiguration.getActiveSectionIds().size()];
            cflConfiguration.getActiveSectionIds().toArray(activeSectionIds);

            RealmResults<RealmCFLSection> realmCFLSections = realm.where(RealmCFLSection.class)
                    .in("identifier", activeSectionIds)
                    .findAll();

            overviewDisplayItems.clear();

            for (RealmCFLSection realmCflSection :
                    realmCFLSections) {

                CFLSection section = RealmCFLSection.createDataObjectFromRealm(realmCflSection, imageDir);

                overviewDisplayItems.add(OverviewDisplayItem.createFromSection(section, OverviewActivity.this));
                Set<DataSource> dataSources = section.getDataSources();
                for (DataSource dataSource :
                        dataSources) {
                    for (CFLDataSourceEntry cflDataSourceEntry :
                            dataSource.getDataSourceEntries()) {
                        overviewDisplayItems.add(OverviewDisplayItem.createFromDataSource(cflDataSourceEntry));
                    }
                }
            }
        }

        if (rvAdapter != null) {
            rvAdapter.notifyDataSetChanged();
        }
    }

    private CFLDataReceiver dataReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        CFL.Injector.getInstance(this).getComponent().inject(this);

        dataReceiver = new CFLDataReceiver(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.overview_drawer_layout) instanceof DrawerLayout) {
            drawerLayout = (DrawerLayout) findViewById(R.id.overview_drawer_layout);
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
            drawerLayout.addDrawerListener(drawerToggle);
        }
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (drawerToggle != null) {
            drawerToggle.syncState();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overview_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuitem_force_refresh) {
            update();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void update() {
        CFLUpdateService.startUpdateAll(this);
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (dataReceiver != null) {
            registerReceiver(dataReceiver, new IntentFilter(CFLUpdateService.ACTION_UPDATE_ALL_COMPLETE));
        }

        setupMasterFragment();
        setupDetailFragment();
        onCFLDataChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (dataReceiver != null) {
            unregisterReceiver(dataReceiver);
        }

        disposables.clear();
    }

    @Override
    public List<OverviewDisplayItem> provideDisplayItems() {
        return overviewDisplayItems;
    }

    @Override
    public int getNumberOfDisplayItems() {
        return overviewDisplayItems.size();
    }

    public static Intent createStartingIntentWithData(Context context, Bundle data) {
        Intent i = new Intent(context, OverviewActivity.class);
        i.putExtra(BUNDLE_KEY_DATA, data);

        return i;
    }

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

        if (detailFragment instanceof OverviewDetailFragment) {
            ((OverviewDetailFragment) detailFragment).setLayoutManager(getLayoutManager());
            rvAdapter = new OverviewRecyclerViewAdapter(this);
            ((OverviewDetailFragment) detailFragment).setAdapter(rvAdapter);
        }
    }
}
