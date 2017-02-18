/*******************************************************************************
 * Copyright (c) 2017 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.model;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

import java.util.LinkedHashSet;
import java.util.Set;

import de.cominto.blaetterkatalog.android.util.LogHelper;

/**
 * Class CFLSection.
 * TODO: Add Description
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
public class CFLSection {

    private static final String TAG = LogHelper.TAG(CFLSection.class);

    /**
     * The color that will be shown as background-color for section headers.
     */
    private int color;
    /**
     * The resource-id of the string that holds the name for this section.
     */
    private final int nameStringRes;
    /**
     * The data-sources that are grouped under this section.
     */
    private final Set<DataSource> dataSources = new LinkedHashSet<>();

    /**
     * The identifier that is used to identify this section.
     */
    private String identifier;

    public static class Builder {

        private final CFLSection newSection;

        public Builder(@StringRes int stringRes) {
            newSection = new CFLSection(stringRes);
        }

        /**
         * Initiates the CFLSection with the color from the color-resource
         * id that has been provided.
         *
         * @param colorRes the color-resource id
         * @return the builder.
         */
        public Builder withColorRes(@ColorRes int colorRes) {
            newSection.color = colorRes;
            return this;
        }

        /**
         * Registers a new data-source with this section.
         *
         * @param dataSource the data-source that should be added to this section.
         * @return the builder.
         */
        public Builder withDataSource(DataSource dataSource) {
            newSection.dataSources.add(dataSource);
            return this;
        }

        /**
         * Sets the identifier for this section.
         * The identifier will be used to identify the Section against
         * the data-layer. If no identifier is provided one will be generated.
         *
         * @param identifier the identifier for this section.
         * @return the builder.
         */
        public Builder withIdentifier(String identifier) {
            newSection.identifier = identifier;
            return this;
        }

        public CFLSection build() {
            return newSection;
        }
    }

    private CFLSection(int nameStringRes) {
        this.nameStringRes = nameStringRes;
        color = 0;
        identifier = null;
    }

    public int getColor() {
        if (color != 0) {
            return color;
        }

        return 0xFFFFFFFF;
    }

    public int getNameStringRes() {
        return nameStringRes;
    }

    public String getName(Context context) {
        return context.getResources().getString(nameStringRes);
    }

    public String getIdentifier() {
        if (identifier != null && !identifier.isEmpty()) {
            return identifier;
        } else {
            return generatedIdentifier();
        }
    }

    public Set<DataSource> getDataSources() {
        return dataSources;
    }

    private String generatedIdentifier() {
        return "generated";
    }

    /**
     * This generates the json representation of this model. It will be used
     * to power the data-layer.
     *
     * @return the json-representation of this object.
     * @see de.cominto.blaetterkatalog.android.cfl.realm.RealmCFLSection
     */
    public String asJson() {
        String jsonString = "{ ";
        jsonString += "\"identifier\": \"" + getIdentifier() + "\", ";
        jsonString += "\"color\": " + getColor() + ", ";
        jsonString += "\"nameStringRes\": " + getNameStringRes() + ", ";
        jsonString += "\"dataSources\": [";

        for (DataSource source : getDataSources()) {
            jsonString += source.asJson() + ", ";
        }

        if (getDataSources().size() > 0) {
            jsonString = jsonString.substring(0, jsonString.length() - 2);
        }

        jsonString += "] }";
        return jsonString;
    }
}