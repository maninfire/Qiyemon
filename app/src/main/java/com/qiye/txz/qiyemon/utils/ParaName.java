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
        setCipherdoFinal();
        setaddview();
        setforname();
        setstartActivityForResult();
        setDialogshow();
        setquery1();
        setgetMessageBody();
        setsleep();
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
    }
    public void initPara(){
        List<String> paranameList=new ArrayList<>();
        paranameList.add("");
        //paranameList.remove();
        findparanameMap.put("",paranameList);
        //map.get("key1")
        //map.remove("key1");
        //map.clear();
    }
    public void setsendTextMessage(){
        List<String> paranameList=new ArrayList<>();
        paranameList.add("destinationAddress");
        paranameList.add("scAddress");
        paranameList.add("text");
        paranameList.add("sentIntent");
        findparanameMap.put("android.telephony.SmsManager->sendTextMessage",paranameList);
    }
    public void setcompareToIgnoreCase(){
        List<String> paranameList=new ArrayList<>();
        paranameList.add("String");
        //paranameList.remove();
        findparanameMap.put("java.lang.String->compareToIgnoreCase",paranameList);
        //map.get("key1")
        //map.remove("key1");
        //map.clear();
    }
    public void setendsWith() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("suffix");
        findparanameMap.put("java.lang.String->endsWith", paranameList);
    }
    public void setCipherinit() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("opmode");
        paranameList.add("Algorithm");
        paranameList.add("Key");
        findparanameMap.put("javax.crypto.Cipher->init", paranameList);
    }
    public void setCipherdoFinal() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("input");
        findparanameMap.put("javax.crypto.Cipher->init", paranameList);
    }
    public void setaddview() {
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
    public void setforname() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("className");
        findparanameMap.put("java.lang.Class->forName", paranameList);
    }
    public void setstartActivityForResult() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("Intent");
        findparanameMap.put("android.app.Activity->startActivityForResult", paranameList);
    }
    public void setDialogshow() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("Text");
        findparanameMap.put("android.app.Dialog->show", paranameList);
    }
    public void setquery() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("selectionArgs");
        paranameList.add("sortOrder");
        paranameList.add("selection");
        paranameList.add("url");
        paranameList.add("projection");
        findparanameMap.put("android.content.ContentResolver->query", paranameList);
    }
    public void setquery1() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("Data");
        paranameList.add("Body");
        paranameList.add("Address");
        findparanameMap.put("android.content.ContentResolver->query", paranameList);
    }
    public void setgetMessageBody() {
        List<String> paranameList = new ArrayList<>();
        findparanameMap.put("android.telephony.SmsMessage->getMessageBody", paranameList);
    }
    public void setsleep() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("millis");
        findparanameMap.put("java.lang.Thread->sleep", paranameList);
    }
    public void setPexec() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("directory");
        paranameList.add("prog");
        findparanameMap.put("java.lang.ProcessManager->exec", paranameList);
    }
    public void setIoopen() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("path");
        paranameList.add("flag");
        findparanameMap.put("libcore.io.IoBridge->open", paranameList);
    }
    public void setnativeLoad() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("filename");
        paranameList.add("loader");
        paranameList.add("ldLibraryPath");
        findparanameMap.put("java.lang.Runtime->nativeLoad", paranameList);
    }
    public void setSysProset() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("key");
        findparanameMap.put("android.os.SystemProperties->get", paranameList);
    }
    public void setreplace() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("oldChar");
        paranameList.add("newChar");
        findparanameMap.put("java.lang.String->replace", paranameList);
    }
    public void setcompareTo() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("anotherString");
        findparanameMap.put("java.lang.String->compareTo", paranameList);
    }
    public void setRexec() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("command");
        findparanameMap.put("java.lang.Runtime->exec", paranameList);
    }
    public void setsplit() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("regex");
        paranameList.add("limit");
        findparanameMap.put("java.lang.String->split", paranameList);
    }
    public void setdoFinal() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("input");
        paranameList.add("limit");
        findparanameMap.put("javax.crypto.Cipher->doFinal", paranameList);
    }
    public void setBdecode() {
        List<String> paranameList = new ArrayList<>();
        paranameList.add("str");
        paranameList.add("flags");
        findparanameMap.put("android.util.Base64->decode", paranameList);
    }

}
