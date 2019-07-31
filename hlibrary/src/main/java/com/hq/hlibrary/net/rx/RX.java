package com.hq.hlibrary.net.rx;

import android.app.Application;

/**
 * @author ruowuming
 * @version 1.0
 * @date 2018/3/30  9:20
 */

public class RX {

    private static Application applications;
    private static boolean isDebug=false;

    public static void init(Application application){
        applications=application;
    }

    public static void init(Application application, boolean debug){
        applications=application;
        isDebug=debug;
    }

    public static void setDebugModel(boolean b){
        isDebug=b;
    }

    public static boolean getDebugModel() {
        return isDebug;
    }

    public static Application getApplications() {
        return applications;
    }

    public static void setApplications(Application applications) {
        RX.applications = applications;
    }



}
