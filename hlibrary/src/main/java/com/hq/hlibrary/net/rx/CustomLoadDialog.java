package com.hq.hlibrary.net.rx;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * @author ruowuming
 * @version 1.0
 * @Project feinuoke_android
 * @date 2019/6/11  16:26
 */
public class CustomLoadDialog extends Dialog {
    private View mDialogView;
//    private AVLoadingIndicatorView loadView=null;
    private boolean cancelTouchOutside;

    public CustomLoadDialog(CustomLoadDialog.Builder builder) {
        super(builder.context);
        mDialogView = builder.mDialogView;
        cancelTouchOutside = builder.cancelTouchOutside;
//        loadView=builder.loadV;
    }

    private CustomLoadDialog(CustomLoadDialog.Builder builder, int themeResId) {
        super(builder.context, themeResId);
        mDialogView = builder.mDialogView;
        cancelTouchOutside = builder.cancelTouchOutside;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mDialogView);
        setCanceledOnTouchOutside(cancelTouchOutside);
        getWindow().setDimAmount(0f);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (mDialogView == null) {
            return;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        animationDrawable.stop();
    }

    @Override
    public void show() {
//        if (loadView!=null){
//            loadView.show();
//        }
        super.show();
    }

    @Override
    public void dismiss() {
//        if (loadView!=null){
//            loadView.hide();
//        }
        super.dismiss();
    }

    public static final class Builder {
        Context context;
        private int resStyle = -1;
        private View mDialogView;
        private boolean cancelTouchOutside;

        public Builder(Context context) {
            this.context = context;
//            mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_load, null);
        }

        /**
         * 设置主题
         * @param resStyle style id
         * @return CustomProgressDialog.Builder
         */
        public CustomLoadDialog.Builder setTheme(int resStyle) {
            this.resStyle = resStyle;
            return this;
        }

        public CustomLoadDialog.Builder setMessage(String message) {
//            TextView tvMessage = (TextView) mDialogView.findViewById(R.id.tv_loadingmsg);
//            if (tvMessage != null) {
//                tvMessage.setText(message);
//            }
            return this;
        }

        /**
         * 设置点击dialog外部是否取消dialog
         *
         * @param val 点击外部是否取消dialog
         * @return
         */
        public CustomLoadDialog.Builder cancelTouchOutside(boolean val) {
            cancelTouchOutside = val;
            return this;
        }

        public CustomLoadDialog build() {
            if (resStyle != -1) {
                return new CustomLoadDialog(this, resStyle);
            } else {
                return new CustomLoadDialog(this);
            }
        }
    }
}
