package com.qiye.txz.qiyemon;

import android.content.pm.ApplicationInfo;

import com.google.gson.Gson;
import com.qiye.txz.qiyemon.utils.Files;
import com.qiye.txz.qiyemon.utils.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static de.robv.android.xposed.XposedHelpers.findClass;
import android.content.ContentResolver.*;
public class InstrumentationManager implements IXposedHookLoadPackage, IXposedHookZygoteInit {
    public boolean off = false;

    public static String CONFIG_FILE = "/data/local/tmp/hooks.json";
    public static String CONFIG_NATIVE_FILE = "/data/local/tmp/hooknatives.json";
    public static Boolean TRACE = false;
    public static String INJECTSO = "/data/local/tmp/mycydia.so";
    private static Set<String> OS_APPS = new HashSet<String>(Arrays.asList(
            "com.android.systemui"
            ,"com.android.inputmethod.latin"
            ,"com.android.phone"
            ,"com.android.launcher"
            ,"com.android.settings"
            ,"com.android.contacts"
            ,"com.android.providers.calendar"
            ,"android.process.media"
            ,"com.android.mms"
            ,"com.android.deskclock"
            ,"com.android.email"
            ,"com.android.exchange"
            ,"com.cuckoo.android.agent"
            ,"com.noshufou.android.su"
            ,"com.android.calendar"
            ,"com.example.zhangzhenguo.droidmon"
            ,"de.robv.android.xposed.installer"
            ,"com.supercleaner"
            ,"com.mgyapp.android"
            ,"com.kingroot.kinguser"
            ,"com.kingsoft"
            ,"com.tencent.android.qqdownloader"
            ,"com.qiye.txz.heartbeatdetect"
            ,"com.qiye.txz.sendsms")) ;

    // Call when zygote initialize
    public void initZygote(StartupParam startupParam) throws Throwable {
        hookNonSystemServices();

    }

