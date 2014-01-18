package org.caiiiyua.unifiedapp.parser;

import java.io.IOException;
import java.util.LinkedList;

import org.caiiiyua.unifiedapp.musicplayer.Volume;
import org.caiiiyua.unifiedapp.utils.LogUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class VolumeListParser implements ContentParser<Volume> {

    private String mPageUrl;
    private Document mParser;

    public VolumeListParser(String uri) {
        mPageUrl = uri;
    }

    @Override
    public LinkedList<Volume> parse() throws IOException {
        LinkedList<Volume> volumes = new LinkedList<Volume>();
        Elements elements = getVolumeElements();
        LogUtils.d(LogUtils.TAG, "VolumeListParser getElements: %s", elements);
        for (Element volumeElement : elements) {
            Volume volume = new Volume(new VolumeParser(volumeElement));
            volumes.add(volume);
            LogUtils.d(LogUtils.TAG, "VolumeListParser add a Volume: %s", volume);
        }
        return volumes;
    }

    private Elements getVolumeElements() throws IOException {
        mParser = Jsoup.connect(mPageUrl).get();
        LogUtils.d(LogUtils.TAG, "VolumeListParser with URL: %s", mPageUrl);
        return mParser.getElementsByClass("like-item");
    }

}
