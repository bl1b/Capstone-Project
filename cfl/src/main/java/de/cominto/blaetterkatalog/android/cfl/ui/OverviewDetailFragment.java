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
import de.cominto.blaetterkatalog.android.util.LogHelper;

/**
 * Class OverviewDetailFragment.
 * This fragment contains the overview of source-items. It basically
 * consists of a RecyclerView and an Adapter powering it.
 *
 * @author Jan Grünewald (2016)
 * @version 1.0.0
 */
public class OverviewDetailFragment extends Fragment {

    private static final String TAG = LogHelper.TAG(OverviewDetailFragment.class);

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_overview_detail, container, false);
        return rootView;
    }
}
