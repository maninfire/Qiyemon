package com.qiye.txz.qiyemon.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParaName {

    Map<String, List> findparanameMap;
    public ParaName(){
        findparanameMap= new HashMap<String,List>();
        setsendTextMessage();
        setcompareToIgnoreCase();
        setendsWith();
        setCipherinit();
        setaddview();
        setforname();
        setstartActivityForResult();
        setDialogshow();
        setquery1();
        setgetMessageBody();
        //setsleep();
        setPexec();
        setIoopen();
        setnativeLoad();
        setSysProset();
        setreplace();
        setcompareTo();
        setRexec();
        setsplit();
        setdoFinal();
        setBdecode();
        setFileoutinit();
        setFiledelet();
        setgetpackageInfo();
        setgetApplication();
        setstringequal();
        setstringcontain();
        setinvoke();
        setreplaceall();
        setregisterReceiver();
        setgetresourceAsStream();
        sethandleCreateService();
        setAssetMangeropen();
        sethandleStopService();
        setstartservice();
        setrequestLocationUpdates();
    }

    private void setrequestLocationUpdates(){
        List<String> paranameList=new ArrayList<>();
        paranameList.add("provider");
        findparanameMap.put("android.location.LocationManager->requestLocationUpdates",paranameList);
    }

    private void setstartservice(){
        List<String> paranameList=new ArrayList<>();
        paranameList.add("Intent");
        findparanameMap.put("android.content.ContextWrapper->startService",paranameList);
    }
    private void sethandleStopService(){
        List<String> paranameList=new ArrayList<>();
        paranameList.add("token");
        findparanameMap.put("android.app.ActivityThread->handleStopService",paranameList);
    }
    private void setAssetMangeropen(){
        List<String> paranameList=new ArrayList<>();
        paranameList.add("fileName");
        findparanameMap.put("android.content.res.AssetManager->open",paranameList);
    }
    private void sethandleCreateService(){
        List<String> paranameList=new ArrayList<>();
        paranameList.add("data");
        paranameList.add("resourceName");
        findparanameMap.put("android.app.ActivityThread->handleCreateService",paranameList);
    }
    private void setgetresourceAsStream(){
        List<String> paranameList=new ArrayList<>();
        paranameList.add("md5");
        paranameList.add("resourceName");
        findparanameMap.put("java.lang.ClassLoader->getResourceAsStream",paranameList);
    }
    private void setregisterReceiver(){
        List<String> paranameList=new ArrayList<>();
        paranameList.add("IntentFilter");
        paranameList.add("BroadcastReceiver");
        findparanameMap.put("android.content.ContextWrapper->registerReceiver",paranameList);
    }
    private void setreplaceall(){
        List<String> paranameList=new ArrayList<>();
        paranameList.add("regularExpression");
        paranameList.add("replacement");
        findparanameMap.put("java.lang.String->replaceAll",paranameList);
    }

    private void setinvoke(){
        List<String> paranameList=new ArrayList<>();
        paranameList.add("receiver");
        paranameList.add("methodName");
        paranameList.add("className");
        paranameList.add("args");
        findparanameMap.put("java.lang.reflect->invoke",paranameList);
    }
    private void setstringcontain(){
        List<String> paranameList=new ArrayList<>();
        paranameList.add("String");
        findparanameMap.put("java.lang.String->contains",paranameList);
    }
    private void setgetApplication(){
        List<String> paranameList=new ArrayList<>();
        paranameList.add("packageName");
        paranameList.add("flags");
        findparanameMap.put("android.app.ApplicationPackageManager->getApplicationInfo",paranameList);
    }

    private void setstringequal(){
        List<String> paranameList=new ArrayList<>();
        paranameList.add("object");
        findparanameMap.put("java.lang.String->equals",paranameList);
    }
    private void setgetpackageInfo(){
        List<String> paranameList=new ArrayList<>();
        paranameList.add("packageName");
        paranameList.add("flags");
        findparanameMap.put("android.app.ApplicationPackageManager->getPackageInfo",paranameList);
    }
    private void setFiledelet(){
        List<String> paranameList=new ArrayList<>();
        paranameList.add("path");
        findparanameMap.put("java.io.File->delete",paranameList);
    }
    private void setFileoutinit(){
        List<String> paranameList=new ArrayList<>();
        paranameList.add("append");
        paranameList.add("file");
        findparanameMap.put("java.io.FileOutputStream-><init>",paranameList);
    }
    private void initPara(){
        List<String> paranameList=new ArrayList<>();
        paranameList.add("");
        //paranameList.remove();
        findparanameMap.put("",paranameList);
        //map.get("key1")
        //map.remove("key1");
        //map.clear();
    }
    private void setsendTextMessage(){
        List<String> paranameList=new ArrayList<>();
        paranameList.add("destinationAddress");
        paranameList.add("scAddress");
        paranameList.add("text");
        paranameList.add("sentIntent");
        findparanameMap.put("android.telephony.SmsManager->sendTextMessage",paranameList);
    }
    private void setcompareToIgnoreCase(){
        List<String> paranameList=new ArrayList<>();
        paranameList.add("String");
        //paranameList.remove();
        findparanameMap.put("java.lang.String->compareToIgnoreCase",paranameList);
        //map.get("key1")
        //map.remove("key1");
        //map.clear();
    }
    private void setendsWith() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("suffix");
        findparanameMap.put("java.lang.String->endsWith", paranameList);
    }
    private void setCipherinit() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("opmode");
        paranameList.add("Algorithm");
        paranameList.add("Key");
        findparanameMap.put("javax.crypto.Cipher->init", paranameList);
    }
    private void setaddview() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("height");
        paranameList.add("flags");
        paranameList.add("TopPackageName");
        paranameList.add("window");
        paranameList.add("SelfPackagename");
        paranameList.add("width");
        paranameList.add("TopClassName");
        paranameList.add("type");
        paranameList.add("WinType");
        findparanameMap.put("android.view.WindowManager->addView", paranameList);
    }
    private void setforname() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("className");
        findparanameMap.put("java.lang.Class->forName", paranameList);
    }
    private void setstartActivityForResult() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("Intent");
        findparanameMap.put("android.app.Activity->startActivityForResult", paranameList);
    }
    private void setDialogshow() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("Text");
        findparanameMap.put("android.app.Dialog->show", paranameList);
    }
    private void setquery() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("selectionArgs");
        paranameList.add("sortOrder");
        paranameList.add("selection");
        paranameList.add("url");
        paranameList.add("projection");
        findparanameMap.put("android.content.ContentResolver->query", paranameList);
    }
    private void setquery1() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("Data");
        paranameList.add("Body");
        paranameList.add("Address");
        findparanameMap.put("android.content.ContentResolver->query", paranameList);
    }
    private void setgetMessageBody() {
        List<String> paranameList = new ArrayList<>();
        findparanameMap.put("android.telephony.SmsMessage->getMessageBody", paranameList);
    }
    private void setsleep() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("millis");
        findparanameMap.put("java.lang.Thread->sleep", paranameList);
    }
    private void setPexec() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("directory");
        paranameList.add("prog");
        findparanameMap.put("java.lang.ProcessManager->exec", paranameList);
    }
    private void setIoopen() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("path");
        paranameList.add("flag");
        findparanameMap.put("libcore.io.IoBridge->open", paranameList);
    }
    private void setnativeLoad() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("filename");
        paranameList.add("loader");
        paranameList.add("ldLibraryPath");
        findparanameMap.put("java.lang.Runtime->nativeLoad", paranameList);
    }
    private void setSysProset() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("key");
        findparanameMap.put("android.os.SystemProperties->get", paranameList);
    }
    private void setreplace() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("oldChar");
        paranameList.add("newChar");
        findparanameMap.put("java.lang.String->replace", paranameList);
    }
    private void setcompareTo() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("anotherString");
        findparanameMap.put("java.lang.String->compareTo", paranameList);
    }
    private void setRexec() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("command");
        findparanameMap.put("java.lang.Runtime->exec", paranameList);
    }
    private void setsplit() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("regex");
        paranameList.add("limit");
        findparanameMap.put("java.lang.String->split", paranameList);
    }
    private void setdoFinal() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("input");
        paranameList.add("limit");
        findparanameMap.put("javax.crypto.Cipher->doFinal", paranameList);
    }
    private void setBdecode() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("str");
        paranameList.add("flags");
        findparanameMap.put("android.util.Base64->decode", paranameList);
    }

}
