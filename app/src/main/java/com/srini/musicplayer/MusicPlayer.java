package com.srini.musicplayer;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

/**
 * Created by srinivasan on 26/07/15.
 */
public class MusicPlayer extends Application {
    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Resources.Theme a = getTheme();
        context = this;
    }

    public static Context getAppContext() {
        return context;
    }
}
