package org.caiiiyua.unifiedapp.ui;

import org.caiiiyua.unifiedapp.provider.UnifiedContentProvider.Volumes.VolumeColumns;

import android.provider.BaseColumns;

public class UIProvider {

    public static final String CONTENT_PREFIX = "content://";
    public static final String AUTHORITY = "org.caiiiyua.unifiedapp.provider";

    public static final String VOLUME_BASE_URI = CONTENT_PREFIX + AUTHORITY + "/volumes";

    public static final class VolumeColumns implements BaseColumns {
        /**
         * This string column contains the human readable of Volume number
         */
        public static final String VOL_NUM = "vol_id";
        /**
         * This string column contains the human readable of Volume topic
         */
        public static final String VOL_TOPIC = "topic";
        /**
         * This string column contains the human readable of Volume description
         */
        public static final String VOL_DESCRIPTION = "description";
        /**
         * This string column contains the human readable of Volume music list
         */
        public static final String MUSIC_LIST_KEY = "music_list_key";
        /**
         * This string column contains the human readable of Volume cover
         */
        public static final String COVER_URI = "cover_uri";
        /**
         * This string column contains the human readable of Volume topic
         */
        public static final String VOL_URL = "vol_url";
        /**
         * This string column contains the human readable of Volume category
         */
        public static final String VOL_CATEGORY = "vol_category";
        /**
         * This string column contains the human readable of Volume category id
         */
        public static final String VOL_CATEGORY_ID = "vol_category_id";
        /**
         * This string column contains the human readable of Volume tag
         */
        public static final String VOL_TAG = "vol_tag";
        /**
         * This string column contains the human readable of Volume tag id
         */
        public static final String VOL_TAG_ID = "vol_tag_id";
        /**
         * This string column contains the human readable of Volume dat
         */
        public static final String VOL_DATE = "vol_date";
    }

    public static final String[] VOLUME_PROJECTION = {
        BaseColumns._ID,
        VolumeColumns.VOL_NUM,
        VolumeColumns.VOL_TOPIC,
        VolumeColumns.VOL_DESCRIPTION,
        VolumeColumns.MUSIC_LIST_KEY,
        VolumeColumns.COVER_URI,
        VolumeColumns.VOL_URL,
        VolumeColumns.VOL_CATEGORY,
        VolumeColumns.VOL_CATEGORY_ID,
        VolumeColumns.VOL_TAG,
        VolumeColumns.VOL_TAG_ID,
        VolumeColumns.VOL_DATE
    };

    public static final int VOLUME_COLUMN_ID = 0;
    public static final int VOLUME_COLUMN_VOL_NUM = 1;
    public static final int VOLUME_COLUMN_VOL_TOPIC = 2;
    public static final int VOLUME_COLUMN_VOL_DESCRIPITON = 3;
    public static final int VOLUME_COLUMN_MUSIC_LIST_KEY = 4;
    public static final int VOLUME_COLUMN_COVER_URI = 5;
    public static final int VOLUME_COLUMN_VOL_URL = 6;
    public static final int VOLUME_COLUMN_VOL_CATEGORY = 7;
    public static final int VOLUME_COLUMN_VOL_CATEGORY_ID = 8;
    public static final int VOLUME_COLUMN_VOL_TAG = 9;
    public static final int VOLUME_COLUMN_VOL_TAG_ID = 10;
    public static final int VOLUME_COLUMN_VOL_DATE = 11;
}
