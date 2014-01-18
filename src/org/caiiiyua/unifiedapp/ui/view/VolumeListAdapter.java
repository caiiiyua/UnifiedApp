package org.caiiiyua.unifiedapp.ui.view;

import org.caiiiyua.unifiedapp.R;
import org.caiiiyua.unifiedapp.ui.ControllableActivity;
import org.caiiiyua.unifiedapp.ui.UIProvider;
import org.caiiiyua.unifiedapp.utils.LogUtils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class VolumeListAdapter extends CursorAdapter {

    private Context mContext;
    private ControllableActivity mActivity;
    private ListView mListView;

    static class ViewHolder {
        TextView lableView;
        TextView descriptionView;
        ImageView coverView;
    }

    public VolumeListAdapter(Context context, Activity activity, ListView listView, Cursor c) {
        super(context, c, 0);
        mContext = context;
        mActivity = (ControllableActivity) activity;
        mListView = listView;
    }

    private View newVolumeCellView(Context context, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(context);
        View cell = inflater.inflate(R.layout.volume_cell, null);
        viewHolder.lableView = (TextView) cell.findViewById(R.id.vol_id_lable);
        viewHolder.descriptionView = (TextView) cell.findViewById(R.id.vol_description);
        viewHolder.coverView = (ImageView) cell.findViewById(R.id.cover);
        cell.setTag(viewHolder);
        return cell;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        if (cursor == null || cursor.isClosed()) {
            return null;
        }
        return newVolumeCellView(context, parent);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor == null || cursor.isClosed()) {
            return;
        }
        View convertView = view;
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = newVolumeCellView(context, null);
        }
        viewHolder = (ViewHolder) convertView.getTag();

        String lable = cursor.getString(UIProvider.VOLUME_COLUMN_VOL_TOPIC);
        String description = cursor.getString(UIProvider.VOLUME_COLUMN_VOL_DESCRIPITON);
        Uri coverUri = Uri.parse(cursor.getString(UIProvider.VOLUME_COLUMN_COVER_URI));
        long volNum = cursor.getLong(UIProvider.VOLUME_COLUMN_VOL_NUM);

        viewHolder.lableView.setText(lable);
        viewHolder.descriptionView.setText(description);
        viewHolder.coverView.setImageResource(R.drawable.cover);
        if (coverUri != null) {
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.cover)
                    .showImageForEmptyUri(R.drawable.cover)
                    .showImageOnFail(R.drawable.cover)
                    .cacheInMemory(true)
                    .cacheOnDisc(true)
                    .considerExifParams(true)
                    .build();

            ImageLoader.getInstance().loadImage(coverUri.toString(),
                    options, new ImageLoadingListener() {
                        
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {
                        }
                        
                        @Override
                        public void onLoadingFailed(String imageUri, View view,
                                FailReason failReason) {
                            // TODO Auto-generated method stub
                            
                        }
                        
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            viewHolder.coverView.setImageBitmap(loadedImage);
                        }
                        
                        @Override
                        public void onLoadingCancelled(String imageUri, View view) {
                            // TODO Auto-generated method stub
                            
                        }
                    });
//            viewHolder.coverView.setImageURI(coverUri);
//            viewHolder.coverView.setImageResource(R.drawable.cover);
        } else {
            viewHolder.coverView.setImageResource(R.drawable.cover);
        }
        LogUtils.d(LogUtils.TAG, "BindView with pageNum:, volNum: %d, lable: %s, description: %s, cover: %s",
                volNum, lable, description, coverUri);
    }

}
