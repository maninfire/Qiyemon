package com.qiye.txz.qiyemon.extra;

import org.json.JSONObject;

import de.robv.android.xposed.XC_MethodHook;

/**
 * Created by luobin on 2018/6/21.
 */

public class HKgetDeviceId extends XC_MethodHook{

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        super.afterHookedMethod(param);
        JSONObject jsonLog = new JSONObject();
        jsonLog.put("Function", "Get DevicesID");
        jsonLog.put("Method", "android.telephony.TelephonyManager->getDeviceId");
        LogParse.getHookParam(jsonLog);
    }

    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        super.beforeHookedMethod(param);
    }
}
