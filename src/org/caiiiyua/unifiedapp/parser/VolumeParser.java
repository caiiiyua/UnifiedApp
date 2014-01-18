package org.caiiiyua.unifiedapp.parser;

import org.caiiiyua.unifiedapp.musicplayer.Volume;
import org.caiiiyua.unifiedapp.utils.LogUtils;
import org.jsoup.nodes.Element;

import android.text.TextUtils;

public class VolumeParser extends AbstractItemParser implements ItemParser {


    private long mVolNum;
    private String mTopic;
    private String mDescription;
    private String mUrl;
    private String mCoverUrl;
    private String mVolDate;
    private String mVolCategory;
    private String mVolTag;
    private long mVolTagId;

    public VolumeParser(Element parser) {
        super(parser);
    }

    public long getVolId() {
        long volId = 0;
        for (Element element : mParser.getElementsByClass("description")) {
            LogUtils.d(LogUtils.TAG, "Description: %s", element.text());
            for (Element sub : element.getElementsByTag("a")) {
                String cateString = sub.attr("href");
                if (cateString.contains("?")) {
                    mVolTag = sub.text();
                    mVolTagId = Long.valueOf(cateString.substring(cateString.lastIndexOf("=") + 1));
                } else {
                    mVolCategory = cateString.substring(cateString.lastIndexOf("/") + 1);
                }
            }
        }
        if (!TextUtils.isEmpty(mUrl)) {
            volId = Long.valueOf(mUrl.substring(mUrl.lastIndexOf("/") + 1));
            LogUtils.d(LogUtils.TAG, "Description volId: %d", volId);
        }
        return volId;
    }

    public String getTopic() {
        String topic = "";
        Element topicElement = getElementByClass("item-title", "a");
        LogUtils.d(LogUtils.TAG, "getTopic: %s", topicElement);
        topic = topicElement.text();
        mUrl = topicElement.attr("href");
        return topic;
    }

    public String getDescription() {
        String volDescription = "";
        Element element = getElementByClass("item-ct", null);
        volDescription = element.text();
        return volDescription;
    }

    public String getUrl() {
        if (!TextUtils.isEmpty(mUrl)) {
            return mUrl;
        }
        String url = getElementByClass("item-title", "a").attr("href");
        return url;
    }

    public String getCoverUrl() {
        String cover = "";
        cover = getElementByClass("vol-cover", "img").attr("src");
        cover = cover.replace(Volume.LIST_COVER, Volume.VOLUME_COVER);
        return cover;
    }

    public String getVolDate() {
        String dateString = "";
        for (Element element : mParser.getElementsByTag("span")) {
            dateString = element.text();
        }
        return dateString;
    }

    @Override
    public Volume parse() {
        if (mParser == null) return null;
        mTopic = getTopic();
        mVolNum = getVolId();
        mDescription = getDescription();
        mUrl = getUrl();
        mCoverUrl = getCoverUrl();
        mVolDate = getVolDate();
        mVolCategory = getCategory();
        return new Volume(mVolNum, mTopic, mDescription, mUrl,
                mCoverUrl, mVolDate, mVolCategory, mVolTag, mVolTagId);
    }

    private String getCategory() {
        return mVolCategory;
    }

}
