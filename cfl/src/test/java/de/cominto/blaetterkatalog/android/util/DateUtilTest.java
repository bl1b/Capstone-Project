/*******************************************************************************
 * Copyright (c) 2017 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.util;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Class DateUtilTest.
 * TODO: Add Description
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
public class DateUtilTest {

    @Test
    public void testUTCConversion() throws Exception {
        String RFC3339String = "2016-12-31T10:03:35Z";

        Date parsedDate = DateUtil.parseRFC3339Date(RFC3339String);

        Calendar myCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        myCalendar.set(2016, 11, 31, 10, 3, 35);

        assertThat(parsedDate.toString(), equalTo("Sat Dec 31 11:03:35 CET 2016"));
        assertThat(parsedDate.toString(), equalTo(myCalendar.getTime().toString()));
    }

    @Test
    public void testTimeZoneHandling() throws Exception {
        String RFC3339withUTC = "2016-12-31T10:03:35Z";
        String RFC3339withCET = "2016-12-31T11:03:35+01:00";
        String RFC3339withPDT = "2016-12-31T03:03:35-07:00";

        Date parsedUTC = DateUtil.parseRFC3339Date(RFC3339withUTC);
        Date parsedCET = DateUtil.parseRFC3339Date(RFC3339withCET);
        Date parsedPDT = DateUtil.parseRFC3339Date(RFC3339withPDT);

        assertThat(parsedUTC.toString(), equalTo(parsedCET.toString()));
        assertThat(parsedUTC.toString(), equalTo(parsedPDT.toString()));
    }

}
