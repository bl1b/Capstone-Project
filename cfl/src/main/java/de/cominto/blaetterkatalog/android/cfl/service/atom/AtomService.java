/*******************************************************************************
 * Copyright (c) 2017 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.service.atom;

import android.content.Context;
import android.graphics.Bitmap;
import de.cominto.blaetterkatalog.android.cfl.CFLConfiguration;
import de.cominto.blaetterkatalog.android.cfl.model.CFLDataSource;
import de.cominto.blaetterkatalog.android.cfl.model.CFLDataSourceEntry;
import de.cominto.blaetterkatalog.android.cfl.model.atom.AtomFeed;
import de.cominto.blaetterkatalog.android.cfl.model.atom.AtomFeedRequester;
import de.cominto.blaetterkatalog.android.cfl.realm.RealmCFLDataSource;
import de.cominto.blaetterkatalog.android.cfl.realm.RealmCFLDataSourceEntry;
import de.cominto.blaetterkatalog.android.cfl.realm.RealmCFLSection;
import de.cominto.blaetterkatalog.android.util.ImgHelper;
import de.cominto.blaetterkatalog.android.util.PicassoHelper;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.internal.Util;
import timber.log.Timber;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class AtomService.
 * Service and utility methods around atom feeds.
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
public class AtomService {

    private final CFLConfiguration configuration;
    private final Realm realm;
    private Context context;

    private final File imageDir;

    public AtomService(CFLConfiguration configuration, Realm realm, Context context, File imageDir) {
        this.configuration = configuration;
        this.realm = realm;
        this.context = context;
        this.imageDir = imageDir;
    }

    public void updateAllActive() {
        Set<CFLDataSource> activeDataSources = getActiveDataSources();
        for (CFLDataSource activeDataSource :
                activeDataSources) {
            updateDataSource(activeDataSource);
        }
    }

    public void updateDataSource(CFLDataSource dataSource) {
        AtomFeedRequester request = new AtomFeedRequester(dataSource);
        final AtomFeed atomFeed = request.requestAtomFeedSynchronously();

        // download the overview image
        downloadImage(atomFeed.getIcon(), atomFeed.getId(), "overview");

        final RealmCFLDataSource parentDataSource = RealmCFLDataSource.createRealmFromDataObject(dataSource, realm);

        realm.beginTransaction();

        for (final CFLDataSourceEntry entry : atomFeed.getDataSourceEntries()) {

            Timber.d("Parsed from uri '%s': '%s'.", dataSource.getRemoteUri(), entry.getIdentifier());

            RealmCFLDataSourceEntry updateOrInserted =
                    realm.createOrUpdateObjectFromJson(RealmCFLDataSourceEntry.class, entry.asJson());

            if (!parentDataSource.containsIdentifier(entry.getIdentifier())) {
                parentDataSource.getSourceEntries().add(updateOrInserted);
            }

            // download the detail image
            downloadImage(parseImageUrlFromContent(entry.getContent()), entry.getIdentifier(), "detail");
        }

        realm.commitTransaction();
    }

    private void downloadImage(String source, String fileIdentifier, String type) {
        FileOutputStream fos = null;
        try {
            Bitmap loadedBitmap = PicassoHelper.getPicassoWithOkHttp3(context)
                    .load(source)
                    .resize(800, 0)
                    .onlyScaleDown()
                    .get();

            fos = new FileOutputStream(ImgHelper.createImageFile(imageDir, fileIdentifier, type));
            loadedBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (IOException e) {
            Timber.e(e, "Unable to download image: '" + e.getMessage() + "'.");
        } finally {
            Util.closeQuietly(fos);
        }

    }


    private String parseImageUrlFromContent(String content) {
        if (content != null && !content.isEmpty()) {
            Pattern imgPattern = Pattern.compile("<img.*?src=\"(.*?)\".*?>");
            Matcher imgMatcher = imgPattern.matcher(content);
            if (imgMatcher.find()) {
                return imgMatcher.group(1);
            }
        }
        return "";
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private Set<CFLDataSource> getActiveDataSources() {
        Set<CFLDataSource> active = new HashSet<>();
        String typeName = CFLDataSource.CFLDataSourceType.FEED_ATOM.name();

        for (String activeSectionId :
                configuration.getActiveSectionIds()) {

            RealmResults<RealmCFLSection> sections = realm
                    .where(RealmCFLSection.class)
                    .equalTo("identifier", activeSectionId)
                    .equalTo("dataSources.type", typeName)
                    .findAll();

            for (RealmCFLSection matchingSection :
                    sections) {
                for (RealmCFLDataSource dataSource :
                        matchingSection.getDataSources()) {
                    active.add(RealmCFLDataSource.createDataObjectFromRealm(dataSource, imageDir));
                }
            }
        }

        return active;
    }


}