    // Hook methods not provided by system services
    private void hookNonSystemServices(){

    }
    // Call when package loaded
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable{
        if(lpparam.appInfo == null ||
                (lpparam.appInfo.flags & (ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) !=0){

            return;
        }else if(lpparam.isFirstApplication && !OS_APPS.contains(lpparam.packageName))
        {
            Logger.PACKAGENAME = lpparam.packageName;
           // nativeHook(lpparam);

            try {
                instrumentApp();
                //hookextra(new HKtoStringex("java.lang.StringBuilder","toString",true,"toString"));
                hookextra(new HkMessageItem("Android.os.Handler","dispatchMessage",true," "));
                hookextra(new HkBroadcastItem("Android.app.LoadedApk$ReceiverDispatcher$Args","run",true," "));
            } catch (FileNotFoundException e) {
                Logger.logError(e.getMessage());
            } catch (IOException e) {
                Logger.logError(e.getMessage());
            }
        }else if(lpparam.packageName.equals("com.qiye.txz.heartbeatdetect")){
            showmethod(lpparam);
        }
    }
    public void nativeHook(XC_LoadPackage.LoadPackageParam loadPackageParam){
        try{
            Class clazz = XposedHelpers.findClass(GlobalConfig.Inject_ClassName, loadPackageParam.classLoader);
            Method method = XposedHelpers.findMethodExact(clazz, GlobalConfig.Inject_Invoke_FunName, String.class);//查找system.load函数
            method.invoke(null, INJECTSO);//加载待注入库
        }catch (Exception e){
            XposedBridge.log("can not load "+INJECTSO);
            XposedBridge.log(e);
        }
    }

    public class HooknativeConfig {
        private String so_name;
        private String method;
        private Boolean thisObject;
        private String Function;
    }

    public void instrumentApp() throws FileNotFoundException, IOException
    {
        Gson gson = new Gson();
        String json = Files.readFile(CONFIG_FILE);
        InstrumentationConfiguration instrumentationConfiguration = gson.fromJson(json, InstrumentationConfiguration.class);
        TRACE=instrumentationConfiguration.trace;
        for (HookConfig hookConfig : instrumentationConfiguration.hookConfigs) {

            hook(new MethodHookImpl(hookConfig.class_name,hookConfig.method,hookConfig.thisObject,hookConfig.type));
        }
        //hookextra(new HKequals("java.lang.String","equals",true,"equals"));
        //hookextra(new HKtoString("java.lang.String","toString",true,"toString"));
        //hookextra(new HKtoString("java.lang.String","startsWith",true,"toString"));
        //hookextra(new HKtoString("java.lang.String","indexOf",true,"toString"));
        //hookextra(new HKtoString("java.lang.String","contains",true,"toString"));
    }

    public class InstrumentationConfiguration {
        public List<HookConfig> hookConfigs;
        public List<HooknativeConfig> hooknativeConfigs;
        public Boolean trace;
    }

    public class HookConfig {
        private String class_name;
        private String method;
        private Boolean thisObject;
        //private MethodApiType type;
        private String type;
    }

    private static void hook(MethodHookImpl methodHook) {
        hook(methodHook, null);

    }
    private static void hookextra(HKmethod methodHook) {
        hookextra(methodHook, null);
    }

    private static void hook(final MethodHookImpl methodHook, ClassLoader classLoader) {
        try {

            // Create hook method
            XC_MethodHook xcMethodHook = methodHook;

            // Find hook class
            Class<?> hookClass = findClass(methodHook.getClassName(), classLoader);
            if (hookClass == null) {
                String message = String.format("Hook-Class not found: %s", methodHook.getClassName());
                Logger.logError(message);
                return;
            }

            // Add hook
            if (methodHook.getMethodName() == null) {
                for (Constructor<?> constructor : hookClass.getDeclaredConstructors()){
                    XposedBridge.hookMethod(constructor, xcMethodHook);
                }
            } else{
                for (Method method : hookClass.getDeclaredMethods())
                    if (method.getName().equals(methodHook.getMethodName()))
                        XposedBridge.hookMethod(method, xcMethodHook);
            }

        } catch (Throwable ex) {

        }
    }

    private static void hookextra(final HKmethod methodHook, ClassLoader classLoader) {
        try {

            XC_MethodHook xcMethodHook = (XC_MethodHook)methodHook;
            // Find hook class
            Class<?> hookClass = findClass(methodHook.getClassName(), classLoader);

            if (hookClass == null) {
                String message = String.format("Hook-Class not found: %s", methodHook.getClassName());
                XposedBridge.log(message);
                Logger.logError(message);
                return;
            }

            // Add hook
            if (methodHook.getMethodName() == null) {
                for (Constructor<?> constructor : hookClass.getDeclaredConstructors()){
                    XposedBridge.hookMethod(constructor, xcMethodHook);
                }
            } else{
                for (Method method : hookClass.getDeclaredMethods())
                    if (method.getName().equals(methodHook.getMethodName()))
                        XposedBridge.hookMethod(method, xcMethodHook);
            }

        } catch (Throwable ex) {
            Logger.logError(ex.getMessage());
        }
    }
    private static void showmethod(LoadPackageParam lpparam){
        try {
            // Find hook class
            Class<?> hookClass = findClass("com.qiye.txz.heartbeatdetect.HorizonService", lpparam.classLoader);
            if (hookClass == null) {
                String message = String.format("Hook-Class not found: %s", "");
                XposedBridge.log(message);
                Logger.logError(message);
                return;
            }
            for (Method method : hookClass.getDeclaredMethods()){
                 XposedBridge.log(method.getName());
                 if(method.getName().equals("checkheart")){
                     XposedBridge.hookMethod(method, new HKheartbeat());
                    }
                }
        } catch (Throwable ex) {
            Logger.logError(ex.getMessage());
        }
    }
}
