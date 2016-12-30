/*******************************************************************************
 * Copyright (c) 2016 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl;

import android.content.Context;
import android.content.Intent;

import de.cominto.blaetterkatalog.android.cfl.ui.OverviewActivity;

/**
 * Class CFL.
 * This is the interface class for the Customer Feed Library.
 * The configuration is done here and a intent is provided which will
 * be used to display the UI.
 *
 * @author Jan Grünewald (2016)
 * @version 1.0.0
 */
public class CFL {

    private final Context context;

    public static class Builder {
        private CFL cflInstance;

        public Builder(Context context) {
            cflInstance = new CFL(context);
        }

        public CFL build() {
            return cflInstance;
        }
    }


    private CFL(final Context context) {
        this.context = context;
    }

    public Intent getOverviewIntent() {
        return OverviewActivity.createStartingIntentWithData(context, null);
    }
}
