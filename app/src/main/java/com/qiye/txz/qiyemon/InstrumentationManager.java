package com.qiye.txz.qiyemon;

import android.content.pm.ApplicationInfo;
import android.util.Log;

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
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedHelpers.findClass;

public class InstrumentationManager implements IXposedHookLoadPackage, IXposedHookZygoteInit {
    public boolean off = false;

    public static String CONFIG_FILE = "/data/local/tmp/hooks.json";
    public static String CONFIG_NATIVE_FILE = "/data/local/tmp/hooknatives.json";
    public static Boolean TRACE = false;

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
            ,"com.kingsoft")) ;

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
            try {
                instrumentApp();
                //XHookFramework extrahook=new  XHookFramework();
                //extrahook.setandhookgLoadPackParam(lpparam);
            } catch (FileNotFoundException e) {
                Logger.logError(e.getMessage());
            } catch (IOException e) {
                Logger.logError(e.getMessage());
            }
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
}
