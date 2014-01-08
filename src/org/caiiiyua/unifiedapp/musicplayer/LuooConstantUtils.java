package org.caiiiyua.unifiedapp.musicplayer;

public class LuooConstantUtils {

    public static final String TAG = "LUOO";
    public static final String LUOO_URL_HOME = "http://www.luoo.net/";
    public static final String LUOO_URL_BASE = "http://www.luoo.net/luo/";
    public static final String LUOO_URL_MUSIC_BASE = "http://luoo.800edu.net/low/luoo/radio%d/";
    public static final String LUOO_URL_TRACK_SUFFIX = "%02d.mp3";
    public static final String LUOO_URL_TRACK_URI = "http://luoo.800edu.net/low/luoo/radio%d/%02d.mp3";

    public static String buildVolUrl(long volId) {
        String urlString = LuooConstantUtils.LUOO_URL_BASE;
        
        return String.format("%s%d", urlString, volId);
    }

    public static String buildMusicUriBase(long volId) {
        String urlString = LuooConstantUtils.LUOO_URL_MUSIC_BASE;
        return String.format(urlString, volId);
    }

    public static String buildMusicTrackUri(long volId, long trackId) {
        return String.format(LUOO_URL_TRACK_URI, volId, trackId);
    }
}
