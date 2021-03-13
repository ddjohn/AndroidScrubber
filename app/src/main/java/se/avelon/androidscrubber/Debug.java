package se.avelon.androidscrubber;

import android.util.Log;
import se.avelon.androidscrubber.fragments.DebugFragment;

public class Debug {
    private static final String TAG = "daidalos";

    public static void e(String tag, String msg) {
        Log.e(TAG + ":" + tag, msg);
        DebugFragment.log(tag + ":\n" + msg + "\n");
    }

    public static void e(String tag, String msg, Exception e) {
        Log.e(TAG + ":" + tag, msg, e);
        DebugFragment.log(tag + ":\n" + msg + "\n" + e + "\n");
    }

    public static void w(String tag, String msg) {
        Log.w(TAG + ":" + tag, msg);
        DebugFragment.log(tag + ":\n" + msg + "\n");
    }

    public static void i(String tag, String msg) {
        DebugFragment.log(tag + ":\n" + msg + "\n");
    }

    public static void d(String tag, String msg) {
        Log.d(TAG + ":" + tag, msg);
        DebugFragment.log(tag + ":\n" + msg + "\n");
    }
}
