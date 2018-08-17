package com.qiye.txz.qiyemon;

import android.os.Process;

import com.qiye.txz.qiyemon.utils.Logger;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

public class HKheartbeat extends XC_MethodHook{
    private String mClassName;
    private String mMethodName;
    //private MethodApiType mType;
    private String mType;
    private boolean mThisObject= true;

    public void HKheartbeat(String type){
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
                XposedBridge.log("afterbeat");
                logGenericMethod(param,mThisObject,mType);
            } catch (Throwable ex) {
                throw ex;
            }
    }

    protected void beforeHookedMethod(MethodHookParam arg1) {
    }
    public static void logGenericMethod(MethodHookParam param, boolean mThisObject, /*String*/ String mType)  {
        //JSONObject hookJson = ParseGeneratorNotype.generateHookDataJson(param,mType);
        String hookJson ="{";

        List<String> paraname=new ArrayList<>();
        //paraname.add("object");
        if(param.args!=null)//&&paraname!=null
            hookJson+="\"Parameters\":"+"{"+parseArgs(param,hookJson,paraname)+"},";
        if(param.getResult()!=null)
           hookJson+="\"result\":"+"\""+param.getResult().toString()+"\",";
        if(param.thisObject!=null)
            hookJson+="\"this\":"+"\""+param.thisObject.toString()+"\",";
        hookJson+=addHookDataJson(param,mType)+"}";

        Logger.logHookextra(hookJson);
    }
    public static String addHookDataJson(MethodHookParam param,String type)
    {
        //JSONObject hookData= new JSONObject();
        String hookData="";
        hookData+="\"PID\":"+Process.myPid()+",";
        hookData+="\"TID\":"+Process.myTid()+",";
        hookData+="\"UID\":"+ Process.myUid()+",";
        hookData+="\"Method\":"+"\""+ param.method.getDeclaringClass().getName()+"->"+param.method.getName()+"\",";
        //hookData.put("method", param.method.getName());
        hookData+="\"Function\":"+ "\"heartbeat\",";
        hookData+="\"Time\":"+System.currentTimeMillis()+"";
        return hookData;
    }

    public static String parseArgs(MethodHookParam param, String hookJson,List<String> paraname)
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
}
