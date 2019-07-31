package com.hq.hlibrary.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.hq.hlibrary.R;
import com.trello.rxlifecycle3.components.RxFragment;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;


/**
 * Fragment的基类
 * @author ruowuming
 * @version 1.0
 * @date 2018/3/5  16:35
 */
public abstract class BaseRxFragmentX extends RxFragment {
    protected InputMethodManager inputMethodManager;
    //     根部view
    protected View mRootView = null;
    //    当前的对象
    protected RxAppCompatActivity mContext = null;

    //    是否是第一次加载数据
    private boolean isFirstLoad = true;

    public boolean isFirstLoad() {
        return isFirstLoad;
    }

    public void setFirstLoad(boolean isFirstLoad) {
        this.isFirstLoad = isFirstLoad;
    }

    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = (RxAppCompatActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getContextViewLayout(), container, false);
        }
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    protected abstract int getContextViewLayout();


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView mTitleView = (TextView) mRootView.findViewById(R.id.title);
        if (mTitleView != null) {
            if (getText() != null)
                mTitleView.setText(getText());
        }
        if (getUserVisibleHint()) {
            initData();
        }
    }

    protected abstract String getText();


    //显示Fragment时执行的方法
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint() && mContext != null && mRootView != null && isFirstLoad()) {
            initData();
        }
    }

    //销毁试图时移除View
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRootView != null &&mRootView.getParent()!=null) {
            ((ViewGroup) mRootView.getParent()).removeView(mRootView);
        }
        if (unbinder != null)
            unbinder.unbind();
    }

    // 子类实现初始化数据操作(子类自己调用)
    public abstract void initData();

    /**
     * 传递数据并跳转到下一Activity
     *
     * @param bundle
     * @param cls
     */
    protected void gotoAct(Bundle bundle, Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 跳转到下一Activity
     *
     * @param cls
     */
    protected void gotoAct(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

    /**
     * 传递数据并跳转到下一Activity,并且有返回数据
     *
     * @param bundle
     * @param cls
     * @param requestCode
     */
    protected void gotoActForResult(Bundle bundle, Class<?> cls, int requestCode) {
        Intent intent = new Intent(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 隐藏键盘
     *
     * @param context
     */
    public static void hiddenKeyboard(Activity context) {
        if (context.getCurrentFocus() != null) {
            // 焦点不为空
            InputMethodManager inputMethodManager = ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE));
            if (inputMethodManager.isActive(context.getCurrentFocus())) {
                // 隐藏键盘
                context.getCurrentFocus().clearFocus();
                inputMethodManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 显示软键盘
     *
     * @param context
     */
    public static void showKeyboard(Activity context) {
        if (context.getCurrentFocus() != null) {
            // 焦点不为空
            InputMethodManager inputMethodManager = ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE));
            if (inputMethodManager.isActive(context.getCurrentFocus())) {
                // 键盘未显示
                inputMethodManager.showSoftInput(context.getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
            }
        }
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
