/*******************************************************************************
 * Copyright (c) 2017 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.ioc;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.cominto.blaetterkatalog.android.cfl.CFLConfiguration;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Class CFLModule.
 * TODO: Add Description
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
@Module
public class CFLModule {

    private final CFLConfiguration cflConfiguration;
    private final Context context;

    public CFLModule(CFLConfiguration cflConfiguration, Context context) {
        this.cflConfiguration = cflConfiguration;
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    CFLConfiguration provideCFLConfiguration() {
        return cflConfiguration;
    }

    @Provides
    @Singleton
    @Named("cfl_realm_config")
    RealmConfiguration provideRealmConfiguration() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .modules(new de.cominto.blaetterkatalog.android.cfl.realm.CFLModule())
                .deleteRealmIfMigrationNeeded()
                .build();

        return config;
    }

    @Provides
    Realm provideRealm(@Named("cfl_realm_config") RealmConfiguration configuration) {
        return Realm.getInstance(configuration);
    }

    @Named("ext_storage_dir")
    @Provides
    public File provideExternalStoragePlace() {
        File externalStoragePlace = null;

        if (context != null) {
            try {
                // this is the recommended way and should return the files-directory for the application's files
                // the externalFilesDir already contains the application-package
                externalStoragePlace = context.getExternalFilesDir(null);
                boolean isExtFilesDirValid = externalStoragePlace != null && (externalStoragePlace.isDirectory() || externalStoragePlace.mkdirs());

                if (!isExtFilesDirValid) {
                    // on Nexus 5 (Android L) getExternalStorageDirectory returns "/storage/emulated/0"
                    // this directory does not exist on the device even though File.isDirectory() and File.exists() on that directory returns true
                    // instead there is a directory "/storage/emulated/legacy" so we should test for this directory first
                    externalStoragePlace = Environment.getExternalStorageDirectory();
                    if (externalStoragePlace != null) {
                        File legacyExtStorageDir = new File(externalStoragePlace.getCanonicalPath().replace("/0", "/legacy") + "/Android/data/" + context.getPackageName() + "/files/");
                        if (legacyExtStorageDir.exists() && legacyExtStorageDir.isDirectory()) {
                            externalStoragePlace = legacyExtStorageDir;
                        } else if (externalStoragePlace.isDirectory()) {
                            externalStoragePlace = new File(externalStoragePlace.getCanonicalPath() + "/Android/data/" + context.getPackageName() + "/files/");
                        }
                    }
                }
            } catch (IOException e) {
                Timber.e(e, "Can't resolve external storage Directory.");
                externalStoragePlace = null;
            }
        }

        return externalStoragePlace;
    }

    @Named("ext_image_dir")
    @Provides
    public File provideImageStoragePlace(@Named("ext_storage_dir") File storageDir) {
        File imageDir = new File(storageDir, "images");
        if (imageDir.isDirectory() || imageDir.mkdirs()) {
            return imageDir;
        } else {
            Timber.e("Can't create image-directory.");
            return null;
        }
    }
}
