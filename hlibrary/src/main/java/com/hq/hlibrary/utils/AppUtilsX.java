package com.hq.hlibrary.utils;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import androidx.core.app.ActivityCompat;
import com.hq.hlibrary.GlobalApplication;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * @author ruowuming
 * @version 1.0
 * @Project lixing_android
 * @date 2019/6/14  14:54
 */
public final class AppUtilsX {
    private static volatile AppUtilsX INSTANCE;


    public static AppUtilsX getInstance() {
        if (INSTANCE == null) {
            synchronized (AppUtilsX.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppUtilsX();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 获取上下文对象
     *
     * @return 上下文对象
     */
    public final Context getContext() {
        return GlobalApplication.getContext();
    }

    /**
     * 获取全局handler
     *
     * @return 全局handler
     */
    private final Handler getHandler() {
        return GlobalApplication.getHandler();
    }

    /**
     * 获取主线程id
     *
     * @return 主线程id
     */
    private final int getMainThreadId() {
        return GlobalApplication.getMainThreadId();
    }

    /**
     * 获取SD卡路径
     *
     * @return 如果sd卡不存在则返回null
     */
    private final File getSdPath() {
        File sdDir = (File)null;
        boolean sdCardExist = Intrinsics.areEqual(Environment.getExternalStorageState(), "mounted");
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        return sdDir;
    }

    /**
     * 判断是否运行在主线程
     *
     * @return true：当前线程运行在主线程
     * fasle：当前线程没有运行在主线程
     */
    private final boolean isRunOnUIThread() {
        int myTid = Process.myTid();
        return myTid == this.getMainThreadId();
    }

    /**
     * 获取版本名称
     */
    private final String getAppVersionName(Context context) {
        String versionName = (String)null;

        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception var5) {
            Log.e("VersionInfo", "Exception", (Throwable)var5);
        }

        return versionName;
    }

    /**
     * 获取版本号
     */
    private final int getAppVersionCode(Context context) {
        int versioncode = -1;

        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versioncode = pi.versionCode;
        } catch (Exception var5) {
            Log.e("VersionInfo", "Exception", (Throwable)var5);
        }

        return versioncode;
    }

    private final String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
         if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
             return  tm.getDeviceId();
        } else {
             return null;
         }

    }

    /**
     * 显示软键盘
     */
    private final void openSoftInput(EditText et) {
        InputMethodManager inputMethodManager = (InputMethodManager) et.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(et, InputMethodManager.HIDE_NOT_ALWAYS);

    }

    /**
     * 隐藏软键盘
     */
    private final void hideSoftInput(EditText et) {
        InputMethodManager inputMethodManager = (InputMethodManager) et.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(et.getWindowToken(), InputMethodManager
                    .HIDE_NOT_ALWAYS);
    }

    /**
     * 安装文件
     *
     * @param data
     */
    private final void promptInstall(Context context, Uri data) {
        Intent promptInstall = new Intent(Intent.ACTION_VIEW)
                .setDataAndType(data, "application/vnd.android.package-archive");
        // FLAG_ACTIVITY_NEW_TASK 可以保证安装成功时可以正常打开 app
        promptInstall.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(promptInstall);
    }

    public final void copy2clipboard(@NotNull Context context, @NotNull String text) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText((CharSequence)"clip", (CharSequence)text);
        cm.setPrimaryClip(clip);
    }

    /**
     * 运行在主线程
     *
     * @param r 运行的Runnable对象
     */
    private final void runOnUIThread(Runnable r) {
        if (this.isRunOnUIThread()) {
            // 已经是主线程, 直接运行
            r.run();
        } else {
            // 如果是子线程, 借助handler让其运行在主线程
            this.getHandler().post(r);
        }

    }


}

