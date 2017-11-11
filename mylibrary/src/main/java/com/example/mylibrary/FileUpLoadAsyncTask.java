package com.example.mylibrary;

import android.os.AsyncTask;

/**
 * Created by guhongliang on 2017/9/26.
 */

public class FileUpLoadAsyncTask extends AsyncTask<String, Integer, String> {
    private static String TAG = "FileUpLoadAsyncTask";

    public FileUpLoadAsyncTask() {
        super();
    }

    @Override
    protected void onPreExecute() {
        DLog.e(TAG, "onPreExecute--->" + Thread.currentThread().getName());

        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        DLog.e(TAG, "onPostExecute--->" + Thread.currentThread().getName());
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        DLog.e(TAG, "onProgressUpdate--->" + Thread.currentThread().getName());
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(String s) {
        DLog.e(TAG, "onCancelled--->" + Thread.currentThread().getName());
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        DLog.e(TAG, "onCancelled--->" + Thread.currentThread().getName());
        super.onCancelled();
    }

    @Override
    protected String doInBackground(String... strings) {
        DLog.e(TAG, "doInBackground--->" + Thread.currentThread().getName());

        return null;
    }

    private void downLoad(String[] strings) {
        long totalByte = 0;
        //遍历url地址，依次下载对应的文件
        for (String url : strings
                ) {
            Object[] result = downloadSingleFile(url);
            int byteCount = (int) result[0];
            totalByte += byteCount;
            publishProgress(byteCount);
        }
    }

    private Object[] downloadSingleFile(String url) {
        return new Object[0];
    }


}
