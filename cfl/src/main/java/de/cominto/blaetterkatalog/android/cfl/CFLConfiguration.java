/*******************************************************************************
 * Copyright (c) 2017 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import de.cominto.blaetterkatalog.android.cfl.model.Section;

/**
 * Class CFLConfiguration.
 * This class wraps all the required configuration for the CFL-Library.
 * It will be freshly generated when the library is bootstrapped (e.g. the
 * {@link de.cominto.blaetterkatalog.android.cfl.CFL.Builder} is used).
 * It will be restored from the SharedPreferences when the library is
 * re-initiated (e.g. on external configuration change).
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
public class CFLConfiguration {

    private static final String CFL_SHARED_PREF_IDENTIFIER = "cflSharedPreferences";

    private static final String CFL_SHARED_PREF_IDENTIFIER_SECTION_IDS = "cflSharedPrefsSectionIds";

    private Set<String> activeSectionIds = new HashSet<>();

    /**
     * Use this method when the application/library is freshly setup
     * using the {@link de.cominto.blaetterkatalog.android.cfl.CFL.Builder}.
     *
     * @return an instance of CFLConfiguration
     */
    public static CFLConfiguration fromCleanStart(
            Context context,
            Set<Section> sections
    ) {
        SharedPreferences prefs = context.getSharedPreferences(CFL_SHARED_PREF_IDENTIFIER, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefs.edit();

        CFLConfiguration createdConfiguration = new CFLConfiguration();

        persistActiveSectionIdsToPreferences(sections, prefEditor, createdConfiguration);

        prefEditor.apply();

        return createdConfiguration;
    }

    /**
     * This function restores the configuration from a persisted state.
     *
     * @param context the context to work on
     * @return a new configuration instance.
     */
    public static CFLConfiguration fromRestoredState(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(CFL_SHARED_PREF_IDENTIFIER, Context.MODE_PRIVATE);

        CFLConfiguration newInstance = new CFLConfiguration();

        restoreActiveSectionIds(prefs, newInstance);

        return newInstance;
    }

    private CFLConfiguration() {

    }

    public Set<String> getActiveSectionIds() {
        return activeSectionIds;
    }

    private static void persistActiveSectionIdsToPreferences(Set<Section> sections, SharedPreferences.Editor prefEditor, CFLConfiguration cflConfiguration) {
        Set<String> sectionIds = new HashSet<>();
        for (Section section : sections) {
            sectionIds.add(section.getIdentifier());
        }
        prefEditor.putStringSet(CFL_SHARED_PREF_IDENTIFIER_SECTION_IDS, sectionIds);
        cflConfiguration.activeSectionIds.clear();
        cflConfiguration.activeSectionIds.addAll(sectionIds);
    }

    private static void restoreActiveSectionIds(SharedPreferences prefs, CFLConfiguration newInstance) {
        newInstance.activeSectionIds.clear();
        if (prefs.contains(CFL_SHARED_PREF_IDENTIFIER_SECTION_IDS)) {
            newInstance.activeSectionIds.addAll(prefs.getStringSet(CFL_SHARED_PREF_IDENTIFIER_SECTION_IDS, newInstance.activeSectionIds));
        }
    }
}
