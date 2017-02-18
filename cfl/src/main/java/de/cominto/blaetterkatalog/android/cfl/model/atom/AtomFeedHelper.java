/*******************************************************************************
 * Copyright (c) 2016 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.model.atom;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
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
        baseUrl += baseUrl.endsWith("/") ? "" : "/";
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(createAtomConverterFactory())
                .baseUrl(baseUrl)
                .validateEagerly(true)
                .build();
    }

    public static Retrofit retrofitWithBaseUrl(HttpUrl baseUrl) {
        String newLink = baseUrl.toString().endsWith("/") ? baseUrl.toString() : baseUrl.toString() + "/";
        baseUrl = baseUrl.newBuilder(newLink).build();
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(createAtomConverterFactory())
                .baseUrl(baseUrl)
                .build();
    }

    private static Converter.Factory createAtomConverterFactory() {
        return SimpleXmlConverterFactory.create();
    }
}
