package cn.x1ongzhu.pgyer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.pgyersdk.update.PgyUpdateManager;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;

import org.apache.cordova.CordovaWebView;
import org.json.JSONException;

/**
 * This class echoes a string called from JavaScript.
 */
public class pgyer extends CordovaPlugin {
    @Override
    public void initialize(final CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(0);
        } else {
            checkUpdate();
        }
    }

    @Override
    public void requestPermissions(int requestCode) {
        super.requestPermissions(requestCode);
        cordova.requestPermissions(this, requestCode,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE});
    }

    @Override
    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) throws JSONException {
        super.onRequestPermissionResult(requestCode, permissions, grantResults);
        boolean granted = true;
        for (int res : grantResults) {
            if (res != PackageManager.PERMISSION_GRANTED) {
                granted = false;
                break;
            }
        }
        if (granted) {
            checkUpdate();
        }
    }

    private void checkUpdate() {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                boolean force = preferences.getBoolean("forceUpdate", true);
                Log.d("forceUpdate", force + "");
                new PgyUpdateManager.Builder()
                        .setForced(force)                //设置是否强制更新
                        .setUserCanRetry(true)         //失败后是否提示重新下载
                        .setDeleteHistroyApk(false)     // 检查更新前是否删除本地历史 Apk， 默认为true
                        .register();
            }
        });
    }
}
