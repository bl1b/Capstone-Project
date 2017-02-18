/*******************************************************************************
 * Copyright (c) 2017 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.model;

import android.graphics.drawable.Drawable;

import java.io.File;
import java.util.Date;

/**
 * Interface CFLDataSourceEntry.
 * TODO: Add Description
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
public interface CFLDataSourceEntry {
    File getOverviewIcon();

    String getIdentifier();

    Date getDate();

    String getTitle();

    String getDescription();

    String getContent();

    File getDetailIcon();

    String asJson();
}
