
package com.geili.koudai.util;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class PatchUtil extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_jni);
        try {
            Log.d("PatchUtil", "onCreate(3)," + getPackageCodePath());
            TextView tv = (TextView) findViewById(R.id.text);
            tv.setText(stringFromJNI() + getString(R.string.app_name));
            String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
            String oldApkParh = sdcard + File.separator + "geili/test/1.apk";
            createAPKByDiff(getPackageCodePath(), sdcard + File.separator + "geili/test/hanqw.apk",
                    sdcard + File.separator + "geili/test/patch_file");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_hello_jni, menu);
        return true;
    }

    public native int createAPKByDiff(String srcFileParh, String outFilePath, String diffFilePath);

    public native String stringFromJNI();

    /**
     * @param srcFilePath
     * @param targetFilePath
     * @return
     * @throws IOException
     */
    public native int createDiffData(String srcFilePath, String diffFile, String targetFilePath);

    /*
     * this is used to load the 'koudaipatch' library on application startup.
     * The library has already been unpacked into
     * /data/data/com.geili.koudai.util/lib/libkoudaipatch.so at installation
     * time by the package manager.
     */
    static {
        System.loadLibrary("koudaipatch");
    }
}
