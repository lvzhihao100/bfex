package com.sskj.bfex.utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * ProjectName：（项目名称）
 * DESC: (类描述)
 * Created by 吕志豪 on 2018/04/25 10:06
 * updateName:(吕志豪)
 * updateTime:(10:06)
 * updateDesc:(修改内容)
 */

public class TintUtil {
    /**
     * 对目标Drawable 进行着色
     *
     * @param drawable 目标Drawable
     * @param color    着色的颜色值
     * @return 着色处理后的Drawable
     */
    public static Drawable tintDrawable(@NonNull Drawable drawable, int color) {
        // 获取此drawable的共享状态实例
        Drawable wrappedDrawable = getCanTintDrawable(drawable);
        // 进行着色
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }

    /**
     * 对目标Drawable 进行着色。
     * 通过ColorStateList 指定单一颜色
     *
     * @param drawable 目标Drawable
     * @param color    着色值
     * @return 着色处理后的Drawable
     */
    public static Drawable tintListDrawable(@NonNull Drawable drawable, int color) {
        return tintListDrawable(drawable, ColorStateList.valueOf(color));
    }

    /**
     * 对目标Drawable 进行着色
     *
     * @param drawable 目标Drawable
     * @param colors   着色值
     * @return 着色处理后的Drawable
     */
    public static Drawable tintListDrawable(@NonNull Drawable drawable, ColorStateList colors) {
        Drawable wrappedDrawable = getCanTintDrawable(drawable);
        // 进行着色
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    /**
     * 获取可以进行tint 的Drawable
     * <p>
     * 对原drawable进行重新实例化  newDrawable()
     * 包装  warp()
     * 可变操作 mutate()
     *
     * @param drawable 原始drawable
     * @return 可着色的drawable
     */
    @NonNull
    private static Drawable getCanTintDrawable(@NonNull Drawable drawable) {
        // 获取此drawable的共享状态实例
        Drawable.ConstantState state = drawable.getConstantState();
        // 对drawable 进行重新实例化、包装、可变操作
        return DrawableCompat.wrap(state == null ? drawable : state.newDrawable()).mutate();
    }
}
