package com.hq.hlibrary.base;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.library.utils.NetworkConnectionUtils;
import com.hq.hlibrary.R;
import com.hq.hlibrary.utils.AppUtilsX;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author Alex
 */
public abstract class BaseCompatActivity extends RxAppCompatActivity {
    private FrameLayout mContentLayout;
    private TextView mTvTitle;
    private TextView mTvRight;
    private Unbinder unbinder = null;
    private ImageView mImgvBack;
    private ImageView mImgRight;
    private RelativeLayout mLayoutTitle;

    protected Application mApplication;
    protected Context mContext;
    protected boolean isTransAnim;
    public boolean mCheckNetWork = true;

    /**
     * 网络异常View
     */
    protected View errorView;
    /**
     * loadingView
     */
    protected View loadingView;
    /**
     * 没有内容view
     */
    protected View emptyView;

    private View mTipView;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private NetWorkBroadcastReceiver mNetWorkBroadcastReceiver;

    static {
        //5.0以下兼容vector
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDelegate().setContentView(R.layout.activity_base_x);
        mLayoutTitle = findViewById(R.id.layout_title);
        mTvTitle = findViewById(R.id.title_text);
        mTvRight = findViewById(R.id.tv_right);
        mImgvBack = findViewById(R.id.imgv_back);
        mImgRight = findViewById(R.id.iv_right);
        mContentLayout = findViewById(R.id.content);
        mImgvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onIvLeft(v);
            }
        });
        mTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTvRight(view);
            }
        });
        mImgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onImgRight(view);
            }
        });
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        MIUISetStatusBarLightMode(this.getWindow(), true);
        FlymeSetStatusBarLightMode(this.getWindow(), true);

        init(savedInstanceState);
        showLoading();
    }

