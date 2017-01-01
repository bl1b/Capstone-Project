/*******************************************************************************
 * Copyright (c) 2016 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.model.atom;

import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.simpleframework.xml.core.ValueRequiredException;

import java.io.BufferedInputStream;
import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.fail;

/**
 * Class AtomFeedParserTest.
 * This class tests the behaviour of the atom feed parser.
 *
 * @author Jan Grünewald (2016)
 * @version 1.0.0
 */
public class AtomFeedParserTest {
    private MockWebServer mockWebServer;

    @Before
    public void startServer() {
        mockWebServer = new MockWebServer();
        try {
            mockWebServer.start();
        } catch (IOException e) {
            fail("Could not start mockWebServer.");
        }
    }

    @After
    public void endServer() {
        if (mockWebServer != null) {
            try {
                mockWebServer.shutdown();
            } catch (IOException e) {
                Log.w("AtomFeedParserTest", "could not shutdown mockWebServer", e);
            }
        }
    }

    @Test
    public void testParseCompletion() throws Exception {
        MockResponse mockResponse = new MockResponse()
                .setResponseCode(200)
                .setBody(getFileContentAsString("/google_feed.xml"));

        mockWebServer.enqueue(mockResponse);

        // setup the service we want to test
        Call<AtomFeed> serviceCall = createAtomFeedCall("/google_feed/");

        Response<AtomFeed> response = serviceCall.execute();
        assertThat("Service call could not be executed.", response, notNullValue());
        assertThat("Response should show succesful", response.isSuccessful(), equalTo(true));

        AtomFeed atomFeed = response.body();
        assertThat("Feed should not be null", atomFeed, notNullValue());

        // assert that properties that are not contained in the xml are not null
        assertThat("No null value should be returned.", atomFeed.getGenerator(), notNullValue());
        assertThat("No null value should be returned.", atomFeed.getId(), notNullValue());
        assertThat("No null value should be returned.", atomFeed.getTitle(), notNullValue());
        assertThat("No null value should be returned.", atomFeed.getRights(), notNullValue());
        assertThat("No null value should be returned.", atomFeed.getUpdated(), notNullValue());
        assertThat("No null value should be returned.", atomFeed.getCategories(), notNullValue());
        assertThat("No null value should be returned.", atomFeed.getContributors(), notNullValue());
        assertThat("No null value should be returned.", atomFeed.getIcon(), notNullValue());
        assertThat("No null value should be returned.", atomFeed.getSubtitle(), notNullValue());
        assertThat("No null value should be returned.", atomFeed.getLinks(), notNullValue());
        assertThat("No null value should be returned.", atomFeed.getAuthors(), notNullValue());
        assertThat("No null value should be returned.", atomFeed.getEntries(), notNullValue());

        assertThat("Parsed generator not correct.", atomFeed.getGenerator().getText(), equalTo("NFE/1.0"));
        assertThat("Parsed id not correct.", atomFeed.getId(), equalTo("tag:news.google.de,2016-12-31:/1/de/de/1483184185317"));
        assertThat("Parsed title not correct.", atomFeed.getTitle().getText(), equalTo("Google News: Schlagzeilen"));
        assertThat("Right could not be parsed.", atomFeed.getRights().getText(), equalTo("&copy;2016 Google"));
        assertThat("Update-Date could not be parsed.", atomFeed.getUpdated(), equalTo("2016-12-31T10:03:35Z"));

        // check if links could be parsed
        assertThat("Number of links wrong.", atomFeed.getLinks().size(), equalTo(2));
        assertThat("Rel of link not set.", atomFeed.getLinks().get(0).getRel(), equalTo("self"));
        assertThat("Type of link not set.", atomFeed.getLinks().get(0).getType(), equalTo("application/atom+xml"));
        assertThat("Href of link not set.", atomFeed.getLinks().get(0).getHref(), equalTo("http://news.google.de/news?hl=de&pz=1&ned=de&output=atom"));

        // check if authors could be parsed
        assertThat("Number of authors should at least be 1.", atomFeed.getAuthors().size(), greaterThanOrEqualTo(1));
        assertThat("Name of author not correct.", atomFeed.getAuthors().get(0).getName(), equalTo("Google Inc."));
        assertThat("E-Mail could not be parsed from author-tag.", atomFeed.getAuthors().get(0).getEmail(), equalTo("news-feedback@google.com"));

        // check if entries could be parsed
        assertThat("Parsed entries number not correct.", atomFeed.getEntries().size(), equalTo(10));
        AtomFeedEntry testEntry = atomFeed.getEntries().get(0);
        // check if not null
        assertThat("No null value should be returned.", testEntry.getLinks(), notNullValue());
        assertThat("No null value should be returned.", testEntry.getAuthors(), notNullValue());
        assertThat("No null value should be returned.", testEntry.getCategories(), notNullValue());
        assertThat("No null value should be returned.", testEntry.getContent(), notNullValue());
        assertThat("No null value should be returned.", testEntry.getContributors(), notNullValue());
        assertThat("No null value should be returned.", testEntry.getId(), notNullValue());
        assertThat("No null value should be returned.", testEntry.getPublished(), notNullValue());
        assertThat("No null value should be returned.", testEntry.getSummary(), notNullValue());
        assertThat("No null value should be returned.", testEntry.getTitle(), notNullValue());
        assertThat("No null value should be returned.", testEntry.getUpdated(), notNullValue());
        // check values
        assertThat("Id of entry could not be parsed.", testEntry.getId(), equalTo("tag:news.google.com,2005:cluster=52780198972940"));
        assertThat("Title could not be parsed", testEntry.getTitle().getType(), equalTo("html"));
        assertThat("Title could not be parsed", testEntry.getTitle().getText(), equalTo("KNAPP 3 MONATE NACH DEM TOD DES XXL-OSTFRIESEN | Hanken-Witwe kracht mit ... - BILD"));
        assertThat("Category could not be parsed", testEntry.getCategories().size(), equalTo(1));
        assertThat("Category could not be parsed", testEntry.getCategories().get(0).getTerm(), equalTo("Schlagzeilen"));
        assertThat("Updated could not be parsed", testEntry.getUpdated(), equalTo("2016-12-31T10:22:49Z"));
        assertThat("Link could not be parsed", testEntry.getLinks().size(), equalTo(1));
        assertThat("Link could not be parsed", testEntry.getLinks().get(0).getRel(), equalTo("alternate"));
        assertThat("Link could not be parsed", testEntry.getLinks().get(0).getType(), equalTo("text/html"));
        assertThat("Link could not be parsed", testEntry.getLinks().get(0).getHref(), equalTo("http://news.google.com/news/url?sa=t&fd=R&ct2=de&usg=AFQjCNGVWQB0E7YzlC6sdD0Pvly46dvwIw&clid=c3a7d30bb8a4878e06b80cf16b898331&cid=52780198972940&ei=OZhnWLC6EeGA6ATnqK2QDw&url=http://www.bild.de/regional/bremen/tamme-hanken/witwe-carmen-verunfallt-mit-pferdeanhaenger-49545358.bild.html"));
        assertThat("Link could not be parsed", testEntry.getLinks().get(0).getHreflang(), equalTo("de"));
        assertThat("Content could not be parsed", testEntry.getContent().getType(), equalTo("html"));
        assertThat("Content could not be parsed", testEntry.getContent().getText(), equalTo("<table border=\"0\" cellpadding=\"2\" cellspacing=\"7\" style=\"vertical-align:top;\"><tr><td width=\"80\" align=\"center\" valign=\"top\"><font style=\"font-size:85%;font-family:arial,sans-serif\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;ct2=de&amp;usg=AFQjCNGVWQB0E7YzlC6sdD0Pvly46dvwIw&amp;clid=c3a7d30bb8a4878e06b80cf16b898331&amp;cid=52780198972940&amp;ei=OZhnWLC6EeGA6ATnqK2QDw&amp;url=http://www.bild.de/regional/bremen/tamme-hanken/witwe-carmen-verunfallt-mit-pferdeanhaenger-49545358.bild.html\"><img src=\"//t2.gstatic.com/images?q=tbn:ANd9GcScQWbX_c9oRGNE_lLbGWivGp_iYp8op78BXxeFdM3_6UixwF9SQidRhx1rPoV6n1v5nRvzHIwi\" alt=\"\" border=\"1\" width=\"80\" height=\"80\"><br><font size=\"-2\">BILD</font></a></font></td><td valign=\"top\" class=\"j\"><font style=\"font-size:85%;font-family:arial,sans-serif\"><br><div style=\"padding-top:0.8em;\"><img alt=\"\" height=\"1\" width=\"1\"></div><div class=\"lh\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;ct2=de&amp;usg=AFQjCNGVWQB0E7YzlC6sdD0Pvly46dvwIw&amp;clid=c3a7d30bb8a4878e06b80cf16b898331&amp;cid=52780198972940&amp;ei=OZhnWLC6EeGA6ATnqK2QDw&amp;url=http://www.bild.de/regional/bremen/tamme-hanken/witwe-carmen-verunfallt-mit-pferdeanhaenger-49545358.bild.html\"><b>KNAPP 3 MONATE NACH DEM TOD DES XXL-OSTFRIESEN | Hanken-Witwe kracht mit <b>...</b></b></a><br><font size=\"-1\"><b><font color=\"#6f6f6f\">BILD</font></b></font><br><font size=\"-1\">Eine unerwartete Tragödie riss Carmen Hanken (56) aus dem gewohnten Leben – jetzt ein weiterer Schicksalsschlag für die Familie des XXL-Ostfriesen Tamme Hanken († 56). Jemgum (Niedersachsen) – Am Samstagmorgen krachte die Witwe mit einem&nbsp;...</font><br><font size=\"-1\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;ct2=de&amp;usg=AFQjCNErGWuGP2KtBo-6fuvASKK7ZIiydw&amp;clid=c3a7d30bb8a4878e06b80cf16b898331&amp;cid=52780198972940&amp;ei=OZhnWLC6EeGA6ATnqK2QDw&amp;url=http://www.gala.de/stars/news/carmen-hanken-verkehrsunfall-mit-totalschaden_1589016.html\">Carmen Hanken: Verkehrsunfall mit Totalschaden</a><font size=\"-1\" color=\"#6f6f6f\"><nobr>Gala.de</nobr></font></font><br><font size=\"-1\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;ct2=de&amp;usg=AFQjCNE3JQtwRkpxtv3EP0CmvovXEOE2fg&amp;clid=c3a7d30bb8a4878e06b80cf16b898331&amp;cid=52780198972940&amp;ei=OZhnWLC6EeGA6ATnqK2QDw&amp;url=http://www.derwesten.de/panorama/tamme-hankens-witwe-nach-unfall-in-krankenhaus-gebracht-id209133875.html\">Kollision: Schwerer Verkehrsunfall mit dem Pferdetransporter - Tamme Hankens <b>...</b></a><font size=\"-1\" color=\"#6f6f6f\"><nobr>Derwesten.de</nobr></font></font><br><font size=\"-1\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;ct2=de&amp;usg=AFQjCNFf7gkfAPYV2VFq8o49BpKQqV3F4Q&amp;clid=c3a7d30bb8a4878e06b80cf16b898331&amp;cid=52780198972940&amp;ei=OZhnWLC6EeGA6ATnqK2QDw&amp;url=http://www.noz.de/deutschland-welt/niedersachsen/artikel/828800/witwe-des-xxl-ostfriesen-tamme-hanken-bei-unfall-verletzt\">Witwe des XXL-Ostfriesen Tamme Hanken bei Unfall verletzt</a><font size=\"-1\" color=\"#6f6f6f\"><nobr>NOZ - Neue Osnabrücker Zeitung</nobr></font></font><br><font size=\"-1\" class=\"p\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;ct2=de&amp;usg=AFQjCNHVynIng31WMF9q79EM7t7iNxrddQ&amp;clid=c3a7d30bb8a4878e06b80cf16b898331&amp;cid=52780198972940&amp;ei=OZhnWLC6EeGA6ATnqK2QDw&amp;url=https://www.merkur.de/tv/tamme-hankens-witwe-carmen-faehrt-mit-pferdetransporter-gegen-baum-7183689.html\"><nobr>Merkur.de</nobr></a>&nbsp;-<a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;ct2=de&amp;usg=AFQjCNGpvl4adyAYXEw-aq41aFm41tpnVg&amp;clid=c3a7d30bb8a4878e06b80cf16b898331&amp;cid=52780198972940&amp;ei=OZhnWLC6EeGA6ATnqK2QDw&amp;url=http://www.huffingtonpost.de/2016/12/31/carmen-hanken-unfall_n_13908620.html\"><nobr>Huffington Post Deutschland</nobr></a>&nbsp;-<a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;ct2=de&amp;usg=AFQjCNEf8XSRh_dvu69yjnRry_BHpBRSrQ&amp;clid=c3a7d30bb8a4878e06b80cf16b898331&amp;cid=52780198972940&amp;ei=OZhnWLC6EeGA6ATnqK2QDw&amp;url=http://www.nwzonline.de/blaulicht/witwe-von-xxl-ostfriese-tamme-hanken-bei-unfall-verletzt_a_31,2,580215013.html\"><nobr>Nordwest-Zeitung</nobr></a>&nbsp;-<a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;ct2=de&amp;usg=AFQjCNEE4_VszweBGjKrCL9-qqbcDgmGLw&amp;clid=c3a7d30bb8a4878e06b80cf16b898331&amp;cid=52780198972940&amp;ei=OZhnWLC6EeGA6ATnqK2QDw&amp;url=http://www.tvmovie.de/news/tamme-hankens-witwe-carmen-schwerer-unfall-mit-pferdetransporter-91401\"><nobr>TV Movie</nobr></a></font><br><font class=\"p\" size=\"-1\"><a class=\"p\" href=\"http://news.google.de/news/story?ncl=drq3Vwuwekb0GTMAHzZtF9mpLgilM&amp;ned=de&amp;topic=h\"><nobr><b>Alle 17 Artikel&nbsp;&raquo;</b></nobr></a></font></div></font></td></tr></table>"));

        mockWebServer.takeRequest();
    }

