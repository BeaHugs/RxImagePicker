package com.k2.custom.cview;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by WYB on 2017/12/19 15:34.
 */

public class FristView extends View {
    private final int w = 5;

    private Paint paintX;
    private Paint cPaint;
    private int height;
    private int width;
    private int left;
    private int rngin;
    private int top;
    private int bottom;
    private float yHeight;
    private int xWidth;
    private Paint painText;
    int MAX;
    private Paint zPaint;
    private Path path;
    private Path xyPath;

    public FristView(Context context) {
        this(context, null);
    }

    public FristView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        infoPaint();
    }

    public FristView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = getHeight();
        width = getWidth();
        left = 20;
        rngin = 20;
        top = 20;
        bottom = 20;
    }

    private void infoPaint() {
        path = new Path();
        xyPath = new Path();

        paintX = new Paint();
        paintX.setColor(Color.BLACK);
        paintX.setStrokeWidth(4);
        paintX.setStyle(Paint.Style.STROKE);
        paintX.setAntiAlias(true);

        cPaint = new Paint();
        cPaint.setColor(Color.RED);
        cPaint.setStrokeWidth(1);
        cPaint.setStyle(Paint.Style.FILL);

        zPaint = new Paint();
        zPaint.setColor(Color.BLACK);
        zPaint.setStrokeWidth(1);
        zPaint.setStyle(Paint.Style.STROKE);
        zPaint.setAntiAlias(true);


        painText = new Paint();
        painText.setColor(Color.BLACK);
        painText.setStrokeWidth(1);
        painText.setStyle(Paint.Style.FILL);
        painText.setAntiAlias(true);
        painText.setTextSize(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawXY(canvas);
    }

    private void drawXY(Canvas canvas) {
        xyPath.reset();
        xyPath.close();
        /**
         * 画XY轴\__
         */
        xyPath.moveTo(left, top);
        xyPath.lineTo(left, height - bottom);
        xyPath.lineTo(width - rngin, height - bottom);
        canvas.drawPath(xyPath, paintX);

        if (  strList == null || strList.size() == 0) {
            canvas.drawText("没有数据", getWidth() / 2-rngin-left, getHeight() / 2, painText);
            return;
        }
        /**
         * 高-底部-顶部
         */
        yHeight = height - bottom - top;
        /**
         * 宽-右-左
         */
        xWidth = width - rngin - left;
        /**
         * 画X线的间距
         */
        float wYHeight = (yHeight - top) / w;
        /**
         * 画Y线的间距
         */
        float hXWidth = xWidth / strList.size();

        //绘制X轴
        drawX(canvas, wYHeight);

        //绘制 Y轴线
        drawY(canvas, hXWidth);

        //绘制 数据
        drawData(canvas, wYHeight, hXWidth);
    }

    private void drawX(Canvas canvas, float wYHeight) {
        int itemHeight = 0;
        //叠加 Max/size
        int max = 0;
        for (int i = 0; i < w; i++) {
            if (itemHeight == 0) {
                itemHeight = top * 2;
                //确定X第一条线
                canvas.drawLine(left, itemHeight, width - rngin, itemHeight, cPaint);
                canvas.drawText(""+MAX, 0, itemHeight, painText);
                canvas.drawText(""+0, 0, itemHeight+wYHeight*w, painText);
            } else {
                //最大值 / X轴线数量 =
                max+=MAX/w;
                /**
                 * 间距++
                 */
                itemHeight += wYHeight;
                canvas.drawLine(left, itemHeight, width - rngin, itemHeight, cPaint);
                //最大值 - 间隔数值
                canvas.drawText(MAX-max+"", 0, itemHeight, painText);
            }
        }
    }

    private void drawY(Canvas canvas, float hXWidth) {
        int itemWidth = 0;
        for (int i = 0; i < strList.size(); i++) {
            /**
             * 画Y线
             */
            itemWidth += hXWidth;
            canvas.drawLine(itemWidth + left, top * 2, itemWidth + left, height - bottom, cPaint);
        }
    }

    private void drawData(Canvas canvas, float wYHeight, float hXWidth) {

        path.reset();
        path.close();
       // zPaint.reset();
        int itemX = 0;
        for (int i = 0; i < strList.size(); i++) {
            //数据
            int itemdata = strList.get(i);
            Log.i("xxx",itemdata+"");
            //点的Y轴
            float itemY = (w * wYHeight) * itemdata / MAX;
            if (i == 0) {
                //X轴间隔
                itemX += (i) * hXWidth + left+hXWidth;
                //画线
                path.moveTo(itemX, (wYHeight * w) - itemY + 2 * top);
                //画Y轴上的点                        w 横线数量
                canvas.drawCircle(itemX, (wYHeight * w) - itemY + 2 * top, 6, cPaint);
                //添加时间点
                canvas.drawText(i+1+":00", itemX-left, top*3+wYHeight*w, painText);

            } else {
                path.lineTo((i) * hXWidth + itemX, (wYHeight * w) - itemY + 2 * top);
                canvas.drawCircle((i) * hXWidth + itemX, (wYHeight * w) - itemY + 2 * top, 6, cPaint);
                //添加时间点
                canvas.drawText(i+1+":00", (i) * hXWidth + itemX-left, top*3+wYHeight*w, painText);
            }
            canvas.drawPath(path, zPaint);
        }
    }
    /**
     * 数据源
     */
    List<Integer> strList;

    public void setData(List<Integer> strList) {
        this.strList = strList;
        invalidate();
    }
    //提供最大值
    public void setMax(int max){
        this.MAX = max;
    }
}


