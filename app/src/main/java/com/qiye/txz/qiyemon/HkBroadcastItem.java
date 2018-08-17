package com.qiye.txz.qiyemon;

import android.content.Intent;
import android.os.Bundle;

import com.qiye.txz.qiyemon.utils.BroadcastItem;
import com.qiye.txz.qiyemon.utils.Logger;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

public class HkBroadcastItem extends XC_MethodHook  implements HKmethod{
    private String mClassName;
    private String mMethodName;
    //private MethodApiType mType;
    private String mType;
    private boolean mThisObject= true;
    HkBroadcastItem(String className, String methodName, boolean thisObject,/*MethodApiType*/String type/*hookall arg1*/) {
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
    protected void afterHookedMethod(XC_MethodHook.MethodHookParam arg2) {
        Logger.BroadcastItemobject.remove();
    }

    protected void beforeHookedMethod(MethodHookParam arg7) {
        XposedBridge.log("hitBroadcastItem");
        Object v1 = arg7.thisObject;
        try{
            Field v0 = v1.getClass().getDeclaredField("mCurIntent");
            v0.setAccessible(true);
            Object v0_1 = v0.get(v1);
            String v2 = ((Intent)v0_1).getAction();
            if(("android.intent.action.BATTERY_CHANGED".equals(v2)) || ("android.intent.action.BATTERY_OKAY".equals(v2)) || ("android.intent.action.BATTERY_LOW".equals(v2))) {
                ((Intent)v0_1).putExtra("level", 90);
                ((Intent)v0_1).putExtra("voltage", 3916);
                ((Intent)v0_1).putExtra("temperature", 260);
            }

            Field v2_1 = v1.getClass().getDeclaredField("this$0");
            v2_1.setAccessible(true);
            Object v2_2 = v2_1.get(v1);
            v1 = null;
            if(v2_2 == null || v2_2.getClass() != Class.forName("android.app.LoadedApk$ReceiverDispatcher")) {
                v2_2 = v1;
            }
            else {
                Field v1_1 = v2_2.getClass().getDeclaredField("mReceiver");
                v1_1.setAccessible(true);
                v1 = v1_1.get(v2_2);
                Field v3 = v2_2.getClass().getDeclaredField("mContext");
                v3.setAccessible(true);
                v3.get(v2_2);
                v2_2 = v1;
            }
            Logger.BroadcastItemobject.remove();
            if(Logger.BroadcastItemobject.get() == null) {
                Logger.BroadcastItemobject.set(new BroadcastItem(((Intent)v0_1).getAction(), v2_2.getClass().getName(), "am broadcast " + a(((Intent)v0_1))));
            }
        }catch (Exception e){
            XposedBridge.log(e);
        }
    }
    public static String a(Intent arg14) {
        int v2_3;
        String v4_1;
        StringBuilder v1_3;
        Object v0_1;
        int v13 = 2;
        String v0 = new String();
        String v1 = arg14.getAction();
        if(v1 != null && v1.length() > 0) {
            v0 = v0 + String.format("-a %s ", v1);
        }

        v1 = arg14.getDataString();
        if(v1 != null && v1.length() > 0) {
            v0 = v0 + String.format("-d %s ", v1);
        }

        v1 = arg14.getType();
        if(v1 != null && v1.length() > 0) {
            v0 = v0 + String.format("-t %s ", v1);
        }

        Set v1_1 = arg14.getCategories();
        if(v1_1 != null) {
            Iterator v2 = v1_1.iterator();
            for(v1 = v0; v2.hasNext(); v1 = v0) {
                v0_1 = v2.next();
                v0 = v0_1 == null || ((String)v0_1).length() <= 0 ? v1 : v1 + String.format("-c %s ", v0_1);
            }
        }
        else {
            v1 = v0;
        }

        String v2_1 = "";
        v0 = "";
        if(arg14.getComponent() != null) {
            v2_1 = arg14.getComponent().getPackageName();
            v0 = arg14.getComponent().getShortClassName();
        }

        if(v2_1.length() > 0 && v0.length() > 0) {
            v1 = v1 + String.format("-n %s ", v2_1 + "/" + v0);
        }

        v0 = v1 + String.format("-f %d ", Integer.valueOf(arg14.getFlags()));
        Bundle v5 = arg14.getExtras();
        if(v5 != null) {
            try {
                v1_1 = v5.keySet();
            }
            catch(Exception v1_2) {
                v1_1 = null;
            }

            if(v1_1 == null) {
                return v0;
            }

            Iterator v6 = v1_1.iterator();
            v1 = v0;
            Object v2_2=null;
            while(v6.hasNext()) {

                v0_1 = v6.next();
                try {
                    v2_2 = v5.get(((String)v0_1));
                    if(v2_2 == null) {

                        if(v2_2.getClass() == String.class) {
                            v1_3 = new StringBuilder().append(v1);
                            v0 = v1_3.append(String.format("--es %s \"%s\" ", v0_1, v5.getString(((String)v0_1)))).toString();
                        }
                        else if(v2_2.getClass() == Boolean.class) {
                            v1_3 = new StringBuilder().append(v1);
                            v0 = v1_3.append(String.format("--ez %s %b ", v0_1, Boolean.valueOf(v5.getBoolean(((String)v0_1))))).toString();
                        }
                        else if(v2_2.getClass() == Integer.class) {
                            v1_3 = new StringBuilder().append(v1);
                            v0 = v1_3.append(String.format("--ei %s %d ", v0_1, Integer.valueOf(v5.getInt(((String)v0_1))))).toString();
                        }
                        else if(v2_2.getClass() == Long.class) {
                            v1_3 = new StringBuilder().append(v1);
                            v0 = v1_3.append(String.format("--el %s %d ", v0_1, Long.valueOf(v5.getLong(((String)v0_1))))).toString();
                        }
                        else if(v2_2.getClass() == Float.class) {
                            v1_3 = new StringBuilder().append(v1);
                            v0 = v1_3.append(String.format("--ef %s %f ", v0_1, Float.valueOf(v5.getFloat(((String)v0_1))))).toString();
                        }
                        else if(v2_2.getClass() == int[].class) {
                            v4_1 = "";
                            v2_3 = 0;
                            while(v2_3 < v5.getIntArray(((String)v0_1)).length) {
                                v4_1 = v4_1 + String.format("%d,", Integer.valueOf(v5.getIntArray(((String)v0_1))[v2_3]));
                                ++v2_3;
                            }

                            if(v4_1.length() > 0) {
                                v4_1 = v4_1.substring(0, v4_1.length() - 1);
                            }

                            v1_3 = new StringBuilder().append(v1);
                            v0 = v1_3.append(String.format("--eia %s %s ", v0_1, v4_1)).toString();
                        }
                        else if(v2_2.getClass() == long[].class) {
                            v4_1 = "";
                            v2_3 = 0;
                            while(v2_3 < v5.getLongArray(((String)v0_1)).length) {
                                v4_1 = v4_1 + String.format("%d,", Long.valueOf(v5.getLongArray(((String)v0_1))[v2_3]));
                                ++v2_3;
                            }

                            if(v4_1.length() > 0) {
                                v4_1 = v4_1.substring(0, v4_1.length() - 1);
                            }

                            v1_3 = new StringBuilder().append(v1);
                            v0 = v1_3.append(String.format("--ela %s %s ", v0_1, v4_1)).toString();
                        }
                        else if(v2_2.getClass() == float[].class) {
                            v4_1 = "";
                            v2_3 = 0;
                            while(v2_3 < v5.getFloatArray(((String)v0_1)).length) {
                                v4_1 = v4_1 + String.format("%f,", Float.valueOf(v5.getFloatArray(((String)v0_1))[v2_3]));
                                ++v2_3;
                            }

                            if(v4_1.length() > 0) {
                                v4_1 = v4_1.substring(0, v4_1.length() - 1);
                            }

                            v1_3 = new StringBuilder().append(v1);
                            v0 = v1_3.append(String.format("--efa %s %s ", v0_1, v4_1)).toString();
                        }
                        else {
                            v0 = v1;
                        }

                        label_131:
                        v1 = v0;
                        continue;
                    }
                    break;
                }
                catch(Exception v0_2) {
                    continue;
                }

            }
            if(!v6.hasNext()) {
                return v1;
            }
        }
        return v0;
    }

}

