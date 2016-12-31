/*******************************************************************************
 * Copyright (c) 2016 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.util;

import org.junit.Test;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

/**
 * Class LogHelperTest.
 * This classes tests the LogHelper functionalities.
 *
 * @author Jan Grünewald (2016)
 * @version 1.0.0
 */
public class LogHelperTest {

    private static final class DummyHelperClassWithAVeryLongName {
    }

    @Test
    public void testCreateLogTag() {
        String tag = LogHelper.TAG(LogHelperTest.class);
        assertThat("Function returned null or empty string.", tag, not(isEmptyOrNullString()));

        tag = LogHelper.TAG(DummyHelperClassWithAVeryLongName.class);
        assertThat("Resulting tag is too long.", tag.length(), lessThanOrEqualTo(LogHelper.MAX_LOG_TAG_LENGTH));
        assertThat("Truncated string does not match", tag, comparesEqualTo("DummyHelperClassWithAVe"));
    }
}
