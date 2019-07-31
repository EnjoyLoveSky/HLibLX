package com.hq.hlibrary;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import com.blankj.utilcode.util.Utils;
import com.hq.hlibrary.net.BaseNetProvider;
import com.hq.hlibrary.net.NetManage;

/**
 * 全局Application
 * @author ruowuming
 * @version 1.0
 * @Project lixing_android
 * @date 2019/6/
 */
public class GlobalApplication extends Application {
        protected static Context context;
        protected static Handler handler;
        protected static int mainThreadId;


//        ILoginView iLoginView = new ILoginView() {
//            @Override
//            public boolean isLogin() {
//                return false;
//            }
//
//            @Override
//            public void login(int userDefine) {
//                Toast.makeText(GlobalApplication.this, "需要在GlobalApplication设置逻辑", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void exitLogin() {
//                Toast.makeText(GlobalApplication.this, "需要在GlobalApplication设置逻辑", Toast.LENGTH_SHORT).show();
//            }
//        };

        @Override
        public void onCreate() {
            super.onCreate();
            Utils.init(this);
            NetManage.INSTANCE.registerProvider(new BaseNetProvider(this));
            context = getApplicationContext();
            handler = new Handler();
            mainThreadId = android.os.Process.myTid();
//            LoginAssistant.Companion.getInstance().setView(iLoginView);
//            PreferencesUtil.Companion.get(this);

        }

        /**
         * 获取上下文对象
         *
         * @return context
         */
        public static Context getContext() {
            return context;
        }

        /**
         * 获取全局handler
         *
         * @return 全局handler
         */
        public static Handler getHandler() {
            return handler;
        }

        /**
         * 获取主线程id
         *
         * @return 主线程id
         */
        public static int getMainThreadId() {
            return mainThreadId;
        }
    }

