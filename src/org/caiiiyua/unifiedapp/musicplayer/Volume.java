package org.caiiiyua.unifiedapp.musicplayer;

import java.io.IOException;
import java.util.ArrayList;

import org.caiiiyua.unifiedapp.content.CursorCreator;
import org.caiiiyua.unifiedapp.parser.ContentParser;
import org.caiiiyua.unifiedapp.parser.VolumeListParser;
import org.caiiiyua.unifiedapp.parser.VolumeParser;
import org.caiiiyua.unifiedapp.provider.UnifiedContentProvider.Volumes;
import org.caiiiyua.unifiedapp.provider.UnifiedContentProvider.Volumes.VolumeColumns;
import org.caiiiyua.unifiedapp.ui.UIProvider;
import org.caiiiyua.unifiedapp.utils.LogUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class Volume {

    public static final String VOLUME_COVER = "640x452";
    public static final String LIST_COVER = "160x120";
    public static final String VOLUME_NUM = "vol.";

    private long mId;
    private long mVolId;
    private String mTopic;
    private String mDescription;
    private ArrayList<Music> mMusicList;
    private String mCoverUri;
    private String mUrlString;
    private String mMusicUriBase;
    private MusicHtmlParser mMusicParser;
    private String mVolDate;
    private String mCategory;
    private String mVolTag;
    private long mVolTagId;

    public Volume() {
    }

    public Volume(VolumeParser parser) {
        Volume(parser.parse());
    }

    public void Volume(final Volume volume) {
        mVolId = volume.mVolId;
        mTopic = volume.mTopic;
        mDescription = volume.mDescription;
        mCoverUri = volume.mCoverUri;
        mUrlString = volume.mUrlString;
        mVolDate = volume.mVolDate;
        mCategory = volume.mCategory;
        mVolTag = volume.mVolTag;
        mVolTagId = volume.mVolTagId;
    }

    public Volume(String url) {
        mUrlString = url;
//        mMusicParser = new MusicHtmlParser(mUrlString);
    }

    public Volume(long volId, String topic, String description, String url,
            String mCoverUrl, String volDate, String category, String volTag, long volTagId) {
        mVolId = volId;
        mTopic = topic;
        mDescription = description;
        mUrlString = url;
        mCoverUri = mCoverUrl;
        mVolDate = volDate;
        mCategory = category;
        mVolTag = volTag;
        mVolTagId = volTagId;
//        mMusicParser = new MusicHtmlParser(mUrlString);
    }

    public Volume(Cursor c) {
        mId = c.getLong(UIProvider.VOLUME_COLUMN_ID);
        mVolId = c.getLong(UIProvider.VOLUME_COLUMN_VOL_NUM);
        mTopic = c.getString(UIProvider.VOLUME_COLUMN_VOL_TOPIC);
        mDescription = c.getString(UIProvider.VOLUME_COLUMN_VOL_DESCRIPITON);
        mCoverUri = c.getString(UIProvider.VOLUME_COLUMN_COVER_URI);
        mUrlString = c.getString(UIProvider.VOLUME_COLUMN_VOL_URL);
        mCategory = c.getString(UIProvider.VOLUME_COLUMN_VOL_CATEGORY);
        mVolDate = c.getString(UIProvider.VOLUME_COLUMN_VOL_DATE);
        mVolTag = c.getString(UIProvider.VOLUME_COLUMN_VOL_TAG);
        mVolTagId = c.getLong(UIProvider.VOLUME_COLUMN_VOL_TAG_ID);
    }

    public boolean initializeMetaInfo() {
        if (TextUtils.isEmpty(mUrlString)) {
            return false;
        }
        if (mMusicParser == null) {
            mMusicParser = new MusicHtmlParser(mUrlString);
        }
        
        try {
            mTopic = mMusicParser.getVolTopic();
            mDescription = mMusicParser.getVolDescription();
            mVolId = mMusicParser.getVolId();
            mUrlString = LuooConstantUtils.buildVolUrl(mVolId);
            mMusicUriBase = LuooConstantUtils.buildMusicUriBase(mVolId);
            mCoverUri = mMusicParser.getVolCover();
            mMusicList = mMusicParser.getMusicList(mVolId);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return true;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(UIProvider.VolumeColumns.VOL_NUM, mVolId);
        values.put(UIProvider.VolumeColumns.VOL_TOPIC, mTopic);
        values.put(UIProvider.VolumeColumns.VOL_DESCRIPTION, mDescription);
        values.put(UIProvider.VolumeColumns.MUSIC_LIST_KEY, mMusicList != null ?
                mMusicList.toString() : "");
        values.put(UIProvider.VolumeColumns.COVER_URI, mCoverUri != null ? mCoverUri.toString()
                : "");
        values.put(UIProvider.VolumeColumns.VOL_URL, mUrlString);
        values.put(UIProvider.VolumeColumns.VOL_CATEGORY, mCategory);
        values.put(UIProvider.VolumeColumns.VOL_DATE, mVolDate);
        values.put(UIProvider.VolumeColumns.VOL_TAG, mVolTag);
        values.put(UIProvider.VolumeColumns.VOL_TAG_ID, mVolTagId);

        return values;
    }

    public long getVolumeNum() {
        return mVolId;
    }

    public Uri getTrackUri(long trackId) {
        Log.d(LuooConstantUtils.TAG, "getTrackUri with id:" + trackId);
        if (mMusicList == null) return null;
        for (Music metaInfo : mMusicList) {
            Log.d(LuooConstantUtils.TAG, "getTrackUri with metainfo:" + metaInfo);
            if (metaInfo.getTrackId() == trackId) {
                return Uri.parse(metaInfo.getTrackUri());
            }
        }
        return null;
    }

    public String toString() {
        return "[ " + mVolId + ", "
                + mTopic + ", " + mDescription + ", " + mUrlString + ", "
                + mCoverUri + ", " + mVolDate + ", " + mCategory + ", "
                + mVolTag + " ]";
    }

    public static final CursorCreator<Volume> FACTORY = new CursorCreator<Volume>() {

        @Override
        public Volume createFromCursor(Cursor c) {
            return new Volume(c);
        }
    };

    public void insert(Context context) {
//        LogUtils.d(LogUtils.TAG, "insert a Volume: %s", toString());
        context.getContentResolver().insert(Volumes.CONTENT_URI, toContentValues());
    }

    public void insertWithoutDuplicate(Context context) {
        boolean duplicate = false;
        long latest = 0;
        long oldest = 0;
        Cursor cursor = context.getContentResolver().query(Volumes.CONTENT_URI,
                Volumes.VOLUME_NUM_PROJECTION, null, null, UIProvider.VOLUME_COLUMN_VOL_NUM + " ASC");
        if (cursor == null || cursor.isClosed()) return;
        try {
            if (cursor.moveToFirst()) {
                latest = cursor.getLong(Volumes.VOLUME_COLUMN_VOL_NUM);
            }
            if (cursor.moveToLast()) {
                oldest = cursor.getLong(Volumes.VOLUME_COLUMN_VOL_NUM);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        if (mVolId <= latest && mVolId >= oldest) {
            duplicate = true;
        }
        if (duplicate) {
            LogUtils.d(LogUtils.TAG, "insert a duplicate Volume with id: %d which included in [%d, %d]",
                    mVolId, oldest, latest);
            return;
        }
        LogUtils.d(LogUtils.TAG, "insert a Volume: %s", toString());
        context.getContentResolver().insert(Volumes.CONTENT_URI, toContentValues());
    }

    public boolean isDuplicate(long latest, long oldest) {
        LogUtils.d(LogUtils.TAG, "insert a duplicate Volume with id: %d which included in [%d, %d]",
                mVolId, oldest, latest);
        if (mVolId <= latest && mVolId >= oldest) {
            return true;
        }
        return false;
    }
}
