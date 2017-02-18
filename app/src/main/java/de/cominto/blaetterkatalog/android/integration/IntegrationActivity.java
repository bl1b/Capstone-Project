/*******************************************************************************
 * Copyright (c) 2016 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.integration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import de.cominto.blaetterkatalog.android.cfl.CFL;
import de.cominto.blaetterkatalog.android.cfl.model.CFLSection;
import de.cominto.blaetterkatalog.android.cfl.model.atom.AtomDataSource;

/**
 * Class IntegrationActivity.
 * This is just an integration example of the CFL. This activity does nothing
 * more then configuring the library and starting the intent.
 *
 * @author Jan Grünewald (2016)
 * @version 1.0.0
 */
public class IntegrationActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CFL customerFeedLibrary = new CFL.Builder(this)
                .withSection(new CFLSection.Builder(R.string.section_name_theverge)
                        .withColorRes(R.color.section_color_technews)
                        .withDataSource(new AtomDataSource.Builder("http://www.theverge.com/rss/index.xml")
                                .build())
                        .withIdentifier("the_verge_atom")
                        .build())
                .build();

//            .withDataSource(new AtomDataSource("https://news.google.de/?output=atom"))

        Intent libraryIntent = customerFeedLibrary.getOverviewIntent();
        libraryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(libraryIntent);
    }
}
