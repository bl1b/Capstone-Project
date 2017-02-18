/*******************************************************************************
 * Copyright (c) 2017 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.realm;

import java.io.File;

import de.cominto.blaetterkatalog.android.cfl.model.CFLSection;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Class RealmCFLSection.
 * The database handler of a Section.
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
public class RealmCFLSection extends RealmObject {
    @PrimaryKey
    private String identifier;

    private int color;

    private int nameStringRes;

    private RealmList<RealmCFLDataSource> dataSources;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getNameStringRes() {
        return nameStringRes;
    }

    public void setNameStringRes(int nameStringRes) {
        this.nameStringRes = nameStringRes;
    }

    public RealmList<RealmCFLDataSource> getDataSources() {
        return dataSources;
    }

    public void setDataSources(RealmList<RealmCFLDataSource> dataSources) {
        this.dataSources = dataSources;
    }

    public static CFLSection createDataObjectFromRealm(final RealmCFLSection realmCFLSection, File imageDir) {
        CFLSection.Builder builder = new CFLSection.Builder(realmCFLSection.getNameStringRes())
                .withColorRes(realmCFLSection.getColor())
                .withIdentifier(realmCFLSection.getIdentifier());

        for (RealmCFLDataSource realmDataSource :
                realmCFLSection.getDataSources()) {
            builder.withDataSource(RealmCFLDataSource.createDataObjectFromRealm(realmDataSource, imageDir));
        }

        return builder.build();
    }
}
