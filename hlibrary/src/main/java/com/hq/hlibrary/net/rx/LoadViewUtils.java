package com.hq.hlibrary.net.rx;

import android.content.Context;
import android.content.DialogInterface;

import com.hq.hlibrary.R;

import io.reactivex.disposables.Disposable;

/**
 * @author ruowuming
 * @version 1.0
 * @Project feinuoke_android
 * @date 2019/6/6  17:28
 */
public class LoadViewUtils {
    //  加载进度的dialog
    private CustomLoadDialog mProgressDialog;

    /**
     * 显示ProgressDialog
     */
    public void showProgress(Context context, String msg, boolean canCancel) {
       /* if (context == null || context.isFinishing()) {
            return;
        }*/
        if(mProgressDialog==null){
            mProgressDialog= new CustomLoadDialog.Builder(context)
                    .setTheme(R.style.dialog_style)
                    .setMessage(msg)
                    .build();
            mProgressDialog.setCancelable(canCancel);
        }
        if(mProgressDialog!=null&&!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 显示ProgressDialog
     */
    public void showProgress(Context context, boolean canCancel) {
        /*if (activity == null || activity.isFinishing()) {
            return;
        }*/
        if(mProgressDialog==null){
            mProgressDialog= new CustomLoadDialog.Builder(context)
                    .setTheme(R.style.dialog_style)
                    .build();
            mProgressDialog.setCancelable(canCancel);
        }
        if(mProgressDialog!=null&&!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }

    }

    /**
     * 取消ProgressDialog
     */
    public void dismissProgress() {
        if (mProgressDialog != null&&mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * ProgressDialog是否在展示
     */
    public boolean isShow(){
        if (mProgressDialog!=null){
            return mProgressDialog.isShowing();
        }
        return false;
    }

    public void setCancelListener(final HttpNextListener listener, final Disposable d, final int what){
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (listener!=null){
                    listener.onCancel(what);
                }
                if (d==null)
                    return;
                if (d.isDisposed()) {
                    d.dispose();
                }
            }
        });
    }




}
