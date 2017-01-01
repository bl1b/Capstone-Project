/*******************************************************************************
 * Copyright (c) 2017 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.model.atom;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Text;

/**
 * Class AtomFeedText.
 * TODO: Add Description
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
public class AtomFeedText {
    @Text
    private String text = "";

    @Attribute(required = false, name = "type")
    private String type = "text";

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }
}
