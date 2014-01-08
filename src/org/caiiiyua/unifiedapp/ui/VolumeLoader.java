package org.caiiiyua.unifiedapp.ui;

import org.caiiiyua.unifiedapp.content.CursorCreator;
import org.caiiiyua.unifiedapp.content.ObjectCursor;
import org.caiiiyua.unifiedapp.content.ObjectCursorLoader;
import org.caiiiyua.unifiedapp.musicplayer.Volume;

import android.content.Context;
import android.net.Uri;

public class VolumeLoader extends ObjectCursorLoader<Volume> {

    public VolumeLoader(Context context, Uri uri, String[] projection,
            CursorCreator<Volume> factory) {
        super(context, uri, projection, factory);
        // TODO Auto-generated constructor stub
    }

    @Override
    public ObjectCursor<Volume> loadInBackground() {
        return super.loadInBackground();
    }

}
