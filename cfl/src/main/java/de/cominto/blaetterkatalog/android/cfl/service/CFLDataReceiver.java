package de.cominto.blaetterkatalog.android.cfl.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Class CFLDataReceiver.
 * TODO: Add Description
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
public class CFLDataReceiver extends BroadcastReceiver {

    private CFLDataChangedListener dataChangedListener;

    public CFLDataReceiver(CFLDataChangedListener dataChangedListener) {
        this.dataChangedListener = dataChangedListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (dataChangedListener != null) {
            dataChangedListener.onCFLDataChanged();
        }
    }
}
