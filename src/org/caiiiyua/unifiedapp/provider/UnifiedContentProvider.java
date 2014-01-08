package org.caiiiyua.unifiedapp.provider;

import org.caiiiyua.unifiedapp.provider.UnifiedContentProvider.Volumes.VolumeColumns;
import org.caiiiyua.unifiedapp.utils.LogUtils;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.SparseArray;

public class UnifiedContentProvider extends ContentProvider {

    public static final String TAG = LogUtils.TAG;
    public static final String AUTHORITY = "org.caiiiyua.unifiedapp.provider";

    private static final int BASE_SHIFT = 8;  // 12 bits to the base type: 0, 0x1000, 0x2000, etc.

    private static final SparseArray<String> TABLE_NAMES;
    static {
        SparseArray<String> array = new SparseArray<String>(2);
        array.put(Volumes.VOLUMES_BASE >> BASE_SHIFT, Volumes.TABLE_NAME);
        array.put(Tracks.TRACKS_BASE >> BASE_SHIFT, Tracks.TABLE_NAME);
        TABLE_NAMES = array;
    }
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    protected DBHelper.DatabaseHelper mDbHelper;
    private SQLiteDatabase mDatabase;
    @Override
    public boolean onCreate() {
        synchronized (sUriMatcher) {
            sUriMatcher.addURI(AUTHORITY, Volumes.TABLE_NAME, Volumes.VOLUMES);
            sUriMatcher.addURI(AUTHORITY, Volumes.TABLE_NAME + "/#", Volumes.VOLUMES_ID);
            sUriMatcher.addURI(AUTHORITY, Tracks.TABLE_NAME, Tracks.TRACKS);
            sUriMatcher.addURI(AUTHORITY, Tracks.TABLE_NAME + "/#", Tracks.TRACKS_ID);
        }
        mDbHelper = new DBHelper.DatabaseHelper(getContext());
        mDatabase = mDbHelper.getWritableDatabase();
        mDbHelper.createTable(mDatabase, new Volumes());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        Cursor c = null;
        int match;
        try {
            match = findMatch(uri, "query");
        } catch (IllegalArgumentException e) {
            String uriString = uri.toString();
            // If we were passed an illegal uri, see if it ends in /-1
            // if so, and if substituting 0 for -1 results in a valid uri, return an empty cursor
            if (uriString != null && uriString.endsWith("/-1")) {
                uri = Uri.parse(uriString.substring(0, uriString.length() - 2) + "0");
                match = findMatch(uri, "query");
                return null;
            }
            throw e;
        }
        Context context = getContext();
        SQLiteDatabase db = mDatabase;
        int table = match >> BASE_SHIFT;
        String id;
        String tableName = TABLE_NAMES.valueAt(table);

        switch (match) {
        case Volumes.VOLUMES_ID:
            break;
        case Tracks.TRACKS_ID:
            c = db.query(tableName, Volumes.VOLUME_PROJECTION, null, null, null, null, null);
            break;
        case Volumes.VOLUMES:
            break;
        case Tracks.TRACKS:
            break;

        default:
            break;
        }
        return null;
    }

    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Wrap the UriMatcher call so we can throw a runtime exception if an unknown Uri is passed in
     * @param uri the Uri to match
     * @return the match value
     */
    private static int findMatch(Uri uri, String methodName) {
        int match = sUriMatcher.match(uri);
        if (match < 0) {
            throw new IllegalArgumentException("Unknown uri: " + uri);
        } else {
            LogUtils.v(TAG, methodName + ": uri=" + uri + ", match is " + match);
        }
        return match;
    }

    public static class Volumes extends DBContent implements DatabaseFactory {

        public static final String TABLE_NAME = "volumes";
        public static final String VOLUME_AUTHORITY = AUTHORITY + "/" + TABLE_NAME;

        public static final int VOLUMES_BASE = 0x1;
        public static final int VOLUMES = VOLUMES_BASE;
        public static final int VOLUMES_ID = VOLUMES_BASE + 1;
        public static final int VOLUMES_VOLUME_NUM = VOLUMES_BASE + 2;
        public static final int VOLUMES_VOLUME_TOPIC = VOLUMES_BASE + 3;
        public static final int VOLUMES_VOLUME_DESCRIPTION = VOLUMES_BASE + 4;
        public static final int VOLUMES_VOLUME_MUSICS_KEY = VOLUMES_BASE + 5;
        public static final int VOLUMES_VOLUME_COVER_URI = VOLUMES_BASE + 6;
        public static final int VOLUMES_VOLUME_URL = VOLUMES_BASE + 7;

