package org.caiiiyua.unifiedapp;
import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;


public class UnifiedApplication extends Application {

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
            super.onCreate();
            initImageLoader(getApplicationContext());
    }

    public static void initImageLoader(Context context) {
            // This configuration tuning is custom. You can tune every option, you may tune some of them,
            // or you can create default configuration by
            //  ImageLoaderConfiguration.createDefault(this);
            // method.
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                            .threadPriority(Thread.NORM_PRIORITY - 2)
                            .memoryCacheSize(8 * 1024 * 1024)
                            .discCacheSize(32 * 1024 * 1024)
                            .discCacheFileNameGenerator(new Md5FileNameGenerator())
                            .writeDebugLogs() // Remove for release app
                            .build();
            // Initialize ImageLoader with configuration.
            ImageLoader.getInstance().init(config);
    }
}
