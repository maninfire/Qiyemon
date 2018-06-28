package com.qiye.txz.qiyemon.extra;

import org.json.JSONObject;

import de.robv.android.xposed.XC_MethodHook;

/**
 * Created by luobin on 2018/6/21.
 */

public class HKgetSubscriberId extends XC_MethodHook{

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        super.afterHookedMethod(param);
        JSONObject jsonLog = new JSONObject();
        jsonLog.put("Function", "Get IMSI");
        jsonLog.put("Method", "android.telephony.TelephonyManager->getSubscriberId");
        LogParse.getHookParam(jsonLog);
    }

    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        super.beforeHookedMethod(param);
    }
}
