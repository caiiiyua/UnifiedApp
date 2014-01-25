package org.caiiiyua.unifiedapp.parser;

import java.util.regex.Pattern;

import org.caiiiyua.unifiedapp.musicplayer.Music;
import org.caiiiyua.unifiedapp.utils.LogUtils;
import org.jsoup.nodes.Element;

public class MusicParser extends AbstractItemParser implements ItemParser {

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

    public MusicParser(Element parser) {
        super(parser);
    }

    public MusicParser(Element parser, long volId) {
        super(parser);
        mVolId = volId;
    }

    @Override
    public Music parse() {
        if (mParser == null) return null;
        mVolId = getVolId();
        mTrackId = getTrackId();
        mAlbum = getAlbum();
        mArtist = getArtist();
        mName = getName();
        mCoverUri = getCoverUri();
        mTrackUri = getTrackUri();
        mLrc = getLrc();
        mDuration = getDuration();
        Music music = new Music(mVolId, mTrackId, mAlbum, mArtist, mName, mCoverUri, mTrackUri
                , mLrc, mDuration);
        return music;
    }

    private long getDuration() {
        // TODO Auto-generated method stub
        return 0;
    }

    private String getLrc() {
        // TODO Auto-generated method stub
        return null;
    }

    private String getTrackUri() {
        String trackUri = null;
        Element trackIdElement = getElementByClass("track-num", "span");
        return trackUri;
    }

    private String getCoverUri() {
        String coverUri = null;
        Element coverUriElement = getElementByClass("track-cover", "img");
        coverUri = coverUriElement.attr("src");
        return coverUri;
    }

    private String getName() {
        String name = null;
        Element nameElement = getElementByClass("track-name", "a");
        name = nameElement.text();
        return name;
    }

    private String getArtist() {
        String artist = null;
        Element artistElement = getElementByClass("track-album", "a");
        artist = artistElement.text().split("-")[0];
        return artist;
    }

    private String getAlbum() {
        String album = null;
        Element albumElement = getElementByClass("track-album", "a");
        album = albumElement.text().split("-")[1];
        return album;
    }

    private long getTrackId() {
        long trackId = 0;
        Element trackIdElement = getElementByClass("track-num", "span");
        trackId = Long.valueOf(trackIdElement.text());
        return trackId;
    }

    private long getVolId() {
        if (mVolId <= 0) {
            String volId = "0";
            Pattern digitPatternString = Pattern.compile("\\.");
            String volString = getElementByClass("current-vol", "div").text();
            LogUtils.d(LogUtils.TAG, digitPatternString.split(volString)[1]);
            volId = digitPatternString.split(volString)[1].trim();
            mVolId = Long.valueOf(volId);
        }
        return mVolId;
    }
}
