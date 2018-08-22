package com.qiye.txz.qiyemon.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XposedBridge;


public class Logger {
	public static final ThreadLocal BroadcastItemobject = new ThreadLocal();
	public static final ThreadLocal MessageItemobject = new ThreadLocal();
	public static final ThreadLocal ServiceItemobject = new ThreadLocal();

	public static final Gson gson = new Gson();
	private static PrintWriter logWriter = null;
	
	private static final int MAX_LOGFILE_SIZE = 40*1024;
	public static String LOGTAG_SHELL= "360Qiyemon-shell-";
	public static String LOGTAG_WORKFLOW = "360Qiyemon-apimonitor-";
	public static String LOGTAG_ERROR = "360Qiyemon-error";
	public static String LOG_FILE ="/360Qiyemon.log";
	public static String LOG_FILE_OLD ="/360Qiyemon_old.log";
	public static String PACKAGENAME;
	private static ParaName logpn=new ParaName();

	public static PrintWriter getLogWriter()
	{
		if(logWriter == null)
		{
			try {
				//ile logFile = new File(Context.getAbsolutePath+LOG_FILE);
				//File logFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+LOG_FILE);
                XposedBridge.log("/data/data/"+PACKAGENAME);

				File logFile = new File("/data/data/"+PACKAGENAME+LOG_FILE);
				if (logFile.length() > MAX_LOGFILE_SIZE)
					//logFile.renameTo(new File(Environment.getExternalStorageDirectory().getPath()+LOG_FILE_OLD));
					//logFile.renameTo(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+LOG_FILE_OLD));
					logFile.renameTo(new File("/data/data/"+PACKAGENAME+LOG_FILE));
				logWriter = new PrintWriter(new FileWriter(logFile, true));
				logFile.setReadable(true, false);
				logFile.setWritable(true, false);
                //XposedBridge.log("logprintwirter");
			} catch (IOException e) {
				log(e.getMessage());
			}
		}
		return logWriter;
	}
	
	public static void logHook(JSONObject hookData){
		//XposedBridge.log(LOGTAG_WORKFLOW+PACKAGENAME+":"+hookData.toString());
        //XposedBridge.log("loghook");
		log(LOGTAG_WORKFLOW+PACKAGENAME+":"+hookData.toString());
	}
	public static void logHookextra(String hookData){
		//XposedBridge.log(LOGTAG_WORKFLOW+PACKAGENAME+":"+hookData.toString());
		//XposedBridge.log("loghook");
		//System.out.println(ho);
		StringBuffer text=new StringBuffer();
		text.append(LOGTAG_WORKFLOW);
		text.append("heartbeatsignal");
		text.append(":");
		text.append(hookData);
		log(text.toString());
	}

	public static void logShell(String message){
		log(LOGTAG_SHELL+PACKAGENAME+":"+message);
	}

	public static void logError(String message){
		//XposedBridge.log(LOGTAG_ERROR+":"+message);
        //XposedBridge.log("logError");
		log(LOGTAG_ERROR+":"+message);
	}
	
	public synchronized static void log(String text) {

		//runcmd();
		Log.d("360Qiyemono", text);
		/*
		PrintWriter logger = getLogWriter();
		if (logger != null) {
			logger.println(text);
			logger.flush();
		}*/
	}

	public static void logself(String text){

        PrintWriter logger = getLogWriter();
        if (logger != null) {
            logger.println(text);
            logger.flush();
        }
    }

	public static void runcmd(String cmd){
		try{
			Process proc = Runtime.getRuntime().exec(cmd);
			InputStream inputstream = proc.getInputStream();
			InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
			BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
			// read the ls output
			String line = "";
			StringBuilder sb = new StringBuilder(line);
			while ((line = bufferedreader.readLine()) != null) {
				//System.out.println(line);
				XposedBridge.log(line);
			}

		}catch (IOException e){}
	}

	public static void logProcessWriteMethod(MethodHookParam param, boolean mThisObject, String mType) throws JSONException {
		if(param.thisObject == null)
			return;
		if(param.thisObject.getClass().toString().contains("ProcessOutputStream"))
		{
			JSONObject hookData=ParseGeneratorNotype.generateHookDataJson(param,mType);
			hookData.put("buffer", new String((byte[])param.args[0],(Integer)param.args[1],(Integer)param.args[2]).trim());
			Logger.logHook(hookData);
		}
	}
	
