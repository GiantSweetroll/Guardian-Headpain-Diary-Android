package com.gardyanakbar.guardianheadpaindiary.ui.graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import java.util.LinkedHashMap;

public class LineGraph extends Graph
{
    //Fields
    private static final String TAG = "LineGraph";

    //Constructor
    public LineGraph(Context context,  LinkedHashMap<String, Double> dataMap)
    {
        super(context, dataMap);
        this.dataPointColor = ContextCompat.getColor(this.getContext(), android.R.color.holo_green_light);
    }
    public LineGraph(Context context, LinkedHashMap<String, Double> dataMap, String xAxisName, String yAxisName)
    {
        super(context, dataMap, xAxisName, yAxisName);
        this.dataPointColor = ContextCompat.getColor(this.getContext(), android.R.color.holo_green_light);
    }

    //Private Methods
    /**
     * Draws the connecting lines between data points to simulate a line graph
     * @param canvas
     * @param paint
     * @param color - new color of the connecting lines including its alpha values.
     */
    private void drawConnectingLines(Canvas canvas, Paint paint, int color)
    {
        paint.setColor(color);
        float temp = this.paint.getStrokeWidth();
        this.paint.setStrokeWidth(10);

        for (int i=0; i<this.dataPoints.size()-1; i++)
        {
            canvas.drawLine(this.dataPoints.get(i).x,
                    this.dataPoints.get(i).y,
                    this.dataPoints.get(i+1).x,
                    this.dataPoints.get(i+1).y,
                    paint);
        }

        this.paint.setStrokeWidth(temp);
    }

    //Overridden Methods
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvas.drawBitmap(this.getGraphImage(), 0, 0, this.paint);
        this.drawConnectingLines(canvas, this.paint, ContextCompat.getColor(this.getContext(), android.R.color.holo_red_dark));
    }

    @Override
    protected void drawDataPoints(Canvas canvas, Paint paint, int color, int width)
    {
        int oldColor = this.paint.getColor();
        paint.setColor(color);
        Paint.Style oldStyle = paint.getStyle();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        for (int i=0; i<this.dataPoints.size(); i++)
        {
            if (this.dataPoints.get(i).y == this.axesOrigin.y)    //Skip data with 0 values
            {
                continue;
            }
            canvas.drawCircle(this.dataPoints.get(i).x,
                                this.dataPoints.get(i).y,
                                width, this.paint);
        }
        paint.setStyle(oldStyle);
        paint.setColor(oldColor);
    }

    @Deprecated
    @Override
    public void revalidateLanguage() {}
}