//    @Override
//    protected final void onActivityCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
//        init(savedInstanceState);
//        showLoading();
//    }

    protected abstract int getLayoutId();

    protected void onTvRight(View v) {

    }

    protected void onIvLeft(View v) {
        ActivityUtils.finishActivity(BaseCompatActivity.this);
    }


    protected void onImgRight(View v) {

    }


    /**
     * 设置是否显示返回键
     *
     * @param visible
     */
    protected void setBackVisible(boolean visible) {
        if (mImgvBack == null)
            return;
        if (visible)
            mImgvBack.setVisibility(View.VISIBLE);
        else {
            mImgvBack.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 是否显示标题栏
     *
     * @param visible
     */
    protected void setTitleVisible(boolean visible) {
        if (mLayoutTitle == null)
            return;
        if (visible)
            mLayoutTitle.setVisibility(View.VISIBLE);
        else {
            mLayoutTitle.setVisibility(View.GONE);
        }
    }

    /**
     * 是否显示右边文字
     *
     * @param visible
     */
    protected void setTvRightVisible(boolean visible) {
        if (mTvRight == null)
            return;
        if (visible)
            mTvRight.setVisibility(View.VISIBLE);
        else {
            mTvRight.setVisibility(View.GONE);
        }
    }

    /**
     * 是否显示右边文字
     *
     * @param visible
     */
    protected void setImgRightVisible(boolean visible) {
        if (mImgRight == null)
            return;
        if (visible)
            mImgRight.setVisibility(View.VISIBLE);
        else {
            mImgRight.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题栏的背景颜色
     *
     * @param color
     */
    protected void setTitleBackgroundColor(int color) {
        if (mLayoutTitle != null)
            mLayoutTitle.setBackgroundColor(color);
    }

    /**
     * 设置标题栏的背景图片
     *
     * @param resid
     */
    protected void setTitleBackgroundResource(int resid) {
        mLayoutTitle.setBackgroundResource(resid);
    }

//    protected abstract void onActivityCreate(Bundle savedInstanceState);

    public FrameLayout getContentLayout() {
        return mContentLayout;
    }

    public void setTitle(CharSequence title) {
        if (mTvTitle != null)
            mTvTitle.setText(title);
    }

    public void setTitle(int titleId) {
        if (mTvTitle != null)
            mTvTitle.setText(getString(titleId));
    }

    public void setTvRight(CharSequence title) {
        if (mTvRight != null)
            mTvRight.setText(title);
    }

    public void setTvRight(int titleId) {
        if (mTvRight != null)
            mTvRight.setText(getString(titleId));
    }

    protected void setContentBackground(int color) {
        mContentLayout.setBackgroundResource(color);
    }

    @Override
    public void setContentView(int layoutResID) {
        mContentLayout.removeAllViews();
        getLayoutInflater().inflate(layoutResID, mContentLayout, true);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mContentLayout.addView(view, params);
        unbinder = ButterKnife.bind(this);
    }


    public ViewGroup getContentRoot() {
        return mContentLayout;
    }



    /**
     * 显示加载进度
     */
    protected void showLoading() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mNetWorkBroadcastReceiver != null) {
            unregisterReceiver(mNetWorkBroadcastReceiver);
        }
        if (mTipView != null && mTipView.getParent() != null) {
            mWindowManager.removeView(mTipView);
        }
        if (unbinder != null)
            unbinder.unbind();
        ActivityUtils.finishActivity(this);
    }


    private void init(Bundle savedInstanceState) {
//        setContentView(getLayoutId());
//        ButterKnife.bind(this);
//        StatusBarUtils.INSTANCE.setTransparent(this);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initData();
        initTipView();
        initView(savedInstanceState);
        initBroadcast();
        LayoutInflater inflater = LayoutInflater.from(this);
        errorView = inflater.inflate(R.layout.view_network_error, null);
        loadingView = inflater.inflate(R.layout.view_loading, null);
        emptyView = inflater.inflate(R.layout.view_empty, null);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onErrorViewClick(v);
            }
        });
        ActivityUtils.getActivityList().add(this);
    }

    private void initBroadcast() {
        mNetWorkBroadcastReceiver = new NetWorkBroadcastReceiver(this);
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetWorkBroadcastReceiver, intentFilter);
    }

    private void initTipView() {
        LayoutInflater inflater = getLayoutInflater();
        mTipView = inflater.inflate(R.layout.network_notice_layout, null);
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        mLayoutParams.gravity = Gravity.TOP;
        mLayoutParams.x = 0;
        mLayoutParams.y = 0;
    }

    private void hasNetWork(boolean has) {
        if (isCheckNetWork()) {
            if (has) {
                if (mTipView != null && mTipView.getParent() != null) {
                    mWindowManager.removeView(mTipView);
                }
            } else {
                if (mTipView.getParent() == null) {
                    mWindowManager.addView(mTipView, mLayoutParams);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hasNetWork(NetworkConnectionUtils.INSTANCE.isConnected(this));
    }

    /**
     * 设置是否检测网络
     *
     * @param checkNetWork
     */
    public void setCheckNetWork(boolean checkNetWork) {
        mCheckNetWork = checkNetWork;
    }

    /**
     * 返回是否检测网络标志
     *
     * @return
     */
    public boolean isCheckNetWork() {
        return mCheckNetWork;
    }


    /**
     * @param v
     */
    protected abstract void onErrorViewClick(View v);

    /**
     * 重新加载
     */
    public void reLoad() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }


    /**
     * 初始化数据
     * <p>
     * 子类可以复写此方法初始化子类数据
     */
    protected void initData() {
        mContext = AppUtilsX.getInstance().getContext();
        mApplication = getApplication();
        isTransAnim = true;
    }

    /**
     * 初始化view
     * <p>
     * 子类实现 控件绑定、视图初始化等内容
     *
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 获取当前layouty的布局ID,用于设置当前布局
     * <p>
     * 交由子类实现
     *
     * @return layout Id
     */
//    protected abstract int getLayoutId();


    /**
     * [页面跳转]
     *
     * @param clz 要跳转的Activity
     */
    public void gotoAct(Class<?> clz) {
        startActivity(new Intent(this, clz));
        if (isTransAnim) {
            overridePendingTransition(R.anim.activity_start_zoom_in, R.anim
                    .activity_start_zoom_out);
        }
    }

    /**
     * [页面跳转]
     *
     * @param clz    要跳转的Activity
     * @param intent intent
     */
    public void gotoAct(Class<?> clz, Intent intent) {
        intent.setClass(this, clz);
        startActivity(intent);
        if (isTransAnim) {
            overridePendingTransition(R.anim.activity_start_zoom_in, R.anim
                    .activity_start_zoom_out);
        }
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz    要跳转的Activity
     * @param bundle bundel数据
     */
    public void gotoAct(Bundle bundle, Class<?> clz) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if (isTransAnim) {
            overridePendingTransition(R.anim.activity_start_zoom_in, R.anim
                    .activity_start_zoom_out);
        }
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param clz         要跳转的Activity
     * @param bundle      bundel数据
     * @param requestCode requestCode
     */
    public void startActivityForResult(Class<?> clz, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        if (isTransAnim) {
            overridePendingTransition(R.anim.activity_start_zoom_in, R.anim
                    .activity_start_zoom_out);
        }
    }

    public void startActivityBeforeLOLLIPOP(Activity activity, Class<?> c) {
        startActivity(new Intent(activity, c));
    }


    public void startActivityUseLOLLIPOP(Activity activity, Class<?> c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity);
            startActivity(new Intent(activity, c), options.toBundle());
        } else {
            startActivityBeforeLOLLIPOP(activity, c);
        }
    }

    public void startActivityUseShare(Activity activity, Class<?> c, View view, String transName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, view, transName);
            startActivity(new Intent(activity, c), options.toBundle());
        } else {
            startActivityBeforeLOLLIPOP(activity, c);
        }
    }


    @Override
    public void finish() {
        super.finish();
        if (isTransAnim) {
            overridePendingTransition(R.anim.activity_finish_trans_in, R.anim
                    .activity_finish_trans_out);
        }
    }

    /**
     * 隐藏键盘
     *
     * @return 隐藏键盘结果
     * <p>
     * true:隐藏成功
     * <p>
     * false:隐藏失败
     */
    protected boolean hiddenKeyboard() {
        //点击空白位置 隐藏软键盘
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService
                (INPUT_METHOD_SERVICE);
        return mInputMethodManager.hideSoftInputFromWindow(this
                .getCurrentFocus().getWindowToken(), 0);
    }

    protected void initTitleBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

    }


    /**
     * 是否使用overridePendingTransition过度动画
     *
     * @return 是否使用overridePendingTransition过度动画，默认使用
     */
    protected boolean isTransAnim() {
        return isTransAnim;
    }

    /**
     * 设置是否使用overridePendingTransition过度动画
     */
    protected void setIsTransAnim(boolean b) {
        isTransAnim = b;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static class NetWorkBroadcastReceiver extends BroadcastReceiver {

        private WeakReference<BaseCompatActivity> weakReference;

        public NetWorkBroadcastReceiver(BaseCompatActivity baseCompatActivity) {
            weakReference = new WeakReference<>(baseCompatActivity);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            BaseCompatActivity baseCompatActivity = weakReference.get();
            boolean isConnected = NetworkConnectionUtils.INSTANCE.isConnected(baseCompatActivity);
            baseCompatActivity.hasNetWork(isConnected);

        }
    }

    public void initToolbar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    /**
     * 小米适配
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 魅族适配
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }


}
