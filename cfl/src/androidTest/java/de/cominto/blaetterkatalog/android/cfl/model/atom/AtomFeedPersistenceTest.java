/*******************************************************************************
 * Copyright (c) 2016 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.model.atom;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedInputStream;
import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Class AtomFeedRequestTest.
 * This test-classes tests Atom-Feed requests with Retrofit and the parsed
 * results.
 *
 * @author Jan Grünewald (2016)
 * @version 1.0.0
 */
@RunWith(AndroidJUnit4.class)
public class AtomFeedPersistenceTest {

    private Context instrumentationContext;

    private MockWebServer mockWebServer;


    @Before
    public void setupRealmDb() {
        // fetch the instrumentation context
        instrumentationContext = InstrumentationRegistry.getInstrumentation().getContext();


        // setup the realm instance
//        Realm.init(instrumentationContext);
//        realm = Realm.getDefaultInstance();
//
//        // remove existing data
//        Realm.deleteRealm(realm.getConfiguration());
    }


    @Before
    public void startServer() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @After
    public void endServer() throws Exception {
        if (mockWebServer != null) {
            mockWebServer.shutdown();
        }
    }

    @Test
    public void testFeedRequest() throws Exception {
        // this doesn't need to be tested separately because it's unit-tested
        final AtomFeed atomFeed = setupAtomFeed();
        assertThat(atomFeed, notNullValue());
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.createObjectFromJson(RealmAtomFeed.class, atomFeed.asJSON());
//            }
//        });
    }

    private AtomFeed setupAtomFeed() throws Exception {
        // setup the xml-response for the fake requests
        MockResponse mockResponse = new MockResponse()
                .setResponseCode(200)
                .setBody(getGoogleFeedResponse());
        mockWebServer.enqueue(mockResponse);

        // setup the service we want to test
        HttpUrl baseUrl = mockWebServer.url("/google_feed/");
        Retrofit retrofit = AtomFeedHelper.retrofitWithBaseUrl(baseUrl);
        AtomFeedService feedService = retrofit.create(AtomFeedService.class);
//        Call<AtomFeed> serviceCall = feedService.fetchFeed();

//        Response<AtomFeed> feedResponse = serviceCall.execute();
//
//        assertThat("Service call could not be executed.", feedResponse, notNullValue());
//        AtomFeed atomFeed = feedResponse.body();
//        assertThat(atomFeed, notNullValue());
//        return atomFeed;

        return null;
    }

    private String getGoogleFeedResponse() throws Exception {
        return getFileContentAsString("google_feed.xml");
    }

    private String getFileContentAsString(String filename) throws IOException {
        BufferedInputStream in = new BufferedInputStream(instrumentationContext.getResources().getAssets().open(filename));

        byte[] buffer = new byte[1024];
        int bytesRead = 0;

        String fileContent = "";

        while ((bytesRead = in.read(buffer)) != -1) {
            fileContent += new String(buffer, 0, bytesRead);
        }

        return fileContent;
    }
}
