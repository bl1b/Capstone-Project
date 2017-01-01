/*******************************************************************************
 * Copyright (c) 2016 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.model.atom;

import okhttp3.HttpUrl;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Class AtomFeedHelper.
 * Static helper-methods for atom-feed related operations.
 *
 * @author Jan Grünewald (2016)
 * @version 1.0.0
 */
public class AtomFeedHelper {

    public static Retrofit retrofitWithBaseUrl(String baseUrl) {
        return new Retrofit.Builder()
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .baseUrl(baseUrl)
                .validateEagerly(true)
                .build();
    }

    public static Retrofit retrofitWithBaseUrl(HttpUrl baseUrl) {
        return new Retrofit.Builder()
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }
}
