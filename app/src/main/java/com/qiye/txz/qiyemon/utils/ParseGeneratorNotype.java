package com.qiye.txz.qiyemon.utils;

import android.app.DownloadManager;
import android.app.Notification;
import android.content.ComponentName;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Process;
import android.util.SparseArray;
import android.view.WindowManager;

import com.google.gson.Gson;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XposedBridge;


public class ParseGeneratorNotype {
	private static Gson gson = new Gson();
	private static ParaName pn=new ParaName();

	public static Object parse(Object obj) throws Exception {
		Object returnObj=null;
		int i=0;
		if(obj instanceof byte[])
			returnObj = byteArrayParse(obj);  //////
		else if(obj instanceof HttpRequestBase)
			returnObj = httpRequestBaseParse(obj);
		else if(obj instanceof HttpResponse)
			returnObj = httpResponseParse(obj);
		else if(obj instanceof URL)
			returnObj = URLParse(obj);
		else if(obj instanceof Cipher){
			returnObj = cipherParse((Cipher)obj);
		}else if(obj instanceof SecretKeySpec){

			returnObj = SecretKeySpecParse(obj);
		}else if(obj instanceof String||obj instanceof Integer || obj instanceof Float){
            returnObj = Stringparse(obj);
		}else if(obj instanceof String[]){
            returnObj = StringListparse(obj);
        }else if(obj instanceof Integer || obj instanceof Float ||obj instanceof ContentResolver){
            returnObj = parsetostirng(obj);
        }else if(obj instanceof File){
            //XposedBridge.log(obj.toString());
            returnObj = Fileparse(obj);
        }else if(obj instanceof DownloadManager.Request){
            Requestparse(obj);
        }else if(obj instanceof ComponentName){
			returnObj=ComponentNameparse(obj);
		}else if(obj instanceof Intent) {
            returnObj = intentParse(obj);
        }else
			returnObj = genericParse(obj);////
		return returnObj;
	}
	public static JSONObject windowsMangerlayoutParamsParseafter(Object obj){
            Object v0 = obj;
            if(((WindowManager.LayoutParams)v0).type >= 1 && ((WindowManager.LayoutParams)v0).type <= 99) {
                return null;
            }

            if(((WindowManager.LayoutParams)v0).type >= 1000 && ((WindowManager.LayoutParams)v0).type <= 1999) {
                return null;
            }

            if(((WindowManager.LayoutParams)v0).type < 2000) {
                return null;
            }

            if(((WindowManager.LayoutParams)v0).type > 2999) {
                return null;
            }
            try {
                JSONObject v2 = new JSONObject();
                JSONObject v3 = new JSONObject();
                v3.put("window", "SYSTEM_WINDOW");
                v3.put("type", ((WindowManager.LayoutParams)v0).type);
                v3.put("flags", ((WindowManager.LayoutParams)v0).flags);
                v3.put("height", ((WindowManager.LayoutParams)v0).height);
                v3.put("width", ((WindowManager.LayoutParams)v0).width);
                long v0_1 = System.currentTimeMillis();
                String v4 = String.valueOf(v0_1) + ".png";
                String v5 = "/data/local/tmp/dumpFile/" + v4;
                v3.put("screenshot", v4);
                v3.put("uidump", "uidump_" + String.valueOf(v0_1) + ".xml");
                return v3;
            }catch (Exception e){
             return null;
            }
    }
    public static JSONObject windowsMangerlayoutParamsParsebefore(Object obj){
        String v1;
        String v3 = "";
        String v4 = "";
        Object v0 = obj;
        SparseArray j = new SparseArray();
        j.put(1, "TYPE_BASE_APPLICATION");
        j.put(2, "TYPE_APPLICATION");
        j.put(3, "TYPE_APPLICATION_STARTING");
        j.put(1000, "TYPE_APPLICATION_PANEL");
        j.put(1001, "TYPE_APPLICATION_MEDIA");
        j.put(1002, "TYPE_APPLICATION_SUB_PANEL");
        j.put(1003, "TYPE_APPLICATION_ATTACHED_DIALOG");
        j.put(2000, "TYPE_STATUS_BAR");
        j.put(2001, "TYPE_SEARCH_BAR");
        j.put(2002, "TYPE_PHONE");
        j.put(2003, "TYPE_SYSTEM_ALERT");
        j.put(2005, "TYPE_TOAST");
        j.put(2006, "TYPE_SYSTEM_OVERLAY");
        j.put(2007, "TYPE_PRIORITY_PHONE");
        j.put(2014, "TYPE_STATUS_BAR_PANEL");
        j.put(2008, "TYPE_SYSTEM_DIALOG");
        j.put(2009, "TYPE_KEYGUARD_DIALOG");
        j.put(2010, "TYPE_SYSTEM_ERROR");
        j.put(2011, "TYPE_INPUT_METHOD");
        j.put(2012, "TYPE_INPUT_METHOD_DIALOG");
        try{
        if(((WindowManager.LayoutParams)v0).type < 1 || ((WindowManager.LayoutParams)v0).type > 99) {
            if(((WindowManager.LayoutParams)v0).type >= 1000 && ((WindowManager.LayoutParams)v0).type <= 1999) {
                v1 = "SUB_WINDOW";
            }else if(((WindowManager.LayoutParams)v0).type >= 2000 && ((WindowManager.LayoutParams)v0).type <= 2999){
                v1 = "SYSTEM_WINDOW";
            }else{
                v1 = "UNKOWN_WINDOW";
            }
        }else {
                v1 = "APPLICATION_WINDOW";
        }
            JSONObject v5 = new JSONObject();
            JSONObject v6 = new JSONObject();
            String v7 = "SelfPackagename";
            String v2 = "";
            v6.put(v7, v2);
            v6.put("window", v1);
            Object v1_1 = j.get(((WindowManager.LayoutParams)v0).type);
            v2 = "WinType";
            if(v1_1 == null) {
                v1 = "UNKOWN_TYPE";
            }

            v6.put(v2, v1);
            v6.put("type", ((WindowManager.LayoutParams)v0).type);
            v6.put("flags", ((WindowManager.LayoutParams)v0).flags);
            v6.put("height", ((WindowManager.LayoutParams)v0).height);
            v6.put("width", ((WindowManager.LayoutParams)v0).width);
            v6.put("TopPackageName", v3);
            v6.put("TopClassName", v4);
            v5.put("Parameters", v6);

            return v5;
        }catch (Exception e){
            return null;
        }
    }
	private static Object Fileparse(Object obj){
		File a=(File)obj;
		String path=a.getAbsolutePath();
		return path;
	}
	private static Object ComponentNameparse(Object obj){
		//(ComponentName)obj.getPackageName() + "/" + param.args[0].getClassName();
		ComponentName admin=(ComponentName)obj;
		String v0="";
		v0=admin.getPackageName()+"/"+admin.getClassName();
		return (Object) v0;
	}
    private static Object Requestparse(Object obj){
	    //XposedBridge.log(ObjtoJson(obj)+"");
        return ObjtoJson(obj);
    }
	private static Object Notificationparse(Object obj){
	    return obj;
    }
	private static  Object parsetostirng(Object obj){
	    JSONObject json = new JSONObject();
	    JSONObject nint = new JSONObject();
	    nint=ObjtoJson(obj);
	    //json.put("value",nint.get("value"));
	    return obj.toString();
    }
    private static Object StringListparse(Object objs){
	    JSONObject args = new JSONObject();
        String strlist="[";
        for (Object object: (Object[])objs) {
            if(strlist.equals("[")){
                strlist+="\""+object+"\"";
                continue;
            }
            strlist+=",\""+object+"\"";
        }
        strlist+="]";
	    return strlist;
    }
	private static Object Stringparse(Object obj){
	    //XposedBridge.log(obj.toString());
		String arg3=obj.toString();
		int v1 = 128;
		if(arg3 == null) {
			arg3 = "";
		}
		else if(arg3.length() > v1) {
			arg3 = arg3.substring(0, v1);
		}
        //XposedBridge.log(arg3);
		return arg3;
	}
    public static JSONObject ObjtoJson(Object obj){
		JSONObject json=new JSONObject();
		//Field[] fields = obj.getClass().getDeclaredFields();
		for(Method method:obj.getClass().getMethods()){
			//XposedBridge.log(method.getName());
		}
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields)
		{
			// 对于每个属性，获取属性名
			String varName = field.getName();
			try
			{
				boolean access = field.isAccessible();
				if(!access) field.setAccessible(true);

				//从obj中获取field变量
				Object o = field.get(obj);
				//System.out.println("变量： " + varName + " = " + o);
				//XposedBridge.log("变量： " + varName + " = " + o);
				json.put(varName,o);
				if(!access) field.setAccessible(false);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return json;
	}
	public static Object  reflectobj(Object obj,String name,Class<?>...parameterTypes){
		JSONObject json=new JSONObject();
		Field[] fields = obj.getClass().getDeclaredFields();
		for(Method method:obj.getClass().getMethods()) {
			//XposedBridge.log(method.getName());
		}
		try{
			Method med = obj.getClass().getMethod(name,parameterTypes);
			return med.invoke(obj,"");
		}catch (Exception e){
			XposedBridge.log(e);
		}
		return null;
	}
	private static Object URLParse(Object obj) {
		URL url = (URL) obj;
		return url.toString();
	}

	private static String byteArrayParse(Object obj) {
		if(Strings.isAsciiPrintable((byte[]) obj))
			return new String((byte[])obj);
		else
			return HexDump.dumpHexString((byte[]) obj);
	}
	private static Object SecretKeySpecParse(Object obj) throws JSONException {
		//XposedBridge.log("SecretKeySpecParse1");

		JSONObject json=new JSONObject();
		//JSONObject cipher =new JSONObject(gson.toJson(obj));
		JSONObject cipher =ObjtoJson(obj);
		//json.put("mode", cipher.get("mode"));
		json.put("algorithm",cipher.get("algorithm"));
		//Cipher.
		return cipher.get("algorithm");

	}
	private static JSONObject cipherParse(Object obj) throws JSONException {
		JSONObject json=new JSONObject();
		//JSONObject cipher =new JSONObject(gson.toJson(obj));
		JSONObject cipher =ObjtoJson(obj);
		json.put("mode", cipher.get("mode"));
		//Cipher.
		return json;
	}

	private static Object genericParse(Object obj) {
		try {
			//return obj;
			return ObjtoJson(obj);
		} catch (Exception e) {
			try {
				return new JSONArray(gson.toJson(obj));
			} catch (Exception e1) {
				return obj.toString();
			}
		}
	}

	private static String httpRequestBaseParse(Object obj) throws IOException {
		HttpRequestBase request = (HttpRequestBase)obj;
		StringBuilder sb = new StringBuilder();

		//PreferenceActivity.Header[] headers = request.getAllHeaders();
		Header[] headers = request.getAllHeaders();
		sb.append(request.getRequestLine().toString()+"\n");

		for (Header header : headers) {
			sb.append(header.getName() + ": " + header.getValue()+"\n");
		}

		sb.append("\n");

		if(request instanceof HttpPost)
			sb.append( EntityUtils.toString(((HttpPost) request).getEntity()));
		return sb.toString();
	}

	private static Object httpResponseParse(Object obj) throws IOException {
		HttpResponse response = (HttpResponse)obj;
		return response.getStatusLine().toString();
	}
	
	private static Object intentParse(Object obj){
		JSONObject json=new JSONObject();
		Intent intent = (Intent) obj;

		Bundle bundle = intent.getExtras();
		String cmp;
		try {
			cmp = intent.getComponent().flattenToString();
			json.put("cmp",cmp);
		} catch (Exception e1) {
		}

		try {
			String action = intent.getAction();
			json.put("act", action);
		} catch (Exception e1) {
		}
		JSONArray extraData = new JSONArray();

		try {
			Set<String> set = bundle.keySet();
			for (String key : set) {
				JSONObject extra= new JSONObject();
				extra.put("key",key);
				extra.put("value",bundle.get(key).toString());
				extraData.put(extra);
			}
			json.put("extras", extraData);
		} catch (Exception e) {
		}
		return json;
	}

	private static JSONObject URLConnectionParse(Object obj) throws IOException, JSONException {
		HttpURLConnection con = (HttpURLConnection) obj;
		JSONObject urlConnectionObject=new JSONObject();

		urlConnectionObject.put("response_code", con.getResponseCode());
		urlConnectionObject.put("response_message", con.getResponseMessage());
		urlConnectionObject.put("request_method",con.getRequestMethod());
		urlConnectionObject.put("url",con.getURL());
		urlConnectionObject.put("version","HTTP/1.1");

		return urlConnectionObject;
	}

	private static String messageDigestParse(Object obj) {
		return "";
	}

	public static JSONObject generateHookDataJson(MethodHookParam param, String type) throws JSONException
	{
		JSONObject hookData= new JSONObject();
        hookData.put("PID", Process.myPid());
        hookData.put("TID", Process.myTid());
        hookData.put("UID", Process.myUid());
		hookData.put("Method",param.method.getDeclaringClass().getName()+"->"+param.method.getName());
		//hookData.put("method", param.method.getName());
		hookData.put("Function", type);
		hookData.put("Time", System.currentTimeMillis());
		return hookData;
	}

	public static JSONObject addHookDataJson(JSONObject extraData,MethodHookParam param, String type) throws JSONException
	{
		//JSONObject hookData= new JSONObject();
		JSONObject hookData=extraData;
		hookData.put("PID", Process.myPid());
		hookData.put("TID", Process.myTid());
		hookData.put("UID", Process.myUid());
		hookData.put("Method",param.method.getDeclaringClass().getName()+"->"+param.method.getName());
		//hookData.put("method", param.method.getName());
		hookData.put("Function", type);
		hookData.put("Time", System.currentTimeMillis());
		return hookData;
	}

    public static JSONObject parseArgsspecial(MethodHookParam param, JSONObject hookJson,List<List<String>> allparam,String tag)
    {
        JSONObject args =  new JSONObject();
        List<String> paraname=new ArrayList<String>();  ;
        int i=0;
        for(List<String> selfparam:allparam){
            if(selfparam.size()== param.args.length){
                paraname.addAll(selfparam);
            }
        }

        for (Object object : (Object[]) param.args) {
            try {
                if(paraname.size()>i){
                    if((param.method.getDeclaringClass().getName()+"->"+param.method.getName()).equals("javax.crypto.Cipher->init")){
                        if(paraname.get(i).equals("opmode")){
                            args.put("opmode", Cipheropmodeparse(object).get("opmode"));
                            i++;
                            continue;
                        }
                    }else if((param.method.getDeclaringClass().getName()+"->"+param.method.getName()).equals("android.view.WindowManager->addView")){
                        if(param.args[1] instanceof WindowManager.LayoutParams){
                            args.put("opmode", Cipheropmodeparse(object).get("opmode"));
                            i++;
                            continue;
                        }
                    }
                    args.put(paraname.get(i), ParseGeneratorNotype.parse(object));
                }else{
                    args.put(object.getClass().getName(),ParseGeneratorNotype.parse(object));
                }
            } catch (Exception e) {
                Logger.logShell("args error: " + e.getMessage()+" "+hookJson.toString());
            }
            i++;
        }
        return args;
    }

    public static JSONObject parseArgs(MethodHookParam param, JSONObject hookJson,List<List<String>> allparam)
    {
        JSONObject args =  new JSONObject();
        List<String> paraname=new ArrayList<String>();  ;
        int i=0;
        //XposedBridge.log(param.method.getName()+"..........");
        for(List<String> selfparam:allparam){

           // XposedBridge.log(selfparam.size()+"canshuchangdu");
           // XposedBridge.log(param.args.length+"changdu");\
            for(String name:selfparam){
                //XposedBridge.log(name);
            }
            if(selfparam.size()== param.args.length){
                paraname.addAll(selfparam);
            }
        }
        if((param.method.getDeclaringClass().getName()+"->"+param.method.getName()).equals("android.content.ContextWrapper->registerReceiver")) {
            //XposedBridge.log("start");
            for (Object object : (Object[]) param.args) {
                try{
                    //XposedBridge.log(object.getClass().getName()+":"+ParseGeneratorNotype.parse(object));
                }catch(Exception e){
                   // XposedBridge.log(e);
                }

            }
        }

        for (Object object : (Object[]) param.args) {
            try {
                    if(paraname.size()>i){
                        if((param.method.getDeclaringClass().getName()+"->"+param.method.getName()).equals("javax.crypto.Cipher->init")){
                            //XposedBridge.log("cipherinit");
                            if(paraname.get(i).equals("opmode")){
                                //XposedBridge.log(Cipheropmodeparse(object).get("opmode").toString());
                                args.put("opmode", Cipheropmodeparse(object).get("opmode"));
                                i++;
                                continue;
                            }
                        }
                        args.put(paraname.get(i), ParseGeneratorNotype.parse(object));
                    }else{
                        args.put(object.getClass().getName(),ParseGeneratorNotype.parse(object));
                    }
            } catch (Exception e) {
                Logger.logShell("args error: " + e.getMessage()+" "+hookJson.toString());
            }

			i++;
        }
        return args;
    }

	public static Object parseResults(MethodHookParam param, JSONObject hookJson)
	{
		try {
			return ParseGeneratorNotype.parse(param.getResult());
		} catch (Exception e) {
			Logger.logShell("result error: " + e.getMessage()+" "+hookJson.toString());
			return "";
		}
	}
	
	public static Object parseThis(MethodHookParam param, JSONObject hookJson)
	{
		try {
			return ParseGeneratorNotype.parse(param.thisObject);
		} catch (Exception e) {
			Logger.logShell("thisObject error: " + e.getMessage()+" "+hookJson.toString());
			return "";
		}
	}
	
	public static String parseRefelctionMethodName(MethodHookParam param, JSONObject hookJson) {
			Method method = (Method) param.thisObject;
			if(method!=null)
				return method.getName();
			else
				return "";
	}
	
	public static String parseRefelctionClassName(MethodHookParam param, JSONObject hookJson) {
			if(param.args[0]!=null)
				return param.args[0].getClass().getName();
			else
				return "";
	}
	
	public static JSONArray parseRefelctionArgs(MethodHookParam param, JSONObject hookJson) {
		JSONArray args =  new JSONArray();
		if((Object[]) param.args[1]!=null)
		{
			for (Object object : (Object[]) param.args[1]) {
				try {
					if(object!=null)
						args.put(ParseGeneratorNotype.parse(object));
				} catch (Exception e) {
					Logger.logShell("reflection args error: " + e.getMessage()+" "+hookJson.toString());
				}
			}
		}
		return args;
	}

	public static JSONObject Cipheropmodeparse(Object object){
		JSONObject args =  new JSONObject();
		try{
				if(object.getClass().getName().equals("java.lang.Integer")){
					//XposedBridge.log("Integer");
					//XposedBridge.log(ParseGeneratorNotype.ObjtoJson(object).get("value").toString());
					if(ParseGeneratorNotype.ObjtoJson(object).get("value").toString().equals("1")){
						//XposedBridge.log("1");
						args.put("opmode","ENCRYPT_MODE");
					}else if(ParseGeneratorNotype.ObjtoJson(object).get("value").toString().equals("2")){
						//XposedBridge.log("2");
						args.put("opmode","DECRYPT_MODE");
					}else if(ParseGeneratorNotype.ObjtoJson(object).get("value").toString().equals("3")){
						//XposedBridge.log("3");
						args.put("opmode","WRAP_MODE");
					}else if(ParseGeneratorNotype.ObjtoJson(object).get("value").toString().equals("4")){
						//XposedBridge.log("4");
						args.put("opmode","UNWRAP_MODE");
					}
				}
		}catch (Exception e){
			XposedBridge.log(e);
		}
		return args;
	}
}

