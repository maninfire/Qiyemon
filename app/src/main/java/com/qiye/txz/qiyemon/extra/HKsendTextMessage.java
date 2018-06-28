package com.qiye.txz.qiyemon.extra;

import org.json.JSONObject;

import de.robv.android.xposed.XC_MethodHook;

/**
 * Created by luobin on 2018/6/21.
 */

public class HKsendTextMessage extends XC_MethodHook{

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        super.afterHookedMethod(param);

    }

    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        super.beforeHookedMethod(param);
        JSONObject jsonLog = new JSONObject();
        jsonLog.put("Function", "Send Message");
        jsonLog.put("Method", "android.telephony.SmsManager->sendTextMessage");

        JSONObject paramJson = new JSONObject();
        //获取目标号码
        if (param.args[0] == null){
            paramJson.put("destinationAddress", "");
        }else {
            paramJson.put("destinationAddress", param.args[0]);
        }

        //获取源地址
        if (param.args[1] == null){
            paramJson.put("scAddress", "");
        }else {
            paramJson.put("scAddress", param.args[1]);
        }

        //获取发送内容
        if (param.args[2] == null){
            paramJson.put("text", "");
        }else {
            paramJson.put("text", param.args[2]);
        }

        jsonLog.put("Parameters", paramJson);

        LogParse.getHookParam(jsonLog);
    }
}
