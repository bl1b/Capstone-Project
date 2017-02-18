/*******************************************************************************
 * Copyright (c) 2016 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.model.atom;

import java.util.ArrayList;
import java.util.List;

import de.cominto.blaetterkatalog.android.cfl.model.CFLDataSource;
import de.cominto.blaetterkatalog.android.cfl.model.CFLDataSourceEntry;

/**
 * Class AtomDataSource.
 * This is the class representing an atom feed as data-source.
 *
 * @author Jan Grünewald (2016)
 * @version 1.0.0
 */
public class AtomDataSource implements CFLDataSource {

    private final String remoteUri;

    private CFLDataSourceType type;

    private final List<CFLDataSourceEntry> dataSourceEntries = new ArrayList<>();

    public AtomDataSource(String remoteUri) {
        this.remoteUri = remoteUri;
        setType(CFLDataSourceType.FEED_ATOM);
    }

    @Override
    public String getRemoteUri() {
        return remoteUri;
    }

    public void setType(CFLDataSourceType type) {
        this.type = type;
    }

    @Override
    public CFLDataSourceType getType() {
        return type;
    }

    @Override
    public List<CFLDataSourceEntry> getDataSourceEntries() {
        return dataSourceEntries;
    }

    public void setDataSourceEntries(List<CFLDataSourceEntry> dataSourceEntries) {
        this.dataSourceEntries.clear();
        this.dataSourceEntries.addAll(dataSourceEntries);
    }

    public void addDataSourceEntry(CFLDataSourceEntry dataSourceEntry) {
        dataSourceEntries.add(dataSourceEntry);
    }

    @Override
    public String asJson() {
        String jsonString = "{ ";
        jsonString += "\"remoteUri\": \"" + remoteUri + "\", ";
        jsonString += "\"type\": \"" + type.name() + "\"";
        jsonString += " }";
        return jsonString;
    }
}