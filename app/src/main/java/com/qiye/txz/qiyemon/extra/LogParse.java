package com.qiye.txz.qiyemon.extra;

import android.os.Process;
import com.qiye.txz.qiyemon.utils.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luobin on 2018/6/21.
 */

public class LogParse {
    public static void getHookParam(JSONObject jsobj){
        JSONObject tempJsonObj;
        try {
            jsobj.put("PID", Process.myPid());
            jsobj.put("TID", Process.myTid());
            jsobj.put("UID", Process.myUid());

            //XposedBridge.log(jsobj.toString());
            Logger.logHook(jsobj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
