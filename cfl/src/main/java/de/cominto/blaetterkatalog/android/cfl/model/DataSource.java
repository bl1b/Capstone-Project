/*******************************************************************************
 * Copyright (c) 2016 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface DataSource.
 * This interface describes a basic data source for the customer feed library.
 * It consists of an external URL where data is retrieved from.
 *
 * @author Jan Grünewald (2016)
 * @version 1.0.0
 */
public class DataSource implements DataSourceEntryProvider {
    public static final DataSource EMPTY = new DataSource(null, DataSourceType.UNDEFINED) {
        @Override
        public String asJson() {
            return null;
        }

        @Override
        public List<DataSourceEntry> getDataSourceEntries() {
            return new ArrayList<>(0);
        }
    };

    private final String remoteUri;
    private final DataSourceType dataSourceType;

    private final List<DataSourceEntry> dataSourceEntries = new ArrayList<>();

    private DataSource(final String remoteUri, final DataSourceType dataSourceType) {
        this.remoteUri = remoteUri;
        this.dataSourceType = dataSourceType;
    }

    public void addDataSourceEntry(DataSourceEntry dataObjectFromRealm) {
        dataSourceEntries.add(dataObjectFromRealm);
    }

    public static class Builder {
        private final DataSource dataSource;

        public Builder(final String remoteUri, final DataSourceType dataSourceType) {
            dataSource = new DataSource(remoteUri, dataSourceType);
        }

        public DataSource build() {
            return dataSource;
        }
    }

    public String getRemoteUri() {
        return remoteUri;
    }

    public DataSourceType getType() {
        return dataSourceType;
    }

    public String asJson() {
        String jsonString = "{ ";
        jsonString += "\"remoteUri\": \"" + getRemoteUri() + "\", ";
        jsonString += "\"type\": \"" + getType().name() + "\"";
        jsonString += " }";
        return jsonString;
    }

    @Override
    public List<DataSourceEntry> getDataSourceEntries() {
        return dataSourceEntries;
    }
}
