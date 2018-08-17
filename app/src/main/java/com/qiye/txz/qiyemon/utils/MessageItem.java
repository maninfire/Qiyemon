package com.qiye.txz.qiyemon.utils;

public class MessageItem {
    public static final int A = 123;
    public static final int B = 124;
    public static final int C = 125;
    public static final int D = 126;
    public static final int E = 127;
    public static final int F = 128;
    public static final int G = 129;
    public static final int H = 130;
    public static final int I = 131;
    public static final int J = 132;
    public static final int K = 133;
    public static final int L = 134;
    public static final int M = 135;
    public static final int N = 136;
    public static final int O = 137;
    public static final int P = 138;
    public static final int Q = 139;
    public static final int R = 140;
    public static final int S = 141;
    public static final int T = 142;
    String msgTarget;
    int msgCode;
    String codeString;
    public static final int d = 100;
    public static final int e = 101;
    public static final int f = 102;
    public static final int g = 103;
    public static final int h = 104;
    public static final int i = 105;
    public static final int j = 106;
    public static final int k = 107;
    public static final int l = 108;
    public static final int m = 109;
    public static final int n = 110;
    public static final int o = 111;
    public static final int p = 112;
    public static final int q = 113;
    public static final int r = 114;
    public static final int s = 115;
    public static final int t = 116;
    public static final int u = 117;
    public static final int v = 118;
    public static final int w = 119;
    public static final int x = 120;
    public static final int y = 121;
    public static final int z = 122;

    public MessageItem(String arg2, int arg3) {
        super();
        this.msgTarget = arg2;
        this.msgCode = arg3;
        this.codeString = this.a(arg3);
    }

    private String a(int arg2) {
        String v0;
        switch(arg2) {
            case 100: {
                v0 = "LAUNCH_ACTIVITY";
                break;
            }
            case 101: {
                v0 = "PAUSE_ACTIVITY";
                break;
            }
            case 102: {
                v0 = "PAUSE_ACTIVITY_FINISHING";
                break;
            }
            case 103: {
                v0 = "STOP_ACTIVITY_SHOW";
                break;
            }
            case 104: {
                v0 = "STOP_ACTIVITY_HIDE";
                break;
            }
            case 105: {
                v0 = "SHOW_WINDOW";
                break;
            }
            case 106: {
                v0 = "HIDE_WINDOW";
                break;
            }
            case 107: {
                v0 = "RESUME_ACTIVITY";
                break;
            }
            case 108: {
                v0 = "SEND_RESULT";
                break;
            }
            case 109: {
                v0 = "DESTROY_ACTIVITY";
                break;
            }
            case 110: {
                v0 = "BIND_APPLICATION";
                break;
            }
            case 111: {
                v0 = "EXIT_APPLICATION";
                break;
            }
            case 112: {
                v0 = "NEW_INTENT";
                break;
            }
            case 113: {
                v0 = "RECEIVER";
                break;
            }
            case 114: {
                v0 = "CREATE_SERVICE";
                break;
            }
            case 115: {
                v0 = "SERVICE_ARGS";
                break;
            }
            case 116: {
                v0 = "STOP_SERVICE";
                break;
            }
            case 117: {
                v0 = "REQUEST_THUMBNAIL";
                break;
            }
            case 118: {
                v0 = "CONFIGURATION_CHANGED";
                break;
            }
            case 119: {
                v0 = "CLEAN_UP_CONTEXT";
                break;
            }
            case 120: {
                v0 = "GC_WHEN_IDLE";
                break;
            }
            case 121: {
                v0 = "BIND_SERVICE";
                break;
            }
            case 122: {
                v0 = "UNBIND_SERVICE";
                break;
            }
            case 123: {
                v0 = "DUMP_SERVICE";
                break;
            }
            case 124: {
                v0 = "LOW_MEMORY";
                break;
            }
            case 125: {
                v0 = "ACTIVITY_CONFIGURATION_CHANGED";
                break;
            }
            case 126: {
                v0 = "RELAUNCH_ACTIVITY";
                break;
            }
            case 127: {
                v0 = "PROFILER_CONTROL";
                break;
            }
            case 128: {
                v0 = "CREATE_BACKUP_AGENT";
                break;
            }
            case 129: {
                v0 = "DESTROY_BACKUP_AGENT";
                break;
            }
            case 130: {
                v0 = "SUICIDE";
                break;
            }
            case 131: {
                v0 = "REMOVE_PROVIDER";
                break;
            }
            case 132: {
                v0 = "ENABLE_JIT";
                break;
            }
            case 133: {
                v0 = "DISPATCH_PACKAGE_BROADCAST";
                break;
            }
            case 134: {
                v0 = "SCHEDULE_CRASH";
                break;
            }
            case 135: {
                v0 = "DUMP_HEAP";
                break;
            }
            case 136: {
                v0 = "DUMP_ACTIVITY";
                break;
            }
            case 137: {
                v0 = "SLEEPING";
                break;
            }
            case 138: {
                v0 = "SET_CORE_SETTINGS";
                break;
            }
            case 139: {
                v0 = "UPDATE_PACKAGE_COMPATIBILITY_INFO";
                break;
            }
            case 140: {
                v0 = "TRIM_MEMORY";
                break;
            }
            case 141: {
                v0 = "DUMP_PROVIDER";
                break;
            }
            case 142: {
                v0 = "UNSTABLE_PROVIDER_DIED";
                break;
            }
            default: {
                v0 = Integer.toString(arg2);
                break;
            }
        }

        return v0;
    }
}
