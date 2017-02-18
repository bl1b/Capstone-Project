/*******************************************************************************
 * Copyright (c) 2017 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.model.atom;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import de.cominto.blaetterkatalog.android.cfl.model.DataSource;
import de.cominto.blaetterkatalog.android.util.OkHttpHelper;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Class AtomFeedRequester.
 * TODO: Add Description
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
public class AtomFeedRequester {

    private final DataSource dataSource;

    public AtomFeedRequester(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public AtomFeed requestAtomFeedSynchronously() {
        OkHttpClient client = OkHttpHelper.getBlankClient();

        Request feedRequest = new Request.Builder()
                .url(dataSource.getRemoteUri())
                .get()
                .build();

        Call atomCall = client.newCall(feedRequest);
        AtomFeed result = new AtomFeed();

        try {
            Response feedResponse = atomCall.execute();
            if (feedResponse.isSuccessful()) {
                Serializer serializer = new Persister();
                result = serializer.read(AtomFeed.class, feedResponse.body().charStream());
            }
        } catch (Exception e) {
            Timber.e(e, "Error requesting atom-feed.");
            result = new AtomFeed();
        }

        return result;
    }
}
