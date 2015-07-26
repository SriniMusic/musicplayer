package com.srini.musicplayer.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.srini.musicplayer.R;
import com.srini.musicplayer.model.Song;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by srinivasan on 26/07/15.
 */
public class SongsAdapter extends ArrayAdapter<Song> {
    private LayoutInflater inflater;
    private ArrayList<Song> songsList;

    public SongsAdapter(Activity activity, ArrayList<Song> songsList) {
        super(activity, R.layout.songs_row, songsList);
        this.songsList = songsList;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = null;
        if (convertView == null) {
            rowView = setHolder(rowView, parent);
        } else {
            rowView = convertView;
        }
        populateRowView(position, rowView);
        return rowView;
    }

    private View setHolder(View rowView, ViewGroup parent) {

        rowView = inflater.inflate(R.layout.songs_row, parent, false);
        SongHolder songHolder = new SongHolder();
        songHolder.title = (TextView) rowView.findViewById(R.id.song_title);
        songHolder.artist = (TextView) rowView.findViewById(R.id.song_artist);
        songHolder.year = (TextView) rowView.findViewById(R.id.song_year);
        songHolder.composer = (TextView) rowView.findViewById(R.id.song_composer);
//        songHolder.track = (TextView) rowView.findViewById(R.id.song_track);
        rowView.setTag(songHolder);
        return rowView;
    }

    private static class SongHolder {
        public TextView title;
        public TextView artist;
        public TextView year;
        public TextView composer;
//        public TextView track;
    }

    private void populateRowView(int position, View rowView) {
        SongHolder songHolder = (SongHolder) rowView.getTag();
        Song song = songsList.get(position);
        songHolder.title.setText(song.getTitle());
        songHolder.artist.setText(song.getArtist());
//        songHolder.track.setText(song.getTrack());
        String composer = song.getComposer();
        String year = song.getYear();
        if (composer != null && !composer.isEmpty())
            songHolder.composer.setText(composer);
        else
            songHolder.composer.setVisibility(View.GONE);

        if (year != null && !year.isEmpty())
            songHolder.year.setText(year);
        else
            songHolder.year.setVisibility(View.GONE);
        songHolder.year.setText(song.getYear());
    }

}
