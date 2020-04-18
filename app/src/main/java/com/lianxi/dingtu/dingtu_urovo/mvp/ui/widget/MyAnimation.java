package com.lianxi.dingtu.dingtu_urovo.mvp.ui.widget;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class MyAnimation extends Animation {
    // 先定义成员变量 //X轴的中心坐标
    int center_X;

    //Y轴的中心坐标
    int center_Y;

    // 初始化Camera
    Camera camera = new Camera();

    /**
     * 在initialize对变量进行初始化
     *
     * @param width
     * @param height
     * @param parentWidth
     * @param parentHeight
     */

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {

        super.initialize(width, height, parentWidth, parentHeight);

        //获取X Y 中心点坐标
        center_X = width / 2;
        center_Y = height / 2;

        //动画的执行时间，3000毫秒

        setDuration(3000L);

        setInterpolator(new AccelerateInterpolator());

    }

    /**
     * 在applyTransformation通过矩阵修改动画
     * <p>
     * 这里是自定义动画的核心，动画执行的过程中一直在回调这个方法
     * <p>
     * 每次回调这个方法interpolatedTime都会改变
     *
     * @param interpolatedTime
     * @param t
     */

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

        final Matrix matrix = t.getMatrix();

        //储蓄
        camera.save();

        //中心是绕Y轴旋转，这里可以自行设置其他轴

        camera.rotateY(1080 * interpolatedTime);

        //加在变换矩阵上
        camera.getMatrix(matrix);

        //设置翻转中心点
        matrix.preTranslate(-center_X, -center_Y);

        matrix.postTranslate(center_X, center_Y);
        //恢复
       camera.restore();

    }
}
