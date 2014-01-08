package org.caiiiyua.unifiedapp.musicplayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class MusicHtmlParser {

    private static final String TAG = "MusicHtmlParser";
    private Document mDocument;
    private String mUrl;
    public MusicHtmlParser() {
        
    }

    public MusicHtmlParser(String url) {
        mUrl = url;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

    public Document getDocument() throws IOException {
        if (mDocument != null) {
            return mDocument;
        }

        if (mUrl == null) {
            return null;
        }

        mDocument = MusicHtmlParser.getDocument(mUrl);
        return mDocument;
    }

    public String getVolTopic() throws IOException {
        Document document = getDocument();
        String volTopic = "";
        for (Element element : document.getElementsByClass("fm-title")) {
            if ("h1".equals(element.tagName())){
                volTopic = element.text();
                break;
            }
        }
        return volTopic;
    }

    public String getVolDescription() throws IOException {
        Document document = getDocument();
        String volDescription = "";
        for (Element element : document.getElementsByClass("fm-intro")) {
            if ("p".equals(element.tagName())){
                volDescription = element.text();
                break;
            }
        }
        return volDescription;
    }

    public String getVolCover() throws IOException {
        Document document = getDocument();
        String volCover = "";
        for (Element element : document.getElementsByClass("fm-cover")) {
            if ("img".equals(element.tagName())){
                Log.d(TAG, "fm-cover: " + element.attr("src"));
                volCover = element.attr("src");
                break;
            }
        }
        return volCover;
    }

    public long getVolId() throws IOException {
        Document document = getDocument();
        String volId = "0";
        Pattern digitPatternString = Pattern.compile("\\.");
        for (Element element : document.getElementsByClass("current-vol")) {
            if ("div".equals(element.tagName())){
                String vol = element.text();
                
                Log.d(TAG, digitPatternString.split(vol)[1]);
                volId = digitPatternString.split(vol)[1].trim();
                break;
            }
        }
        return Long.valueOf(volId);
    }

    public Volume getVolMetaInfo() {
        if (TextUtils.isEmpty(mUrl)) {
            return null;
        }
        Volume volMetaInfo = null;
        try {
            Document document = getDocument();
            document.getElementsByClass("fm-title");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return volMetaInfo;
    }

    public ArrayList<Music> getMusicList(long volId) throws IOException {
        Document document = getDocument();
        ArrayList<Music> musicMetaInfos = new ArrayList<Music>();
        for (Element element : document.getElementsByClass("track")) {
            if ("div".equals(element.tagName())){
                Music metaInfo = new Music(volId);
                metaInfo.setTrackId(getTrackId(element));
                metaInfo.setName(getTrackName(element));
                metaInfo.setAlbum(getTrackAlbum(element));
                metaInfo.setCoverUri(Uri.parse(getTrackCover(element)));
                Uri trackUri = Uri.parse(LuooConstantUtils
                        .buildMusicTrackUri(volId, metaInfo.getTrackId()));
                metaInfo.setTrackUri(trackUri);
                Log.d(TAG, "Track info: " + metaInfo);
                musicMetaInfos.add(metaInfo);
            }
        }
        return musicMetaInfos;
    }

    public long getTrackId(Element parentElement) throws IOException {
        String trackIdString = "0";
        for (Element element : parentElement.getElementsByClass("track-num")) {
            if ("span".equals(element.tagName())){
                Log.d(TAG, "track-num:" + element.text());
                trackIdString = element.text();
                break;
            }
        }
        return Long.valueOf(trackIdString);
    }

    public String getTrackCover(Element parentElement) throws IOException {
        String trackCover = "";
        for (Element element : parentElement.getElementsByClass("track-cover")) {
            if ("img".equals(element.tagName())){
                Log.d(TAG, "track-cover:" + element.attr("src"));
                trackCover = element.attr("src");
                break;
            }
        }
        return trackCover;
    }

    public String getTrackName(Element parentElement) throws IOException {
        String trackName = "";
        for (Element element : parentElement.getElementsByClass("track-name")) {
            if ("a".equals(element.tagName())){
                Log.d(TAG, "track-name:" + element.text());
                trackName = element.text();
                break;
            }
        }
        return trackName;
    }

    public String getTrackAlbum(Element parentElement) throws IOException {
        String trackAlbum = "";
        for (Element element : parentElement.getElementsByClass("track-album")) {
            if ("a".equals(element.tagName())){
                Log.d(TAG, "track-album:" + element.text());
                trackAlbum = element.text();
                break;
            }
        }
        return trackAlbum;
    }

    public Element getElementById(String id) throws IOException {
        Document document = getDocument();
        return document.getElementById(id);
    }

    public Elements getElementsByClass(String className) throws IOException {
        Document document = getDocument();
        return document.getElementsByClass(className);
    }

    public static Volume getVolMetaInfo(String url) {
        Volume volMetaInfo = new Volume();
        return volMetaInfo;
    }

    public static Document getDocument(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        return document;
    }

    private static Elements getElementsbyTag(Document doc, String tag) {
        Elements elements = doc.getElementsByTag(tag);
        return elements;
    }
}
