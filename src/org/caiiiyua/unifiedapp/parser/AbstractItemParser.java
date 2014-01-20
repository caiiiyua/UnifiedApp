package org.caiiiyua.unifiedapp.parser;

import org.caiiiyua.unifiedapp.utils.LogUtils;
import org.jsoup.nodes.Element;

import android.text.TextUtils;
import android.util.Log;

public class AbstractItemParser implements ItemParser {

    protected Element mParser;

    public AbstractItemParser(Element parser) {
        mParser = parser;
    }

    protected Element getElementByClass(String className, String tag) {
        LogUtils.d(LogUtils.TAG, "getElementByClass %s with tag: %s", className, tag);
        for (Element element : mParser.getElementsByClass(className)) {
            if (TextUtils.isEmpty(tag) || tag.equals(element.tagName())){
                return element;
            }
        }
        return mParser;
    }

    @Override
    public Object parse() {
        // TODO Auto-generated method stub
        return null;
    }
}
