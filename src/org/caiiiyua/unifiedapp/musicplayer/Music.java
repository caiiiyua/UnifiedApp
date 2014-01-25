package org.caiiiyua.unifiedapp.musicplayer;

import org.caiiiyua.unifiedapp.parser.MusicParser;

import android.net.Uri;

public class Music {

    public static final String MUSIC_COVER = "580x580";
    public static final String LIST_COVER = "60x60";

    private long mId;
    private long mTrackId;
    private String mName;
    private String mAlbum;
    private String mArtist;
    private long mDuration;
    private int mSize;
    private long mVolId;
    private String mDescription;
    private String mCoverUri;
    private String mLrc;
    private String mTrackUri;

    public Music() {
        
    }

    public Music(long volId) {
        mVolId = volId;
    }

    public Music(MusicParser musicParser) {
        Music(musicParser.parse());
    }

    public Music(long volId, long trackId, String album, String artist,
            String name, String coverUri, String trackUri, String lrc,
            long duration) {
        mVolId = volId;
        mTrackId = trackId;
        mAlbum = album;
        mArtist = artist;
        mName = name;
        mCoverUri = coverUri;
        mTrackUri = trackUri;
        mLrc = lrc;
        mDuration = duration;
    }

    private void Music(Music music) {
        mVolId = music.getVolId();
        mTrackId = music.getTrackId();
        mAlbum = music.getAlbum();
        mArtist = music.getArtist();
        mName = music.getName();
        mCoverUri = music.getCoverUri();
        mTrackUri = music.getTrackUri();
        mLrc = music.getLrc();
        mDuration = music.getDuration();
    }

    public long getTrackId() {
        return mTrackId;
    }

    public void setTrackId(long trackId) {
        mTrackId = trackId;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public void setAlbum(String album) {
        this.mAlbum = album;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        this.mArtist = artist;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        this.mDuration = duration;
    }

    public int getSize() {
        return mSize;
    }

    public void setSize(int size) {
        this.mSize = size;
    }

    public long getVolId() {
        return mVolId;
    }

    public void setVolId(long volId) {
        this.mVolId = volId;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getCoverUri() {
        return mCoverUri;
    }

    public void setCoverUri(String coverUri) {
        this.mCoverUri = coverUri;
    }

    public String getTrackUri() {
        return mTrackUri;
    }

    public void setTrackUri(String trackUri) {
        this.mTrackUri = trackUri;
    }

    public String getLrc() {
        return mLrc;
    }

    public void setLrc(String lrc) {
        this.mLrc = lrc;
    }

    @Override
    public String toString() {
        return "[ " + mVolId + ", " + mTrackId + ", " + mName + ", " + mAlbum + ", " + mCoverUri +
                ", " + mTrackUri + " ]";
    }
}