    @Test
    public void testHttpError() throws Exception {
        MockResponse mockResponse = new MockResponse()
                .setResponseCode(404);

        mockWebServer.enqueue(mockResponse);

        Call<AtomFeed> serviceCall = createAtomFeedCall("/google_feed_not_found/");

        Response<AtomFeed> response = serviceCall.execute();
        AtomFeed atomFeed = response.body();

        assertThat("Feed should be null because of 404 status code.", atomFeed, nullValue());
        assertThat("Response code should be 404", response.code(), equalTo(404));
        assertThat("isSuccessful() on response should return false", response.isSuccessful(), equalTo(false));
    }

    @Test
    public void testMissingRequiredElement() throws Exception {
        MockResponse mockResponse = new MockResponse()
                .setResponseCode(200)
                .setBody(getFileContentAsString("/feed_with_missing_element.xml"));

        mockWebServer.enqueue(mockResponse);

        // setup the service we want to test
        Call<AtomFeed> serviceCall = createAtomFeedCall("/feed_with_missing_element/");

        boolean runTimeExceptionWasCaught = false;

        try {
            Response<AtomFeed> response = serviceCall.execute();
            assertThat(response.isSuccessful(), equalTo(false));
        } catch (RuntimeException e) {
            assertThat(e.getCause(), instanceOf(ValueRequiredException.class));
            runTimeExceptionWasCaught = true;
        } finally {
            assertThat(runTimeExceptionWasCaught, equalTo(true));
        }
    }

