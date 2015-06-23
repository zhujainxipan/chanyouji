package com.chanyouji.chanyouji.thread;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.chanyouji.chanyouji.util.ImageFileCache;
import com.chanyouji.chanyouji.util.ImageGetFromHttp;
import com.chanyouji.chanyouji.util.ImageMemoryCache;
import com.chanyouji.chanyouji.util.RoundImageUtil;

public class CacheImageAsyncTask extends AsyncTask<String, Integer, Bitmap> {
    private ImageView imageView;
    private ImageFileCache fileCache;
    private ImageMemoryCache memoryCache;
    private String imgType;
    private String url;


    public CacheImageAsyncTask(ImageView imageView, Context context) {
        this.imageView = imageView;
        fileCache = new ImageFileCache();
        memoryCache = new ImageMemoryCache(context);
    }


    /**
     * 加载图片给特定的imageview
     *
     * @param imageView
     */
    public CacheImageAsyncTask(ImageView imageView, Context context, String imgType) {
        this.imageView = imageView;
        fileCache = new ImageFileCache();
        memoryCache = new ImageMemoryCache(context);
        this.imgType = imgType;
    }


    public Bitmap getBitmap(String url) {

        // 从内存缓存中获取图片
        Bitmap result = memoryCache.getBitmapFromCache(url);
        if (result == null) {
            // 文件缓存中获取
            result = fileCache.getImage(url);
            if (result == null) {
                // 从网络获取
                result = ImageGetFromHttp.downloadBitmap(url);
                if (result != null) {
                    fileCache.saveBitmap(result, url);
                    memoryCache.addBitmapToCache(url, result);
                }
            } else {
                // 添加到内存缓存
                memoryCache.addBitmapToCache(url, result);
            }
        }
        return result;
    }

    protected Bitmap doInBackground(String... params) {
        url = params[0];
        return getBitmap(url);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            if (imageView != null) {
                if (imgType != null && imgType.equals("userico")) {
                    bitmap = RoundImageUtil.toRoundCorner(bitmap, 18);
                }
                Object tag = imageView.getTag();
                if (tag != null && tag instanceof String) {
                    String s = (String) tag;
                    if (s.equals(url)) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
        }
    }
}