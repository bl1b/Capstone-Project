/*******************************************************************************
 * Copyright (c) 2017 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.service;

import java.util.ArrayList;
import java.util.List;

import de.cominto.blaetterkatalog.android.cfl.model.CFLDataSourceEntry;

/**
 * Class CFLDataSourceEntryProvider.
 * TODO: Add Description
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
public interface CFLDataSourceEntryProvider {
    List<CFLDataSourceEntry> getDataSourceEntries();

    CFLDataSourceEntryProvider EMPTY = new CFLDataSourceEntryProvider() {
        @Override
        public List<CFLDataSourceEntry> getDataSourceEntries() {
            return new ArrayList<>(0);
        }
    };
}
