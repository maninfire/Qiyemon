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
    // registerContentObserver(Uri uri, boolean notifyForDescendants, ContentObserver observer)
    private void setregisterContentObserver(){
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList=new ArrayList<>();
        paranameList.add("provider");
        allparam.add(paranameList);
        findparanameMap.put("android.location.LocationManager->requestLocationUpdates",allparam);
    }

    private void setrequestLocationUpdates(){
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList=new ArrayList<>();
        paranameList.add("provider");
        allparam.add(paranameList);
        findparanameMap.put("android.location.LocationManager->requestLocationUpdates",allparam);
    }

    private void setstartservice(){
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList=new ArrayList<>();
        paranameList.add("Intent");
        allparam.add(paranameList);
        findparanameMap.put("android.content.ContextWrapper->startService",allparam);
    }
    private void sethandleStopService(){
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList=new ArrayList<>();
        paranameList.add("token");
        allparam.add(paranameList);
        findparanameMap.put("android.app.ActivityThread->handleStopService",allparam);
    }
    private void setAssetMangeropen(){
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList=new ArrayList<>();
        paranameList.add("fileName");
        allparam.add(paranameList);
        findparanameMap.put("android.content.res.AssetManager->open",allparam);
    }
    private void sethandleCreateService(){
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList=new ArrayList<>();
        paranameList.add("data");
        paranameList.add("resourceName");
        allparam.add(paranameList);
        findparanameMap.put("android.app.ActivityThread->handleCreateService",allparam);
    }
    private void setgetresourceAsStream(){
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList=new ArrayList<>();
        paranameList.add("md5");
        paranameList.add("resourceName");
        allparam.add(paranameList);
        findparanameMap.put("java.lang.ClassLoader->getResourceAsStream",allparam);
    }
    private void setregisterReceiver(){
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList=new ArrayList<>();
        paranameList.add("IntentFilter");
        paranameList.add("BroadcastReceiver");
        allparam.add(paranameList);
        findparanameMap.put("android.content.ContextWrapper->registerReceiver",allparam);
    }
    private void setreplaceall(){
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList=new ArrayList<>();
        paranameList.add("regularExpression");
        paranameList.add("replacement");
        allparam.add(paranameList);
        findparanameMap.put("java.lang.String->replaceAll",allparam);
    }

    private void setinvoke(){
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList=new ArrayList<>();
        paranameList.add("receiver");
        paranameList.add("methodName");
        paranameList.add("className");
        paranameList.add("args");
        allparam.add(paranameList);
        findparanameMap.put("java.lang.reflect->invoke",allparam);
    }
    private void setstringcontain(){
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList=new ArrayList<>();
        paranameList.add("String");
        allparam.add(paranameList);
        findparanameMap.put("java.lang.String->contains",allparam);
    }
    private void setgetApplication(){
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList=new ArrayList<>();
        paranameList.add("packageName");
        paranameList.add("flags");
        allparam.add(paranameList);
        findparanameMap.put("android.app.ApplicationPackageManager->getApplicationInfo",allparam);
    }

    private void setstringequal(){
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList=new ArrayList<>();
        paranameList.add("object");
        allparam.add(paranameList);
        findparanameMap.put("java.lang.String->equals",allparam);
    }
    private void setgetpackageInfo(){
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList=new ArrayList<>();
        paranameList.add("packageName");
        paranameList.add("flags");
        allparam.add(paranameList);
        findparanameMap.put("android.app.ApplicationPackageManager->getPackageInfo",allparam);
    }
    private void setFiledelet(){
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList=new ArrayList<>();
        paranameList.add("path");
        allparam.add(paranameList);
        findparanameMap.put("java.io.File->delete",allparam);
    }
    private void setFileoutinit(){
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList=new ArrayList<>();
        paranameList.add("append");
        paranameList.add("file");
        allparam.add(paranameList);
        findparanameMap.put("java.io.FileOutputStream-><init>",allparam);
    }
    private void initPara(){
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList=new ArrayList<>();
        paranameList.add("");
        //paranameList.remove();
        findparanameMap.put("",allparam);
        //map.get("key1")
        //map.remove("key1");
        //map.clear();
    }
    private void setsendTextMessage(){
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList=new ArrayList<>();
        paranameList.add("destinationAddress");
        paranameList.add("scAddress");
        paranameList.add("text");
        paranameList.add("sentIntent");
        allparam.add(paranameList);
        findparanameMap.put("android.telephony.SmsManager->sendTextMessage",allparam);
    }
    private void setcompareToIgnoreCase(){
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList=new ArrayList<>();
        paranameList.add("String");
        //paranameList.remove();
        allparam.add(paranameList);
        findparanameMap.put("java.lang.String->compareToIgnoreCase",allparam);
        //map.get("key1")
        //map.remove("key1");
        //map.clear();
    }
    private void setendsWith() {
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList = new ArrayList<>();
        paranameList.add("suffix");
        allparam.add(paranameList);
        findparanameMap.put("java.lang.String->endsWith", allparam);
    }
    private void setCipherinit() {
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList = new ArrayList<>();
        paranameList.add("opmode");
        paranameList.add("Algorithm");
        paranameList.add("Key");
        allparam.add(paranameList);
        findparanameMap.put("javax.crypto.Cipher->init", allparam);
    }
    private void setaddview() {
        List<List<String>> allparam=new ArrayList<>();
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
        allparam.add(paranameList);
        findparanameMap.put("android.view.WindowManager->addView", allparam);
    }
    private void setforname() {
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList = new ArrayList<>();
        paranameList.add("className");
        allparam.add(paranameList);
        findparanameMap.put("java.lang.Class->forName", allparam);
    }
    private void setstartActivityForResult() {
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList = new ArrayList<>();
        paranameList.add("Intent");
        allparam.add(paranameList);
        findparanameMap.put("android.app.Activity->startActivityForResult", allparam);
    }
    private void setDialogshow() {
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList = new ArrayList<>();
        paranameList.add("Text");
        allparam.add(paranameList);
        findparanameMap.put("android.app.Dialog->show", allparam);
    }
    private void setquery() {
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList = new ArrayList<>();
        paranameList.add("selectionArgs");
        paranameList.add("sortOrder");
        paranameList.add("selection");
        paranameList.add("url");
        paranameList.add("projection");
        allparam.add(paranameList);
        findparanameMap.put("android.content.ContentResolver->query", allparam);
    }
    private void setquery1() {
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList = new ArrayList<>();
        paranameList.add("Data");
        paranameList.add("Body");
        paranameList.add("Address");
        allparam.add(paranameList);
        findparanameMap.put("android.content.ContentResolver->query", allparam);
    }
    private void setgetMessageBody() {
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList = new ArrayList<>();
        allparam.add(paranameList);
        findparanameMap.put("android.telephony.SmsMessage->getMessageBody", allparam);
    }
    private void setsleep() {
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList = new ArrayList<>();
        paranameList.add("millis");
        allparam.add(paranameList);
        findparanameMap.put("java.lang.Thread->sleep", allparam);
    }
    private void setPexec() {
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList0 = new ArrayList<>();
        paranameList0.add("command");
        paranameList0.add("directory");
        paranameList0.add("callback");
        List<String> paranameList1 = new ArrayList<>();
        paranameList1.add("command");
        paranameList1.add("directory");
        paranameList1.add("callback");
        paranameList1.add("in");
        paranameList1.add("out");
        paranameList1.add("redirectErrorStream");
        allparam.add(paranameList0);
        allparam.add(paranameList1);
        findparanameMap.put("java.lang.ProcessManager->exec", allparam);
    }
    private void setIoopen() {
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList = new ArrayList<>();
        paranameList.add("path");
        paranameList.add("flag");
        allparam.add(paranameList);
        findparanameMap.put("libcore.io.IoBridge->open", allparam);
    }
    private void setnativeLoad() {
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList = new ArrayList<>();
        paranameList.add("filename");
        paranameList.add("loader");
        paranameList.add("ldLibraryPath");
        allparam.add(paranameList);
        findparanameMap.put("java.lang.Runtime->nativeLoad", allparam);
    }
    private void setSysProset() {
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList = new ArrayList<>();
        paranameList.add("key");
        allparam.add(paranameList);
        findparanameMap.put("android.os.SystemProperties->get", allparam);
    }
    private void setreplace() {
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList = new ArrayList<>();
        paranameList.add("oldChar");
        paranameList.add("newChar");
        allparam.add(paranameList);
        findparanameMap.put("java.lang.String->replace", allparam);
    }
    private void setcompareTo() {
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList = new ArrayList<>();
        paranameList.add("anotherString");
        allparam.add(paranameList);
        findparanameMap.put("java.lang.String->compareTo", allparam);
    }
    private void setRexec() {
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList = new ArrayList<>();
        paranameList.add("command");
        allparam.add(paranameList);
        findparanameMap.put("java.lang.Runtime->exec", allparam);
    }
    private void setsplit() {
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList = new ArrayList<>();
        paranameList.add("regex");
        paranameList.add("limit");
        allparam.add(paranameList);
        findparanameMap.put("java.lang.String->split", allparam);
    }
    private void setdoFinal() {
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList = new ArrayList<>();
        paranameList.add("input");
        paranameList.add("limit");
        allparam.add(paranameList);
        findparanameMap.put("javax.crypto.Cipher->doFinal", allparam);
    }
    private void setBdecode() {
        List<List<String>> allparam=new ArrayList<>();
        List<String> paranameList = new ArrayList<>();
        paranameList.add("str");
        paranameList.add("flags");
        allparam.add(paranameList);
        findparanameMap.put("android.util.Base64->decode", allparam);
    }

}
