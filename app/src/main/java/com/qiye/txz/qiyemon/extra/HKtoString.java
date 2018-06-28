package com.qiye.txz.qiyemon.extra;

import org.json.JSONObject;

import de.robv.android.xposed.XC_MethodHook;

public class HKtoString extends XC_MethodHook {
    //HKtoString(f arg1) {
    //    this.a = arg1;
     //   super();
   // }

    protected void afterHookedMethod(MethodHookParam arg6) {
        JSONObject v1 = new JSONObject();
        try{
            v1.put("Function", "String toString");
            v1.put("Method", "java.lang.StringBuilder->toString");
            JSONObject v0 = new JSONObject();
           // v0.put("this",  arg6.thisObject.toString());//f.a(this.a,)
            //v1.put("Parameters", v0);
            String v2 = "result";
           // f v3 = this.a;
            //String v0_1 = arg6.getResult() == null ? "" : arg6.getResult().toString();
            //v1.put(v2,a(v0_1));
            LogParse.getHookParam(v1);//p.a(v1);
        }catch (Exception e){

        }
    }

    protected void beforeHookedMethod(MethodHookParam arg1) {
    }

    static String a(String arg2) {
        return c(arg2);
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
}
