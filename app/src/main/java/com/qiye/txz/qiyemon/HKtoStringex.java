package com.qiye.txz.qiyemon;

import android.os.Process;
import android.util.Log;

import com.qiye.txz.qiyemon.utils.Logger;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

public class HKtoStringex extends XC_MethodHook implements HKmethod{
    private String mClassName;
    private String mMethodName;
    //private MethodApiType mType;
    private String mType;
    private boolean mThisObject= true;

    public HKtoStringex(String className, String methodName, boolean thisObject,/*MethodApiType*/String type){
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
                logGenericMethod(param,mThisObject,mType);
            } catch (Throwable ex) {
                throw ex;
            }
    }

    protected void beforeHookedMethod(MethodHookParam arg1) {
    }
    public static void logGenericMethod(MethodHookParam param, boolean mThisObject, /*String*/ String mType)  {
        //JSONObject hookJson = ParseGeneratorNotype.generateHookDataJson(param,mType);
        StringBuffer hookJson =new StringBuffer();
        hookJson.append("{");

        if(param.args!=null)//&&paraname!=null
        {
            //hookJson+="\"Parameters\":"+"{"+parseArgs(param,hookJson,paraname)+"},";
            //hookJson.append("\"Parameters\":{");
            //hookJson.append(parseArgs(param,hookJson,null));
        }
        if(param.getResult()!=null){
            //hookJson+="\"result\":"+"\""+param.getResult().toString()+"\",";
            hookJson.append("\"result\":\"");

            hookJson.append(param.getResult().toString());
            hookJson.append("\",");
            //Log.d("test",hookJson.toString());
        }
        if(param.thisObject!=null)
        {
            hookJson.append("\"this\":\"");
            //StringBuffer temp=(StringBuffer)(param.thisObject);
            //hookJson.append(temp.toString());

            //Log.d("test",param);
            hookJson.append("\",");
        }

        hookJson.append(addHookDataJson(param,mType));
        hookJson.append("}");
        //addHookDataJson(param,mType);
       Logger.logHookextra(hookJson.toString());
    }
    private static String c(String arg3) {
        int v1 = 128;
        if(arg3 == null) {
            arg3 = "";
        }
        else if(arg3.length() > v1) {
            arg3 = arg3.substring(0, v1);
        }
        return arg3;
    }
    public static String addHookDataJson(MethodHookParam param,String type)
    {
        //JSONObject hookData= new JSONObject();
        StringBuffer hookData=new StringBuffer();
        //hookData+="\"PID\":"+Process.myPid()+",";
        hookData.append("\"PID\":");
        hookData.append(Process.myPid());
        hookData.append(",");
        //hookData+="\"TID\":"+Process.myTid()+",";
        hookData.append("\"TID\":");
        hookData.append(Process.myTid());
        hookData.append(",");
        //hookData+="\"UID\":"+ Process.myUid()+",";
        hookData.append("\"UID\":");
        hookData.append(Process.myUid());
        hookData.append(",");
       // hookData+="\"Method\":"+"\""+ param.method.getDeclaringClass().getName()+"->"+param.method.getName()+"\",";
        hookData.append("\"Method\":\"");
        hookData.append(param.method.getDeclaringClass().getName());
        hookData.append("->");
        hookData.append(param.method.getName());
        hookData.append("\",");
        //hookData.put("method", param.method.getName());
       // hookData+="\"Function\":"+ "\""+type+"\",";
        hookData.append("\"Funtion\":\"");
        hookData.append(type);
        hookData.append("\",");
        //hookData+="\"Time\":"+System.currentTimeMillis()+",";
        hookData.append("\"Time\":");
        hookData.append(System.currentTimeMillis());
        hookData.append("");
        return hookData.toString();
    }

    public static String parseArgs(MethodHookParam param, String hookJson,List<String> paraname)
    {
        String args = "";

        return args;
    }
}
