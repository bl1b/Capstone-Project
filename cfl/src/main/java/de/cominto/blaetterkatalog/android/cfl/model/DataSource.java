/*******************************************************************************
 * Copyright (c) 2016 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.model;

import java.util.ArrayList;
import java.util.List;

import de.cominto.blaetterkatalog.android.cfl.service.CFLDataSourceEntryProvider;

/**
 * Interface DataSource.
 * This interface describes a basic data source for the customer feed library.
 * It consists of an external URL where data is retrieved from.
 *
 * @author Jan Grünewald (2016)
 * @version 1.0.0
 */
public interface DataSource extends CFLDataSourceEntryProvider {
    DataSource EMPTY = new DataSource() {
        @Override
        public String getRemoteUri() {
            return null;
        }

        @Override
        public DataSourceType getType() {
            return DataSourceType.UNDEFINED;
        }

        @Override
        public String asJson() {
            return null;
        }

        @Override
        public List<CFLDataSourceEntry> getDataSourceEntries() {
            return new ArrayList<>(0);
        }
    };

    String getRemoteUri();

    DataSourceType getType();

    String asJson();

}
