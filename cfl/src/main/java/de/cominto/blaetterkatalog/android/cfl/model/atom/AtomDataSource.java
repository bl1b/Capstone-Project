/*******************************************************************************
 * Copyright (c) 2016 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.model.atom;

import de.cominto.blaetterkatalog.android.cfl.model.DataSource;
import de.cominto.blaetterkatalog.android.cfl.model.CFLDataSourceEntry;
import de.cominto.blaetterkatalog.android.cfl.model.CFLDataSourceType;

import java.util.ArrayList;
import java.util.List;

/**
 * Class AtomDataSource.
 * This is the class representing an atom feed as data-source.
 *
 * @author Jan Grünewald (2016)
 * @version 1.0.0
 */
public class AtomDataSource implements DataSource {

    public static class Builder {
        private final AtomDataSource atomDataSource;

        public Builder(final String remoteUri) {
            atomDataSource = new AtomDataSource(remoteUri);
        }

        public AtomDataSource build() {
            return atomDataSource;
        }
    }

    private final String remoteUri;
    private final List<CFLDataSourceEntry> dataSourceEntries = new ArrayList<>();

    private AtomDataSource(String remoteUri) {
        this.remoteUri = remoteUri;
    }

    @Override
    public String getRemoteUri() {
        return remoteUri;
    }

    @Override
    public CFLDataSourceType getType() {
        return CFLDataSourceType.FEED_ATOM;
    }

    @Override
    public List<CFLDataSourceEntry> getDataSourceEntries() {
        return dataSourceEntries;
    }

    public void addDataSourceEntry(CFLDataSourceEntry dataSourceEntry) {
        dataSourceEntries.add(dataSourceEntry);
    }

    @Override
    public String asJson() {
        String jsonString = "{ ";
        jsonString += "\"remoteUri\": \"" + getRemoteUri() + "\", ";
        jsonString += "\"type\": \"" + CFLDataSourceType.FEED_ATOM.name() + "\"";
        jsonString += " }";
        return jsonString;
    }
}