package com.qiye.txz.qiyemon;

import android.os.Process;

import com.qiye.txz.qiyemon.InstrumentationManager;
import com.qiye.txz.qiyemon.utils.Logger;
import com.qiye.txz.qiyemon.utils.ParseGeneratorNotype;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;

public class HKequals extends XC_MethodHook implements HKmethod{
    private String mClassName;
    private String mMethodName;
    //private MethodApiType mType;
    private String mType;
    private boolean mThisObject= true;

    public HKequals(String className, String methodName, boolean thisObject,/*MethodApiType*/String type){
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
    protected void afterHookedMethod(MethodHookParam param) {
        if (!param.hasThrowable())
            try {
                if (Process.myUid() <= 0)
                    return;
                if (InstrumentationManager.TRACE)
                {
                    //XposedBridge.log("true");
                    traceMethod(param);      //这里设置了false
                }
                else
                {
                    //XposedBridge.log("false");
                    monitorMethod(param);
                }
            } catch (Throwable ex) {
                throw ex;
            }
    }

    protected void beforeHookedMethod(MethodHookParam arg1) {
    }
    public static void logGenericMethod(MethodHookParam param, boolean mThisObject, /*String*/ String mType) throws JSONException {
        //JSONObject hookJson = ParseGeneratorNotype.generateHookDataJson(param,mType);
        JSONObject hookJson = new JSONObject();
        List<String> paraname=new ArrayList<> ();
        paraname.add("object");
        if(param.args!=null)//&&paraname!=null
                hookJson.put("Parameters", ParseGeneratorNotype.parseArgs(param,hookJson,paraname));
        if(param.getResult()!=null)
            hookJson.put("result", ParseGeneratorNotype.parseResults(param,hookJson));
        if(param.thisObject!=null && mThisObject)
            hookJson.put("this",ParseGeneratorNotype.parseThis(param,hookJson));
        hookJson=ParseGeneratorNotype.addHookDataJson(hookJson,param,mType);
        Logger.logHook(hookJson);
    }
    public void monitorMethod(MethodHookParam param)
    {
        try {
            if(param.method.getName().contains("invoke"))
                Logger.logReflectionMethod(param,mThisObject,mType);
            else if(param.method.getName().contains("write"))
                Logger.logProcessWriteMethod(param,mThisObject,mType);
            else if(param.method.getName().contains("read"))
                Logger.logProcessReadMethod(param,mThisObject,mType);
            //else if(param.method.getName().contains("openDexFile") || param.method.getName().equals("load"))
               // Logger.logAndDumpFile(param,mThisObject,mType);
            else
                logGenericMethod(param,mThisObject,mType);
        } catch (Exception e) {
            Logger.logError(param.method.getDeclaringClass().getName()+"->"+param.method.getName());
        }
    }
    public void traceMethod(MethodHookParam param)
    {
        try {
            if(param.method.getName().contains("invoke"))
                Logger.logTraceReflectionMethod(param,mType);
            else
                Logger.logTraceMethod(param,mType);
        } catch (Exception e) {
            Logger.logError( param.method.getDeclaringClass().getName()+"->"+param.method.getName());
        }
    }
}
