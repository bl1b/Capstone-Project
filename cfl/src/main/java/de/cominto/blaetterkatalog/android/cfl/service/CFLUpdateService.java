/*******************************************************************************
 * Copyright (c) 2016 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;

import de.cominto.blaetterkatalog.android.cfl.CFL;
import de.cominto.blaetterkatalog.android.cfl.CFLConfiguration;
import de.cominto.blaetterkatalog.android.cfl.service.atom.AtomService;
import io.realm.Realm;
import timber.log.Timber;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class CFLUpdateService extends IntentService {

    private static final String ACTION_UPDATEALL = "de.cominto.blaetterkatalog.android.cfl.updateservice.action.UPDATE_ALL";
    public static final String ACTION_UPDATE_ALL_COMPLETE = "de.cominto.blaetterkatalog.android.cfl.UPDATE_ALL_COMPLETE";


    public CFLUpdateService() {
        super("CFLUpdateService");
    }

    @Inject
    CFLConfiguration cflConfiguration;

    @Inject
    Realm realm;

    @Inject
    AtomService atomService;

    /**
     * Starts this service to update all registered resources.
     *
     * @param context the context this service is started on.
     * @see IntentService
     */
    public static void startUpdateAll(Context context) {
        Intent intent = new Intent(context, CFLUpdateService.class);
        intent.setAction(ACTION_UPDATEALL);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        CFL.Injector.getInstance(getApplicationContext()).getComponent().inject(this);
        atomService.setContext(this);

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATEALL.equals(action)) {
                handleUpdateAll();
            }
        }
    }

    /**
     * Handles the update of all registered sources on a background thread.
     */
    private void handleUpdateAll() {
        atomService.updateAllActive();

        sendUpdateAllFinishedBroadcast();
        Timber.d("Update all completed.");
    }

    private void sendUpdateAllFinishedBroadcast() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(ACTION_UPDATE_ALL_COMPLETE);
        sendBroadcast(broadcastIntent);
    }
}
