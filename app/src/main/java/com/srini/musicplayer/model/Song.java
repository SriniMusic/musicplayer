package com.srini.musicplayer.model;

import com.srini.musicplayer.dbhandler.SongsDbHandler;

/**
 * Created by srinivasan on 26/07/15.
 */
public class Song {
    private long id;
    private String title;
    private String artist;
    private String track;
    private String year;
    private String composer;
    private int score;

    public Song(long id,String title,String artist,String track,String year, String composer){
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.track = track;
        this.year = year;
        this.composer = composer;
    }

    public void setScore(int score){
        this.score = score;
    }
    public long getId() {
        return id;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public String getComposer() {
        return composer;
    }

    public String getTrack() {
        return track;
    }

    public String getYear() {
        return year;
    }
    public int getScore() {return score; }
}