        public static final class VolumeColumns implements BaseColumns {
            /**
             * This string column contains the human readable of Volume number
             */
            public static final String VOL_NUM = "vol_id";
            /**
             * This string column contains the human readable of Volume topic
             */
            public static final String VOL_TOPIC = "topic";
            /**
             * This string column contains the human readable of Volume description
             */
            public static final String VOL_DESCRIPTION = "description";
            /**
             * This string column contains the human readable of Volume music list
             */
            public static final String MUSIC_LIST_KEY = "music_list_key";
            /**
             * This string column contains the human readable of Volume cover
             */
            public static final String COVER_URI = "cover_uri";
            /**
             * This string column contains the human readable of Volume topic
             */
            public static final String VOL_URL = "vol_url";
        }

        public static final String[] VOLUME_PROJECTION = {
            BaseColumns._ID,
            VolumeColumns.VOL_NUM,
            VolumeColumns.VOL_TOPIC,
            VolumeColumns.VOL_DESCRIPTION,
            VolumeColumns.MUSIC_LIST_KEY,
            VolumeColumns.COVER_URI,
            VolumeColumns.VOL_URL
        };

        public static final int VOLUME_COLUMN_ID = 0;
        public static final int VOLUME_COLUMN_VOL_NUM = 1;
        public static final int VOLUME_COLUMN_VOL_TOPIC = 2;
        public static final int VOLUME_COLUMN_VOL_DESCRIPITON = 3;
        public static final int VOLUME_COLUMN_MUSIC_LIST_KEY = 4;
        public static final int VOLUME_COLUMN_COVER_URI = 5;
        public static final int VOLUME_COLUMN_VOL_URL = 6;

        private static final String VOLUMES_TABLE_COLUMNS = VolumeColumns.VOL_NUM + " integer, "
                + VolumeColumns.VOL_TOPIC + " text, "
                + VolumeColumns.VOL_DESCRIPTION + " text, "
                + VolumeColumns.MUSIC_LIST_KEY + " text, "
                + VolumeColumns.COVER_URI + " text, "
                + VolumeColumns.VOL_URL + " text ";

        @Override
        public String getTableName() {
            // TODO Auto-generated method stub
            return TABLE_NAME;
        }

        @Override
        public String getIndexColumns() {
            // TODO Auto-generated method stub
            return VOLUMES_TABLE_COLUMNS;
        }
        
    }

    public static class Tracks extends DBContent implements DatabaseFactory {

        public static final String TABLE_NAME = "tracks";
        public static final String TRACKS_AUTHORITY = AUTHORITY + "/" + TABLE_NAME;

        public static final int TRACKS_BASE = 0x100;
        public static final int TRACKS = TRACKS_BASE;
        public static final int TRACKS_ID = TRACKS_BASE + 1;
        public static final int TRACKS_TRACK_NUM = TRACKS_BASE + 2;
        public static final int TRACKS_TRACK_NAME = TRACKS_BASE + 3;
        public static final int TRACKS_TRACK_ALBUM = TRACKS_BASE + 4;
        public static final int TRACKS_TRACK_ARTIST = TRACKS_BASE + 5;
        public static final int TRACKS_TRACK_COVER_URI = TRACKS_BASE + 6;
        public static final int TRACKS_TRACK_URI = TRACKS_BASE + 7;
        public static final int TRACKS_TRACK_DURATION = TRACKS_BASE + 8;
        public static final int TRACKS_TRACK_SIZE = TRACKS_BASE + 9;
        public static final int TRACKS_TRACK_VOLUME_KEY = TRACKS_BASE + 10;
        public static final int TRACKS_TRACK_LRC = TRACKS_BASE + 11;
        public static final int TRACKS_TRACK_SAVED = TRACKS_BASE + 12;

        @Override
        public String getTableName() {
            // TODO Auto-generated method stub
            return TABLE_NAME;
        }

        @Override
        public String getIndexColumns() {
            // TODO Auto-generated method stub
            return null;
        }
        
    }
}
