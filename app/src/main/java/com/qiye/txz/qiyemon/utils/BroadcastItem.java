package com.qiye.txz.qiyemon.utils;

public class BroadcastItem {
    String intentAction;
    String receiverTo;
    String intentCommand;

    public BroadcastItem(String arg2, String arg3, String arg4) {
        super();
        this.intentAction = "";
        this.receiverTo = "";
        this.intentCommand = "";
        this.intentAction = arg2;
        this.receiverTo = arg3;
        this.intentCommand = arg4;
    }
}
