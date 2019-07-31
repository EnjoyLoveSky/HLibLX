package com.hq.hlibrary.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.LayoutRes;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.hq.hlibrary.R;
import com.trello.rxlifecycle3.components.support.RxFragment;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author ruowuming
 * @version 1.0
 * @Project lixing_android
 * @date 2019/6/14  14:11
 */
public abstract class BaseCompatFragment extends RxFragment {
    protected String TAG;
    protected Context mContext;
    protected Activity mActivity;
    protected Application mApplication;
    private Unbinder binder;
    protected CompositeDisposable mCompositeDisposable;
    //     根部view
    protected View mRootView = null;
    /**
     * 视图是否加载完毕
     */
    private boolean isViewPrepare = false;
    /**
     * 数据是否加载过了
     */
    private boolean hasLoadData = false;

    /**
     * 无网状态—>有网状态 的自动重连操作，子类可重写该方法
     */
    public void doReConnected() {
        this.lazyLoadData();
    }

    /**
     * 获取xml文件
     */
    @LayoutRes
    public abstract int getLayoutId();

    public void onAttach(@Nullable Context context) {
        super.onAttach(context);
        if (context == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.app.Activity");
        } else {
            this.mActivity = (Activity) context;
            this.mContext = context;
        }
    }

    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(this.getLayoutId(), container, false);
        }
        binder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            this.loadDataPrepare();
        }

    }

    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView mTitleView = (TextView) getActivity().findViewById(R.id.title_text);
        if (mTitleView != null) {
            if (getText() != null)
                mTitleView.setText(getText());
        }

        RelativeLayout rlTitle = (RelativeLayout) getActivity().findViewById(R.id.layout_title);
        if (rlTitle != null) {
            if (getText() != null)
                rlTitle.setVisibility(getVisible());
        }

        this.mCompositeDisposable = new CompositeDisposable();
        this.isViewPrepare = true;
        this.getBundle(this.getArguments());
        this.prepare();
        this.initUI(view, savedInstanceState);
        this.loadDataPrepare();
    }

    protected String getText(){
        return "";
    }


    protected int getVisible(){
     return View.GONE;
    }


    private final void loadDataPrepare() {
        if (this.getUserVisibleHint() && this.isViewPrepare && !this.hasLoadData) {
            this.lazyLoadData();
            this.hasLoadData = true;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRootView != null && mRootView.getParent() != null) {
            ((ViewGroup) mRootView.getParent()).removeView(mRootView);
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (this.binder != null) {
            this.binder.unbind();
        }
        this.clearCompositeDisposable();
    }


    public final void addCompositeDisposable(@NotNull Disposable disposable) {
        Intrinsics.checkParameterIsNotNull(disposable, "disposable");
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(disposable);
    }

    private final void clearCompositeDisposable() {
        if (this.mCompositeDisposable != null) {
            this.mCompositeDisposable.clear();
        }

    }

    public final void checkNetWork() {
    }

    /**
     * 得到Activity传进来的值
     */
    private final void getBundle(Bundle bundle) {
    }

    /**
     * 初始化UI
     *
     * @param view               onCreateView()
     * @param savedInstanceState Bundle
     */
    public abstract void initUI(@NotNull View view, @Nullable Bundle savedInstanceState);


    /**
     * 在监听器之前把数据准备好
     */
    public void prepare() {
        this.mApplication = this.mActivity.getApplication();
    }

    public abstract void lazyLoadData();


    /**
     * [页面跳转]
     *
     * @param clz 要跳转的Activity
     */
    public final void gotoAct(@NotNull Class clz) {
        Intent intent = new Intent();
        intent.setClass(this.mActivity, clz);
        this.startActivity(intent);
    }

    /**
     * [页面跳转]
     *
     * @param clz    要跳转的Activity
     * @param intent intent
     */
    public final void gotoAct(@NotNull Class clz, @NotNull Intent intent) {
        intent.setClass(this.mActivity, clz);
        this.startActivity(intent);
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz    要跳转的Activity
     * @param bundle bundel数据
     */
    public final void gotoAct(@Nullable Bundle bundle, @NotNull Class clz) {
        Intent intent = new Intent();
        intent.setClass(this.mContext, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        this.startActivity(intent);
    }


}
