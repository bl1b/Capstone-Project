/*******************************************************************************
 * Copyright (c) 2016 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.cominto.blaetterkatalog.android.cfl.R;

/**
 * Class OverviewMasterFragment.
 * This fragment contains the short list for available content and sections.
 * It basically consists of a ExpandableListView and the Adapter powering it.
 * On Tablets this is shown as `master` in a Master/Detail-Layout on Smartphones
 * this fragment is shown in a DrawerMenu.
 *
 * @author Jan Grünewald (2016)
 * @version 1.0.0
 */
public class OverviewMasterFragment extends Fragment {

    private View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_overview_master, container, false);
        return rootView;
    }
}
