package com.srini.musicplayer.activity;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.srini.musicplayer.R;
import com.srini.musicplayer.adapter.SongsAdapter;
import com.srini.musicplayer.dbhandler.SongsDbHandler;
import com.srini.musicplayer.model.Song;
import com.srini.musicplayer.preferences.ColorPreferences;
import com.srini.musicplayer.service.MusicService;
import com.srini.musicplayer.utils.ThemeUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;


public class MusicIndex extends ActionBarActivity {
    ListView songsListView;
    ArrayList<Song> songsList = new ArrayList<Song>();
    private MusicService musicSrv;
    private Intent playIntent;
    private boolean musicBound=false;
    SongsAdapter songsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_index);
        setupSongs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_music_index, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings :
                return true;

            case R.id.play_all:
                playAllSongs();
                break;
            case R.id.shuffle:
                shuffle();
                break;

            case R.id.end :
                stopService(playIntent);
                musicSrv=null;
                System.exit(0);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicSrv.setSongsList(songsList,false);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        if(playIntent==null){
            playIntent = new Intent(this, MusicService.class);
            startService(playIntent);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onDestroy() {
        stopService(playIntent);
        musicSrv = null;
        super.onDestroy();
    }


    public void setupSongs(){
        songsListView = (ListView) findViewById(R.id.songs_list);
        populateSongs();
        songsAdapter = new SongsAdapter(this, songsList);
        songsListView.setAdapter(songsAdapter);
        songsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                playSelectedSong(i);
            }
        });
    }

    public void populateSongs(){
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if(musicCursor!=null && musicCursor.moveToFirst()){
            int titleColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST);
            int trackColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TRACK);
            int yearColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.YEAR);
            int composerColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER);

            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thisTrack = musicCursor.getString(trackColumn);
                String thisYear = musicCursor.getString(yearColumn);
                String thisComposer = musicCursor.getString(composerColumn);

                Song s = new Song(thisId,thisTitle,thisArtist,thisTrack,thisYear,thisComposer);
                SongsDbHandler db = new SongsDbHandler(this);
                s.setScore(db.getScore(s));
                Log.d("music", "" + s.getScore());
                songsList.add(s);
            }
            while (musicCursor.moveToNext());
        }
    }

    public void playSelectedSong(int pos){
        Song s = songsList.get(pos);
        int score = s.getScore();
        s.setScore(++score);
        SongsDbHandler db = new SongsDbHandler(this);
        db.updateScore(s);
        musicSrv.setSong(pos);
        musicSrv.playSong();
    }

    public void playAllSongs(){
        musicSrv.setSongsList(songsList, true);
        musicSrv.playSong();
    }

    public void shuffle(){
        Collections.shuffle(songsList);
        songsAdapter.notifyDataSetChanged();
        playAllSongs();
    }
}
