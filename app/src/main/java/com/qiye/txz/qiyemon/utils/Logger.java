package com.qiye.txz.qiyemon.utils;

import android.util.Log;

import com.google.gson.Gson;

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
import java.util.List;
import java.util.UUID;

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
		//XposedBridge.log(LOGTAG_SHELL+PACKAGENAME+":"+message);
        //XposedBridge.log("logShell");
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
	
	public static void logGenericMethod(MethodHookParam param, boolean mThisObject, /*String*/ String mType) throws JSONException {
		//JSONObject hookJson = ParseGeneratorNotype.generateHookDataJson(param,mType);
        JSONObject tempjson;
		JSONObject hookJson = new JSONObject();
		List<List<String>> paraname=null;
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
				//XposedBridge.log("specialwork");
				hookJson.put("Parameters", ParseGeneratorNotype.parseArgsspecial(param, hookJson, paraname));
			}
			else{
				//XposedBridge.log("specialnotwork");
				hookJson.put("Parameters", ParseGeneratorNotype.parseArgs(param,hookJson,paraname));
			}
        if(param.getResult()!=null)
			//hookJson.put("result",param.getResult());
			hookJson.put("result", ParseGeneratorNotype.parseResults(param,hookJson));
		if(param.thisObject!=null && mThisObject)
			hookJson.put("this",ParseGeneratorNotype.parseThis(param,hookJson));
		hookJson=ParseGeneratorNotype.addHookDataJson(hookJson,param,mType);
		Logger.logHook(hookJson);
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