	public static void logProcessReadMethod(MethodHookParam param, boolean mThisObject, String mType) throws JSONException {
		if(param.thisObject == null)
			return;
		if(param.thisObject.getClass().toString().contains("ProcessInputStream"))
		{
			JSONObject hookData=ParseGeneratorNotype.generateHookDataJson(param,mType);
			hookData.put("buffer", new String((byte[])param.args[0],(Integer)param.args[1],(Integer)param.args[2]).trim());
			Logger.logHook(hookData);
		}
		
	}
	public static void updatee(String arg8, String arg9, XC_MethodHook.MethodHookParam param) {

		try{
			JSONObject hookData=ParseGeneratorNotype.generateHookDataJson(param,"");
			int v7 = 3;
			JSONObject v3 = new JSONObject();
			v3.put("Function", arg8);
			v3.put("Method", arg9);
			JSONObject v4 = new JSONObject();
			v4.put("url", param.args[0]);
			Object v0 = param.args[1];
			JSONObject v5 = new JSONObject();
			Set v0_1 = ((ContentValues)v0).valueSet();
			if(v0_1 != null) {
				Iterator v6 = v0_1.iterator();
				while(v6.hasNext()) {
					v0 = v6.next();
					v5.put(((Map.Entry)v0).getKey().toString(), ((Map.Entry)v0).getValue());
				}

				v4.put("ContentValue", v5);
				v4.put("where", param.args[2]);
				JSONArray v5_1 = new JSONArray();
				if(param.args[v7] != null) {
					v0 = param.args[v7];
					String [] a=(String[])v0;
					int v6_1 = a.length;
					int v1;
					for(v1 = 0; v1 < v6_1; ++v1) {
						v5_1.put(a[v1]);
					}
				}

				v4.put("selectionArgs", v5_1);
				v3.put("Parameters", v4);
				hookData.put("Parameter",v4);
				Logger.logHook(hookData);
			}
		}catch(Exception e){
			XposedBridge.log("updateee exception");
		}

	}
	public static void logGenericMethod(MethodHookParam param, boolean mThisObject, /*String*/ String mType) throws JSONException {
		//JSONObject hookJson = ParseGeneratorNotype.generateHookDataJson(param,mType);
        JSONObject tempjson;
		JSONObject hookJson = new JSONObject();
		List<List<String>> paraname=new ArrayList<List<String>>();
		for (String method : logpn.findparanameMap.keySet()) {
			//System.out.println(method + " ：" + pn.findparanameMap.get(method));
			if(method.equals(param.method.getDeclaringClass().getName()+"->"+param.method.getName())){
				paraname=logpn.findparanameMap.get(method);
			}
		}
        Object p = Logger.BroadcastItemobject.get();
        if(p != null) {
            tempjson = new JSONObject();
            tempjson.put("intentAction", ((BroadcastItem)p).intentAction);
            tempjson.put("receiverTo", ((BroadcastItem)p).receiverTo);
            tempjson.put("intentCommand", ((BroadcastItem)p).intentCommand);
            hookJson.put("BroadcastItem", tempjson);
        }

        p = Logger.MessageItemobject.get();
        if(p != null) {
            tempjson = new JSONObject();
            tempjson.put("msgTarget", ((MessageItem)p).msgTarget);
            tempjson.put("msgCode", ((MessageItem)p).msgCode);
            tempjson.put("codeString", ((MessageItem)p).codeString);
            hookJson.put("MessageItem", tempjson);
        }

        p = Logger.ServiceItemobject.get();
        if(p != null) {
            tempjson = new JSONObject();
            tempjson.put("className", ((ServiceItem)p).className);
            tempjson.put("packageName", ((ServiceItem)p).packageName);
            tempjson.put("amCommand", ((ServiceItem)p).amCommand);
            hookJson.put("ServiceItem", tempjson);
        }
        if(param.args!=null)//&&paraname!=null
        	if(param.method.getName().equals("equals")) {
				//hookJson.put("Parameters", ParseGeneratorNotype.parseArgsspecial(param, hookJson, paraname));
			}
			else{
				hookJson.put("Parameters", ParseGeneratorNotype.parseArgs(param,hookJson,paraname));
			}
        if(param.getResult()!=null)
			hookJson.put("result", ParseGeneratorNotype.parseResults(param,hookJson));
		if(param.thisObject!=null && mThisObject)
			hookJson.put("this",ParseGeneratorNotype.parseThis(param,hookJson));
		//XposedBridge.log(param.method.getClass().getName()+"->"+param.method.getName());
        if((param.method.getDeclaringClass().getName()+"->"+param.method.getName()).equals("com.android.server.pm.PackageManagerService->installPackageAsUser")){
            String a= d();
            hookJson.put("CallStack",makestr(a));
        }else if((param.method.getDeclaringClass().getName()+"->"+param.method.getName()).equals("com.android.server.pm.PackageManagerService->installPackage")){
            hookJson.put("CallStack",makestr(d()));
        }else if((param.method.getDeclaringClass().getName()+"->"+param.method.getName()).equals("com.android.server.pm.PackageManagerService->deletePackage")){
            hookJson.put("CallStack",makestr(d()));
        }else if((param.method.getDeclaringClass().getName()+"->"+param.method.getName()).equals("android.content.pm.IPackageManager.Stub.Proxy->installPackage")){
            hookJson.put("CallStack",makestr(d()));
        }else if((param.method.getDeclaringClass().getName()+"->"+param.method.getName()).equals("android.content.pm.IPackageManager.Stub.Proxy->deletePackage")){
            hookJson.put("CallStack",makestr(d()));
        }
            hookJson=ParseGeneratorNotype.addHookDataJson(hookJson,param,mType);
		Logger.logHook(hookJson);
	}
	public static String makestr(String Putin){
        String arg3=Putin;
        int v1 = 128;
        if(arg3 == null) {
            arg3 = "";
        }
        else if(arg3.length() > v1) {
            arg3 = arg3.substring(0, v1);
        }
        return arg3;
    }
    public static String d() {
        RuntimeException e = new RuntimeException("run is here");
        e.fillInStackTrace();
        String stackTraces="";
        for (Map.Entry<Thread, StackTraceElement[]> stackTrace:Thread.getAllStackTraces().entrySet())
        {
            Thread thread = (Thread) stackTrace.getKey();
            StackTraceElement[] stack = (StackTraceElement[]) stackTrace.getValue();

            // 进行过滤
            if (thread.equals(Thread.currentThread())) {
                continue;
            }

            stackTraces+=("[Dump Stack]"+"**********Thread name：" + thread.getName()+"**********");
            int index = 0;
            for (StackTraceElement stackTraceElement : stack) {
                stackTraces+="[Dump Stack]"+index+": "+ stackTraceElement.getClassName()
                        +"----"+stackTraceElement.getFileName()
                        +"----" + stackTraceElement.getLineNumber()
                        +"----" +stackTraceElement.getMethodName();
            }
            // 增加序列号
            index++;
        }
        stackTraces+=("[Dump Stack]"+"********************* over **********************");

        return stackTraces;
    }

