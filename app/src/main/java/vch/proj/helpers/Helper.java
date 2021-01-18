package vch.proj.helpers;

import android.util.Log;

public class Helper {
    public static final String MY_LOG = "my_log";
    public static final String prefix = "___";

    public static void l(String data) { Log.e(MY_LOG, prefix + data); }
    public static void l(int data) { Log.e(MY_LOG, prefix + String.valueOf(data)); }
    public static void l(Long data) { Log.e(MY_LOG, prefix + String.valueOf(data)); }
    public static void l(double data) { Log.e(MY_LOG, prefix + String.valueOf(data)); }
    public static void l(Object... data) {
        for (Object object : data) {
            Log.e(MY_LOG, prefix + object.toString());
        }
    }
}