/*******************************************************************************
 * Copyright (c) 2016 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.model.atom;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import de.cominto.blaetterkatalog.android.cfl.CFLDataSource;

/**
 * Class AtomDataSource.
 * This is the class representing an atom feed as data-source.
 *
 * @author Jan Grünewald (2016)
 * @version 1.0.0
 */
public class AtomDataSource implements CFLDataSource {

    private final String atomFeedUrl;

    public static final Parcelable.Creator<AtomDataSource> CREATOR = new Parcelable.Creator<AtomDataSource>() {
        @Override
        public AtomDataSource createFromParcel(Parcel parcel) {
            return new AtomDataSource(parcel);
        }

        @Override
        public AtomDataSource[] newArray(int i) {
            return new AtomDataSource[i];
        }
    };

    public AtomDataSource(final String atomFeedUrl) {
        this.atomFeedUrl = atomFeedUrl;
    }

    private AtomDataSource(final Parcel in) {
        this.atomFeedUrl = in.readString();
    }

    @Override
    public Uri getRemoteUri() {
        return Uri.parse(atomFeedUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(atomFeedUrl);
    }
}