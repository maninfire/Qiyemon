package com.qiye.txz.qiyemon;

import android.os.Process;

import com.qiye.txz.qiyemon.utils.Logger;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

public class HKindexof extends XC_MethodHook implements HKmethod{
    private String mClassName;
    private String mMethodName;
    //private MethodApiType mType;
    private String mType;
    private boolean mThisObject= true;

    public HKindexof(String className, String methodName, boolean thisObject,/*MethodApiType*/String type){
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
    protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) {
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

    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam arg1) {
    }
    public static void logGenericMethod(XC_MethodHook.MethodHookParam param, boolean mThisObject, /*String*/ String mType) throws JSONException {
        //JSONObject hookJson = ParseGeneratorNotype.generateHookDataJson(param,mType);
        String hookJson ="{";
        List<String> paraname=new ArrayList<>();
        //paraname.add("object");
        if(param.args!=null)//&&paraname!=null
            hookJson+="\"Parameters\":"+"{"+parseArgs(param,hookJson,paraname)+"},";
        if(param.getResult()!=null)
            hookJson+="\"result\":"+(String)param.getResult()+",";
        if(param.thisObject!=null)
            hookJson+="\"this\":"+(String)param.thisObject+",";
        hookJson+=addHookDataJson(param,mType)+"}";

        Logger.logHookextra(hookJson);
    }
    public static String addHookDataJson(XC_MethodHook.MethodHookParam param, String type) throws JSONException
    {
        //JSONObject hookData= new JSONObject();
        String hookData="";
        hookData+="\"PID\":"+Process.myPid()+",";
        hookData+="\"TID\":"+Process.myTid()+",";
        hookData+="\"UID\":"+ Process.myUid()+",";
        hookData+="\"Method\":"+ param.method.getDeclaringClass().getName()+"->"+param.method.getName()+",";
        //hookData.put("method", param.method.getName());
        hookData+="\"Function\":"+ type+",";
        hookData+="\"Time\":"+System.currentTimeMillis()+",";
        return hookData;
    }

    public static String parseArgs(XC_MethodHook.MethodHookParam param, String hookJson, List<String> paraname)
    {
        String args = "";
        int i=0;
        for (Object object : (Object[]) param.args) {
            try {
                if(object!=null)
                    if(paraname.get(i)!=null){
                        args+=paraname.get(i)+ object;
                    }else{
                        args+=""+ object;
                    }
                else{
                    if(paraname.get(i)!=null){
                        args+=paraname.get(i)+"";
                    }else{
                        args+=""+"";
                    }
                }
            } catch (Exception e) {
                Logger.logShell("args error: " + e.getMessage()+" "+hookJson.toString());
            }
            i++;
        }
        return args;
    }
    public void monitorMethod(XC_MethodHook.MethodHookParam param)
    {
        try {
            /*
            if(param.method.getName().contains("invoke"))
                Logger.logReflectionMethod(param,mThisObject,mType);
            else if(param.method.getName().contains("write"))
                Logger.logProcessWriteMethod(param,mThisObject,mType);
            else if(param.method.getName().contains("read"))
                Logger.logProcessReadMethod(param,mThisObject,mType);
                //else if(param.method.getName().contains("openDexFile") || param.method.getName().equals("load"))
                // Logger.logAndDumpFile(param,mThisObject,mType);
            else*/
            logGenericMethod(param,mThisObject,mType);
        } catch (Exception e) {
            XposedBridge.log(e);
            Logger.logError(param.method.getDeclaringClass().getName()+"->"+param.method.getName());
        }
    }
    public void traceMethod(XC_MethodHook.MethodHookParam param)
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
