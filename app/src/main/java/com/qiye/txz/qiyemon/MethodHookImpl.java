package com.qiye.txz.qiyemon;

import android.content.ClipData;
import android.content.ComponentName;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Parcelable;
import android.os.Process;
import android.provider.Browser;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.view.WindowManager;


import com.qiye.txz.qiyemon.utils.Logger;
import com.qiye.txz.qiyemon.utils.ParseGeneratorNotype;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

import static com.qiye.txz.qiyemon.utils.MessageItem.e;


public class MethodHookImpl extends XC_MethodHook {
	
	private String mClassName;
	private String mMethodName;
	//private MethodApiType mType;
	private String mType;
	private boolean mThisObject= false;

	public MethodHookImpl(String className, String methodName, boolean thisObject,/*MethodApiType*/String type){
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

	@Override
	protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
		if(param.method.getName().equals("getInstalledPackages")&&param.method.getDeclaringClass().getName().equals("android.app.ApplicationPackageManager")){
			Logger.logbeforegetInstalledPackages(param,mThisObject,mType);
		}else if(param.method.getName().equals("getInstalledApplications")&&param.method.getDeclaringClass().getName().equals("android.app.ApplicationPackageManager")){
			Logger.logbeforegetInstalledApplication(param,mThisObject,mType);
		}else if(param.method.getName().equals("getRunningTasks")&&param.method.getDeclaringClass().getName().equals("android.app.ActivityManager")){
            Logger.logbeforegetRunningServices(param,mThisObject,mType);
        }else if(param.method.getName().equals("update")&&param.method.getDeclaringClass().getName().equals("android.content.ContentResolver")){
			if(param.args[0] != null && (param.args[0].equals(Uri.parse("content://telephony/carriers/preferapn")))) {
				param.setResult(Integer.valueOf(1));
                Logger.log("android.content.ContentResolver->update modify apn return true");
                return;
			}
		}else if(param.method.getName().equals("addView")&&param.method.getDeclaringClass().getName().equals("android.view.WindowManager")){
            if(param.args[0] != null && (param.args[0].equals(Uri.parse("content://telephony/carriers/preferapn")))) {
                param.setResult(Integer.valueOf(1));
                Logger.log("android.content.ContentResolver->update modify apn return true");
                return;
            }
        }else if(param.method.getName().equals("addView")&&param.method.getDeclaringClass().getName().equals("android.view.WindowManager")){
            if((param.args[1] instanceof WindowManager.LayoutParams)) {
                JSONObject hookJson = new JSONObject();
                Object obj = (Object) param.args[1];
                hookJson.put("Parameter", ParseGeneratorNotype.windowsMangerlayoutParamsParsebefore(obj));
                hookJson = ParseGeneratorNotype.addHookDataJson(hookJson, param, mType);
                Logger.logHook(hookJson);
            }
        }else if((param.method.getDeclaringClass().getName()+"->"+param.method.getName()).equals("java.util.jar.JarVerifier->verify")){
			param.setResult(Boolean.valueOf(true));
		}else if((param.method.getDeclaringClass().getName()+"->"+param.method.getName()).equals("android.content.ClipboardManager->setPrimaryClip")){
            Context v0_3;
            Context v1 = null;
            Object v0 = param.args[0];
            if(v0 != null) {
                ClipData.Item v2 = ((ClipData)v0).getItemAt(0);
                if(v2 == null) {
                    return;
                }
                try {
                    Method v0_2 = Class.forName("android.app.ActivityThread").getMethod("currentActivityThread");
                    v0_2.setAccessible(true);
                    v0 = v0_2.invoke(null);
                    Field v3 = v0.getClass().getDeclaredField("mInitialApplication");
                    v3.setAccessible(true);
                    Object obj=(v3.get(v0));
                    Context con=(Context)obj;
                    v0_3 = con.getApplicationContext();
                }
                catch(Exception v0_1) {
                    v0_1.printStackTrace();
                    v0_3 = v1;
                }

                if(v0_3 == null) {
                    return;
                }

                JSONObject v1_1 = new JSONObject();
                JSONObject v3_1 = new JSONObject();
                v3_1.put("clip", v2.coerceToText(v0_3));
                v1_1.put("Parameters", v3_1);

                v1_1 = ParseGeneratorNotype.addHookDataJson(v1_1, param, mType);
                Logger.logHook(v1_1);
        }}
	}

