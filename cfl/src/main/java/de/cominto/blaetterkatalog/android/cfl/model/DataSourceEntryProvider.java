/*******************************************************************************
 * Copyright (c) 2017 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class DataSourceEntryProvider.
 * TODO: Add Description
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
public interface DataSourceEntryProvider {
    List<DataSourceEntry> getDataSourceEntries();

    DataSourceEntryProvider EMPTY = new DataSourceEntryProvider() {
        @Override
        public List<DataSourceEntry> getDataSourceEntries() {
            return new ArrayList<>(0);
        }
    };
}
