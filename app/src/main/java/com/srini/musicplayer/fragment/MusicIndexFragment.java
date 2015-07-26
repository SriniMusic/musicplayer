package com.srini.musicplayer.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.srini.musicplayer.R;
import com.srini.musicplayer.adapter.SongsAdapter;
import com.srini.musicplayer.model.Song;
import com.srini.musicplayer.service.MusicService;
import com.srini.musicplayer.service.MusicService.MusicBinder;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MusicIndexFragment extends Fragment {

    public MusicIndexFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music_index, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


}
