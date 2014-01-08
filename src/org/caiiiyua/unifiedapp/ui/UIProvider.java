package org.caiiiyua.unifiedapp.ui;

import android.provider.BaseColumns;

public class UIProvider {

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
    }

    public static final String[] VOLUME_PROJECTION = {
        BaseColumns._ID,
        VolumeColumns.VOL_NUM,
        VolumeColumns.VOL_TOPIC,
        VolumeColumns.VOL_DESCRIPTION,
        VolumeColumns.MUSIC_LIST_KEY,
        VolumeColumns.COVER_URI,
        VolumeColumns.VOL_URL
    };

    public static final int VOLUME_COLUMN_ID = 0;
    public static final int VOLUME_COLUMN_VOL_NUM = 1;
    public static final int VOLUME_COLUMN_VOL_TOPIC = 2;
    public static final int VOLUME_COLUMN_VOL_DESCRIPITON = 3;
    public static final int VOLUME_COLUMN_MUSIC_LIST_KEY = 4;
    public static final int VOLUME_COLUMN_COVER_URI = 5;
    public static final int VOLUME_COLUMN_VOL_URL = 6;
}
