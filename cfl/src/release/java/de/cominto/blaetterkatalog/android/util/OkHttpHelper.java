/*******************************************************************************
 * Copyright (c) 2017 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.util;

import okhttp3.OkHttpClient;

/**
 * Class OkHttpHelper.
 * Helper methods for okhttp3.
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
public class OkHttpHelper {

    public static OkHttpClient getBlankClient() {
        return new OkHttpClient.Builder()
                .build();
    }

}
