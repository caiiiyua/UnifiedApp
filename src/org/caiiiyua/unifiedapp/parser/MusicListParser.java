package org.caiiiyua.unifiedapp.parser;

import java.io.IOException;
import java.util.LinkedList;

import org.caiiiyua.unifiedapp.musicplayer.Music;
import org.caiiiyua.unifiedapp.utils.LogUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MusicListParser implements ContentParser<Music> {

    private Document mParser;
    private String mPageUrl;
    private long mVolId;

    public MusicListParser(String url, long volId) {
        mPageUrl = url;
        mVolId = volId;
    }
    @Override
    public LinkedList<Music> parse() throws IOException {
        // TODO Auto-generated method stub
        LinkedList<Music> musics = new LinkedList<Music>();
        Elements elements = getMusicElements();
//        LogUtils.d(LogUtils.TAG, "VolumeListParser getElements: %s", elements);
        for (Element musicElement : elements) {
            Music music = new Music(new MusicParser(musicElement, mVolId));
            musics.add(music);
            LogUtils.d(LogUtils.TAG, "MusicListParser add a Music: %s", music);
        }
        return musics;
    }

    private Elements getMusicElements() throws IOException {
        mParser = Jsoup.connect(mPageUrl).get();
        LogUtils.d(LogUtils.TAG, "MusicListParser with URL: %s", mPageUrl);
        return mParser.getElementsByClass("track");
    }
}
