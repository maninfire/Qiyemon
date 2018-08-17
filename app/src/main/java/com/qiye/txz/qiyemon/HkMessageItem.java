package com.qiye.txz.qiyemon;


import android.os.Message;

import com.qiye.txz.qiyemon.utils.Logger;
import com.qiye.txz.qiyemon.utils.MessageItem;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

public class HkMessageItem  extends XC_MethodHook implements HKmethod{
    private String mClassName;
    private String mMethodName;
    //private MethodApiType mType;
    private String mType;
    private boolean mThisObject= true;
    HkMessageItem(String className, String methodName, boolean thisObject,/*MethodApiType*/String type/*bY arg1*/) {
        //this.a = arg1;
        super();
        mClassName = className;
        mMethodName = methodName;
        mThisObject=thisObject;
        mType=type;

    }
    public String getClassName(){
        return mClassName;
    }

    public String getMethodName(){
        return mMethodName;
    }

    protected void afterHookedMethod(MethodHookParam arg2) {
       // Logger.MessageItemobject.remove();
    }

    protected void beforeHookedMethod(MethodHookParam arg5) {
        XposedBridge.log("hitMessageItem");
        Object v1 = arg5.args[0];
        if(v1 != null) {
            Object v0 = arg5.args[0];
            try {
                Field v2 = v1.getClass().getDeclaredField("target");
                v2.setAccessible(true);
                v1 = v2.get(v1);
                if(v1 == null) {
                    return;
                }
            }
            catch(Exception v0_1) {
                return;
            }

            String v1_1 = v1.getClass().getName();
            int v2_1 = ((Message)v0).what;
            if(/**/Logger.MessageItemobject.get() != null) {
                return;
            }

            Logger.MessageItemobject.set(new MessageItem(v1_1, v2_1));
        }
    }
}