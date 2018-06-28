package com.qiye.txz.qiyemon.extra;

import android.app.PendingIntent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by luobin on 2018/6/21.
 */

public class XHookFramework implements IXposedHookLoadPackage{
    private XC_LoadPackage.LoadPackageParam  gLoadPackParam = null;
    private static Set OS_APPS;

    static {
        XHookFramework.OS_APPS = new HashSet(Arrays.asList(new String[]{"com.android.systemui",
                "com.android.inputmethod.latin", "com.android.phone", "com.android.launcher",
                "com.android.settings", "com.android.contacts", "com.android.providers.calendar",
                "android.process.media", "com.android.mms", "com.android.deskclock",
                "com.android.email", "com.android.exchange", "eu.chainfire.supersu",
                "com.noshufou.android.su", "com.android.calendar", "com.mk.mkbehaviormonitor",
                "de.robv.android.xposed.installer", "com.android.defcontainer","com.kingroot.kinguser","com.example.zhangzhenguo.droidmon"}));
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        gLoadPackParam = loadPackageParam;
        //系统本身应用不HOOK
        if (loadPackageParam.appInfo != null && (
                (loadPackageParam.appInfo.flags & 129) == 0 &&
                (loadPackageParam.isFirstApplication) &&
                !XHookFramework.OS_APPS.contains(loadPackageParam.packageName)
        )){
            XposedBridge.log("loaded: " + loadPackageParam.packageName);
            HookMain();
        }
    }
    public void setandhookgLoadPackParam(XC_LoadPackage.LoadPackageParam loadPackageParam){
        gLoadPackParam = loadPackageParam;
        HookMain();
    }
    private void HookMain(){
        //hookTelephonyManager();
        //hookSmsManager();
        //hookDevPolicyManager();
        //TODO
        hooktoString();
    }
    private void hooktoString(){
        hook_method("java.lang.StringBuilder", gLoadPackParam.classLoader, "toString", new Object
                []{new HKtoString()});
    }
    private void hookSmsManager(){
        hook_method("android.telephony.SmsManager", gLoadPackParam.classLoader, "sendTextMessage", new Object
                []{String.class, String.class, String.class, PendingIntent.class, PendingIntent.class,
                new HKsendTextMessage()});
    }

    private void hookDevPolicyManager(){
        hook_method("android.app.admin.DevicePolicyManager", gLoadPackParam.classLoader, "lockNow", new Object[] {new HKlockNow()});
    }

    private void hookTelephonyManager(){
        hook_method("android.telephony.TelephonyManager", gLoadPackParam.classLoader, "getVoiceMailNumber", new Object[]{new HKgetVoiceMailNumber()});
        hook_method("android.telephony.TelephonyManager", gLoadPackParam.classLoader, "getLine1Number", new Object[]{new HKgetLine1Number()});
        hook_method("android.telephony.TelephonyManager", gLoadPackParam.classLoader, "getDeviceId", new Object[]{new HKgetDeviceId()});
        hook_method("android.telephony.TelephonyManager", gLoadPackParam.classLoader, "getSubscriberId", new Object[]{new HKgetSubscriberId()});
        hook_method("android.telephony.TelephonyManager", gLoadPackParam.classLoader, "getSimSerialNumber", new Object[]{new HKgetSimSerialNumber()});
    }

    private void hook_method(String classname, ClassLoader classLoader, String methodName, Object... parameterTypesAndCallback){
        try{
            XposedHelpers.findAndHookMethod(classname, classLoader, methodName, parameterTypesAndCallback);
        }catch (Exception e){
            XposedBridge.log(e);
        }
    }
}
