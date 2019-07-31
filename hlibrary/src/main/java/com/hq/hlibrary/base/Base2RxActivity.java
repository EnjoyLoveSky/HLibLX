package com.hq.hlibrary.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.blankj.utilcode.util.ActivityUtils;
import com.hq.hlibrary.R;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 基类Activity
 *
 * @author ruowuming
 * @version 1.0
 * @date 2018/3/5  15:35
 */
public abstract class Base2RxActivity extends RxAppCompatActivity {
    private FrameLayout mContentLayout;
    private TextView mTvTitle;
    private TextView mTvRight;
    private Unbinder unbinder = null;
    private ImageView mImgvBack;
    private ImageView mImgRight;
    private RelativeLayout mLayoutTitle;

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
                ActivityUtils.finishActivity(Base2RxActivity.this);
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
//        try {
//            BarUtils.setStatusBarColor(this, Color.TRANSPARENT).setBackgroundResource(R.drawable.color_theme);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        onActivityCreate(savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected void onTvRight(View v) {

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

    protected abstract void onActivityCreate(Bundle savedInstanceState);

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
        ActivityUtils.finishActivity(this);
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
