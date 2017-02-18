/*******************************************************************************
 * Copyright (c) 2017 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.ioc;

import android.content.Context;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.cominto.blaetterkatalog.android.cfl.CFLConfiguration;
import de.cominto.blaetterkatalog.android.cfl.service.atom.AtomService;
import io.realm.Realm;

/**
 * Class CFLAtomModule.
 * Dependency-Management for AtomFeed-related operations.
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
@Module
public class CFLAtomModule {

    @Provides
    @Singleton
    AtomService provideAtomService(CFLConfiguration configuration, Realm realm, Context context, @Named("ext_image_dir") File imageDir) {
        return new AtomService(configuration, realm, context, imageDir);
    }
}
