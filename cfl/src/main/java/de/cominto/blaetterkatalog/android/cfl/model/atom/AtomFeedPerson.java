/*******************************************************************************
 * Copyright (c) 2017 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.model.atom;

import org.simpleframework.xml.Element;

/**
 * Class AtomFeedPerson.
 * TODO: Add Description
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
public class AtomFeedPerson {
    @Element(name = "name")
    private String name;

    @Element(name = "uri", required = false)
    private String uri = "";

    @Element(name = "email", required = false)
    private String email = "";

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public String getEmail() {
        return email;
    }
}
