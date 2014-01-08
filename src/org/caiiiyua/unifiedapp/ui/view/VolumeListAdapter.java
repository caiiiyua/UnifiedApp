package org.caiiiyua.unifiedapp.ui.view;

import org.caiiiyua.unifiedapp.ui.ControllableActivity;
import org.caiiiyua.unifiedapp.ui.UIProvider;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class VolumeListAdapter extends SimpleCursorAdapter {

    private Context mContext;
    private ControllableActivity mActivity;
    private ListView mListView;

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // TODO Auto-generated method stub
        
    }

    public VolumeListAdapter(Context context, Activity activity, ListView listView,
            Cursor cursor) {
        super(context, -1, cursor, UIProvider.VOLUME_PROJECTION, null, 0);
        mContext = context;
        mActivity = (ControllableActivity) activity;
        mListView = listView;
    }

}
