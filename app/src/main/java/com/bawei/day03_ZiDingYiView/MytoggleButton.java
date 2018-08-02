package com.bawei.day03_ZiDingYiView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.transition.Fade;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * view 对象显示的屏幕上，有几个重要步骤：
 * 1.构造方法创建对象
 * 2.测量view 的大小 onMeasure(int,int)
 * 3.确定view 的位置，view自身有一些建议权，决定权在父view手中 onLayout()
 * 4.绘制view 的内容 onDraw(canvas)
 * Created by 房国伟 on 2018/8/2.
 */
public class MytoggleButton extends View implements View.OnClickListener {

    private Bitmap switchBackgroud;//按钮背景图片
    private Bitmap slideButton;//按钮可滑动的图片
    private Paint paint;
    private float slideBtn_left; //滑动按钮的左边界，默认状态下是0

    //在代码里创建对象的时候，使用此方法
    public MytoggleButton(Context context) {
        super(context);
    }

    //在布局文件中声明的view，创建时由系统自动调用
    public MytoggleButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //1.构造方法创建对象
        initView();
    }

    //初始化
    private void initView() {
        //初始化图片
        switchBackgroud = BitmapFactory.decodeResource(getResources(), R.mipmap.a);
        slideButton = BitmapFactory.decodeResource(getResources(), R.mipmap.b);

        paint = new Paint();//初始化画笔
        paint.setAntiAlias(true);//打开齿轮
        //添加onclick
        setOnClickListener(this);
    }

    /**
     * 测量尺寸时的回调方法
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //2.设置当前view的大小
        setMeasuredDimension(switchBackgroud.getWidth(),switchBackgroud.getHeight());
    }

    /**
     * 3.确定位置的时候调用此方法
     *   自定义view的时候，作用不大
     */
   /* @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }*/

    private boolean currstate=false;//判断当前开关的状态 true:打开 false:关闭

    //4.绘制当前view的内容
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 绘制背景
         * left：左边界
         * top：上边界
         * paint：绘制图片的画笔
         */
        canvas.drawBitmap(switchBackgroud,0,0,paint);
        //绘制可滑动的按钮 左上角值
        canvas.drawBitmap(slideButton,slideBtn_left,0,paint);
    }

    /**
     * 判断是否发生了拖动
     * 如果拖动了，就不再做相应的onclick事件
     * @param v
     */
    private boolean isDrag=false;

    /**
     * onclick 事件在View.onToutchEvent 中被解析
     * 系统对onclick 事件解析，过于简陋，只要有down事件 和 up事件，系统即认为发生了 onclick事件
     */
    @Override
    public void onClick(View v) {
        //如果没有拖动，才执行改变状态的动作
        if (!isDrag){
            currstate = !isDrag;
            flushState();
        }
    }

    /**
     * 判断当前状态
     */
    private void flushState() {
        if (currstate){
            slideBtn_left = switchBackgroud.getWidth() - slideButton.getWidth();
        }else {
            slideBtn_left = 0;
        }
        //刷新当前view 会导致onDraw方法的執行
        invalidate();
    }
    //down 事件的X值
    private int firstx;
    //touch 事件的上一个X值
    private int lastx;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                firstx = lastx = (int) event.getX();
                isDrag = false;
                break;
            case MotionEvent.ACTION_MOVE:
                //是否发生拖动
                if (Math.abs(event.getX() - firstx) > 5){
                    isDrag = true;
                }
                //计算手指在屏幕上移动的距离
                int dis = (int)(event.getX() - lastx);
                //将本次的位置 设置给lastX
                lastx = (int)event.getX();
                //根据手指移动的距离，改变slideBtn_left 的值
                slideBtn_left = slideBtn_left + dis;
                break;
            case MotionEvent.ACTION_UP:
                //在发生拖动的情况下，根据最后的位置，判断当前开关的状态
                if (isDrag){
                    int maxLeft = switchBackgroud.getWidth() - slideButton.getWidth();
                    /**
                     * 根据 slideBtn_left 判断，当前应是什么状态
                     */
                    if (slideBtn_left > maxLeft/2){//此时应为打开的状态
                        currstate = true;
                    }else {
                        currstate = false;
                    }
                    flushState();
                }
                break;
        }
        flushView();
        return true;
    }

    /**
     * 刷新当前视图
     */
    private void flushView() {
        /**
         * 对 slideBtn_left 的值进行判断，确保其在合理的位置
         *  0 <= slideBtn_left <= maxLeft
         */

        // slideBtn 左边界最大值
        int maxLeft = switchBackgroud.getWidth() - slideButton.getWidth();
        //确保slideBtn_left >= 0
        slideBtn_left = (slideBtn_left > 0) ? slideBtn_left : 0;
        //确保 slideBtn_left <= maxLeft
        slideBtn_left = (slideBtn_left < maxLeft) ? slideBtn_left : maxLeft;

        invalidate();
    }
}
