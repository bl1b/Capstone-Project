/*******************************************************************************
 * Copyright (c) 2017 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.realm;

import de.cominto.blaetterkatalog.android.cfl.model.DataSource;
import de.cominto.blaetterkatalog.android.cfl.model.DataSourceType;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import timber.log.Timber;

import java.io.File;

/**
 * Class RealmDataSource.
 * Data-Layer representation of a data-source (like Feed, YouTube-API,
 * Facebook-API, Twitter, API).
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
public class RealmDataSource extends RealmObject {
    @PrimaryKey
    private String remoteUri;

    private String type;

    private RealmList<RealmDataSourceEntry> sourceEntries;

    public String getRemoteUri() {
        return remoteUri;
    }

    public String getType() {
        return type;
    }

    public RealmList<RealmDataSourceEntry> getSourceEntries() {
        return sourceEntries;
    }

    public static DataSource createDataObjectFromRealm(final RealmDataSource realmDataSource, File imageStorageDir) {
        try {
            DataSource source = new DataSource.Builder(realmDataSource.getRemoteUri(), DataSourceType.valueOf(realmDataSource.getType())).build();
            for (RealmDataSourceEntry realmDataSourceEntry :
                    realmDataSource.getSourceEntries()) {
                source.addDataSourceEntry(RealmDataSourceEntry.createDataObjectFromRealm(realmDataSourceEntry, imageStorageDir));
            }
            return source;
        } catch (IllegalArgumentException e) {
            Timber.e(e, "Tried to create dataSourceType from illegal realmCFLDataSourceType: '" + realmDataSource.getType() + "'.");
            return DataSource.EMPTY;
        }
    }

    public boolean containsIdentifier(String identifier) {
        if (getSourceEntries() == null) {
            return false;
        }

        for (RealmDataSourceEntry entry :
                getSourceEntries()) {
            if (entry.getIdentifier().equals(identifier)) {
                return true;
            }
        }

        return false;
    }

    public static RealmDataSource createRealmFromDataObject(final DataSource dataSource, final Realm realm) {
        return realm.where(RealmDataSource.class)
                .equalTo("remoteUri", dataSource.getRemoteUri())
                .findFirst();
    }
}