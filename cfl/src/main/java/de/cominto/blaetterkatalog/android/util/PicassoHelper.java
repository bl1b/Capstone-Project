/*******************************************************************************
 * Copyright (c) 2017 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.util;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

/**
 * Class PicassoHelper.
 * TODO: Add Description
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
public class PicassoHelper {

    public static Picasso getPicassoWithOkHttp3(Context context) {
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(OkHttpHelper.getBlankClient()))
                .build();
    }

}
