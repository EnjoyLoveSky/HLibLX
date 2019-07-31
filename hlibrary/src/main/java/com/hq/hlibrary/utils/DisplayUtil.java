package com.hq.hlibrary.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * @description: 控件显示相关工具
 */
public class DisplayUtil {

    /**
     * px转换成dip工具
     */
    public static float px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxValue / scale + 0.5f);
    }

    /**
     * sp转换成dip
     *
     * @param context
     * @param spVal
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
            spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * dip转px工具
     *
     * @param context 上下文资源
     * @param dp      dp大小
     * @return px值
     */
    public static int dip2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp, context.getResources().getDisplayMetrics());
    }

    public static DisplayMetrics getWindowDisplayMetrics(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    /**
     * @param screenWidth 手机屏幕的宽度
     * @param picWidth    原始图片所用分辨率的宽度
     * @param retainValue 保留小数位
     * @return 手机屏幕分辨率与原始图片分辨率的宽度比
     * @author SheXiaoHeng
     */
    public static double divideWidth(int screenWidth, int picWidth, int retainValue) {
        BigDecimal screenBD = new BigDecimal(Double.toString(screenWidth));
        BigDecimal picBD = new BigDecimal(Double.toString(picWidth));
        return screenBD.divide(picBD, retainValue, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context){
        Class<?> c;
        Object obj;
        Field field;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        if (statusBarHeight <= 0) {
            statusBarHeight = dip2px(context, 25);
        }
        return statusBarHeight;
    }

    /**
     * 将图片设置为圆角
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int viewWidth, int viewHeight, float[] outerRadii) {
        int bitmapHeight = bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();
        float wScale = Float.valueOf(viewWidth) / bitmapWidth;
        float hScale = Float.valueOf(viewHeight) / bitmapHeight;
        float scale = 1;
        int x = 0;
        int y = 0;
        if (wScale > hScale) {
            scale = wScale;
            y = (int) (bitmapHeight - viewHeight / scale) / 2;
            x = 0;
        } else {
            scale = hScale;
            x = (int) (bitmapWidth - viewWidth / scale) / 2;
            y = 0;
        }
        Bitmap output = null;
        try {
            bitmap = Bitmap.createBitmap(bitmap, x, y, bitmapWidth - 2 * x, bitmapHeight - 2 * y);
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);

            // 圆角半径
            ShapeDrawable shape = new ShapeDrawable(new RoundRectShape(outerRadii, null, null));
            shape.setBounds(rect);
            canvas.drawBitmap(drawableToBitmap(shape, rect), rect, rect, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    public static Bitmap drawableToBitmap(Drawable drawable, Rect rect) {
        Bitmap bitmap = null;
        try {
            bitmap = Bitmap.createBitmap(rect.width(), rect.height(),
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            // canvas.setBitmap(bitmap);
            drawable.setBounds(0, 0, rect.width(), rect.height());
            drawable.draw(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
