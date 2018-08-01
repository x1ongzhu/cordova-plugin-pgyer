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
                PgyUpdateManager.setIsForced(force);
                PgyUpdateManager.register(cordova.getActivity());
            }
        });
    }
}
