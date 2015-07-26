package com.srini.musicplayer.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;

import com.srini.musicplayer.R;
import com.srini.musicplayer.preferences.ColorPreferences;

/**
 * Created by srinivasan on 26/07/15.
 */
public class ThemeUtils {
    public ThemeUtils(Activity activity){
        activity.findViewById(android.R.id.content).setBackgroundColor(Color.parseColor(ColorPreferences.INSTANCE.getBackground()));
    }
}
