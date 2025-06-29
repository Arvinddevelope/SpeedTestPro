package com.arvind.speedtestpro;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

import java.util.LinkedList;
import java.util.Queue;

public class SpeedGraphView extends View {

    private Paint paint;
    private Queue<Float> speedData = new LinkedList<>();
    private final int maxPoints = 50;

    public SpeedGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(5f);
        paint.setAntiAlias(true);
    }

    public void updateGraph(float speed) {
        if (speedData.size() >= maxPoints) {
            speedData.poll();
        }
        speedData.add(speed);
        postInvalidate();
    }

    public void resetGraph() {
        speedData.clear();
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth();
        float height = getHeight();

        float maxSpeed = 20f; // max speed in Mbps for scaling

        float xStep = width / maxPoints;
        float lastX = 0, lastY = height;

        int i = 0;
        for (Float speed : speedData) {
            float x = i * xStep;
            float y = height - (speed / maxSpeed * height);
            if (i != 0) {
                canvas.drawLine(lastX, lastY, x, y, paint);
            }
            lastX = x;
            lastY = y;
            i++;
        }
    }
}