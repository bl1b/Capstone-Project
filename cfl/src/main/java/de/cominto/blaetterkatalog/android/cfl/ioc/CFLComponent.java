/*******************************************************************************
 * Copyright (c) 2017 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.ioc;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import de.cominto.blaetterkatalog.android.cfl.CFL;
import de.cominto.blaetterkatalog.android.cfl.service.CFLUpdateService;
import de.cominto.blaetterkatalog.android.cfl.ui.OverviewActivity;

/**
 * Interface CFLComponent.
 * TODO: Add Description
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
@Singleton
@Component(modules = {
        CFLModule.class,
        CFLAtomModule.class
})
public interface CFLComponent {
    void inject(OverviewActivity overviewActivity);

    void inject(CFL.Builder builder);

    void inject(CFLUpdateService cflUpdateService);

    Context context();
}
