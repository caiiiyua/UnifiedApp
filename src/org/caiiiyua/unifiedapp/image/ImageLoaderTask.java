package org.caiiiyua.unifiedapp.image;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.caiiiyua.unifiedapp.R;
import org.caiiiyua.unifiedapp.content.EasyAsyncTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;


public class ImageLoaderTask extends EasyAsyncTask<Void, Void, String>{

    private String mImageUri;
    private boolean mDecodeOnly = false;
    private final WeakReference<ImageView> mImageViewReference;
    private int mVolNum = 0;

    public ImageLoaderTask(ImageView view, String uri, int volNum,Tracker tracker) {
        super(tracker);
        mImageViewReference = new WeakReference<ImageView>(view);
        mImageUri = uri;
        mVolNum  = volNum;
        if (mImageUri.startsWith("content://")) {
            mDecodeOnly = true;
        }
    }

    public Bitmap loadImageUri() {
        URLConnection conn;
        Bitmap bitmap = null;
        try {
            conn = new URL(mImageUri).openConnection();
            conn.connect();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
//            BitmapUtils.cacheImage(BitmapUtils.decodeSampledBitmapFromBitmap(bitmap,
//                    R.dimen.volume_cover_weigth, R.dimen.volume_cover_height),
//                    "Vol-" + mVolNum + ".jpg");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected String doInBackground(Void... params) {
        String uri = mImageUri;
        Bitmap bitmap = null;
        if (!mDecodeOnly) {
            bitmap = loadImageUri();
            uri = cacheImage(bitmap);
        }
        return uri;
    }

    private String cacheImage(Bitmap bitmap) {
        String fileName = "Vol-" + mVolNum + ".jpg";
        String uri = "";
        Bitmap image = null;
        if (!mDecodeOnly) {
            image = BitmapUtils.decodeSampledBitmapFromBitmap(bitmap,
                    R.dimen.volume_cover_weigth, R.dimen.volume_cover_height);
            uri = BitmapUtils.cacheImage(bitmap, fileName);
        }
        return uri;
    }

    @Override
    protected void onSuccess(String uri) {
        if (!TextUtils.isEmpty(uri)) {
            ImageView imageView = mImageViewReference.get();
            imageView.setImageURI(Uri.parse(uri));
        }
    }
}
