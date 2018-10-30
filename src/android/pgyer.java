package cn.x1ongzhu.pgyer;

import android.util.Log;

import com.pgyersdk.update.PgyUpdateManager;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;

import org.apache.cordova.CordovaWebView;

/**
 * This class echoes a string called from JavaScript.
 */
public class pgyer extends CordovaPlugin {
    @Override
    public void initialize(final CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                boolean force = preferences.getBoolean("forceUpdate", false);
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
