package com.bawei.day03_ZiDingYiView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 房国伟 on 2018/8/2.
 */

public class CircleView extends View {

    //設置画笔变量
    private Paint mPaint1;

    // 自定义View有四个构造函数
    // 如果View是在Java代码里面new的，则调用第一个构造函数
    public CircleView(Context context) {
        super(context);
        //初始化画笔的操作
        init();
    }

    // 如果View是在.xml里声明的，则调用第二个构造函数
    // 自定义属性是从AttributeSet参数传进来的
    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //这个在布局文件中声明的view，创建时系统自动调用。
        init();
    }

    // 不会自动调用
    // 一般是在第二个构造函数里主动调用
    // 如View有style属性时
    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //API21之后才使用
    // 不会自动调用
    // 一般是在第二个构造函数里主动调用
    // 如View有style属性时
    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        //创建画笔
        mPaint1 = new Paint();
        //设置画笔颜色为蓝色
        mPaint1.setColor(Color.BLUE);
        //设置画笔宽度为10px
        mPaint1.setStrokeMiter(5f);
        /**
         * 线
         * Paint.Style.STROKE
         */
        //设置画笔模式为填充
        mPaint1.setStyle(Paint.Style.FILL);
    }

    //复写onDraw()进行绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //获取控件的高度
        int width = getWidth();
        int height = getHeight();

        //设置元的半径 = 宽，高最小值的2分之1
        int r = Math.min(width,height)/2;
        //画圆
        canvas.drawCircle(width/2,height/2,r,mPaint1);
    }
}
