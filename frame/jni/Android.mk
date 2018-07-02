LOCAL_PATH := $(call my-dir)
APP_ABI:=armeabi
NDK_TOOLCHAIN_VERSION = 4.8
APP_CPPFLAGS +=-std=c++11
include $(CLEAR_VARS)
LOCAL_MODULE:= substrate-dvm
LOCAL_SRC_FILES := libsubstrate-dvm.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE:= substrate
LOCAL_SRC_FILES := libsubstrate.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := mycydia #生成的模块名
LOCAL_SRC_FILES := mycydia.cpp #源文件名

LOCAL_LDLIBS += -llog -ldl 
LOCAL_LDLIBS += -L$(LOCAL_PATH) -lsubstrate-dvm -lsubstrate
include $(BUILD_SHARED_LIBRARY)