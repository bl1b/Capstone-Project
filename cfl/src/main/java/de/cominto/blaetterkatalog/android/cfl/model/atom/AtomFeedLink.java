/*******************************************************************************
 * Copyright (c) 2016 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.model.atom;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Class AtomFeedLink.
 * This model defines are <link/>-element either in a <feed/> or an <entry/>
 * in an Atom-RSS Feed.
 *
 * @author Jan Grünewald (2016)
 * @version 1.0.0
 */
@Root(name = "link", strict = false)
public class AtomFeedLink {
    @Attribute
    private String rel;

    @Attribute
    private String href;

    @Attribute(required = false)
    private String type;

    @Attribute(required = false)
    private String hreflang;

    public String getRel() {
        return rel;
    }

    public String getHref() {
        return href;
    }

    public String getType() {
        return type;
    }

    public String getHreflang() {
        return hreflang;
    }
}
