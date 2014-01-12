package org.caiiiyua.unifiedapp.musicplayer;

import java.io.IOException;
import java.util.ArrayList;

import org.caiiiyua.unifiedapp.content.CursorCreator;
import org.caiiiyua.unifiedapp.provider.UnifiedContentProvider.Volumes;
import org.caiiiyua.unifiedapp.ui.UIProvider;
import org.caiiiyua.unifiedapp.utils.LogUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class Volume {

    private long mId;
    private long mVolId;
    private String mTopic;
    private String mDescription;
    private ArrayList<Music> mMusicList;
    private Uri mCoverUri;
    private String mUrlString;
    private String mMusicUriBase;
    private MusicHtmlParser mMusicParser;

    public Volume() {
        // TODO Auto-generated constructor stub
    }

    public Volume(String url) {
        mUrlString = url;
        mMusicParser = new MusicHtmlParser(mUrlString);
    }

    public Volume(long volId, String topic, String description, String url) {
        mVolId = volId;
        mTopic = topic;
        mDescription = description;
        mUrlString = url;
        mMusicParser = new MusicHtmlParser(mUrlString);
    }

    public Volume(Cursor c) {
        mId = c.getLong(UIProvider.VOLUME_COLUMN_ID);
        mVolId = c.getLong(UIProvider.VOLUME_COLUMN_VOL_NUM);
        mTopic = c.getString(UIProvider.VOLUME_COLUMN_VOL_TOPIC);
        mDescription = c.getString(UIProvider.VOLUME_COLUMN_VOL_DESCRIPITON);
        mCoverUri = Uri.parse(c.getString(UIProvider.VOLUME_COLUMN_COVER_URI));
        mUrlString = c.getString(UIProvider.VOLUME_COLUMN_VOL_URL);
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
            mCoverUri = Uri.parse(mMusicParser.getVolCover());
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
        values.put(UIProvider.VolumeColumns.MUSIC_LIST_KEY, mMusicList.toString());
        values.put(UIProvider.VolumeColumns.COVER_URI, mCoverUri.toString());
        values.put(UIProvider.VolumeColumns.VOL_URL, mUrlString);

        return values;
    }

    public Uri getTrackUri(long trackId) {
        Log.d(LuooConstantUtils.TAG, "getTrackUri with id:" + trackId);
        if (mMusicList == null) return null;
        for (Music metaInfo : mMusicList) {
            Log.d(LuooConstantUtils.TAG, "getTrackUri with metainfo:" + metaInfo);
            if (metaInfo.getTrackId() == trackId) {
                return metaInfo.getTrackUri();
            }
        }
        return null;
    }

    public String toString() {
        return "[ " + mVolId + ", "
                + mTopic + ", " + mDescription + ", " + mUrlString + ", "
                + mCoverUri + ", " + mMusicUriBase + " ]";
    }

    public static final CursorCreator<Volume> FACTORY = new CursorCreator<Volume>() {

        @Override
        public Volume createFromCursor(Cursor c) {
            // TODO Auto-generated method stub
            return new Volume(c);
        }
    };

    public void insert(Context context) {
        LogUtils.d(LogUtils.TAG, "insert a Volume: %s", toString());
        context.getContentResolver().insert(Volumes.CONTENT_URI, toContentValues());
    }
}
