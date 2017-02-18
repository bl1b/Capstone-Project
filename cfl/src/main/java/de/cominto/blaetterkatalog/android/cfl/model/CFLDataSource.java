/*******************************************************************************
 * Copyright (c) 2016 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.model;

import java.util.ArrayList;
import java.util.List;

import de.cominto.blaetterkatalog.android.cfl.service.CFLDataSourceEntryProvider;

/**
 * Interface CFLDataSource.
 * This interface describes a basic data source for the customer feed library.
 * It consists of an external URL where data is retrieved from.
 *
 * @author Jan Grünewald (2016)
 * @version 1.0.0
 */
public interface CFLDataSource extends CFLDataSourceEntryProvider {
    CFLDataSource EMPTY = new CFLDataSource() {
        @Override
        public String getRemoteUri() {
            return null;
        }

        @Override
        public CFLDataSourceType getType() {
            return CFLDataSourceType.UNDEFINED;
        }

        @Override
        public String asJson() {
            return null;
        }

        @Override
        public List<CFLDataSourceEntry> getDataSourceEntries() {
            return new ArrayList<>(0);
        }
    };

    String getRemoteUri();

    CFLDataSourceType getType();

    String asJson();

    /**
     * Describes the available types of sources where data
     * can be retrieve from.
     */
    enum CFLDataSourceType {
        FEED_ATOM, FEED_RSS2, FACEBOOK_PAGE, TWITTER_TIMELINE, YOUTUBE_CHANNEL, UNDEFINED;
    }
}
