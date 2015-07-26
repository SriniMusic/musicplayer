package com.srini.musicplayer.dbhandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.srini.musicplayer.model.Song;

/**
 * Created by srinivasan on 26/07/15.
 */
public class SongsDbHandler extends SQLiteOpenHelper {
    private static final int DATABASEVERSION = 1;
    private static final String TABLE_SONGS = "songs";
    private static final String DB_NAME = "songsDb";

    private static final String KEY_ID = "id";
    private static final String KEY_SCORE = "score";

    public SongsDbHandler(Context context) {
        super(context, DB_NAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SONGS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_SCORE + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
        onCreate(sqLiteDatabase);
    }

    public void addSong(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, song.getId());
        values.put(KEY_SCORE, song.getScore()); // Contact Phone Number

        // Inserting Row
        db.insert(TABLE_SONGS, null, values);
        db.close(); // Closing database connection
    }

    public int getScore(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();
        int score = 0;
        String SELECT_QUERY = "SELECT * FROM "+TABLE_SONGS+" WHERE "+KEY_ID+" = " +song.getId()+";";
        Cursor cursor = db.rawQuery(SELECT_QUERY,null);

        if (cursor != null && cursor.moveToFirst()){
            score = Integer.parseInt(cursor.getString(1));
        }
        else {
            song.setScore(score);
            addSong(song);
        }
        db.close();
        return score;
    }

    public int updateScore(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SCORE, song.getScore());

        // updating row
        int ret =  db.update(TABLE_SONGS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(song.getId()) });
        db.close();
        return ret;
    }
}