    @Test
    public void testMissingRequiredAttribute() throws Exception {
        MockResponse mockResponse = new MockResponse()
                .setResponseCode(200)
                .setBody(getFileContentAsString("/feed_with_missing_attribute.xml"));

        mockWebServer.enqueue(mockResponse);

        // setup the service we want to test
        Call<AtomFeed> serviceCall = createAtomFeedCall("/feed_with_missing_attribute/");

        boolean runTimeExceptionWasCaught = false;
        try {
            Response<AtomFeed> response = serviceCall.execute();
            assertThat(response.isSuccessful(), equalTo(false));
        } catch (RuntimeException e) {
            assertThat(e.getCause(), instanceOf(ValueRequiredException.class));
            runTimeExceptionWasCaught = true;
        } finally {
            assertThat(runTimeExceptionWasCaught, equalTo(true));
        }
    }

    private Call<AtomFeed> createAtomFeedCall(String mockUrl) {
        HttpUrl baseUrl = mockWebServer.url(mockUrl);
        Retrofit retrofit = AtomFeedHelper.retrofitWithBaseUrl(baseUrl);
        AtomFeedService feedService = retrofit.create(AtomFeedService.class);

        return feedService.fetchFeed();
    }

    private String getFileContentAsString(String filename) throws IOException {
        BufferedInputStream in = new BufferedInputStream(this.getClass().getResourceAsStream(filename));

        byte[] buffer = new byte[1024];
        int bytesRead;

        String fileContent = "";

        while ((bytesRead = in.read(buffer)) != -1) {
            fileContent += new String(buffer, 0, bytesRead);
        }

        return fileContent;
    }
}
