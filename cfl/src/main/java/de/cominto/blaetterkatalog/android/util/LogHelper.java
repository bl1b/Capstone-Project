/*******************************************************************************
 * Copyright (c) 2016 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.util;

import android.util.Log;

import de.cominto.blaetterkatalog.android.cfl.BuildConfig;

/**
 * Class LogHelper.
 * Various helper methods for logging.
 *
 * @author Jan Grünewald (2016)
 * @version 1.0.0
 */
public class LogHelper {

    static final int MAX_LOG_TAG_LENGTH = 23;

    /**
     * This method creates a log-tag from the class-name of the class that wants
     * to log. Log-tags should not be longer than 23 characters.
     *
     * @param loggingClass the class the log-tag should be created for
     * @return the log-tag.
     */
    public static String TAG(Class loggingClass) {
        return loggingClass.getSimpleName().substring(0, Math.min(loggingClass.getSimpleName().length(), MAX_LOG_TAG_LENGTH));
    }
}