    public static void logReflectionMethod(MethodHookParam param, boolean mThisObject, String mType) throws JSONException {
		JSONObject hookJson = ParseGeneratorNotype.generateHookDataJson(param,mType);

		hookJson.put("hooked_class", ParseGeneratorNotype.parseRefelctionClassName(param, hookJson));
		hookJson.put("hooked_method", ParseGeneratorNotype.parseRefelctionMethodName(param, hookJson));
		if(param.args!=null)
			hookJson.put("args", ParseGeneratorNotype.parseRefelctionArgs(param,hookJson));
		if(param.getResult()!=null)
			hookJson.put("result", ParseGeneratorNotype.parseResults(param,hookJson));
		
		Logger.logHook(hookJson);
	}
	public static void logaftergetInstalledPackages(MethodHookParam param, boolean mThisObject, String mType) throws JSONException {
		JSONObject hookJson = ParseGeneratorNotype.generateHookDataJson(param,mType);
		Object v0 = param.getResult();
		ArrayList v1 = new ArrayList();
		JSONObject v2 = new JSONObject();
		JSONObject v3 = new JSONObject();
		JSONArray v4 = new JSONArray();
		if(v0 != null) {
			Iterator v5 = ((List)v0).iterator();
			while(v5.hasNext()) {
				v0 = v5.next();
				if("com.qiye.txz.qiyemon".equals(((PackageInfo)v0).packageName)) {
					continue;
				}

				((List)v1).add(v0);
				v4.put(((PackageInfo)v0).packageName);
			}
		}

		v3.put("PackageName", v4);
		hookJson.put("result", v3);
		param.setResult(v1);
		Logger.logHook(hookJson);
	}
	public static void logaftergetInstalledApplication(MethodHookParam param, boolean mThisObject, String mType) throws JSONException {
		JSONObject hookJson = ParseGeneratorNotype.generateHookDataJson(param,mType);
		Object v0 = param.getResult();
		ArrayList v1 = new ArrayList();
		JSONObject v3 = new JSONObject();
		JSONArray v4 = new JSONArray();
		if(v0 != null) {
			Iterator v5 = ((List)v0).iterator();
			while(v5.hasNext()) {
				v0 = v5.next();
				if("com.qiye.txz.qiyemon".equals(((ApplicationInfo)v0).packageName)) {
					continue;
				}

				((List)v1).add(v0);
				v4.put(((ApplicationInfo)v0).packageName);
			}
		}

		v3.put("PackageName", v4);
		hookJson.put("result", v3);
		param.setResult(v1);
		Logger.logHook(hookJson);
	}
	public static void logbeforegetInstalledApplication(MethodHookParam param, boolean mThisObject, String mType) throws JSONException {
		JSONObject hookJson = ParseGeneratorNotype.generateHookDataJson(param,mType);
		Object v0=null;
		JSONObject v2 = new JSONObject();
		String v3 = "flags";
		if(param.args[0] != null) {
			v0 = param.args[0];
		}
		else {
			String v0_1 = "";
		}

		v2.put(v3, v0);
		hookJson.put("Parameters", v2);
		Logger.logHook(hookJson);
	}
    public static void logbeforesetPasswordQuality(MethodHookParam param, boolean mThisObject, String mType) throws JSONException {
        JSONObject hookJson = ParseGeneratorNotype.generateHookDataJson(param,mType);
        JSONObject v2 = new JSONObject();
        String v0 = "";
        if(param.args[0] != null) {
            try{
                v0=ParseGeneratorNotype.parse((Object)(param.args[0])).toString();
            }catch (Exception e){
                XposedBridge.log("");
            }

        }

        v2.put("admin", v0);
        v2.put("quality", param.args[1]);
        hookJson.put("Parameters", v2);
        Logger.logHook(hookJson);
    }
	public static void logbeforegetInstalledPackages(MethodHookParam param, boolean mThisObject, String mType) throws JSONException {
		JSONObject hookJson = ParseGeneratorNotype.generateHookDataJson(param,mType);
		String v0_1="";
		JSONObject v2 = new JSONObject();
		String v3 = "flags";
		if(param.args[0] != null) {
			Object v0 = param.args[0];
		}
		else {
			v0_1 = "";
		}

		v2.put(v3, v0_1);
		hookJson.put("Parameters", v2);
		Logger.logHook(hookJson);
	}
    public static void logaftergetRunningServices(MethodHookParam param, boolean mThisObject, String mType) throws JSONException {
        JSONObject hookJson = ParseGeneratorNotype.generateHookDataJson(param,mType);
        Object v0 = param.getResult();
        ArrayList v1 = new ArrayList();
        if(v0 != null) {
            Iterator v2 = ((List)v0).iterator();
            while(v2.hasNext()) {
                v0 = v2.next();
                if(((ActivityManager.RunningServiceInfo)v0).service != null && ("com.qiye.txz.qiyemon".equals(((ActivityManager.RunningServiceInfo)v0).service.getPackageName()))) {
                    continue;
                }

                ((List)v1).add(v0);
            }
        }

        param.setResult(v1);
    }
    public static void logbeforegetRunningServices(MethodHookParam param, boolean mThisObject, String mType) throws JSONException {
        JSONObject hookJson = ParseGeneratorNotype.generateHookDataJson(param,mType);
        JSONObject v1 = new JSONObject();
        v1.put("maxNum", param.args[0]);
        hookJson.put("Parameters", v1);
        Logger.logHook(hookJson);
    }
	public static void logTraceReflectionMethod(MethodHookParam param, String mType) throws JSONException {
		JSONObject hookJson = ParseGeneratorNotype.generateHookDataJson(param,mType);
		
		hookJson.put("hooked_method", ParseGeneratorNotype.parseRefelctionMethodName(param, hookJson));
		hookJson.put("hooked_class", ParseGeneratorNotype.parseRefelctionClassName(param, hookJson));
		
		Logger.logHook(hookJson);
		
	}
	
	public static void logTraceMethod(MethodHookParam param, String mType) throws JSONException {
		Logger.logHook(ParseGeneratorNotype.generateHookDataJson(param,mType));
	}

	public static void logAndDumpFile(MethodHookParam param, boolean mThisObject, String mType) throws JSONException, IOException {
		JSONObject hookJson = ParseGeneratorNotype.generateHookDataJson(param,mType);
		
		String outDir = "";
		String dexPath = (String) param.args[0];
		hookJson.put("orig", dexPath);
        
		//Ignore loading of files from /system or /data/app
        if (dexPath.startsWith("/system/") || dexPath.startsWith("/data/app"))
        {
        	hookJson.put("dump", false);
        	hookJson.put("path", dexPath);
        }
        else
        {
        	hookJson.put("dump", true);
            String uniq = UUID.randomUUID().toString();
            //outDir = outDir + "/" + PACKAGENAME  + dexPath.replace("/", "_") + "-" + uniq;
            outDir = dexPath + "_" + uniq+".DROPPED_FILE";

            InputStream in = new FileInputStream(dexPath);
            OutputStream out = new FileOutputStream(outDir);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            hookJson.put("path", outDir);
        }
		Logger.logHook(hookJson);
	}

}
