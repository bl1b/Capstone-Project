/*******************************************************************************
 * Copyright (c) 2017 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.model.atom;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

/**
 * Class AtomFeedGenerator.
 * TODO: Add Description
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
@Root(name = "generator")
public class AtomFeedGenerator {
    @Text
    private String text = "";

    @Attribute(name = "uri", required = false)
    private String uri = "";

    @Attribute(name = "version", required = false)
    private String version = "";

    public String getText() {
        return text;
    }

    public String getUri() {
        return uri;
    }

    public String getVersion() {
        return version;
    }
}
