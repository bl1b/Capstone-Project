/*******************************************************************************
 * Copyright (c) 2016 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.model.atom;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Interface AtomFeedService.
 * This is the retrofit-request service which is used to retrieve atom feeds
 * from external sources. Each AtomFeedService should use an own
 * {@link retrofit2.Retrofit}-Instance since the base-url will most likely
 * differ in each case.
 *
 * @author Jan Grünewald (2016)
 * @version 1.0.0
 */
public interface AtomFeedService {
    @GET("/")
    Observable<AtomFeed> fetchFeed();

    @GET("/{path}")
    Observable<AtomFeed> fetchFeedWithAdditionalPath(@Path("path") String path);
}
