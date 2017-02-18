/*******************************************************************************
 * Copyright (c) 2017 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.realm;

import de.cominto.blaetterkatalog.android.cfl.model.DataSourceEntry;
import de.cominto.blaetterkatalog.android.util.ImgHelper;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import java.io.File;
import java.util.Date;

/**
 * Class RealmDataSourceEntry.
 * This class represents the the persistence-layer for source-entries, like
 * Atom-Feeds, Facebook-Entries, Twitter-Tweets or YouTube-Videos.
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
public class RealmDataSourceEntry extends RealmObject {
    @PrimaryKey
    private String identifier;

    private Date date;
    private String title;
    private String description;
    private String content;

    public static DataSourceEntry createDataObjectFromRealm(final RealmDataSourceEntry realmDataSourceEntry, final File imageStorageDir) {

        DataSourceEntry dataSourceEntry = new DataSourceEntry() {
            @Override
            public File getOverviewIcon() {
                String filename = ImgHelper.createImageFilename(getIdentifier(), "overview");
                File imgFile = new File(imageStorageDir, filename);
                return imgFile.isFile() ? imgFile : null;
            }

            @Override
            public String getIdentifier() {
                return realmDataSourceEntry.getIdentifier();
            }

            @Override
            public Date getDate() {
                return realmDataSourceEntry.getDate();
            }

            @Override
            public String getTitle() {
                return realmDataSourceEntry.getTitle();
            }

            @Override
            public String getDescription() {
                return realmDataSourceEntry.getDescription();
            }

            @Override
            public String getContent() {
                return realmDataSourceEntry.getContent();
            }

            @Override
            public File getDetailIcon() {
                String filename = ImgHelper.createImageFilename(getIdentifier(), "detail");
                File imgFile = new File(imageStorageDir, filename);
                return imgFile.isFile() ? imgFile : null;
            }

            @Override
            public String asJson() {
                String jsonString = "{ ";

                jsonString += "\"identifier\": \"" + getIdentifier() + "\", ";
                jsonString += "\"date\": \"" + getDate().getTime() + "\", ";
                jsonString += "\"title\": \"" + getTitle() + "\", ";
                jsonString += "\"description\": \"" + getDescription() + "\", ";
                jsonString += "\"content\": \"" + getContent() + "\"";

                jsonString += " }";
                return jsonString;
            }
        };

        return dataSourceEntry;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
