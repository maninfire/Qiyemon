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
            ,"com.kingroot.kinguser")) ;

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
    public void nativeloadhook(XC_LoadPackage.LoadPackageParam loadPackageParam)throws Throwable{
        if (off == false)
        {
            flushConfig();
        }
        if (!loadPackageParam.packageName.equals(GlobalConfig.Inject_PackageName))
            return;
        Class findloadclazz = XposedHelpers.findClass(GlobalConfig.Inject_ClassName, loadPackageParam.classLoader); //使用注入包的类加载器来反射查找System.load函数,从而调用加载自己的框架so
        if(findloadclazz == null)
        {
            XposedBridge.log("[-] not found class: " + GlobalConfig.Inject_ClassName);
            Log.e(GlobalConfig.Log_TAG, "[-] can't found class: " + GlobalConfig.Inject_ClassName);
            return;
        }
        Log.e(GlobalConfig.Log_TAG, "[+] found class: " + GlobalConfig.Inject_ClassName);
        Method methodload = XposedHelpers.findMethodExact(findloadclazz, GlobalConfig.Inject_Invoke_FunName, String.class);//查找system.load函数
        if (methodload == null)
        {
            Log.e(GlobalConfig.Log_TAG, "[-] can't found function: " + methodload.getName());
        }
        Log.e(GlobalConfig.Log_TAG, "[+] found function: " + methodload.getName());
        methodload.invoke(null, GlobalConfig.Inject_SoPath);//加载待注入库

    }

    public boolean flushConfig()
    {
        XSharedPreferences ms = new XSharedPreferences(GlobalConfig.Current_PackageName, GlobalConfig.Setting_FileName);

        GlobalConfig.Inject_PackageName = ms.getString(GlobalConfig.Setting_Key_inject_pckname, "");
        GlobalConfig.Inject_SoPath = ms.getString(GlobalConfig.Setting_Key_inject_soname, "");
        if(GlobalConfig.Inject_PackageName.equals("") || GlobalConfig.Inject_SoPath.equals("") )
        {
            XposedBridge.log("[-] not  package or so name is set");
            Log.e(GlobalConfig.Log_TAG,"[-] not set package:" + GlobalConfig.Inject_PackageName + " or so: " + GlobalConfig.Inject_SoPath );
            off = false;
            return false;
        }
        Log.e(GlobalConfig.Log_TAG,"[+] set package:" + GlobalConfig.Inject_PackageName + " and so: " + GlobalConfig.Inject_SoPath );
        off = true;
        return true;

    }
    public void instrumentAppnative() throws FileNotFoundException, IOException
    {
        Gson gson = new Gson();
        String json = Files.readFile(CONFIG_NATIVE_FILE);
        InstrumentationConfiguration instrumentationConfiguration = gson.fromJson(json, InstrumentationConfiguration.class);
        TRACE=instrumentationConfiguration.trace;
        for (HooknativeConfig hooknativeConfig : instrumentationConfiguration.hooknativeConfigs) {
            //native层的hook不仅仅是传递个so名称和方法名称，还要有适配的参数，这里不是那么容易添加统一的入口。
            //现有的框架是在interface实现hook,没法传递参数，比较头疼。集团那个沙箱native居然也是用的配置文件，不知道是如何
            //解决适配问题的。
            //一般来说只要在每个包里加载一下我们的frame（也就是调用我们的nativeloadhook函数）
            // ,那么我们的沙箱就可以实现native层所有的函数挂钩
            //但统一的配置文件挂钩函数有一点小问题的。
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
