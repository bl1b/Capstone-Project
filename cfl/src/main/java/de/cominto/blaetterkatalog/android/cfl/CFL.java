/*******************************************************************************
 * Copyright (c) 2016 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl;

import android.content.Context;
import android.content.Intent;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import de.cominto.blaetterkatalog.android.cfl.ioc.CFLComponent;
import de.cominto.blaetterkatalog.android.cfl.ioc.CFLModule;
import de.cominto.blaetterkatalog.android.cfl.ioc.DaggerCFLComponent;
import de.cominto.blaetterkatalog.android.cfl.model.Section;
import de.cominto.blaetterkatalog.android.cfl.realm.RealmSection;
import de.cominto.blaetterkatalog.android.cfl.ui.OverviewActivity;
import io.realm.Realm;

/**
 * Class CFL.
 * This is the interface class for the Customer Feed Library.
 * The configuration is done here and a intent is provided which will
 * be used to display the UI.
 *
 * @author Jan Grünewald (2016)
 * @version 1.0.0
 */
public class CFL {

    private final Context context;
    private final Set<Section> sections = new HashSet<>();

    public static class Builder {
        private final CFL cflInstance;
        private final Context context;

        @Inject
        Realm realm;

        public Builder(Context context) {
            this.context = context;
            cflInstance = new CFL(context);
        }

        public Builder withSection(Section section) {
            cflInstance.sections.add(section);
            return this;
        }

        public CFL build() {
            Realm.init(context.getApplicationContext());

            setupInjector();
            setupRealm();

            return cflInstance;
        }

        /**
         * Sets up the injector which can / will be used for dagger2-injections
         * throughout the library.
         */
        private void setupInjector() {
            CFLConfiguration cflConfiguration = CFLConfiguration.fromCleanStart(context, cflInstance.sections);
            Injector.createFromConfiguration(cflConfiguration, context.getApplicationContext()).getComponent().inject(this);
        }

        private void setupRealm() {
            for (final Section section : cflInstance.sections) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.createOrUpdateObjectFromJson(RealmSection.class, section.asJson());
                    }
                });
            }
            realm.close();
        }
    }

    private CFL(final Context context) {
        this.context = context;
    }

    public Intent getOverviewIntent() {
        return OverviewActivity.createStartingIntentWithData(context, null);
    }

    /**
     * This class handles the injection inside the cfl-library.
     * This encapsulates the Dagger2-Dependency Graph onto the library and separates
     * it from other Dependency-Graphs which may be present in the parent application.
     *
     * @author Jan Grünewald (2017)
     * @version 1.0.0
     */
    public static class Injector {
        private static Injector instance;

        private CFLComponent cflComponent;

        public static Injector getInstance(Context context) {
            if (instance == null) {
                restoreState(context);
            }
            return instance;
        }

        public static Injector createFromConfiguration(CFLConfiguration cflConfiguration, Context context) {
            instance = new Injector(cflConfiguration, context.getApplicationContext());
            return instance;
        }

        public static Injector restoreState(final Context context) {
            instance = new Injector(CFLConfiguration.fromRestoredState(context), context.getApplicationContext());
            return instance;
        }

        public CFLComponent getComponent() {
            return cflComponent;
        }

        private Injector(final CFLConfiguration cflConfiguration, final Context context) {
            initCFLComponent(cflConfiguration, context);
        }

        private void initCFLComponent(CFLConfiguration cflConfiguration, Context context) {
            cflComponent = DaggerCFLComponent.builder()
                    .cFLModule(new CFLModule(cflConfiguration, context))
                    .build();
        }
    }
}
