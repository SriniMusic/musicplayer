package com.srini.musicplayer.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.srini.musicplayer.MusicPlayer;

/**
 * Created by srinivasan on 26/07/15.
 */
public enum ColorPreferences {

    INSTANCE;
    SharedPreferences colors;
    private final String MUSIC_COLOR_PREF = "Srini_Music_color_pref";
    private final String BACKGROUND = "BackgroundColor";
    private final String TEXT = "TextColor";
    ColorPreferences(){
        colors = MusicPlayer.getAppContext().getSharedPreferences(MUSIC_COLOR_PREF, Context.MODE_PRIVATE);
    }

    public void setBackgorund(String c) {
        SharedPreferences.Editor e = colors.edit();
        e.putString(BACKGROUND,c);
        e.apply();
    }

    public void setTextColor(String c) {
        SharedPreferences.Editor e = colors.edit();
        e.putString(TEXT,c);
        e.apply();
    }
    public String getBackground(){
      return colors.getString(BACKGROUND, "#FFF");
    }

    public String getTextColor(){
        return colors.getString(TEXT, "#FFF");
    }
}
