/*******************************************************************************
 * Copyright (c) 2017 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.model.atom;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Class AtomFeedCategory.
 * TODO: Add Description
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
@Root(name = "category")
public class AtomFeedCategory {
    @Attribute(name = "term")
    private String term;

    @Attribute(name = "scheme", required = false)
    private String scheme;

    @Attribute(name = "label", required = false)
    private String label;

    public String getTerm() {
        return term;
    }

    public String getScheme() {
        return scheme;
    }

    public String getLabel() {
        return label;
    }
}