	@Override
	protected void afterHookedMethod(MethodHookParam param) throws Throwable {

		if (!param.hasThrowable())
            //XposedBridge.log(param.method.getDeclaringClass().getName()+"->"+param.method.getName());
			try {
				if (Process.myUid() <= 0)
					return;
				if (InstrumentationManager.TRACE)
				{
					traceMethod(param);      //这里设置了false
				}
				else
				{
					monitorMethod(param);
				}
			} catch (Throwable ex) {
				throw ex;
			}
	}
	
	public void monitorMethod(MethodHookParam param)
	{
		try {
            //XposedBridge.log(param.method.getClass().getName()+"->"+param.method.getName());
			if(param.method.getName().equals("getInstalledPackages")&&param.method.getDeclaringClass().getName().equals("android.app.ApplicationPackageManager")){
				Logger.logaftergetInstalledPackages(param,mThisObject,mType);
				return;
			}else if(param.method.getName().equals("getInstalledApplications")&&param.method.getDeclaringClass().getName().equals("android.app.ApplicationPackageManager")){
				Logger.logaftergetInstalledApplication(param,mThisObject,mType);
				return;
			}else if(param.method.getName().equals("getRunningTasks")&&param.method.getDeclaringClass().getName().equals("android.app.ActivityManager")){
                Logger.logaftergetRunningServices(param,mThisObject,mType);
                return;
            }else if(param.method.getName().equals("setPasswordQuality")&&param.method.getDeclaringClass().getName().equals("android.app.admin.DevicePolicyManager")){
                Logger.logaftergetRunningServices(param,mThisObject,mType);
                return;
            }else if(param.method.getName().equals("update")&&param.method.getDeclaringClass().getName().equals("android.content.ContentResolver")){
				if(param.args[0] != null) {
					Object v0 = param.args[0];
					if(!((Uri)v0).equals(ContactsContract.Contacts.CONTENT_URI) && !((Uri)v0).equals(Uri.parse("content://com.android.contacts/raw_contacts"))) {
						return;
					}else if(param.args[0] != null && (param.args[0].equals(CallLog.Calls.CONTENT_URI))) {
                        Logger.updatee("Update CallLog", "android.content.ContentResolver->update", param);
                        return;
                    }
                    //if(param.args[0] != null && (param.args[0].equals(Browser.BOOKMARKS_URI))) {
                    //    Logger.updatee("Update BookMarks", "android.content.ContentResolver->update", param);
                    //    return ;
					//}
					Logger.updatee("Update Contacts", "android.content.ContentResolver->update", param);
				return;
				}

			}else if(param.method.getName().equals("addView")&&param.method.getDeclaringClass().getName().equals("android.view.WindowManager")){
                if((param.args[1] instanceof WindowManager.LayoutParams)){
                    JSONObject hookJson = new JSONObject();
                    Object obj=(Object)param.args[1];
                    hookJson.put("Parameter",ParseGeneratorNotype.windowsMangerlayoutParamsParseafter(obj));
                    hookJson=ParseGeneratorNotype.addHookDataJson(hookJson,param,mType);
                    Logger.logHook(hookJson);
                }
            }else if((param.method.getDeclaringClass().getName()+"->"+param.method.getName()).equals("android.content.ContentResolver->applyBatch")){
                JSONObject v1 = new JSONObject();
			    JSONObject hookJson = new JSONObject();
                hookJson.put("authority", param.args[0]);
                JSONArray v3 = new JSONArray();
                ArrayList<ContentProviderOperation> arg=(ArrayList<ContentProviderOperation>)param.args[1];
                if(arg != null) {
                    Iterator v4 = arg.iterator();
                    while(v4.hasNext()) {
                        v3.put(v4.next().toString());
                    }
                }
                hookJson.put("cpo", v3);
                v1.put("Parameters", hookJson);
			    hookJson=ParseGeneratorNotype.addHookDataJson(v1,param,mType);
                Logger.logHook(hookJson);
                return;
            }else if((param.method.getDeclaringClass().getName()+"->"+param.method.getName()).equals("android.content.ClipboardManager->setPrimaryClip")){
               return;}

            if(param.method.getName().contains("invoke"))
				Logger.logReflectionMethod(param,mThisObject,mType);
				//Logger.logGenericMethod(param,mThisObject,mType);
			else if(param.method.getName().contains("write"))
				Logger.logProcessWriteMethod(param,mThisObject,mType);
			else if(param.method.getName().contains("read"))
				Logger.logProcessReadMethod(param,mThisObject,mType);
			//else if(param.method.getName().contains("openDexFile") || param.method.getName().equals("load"))
				//Logger.logAndDumpFile(param,mThisObject,mType);
			else
				Logger.logGenericMethod(param,mThisObject,mType);
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
