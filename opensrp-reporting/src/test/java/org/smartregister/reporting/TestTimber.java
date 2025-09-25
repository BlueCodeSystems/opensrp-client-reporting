package org.smartregister.reporting;

import timber.log.Timber;

public final class TestTimber {

    private static boolean planted;

    private TestTimber() {
        // no-op
    }

    public static void plant() {
        if (!planted) {
            Timber.uprootAll();
            Timber.plant(new Timber.Tree() {
                @Override
                protected void log(int priority, String tag, String message, Throwable t) {
                    // no-op
                }
            });
            planted = true;
        }
    }
}
