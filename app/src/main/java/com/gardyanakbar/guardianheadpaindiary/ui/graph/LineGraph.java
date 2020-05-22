package com.gardyanakbar.guardianheadpaindiary.ui.graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import java.util.LinkedHashMap;

public class LineGraph extends Graph
{
    //Constructor
    public LineGraph(Context context,  LinkedHashMap<String, Double> dataMap)
    {
        super(context, dataMap);
    }
    public LineGraph(Context context, LinkedHashMap<String, Double> dataMap, String xAxisName, String yAxisName)
    {
        super(context, dataMap, xAxisName, yAxisName);
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

        for (int i=0; i<this.dataPoints.size()-1; i++)
        {
            canvas.drawLine(this.dataPoints.get(i).x,
                    this.dataPoints.get(i).y,
                    this.dataPoints.get(i+1).x,
                    this.dataPoints.get(i+1).y,
                    paint);
        }
    }

    //Overridden Methods
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        this.drawConnectingLines(canvas, this.paint, ContextCompat.getColor(this.getContext(), android.R.color.holo_red_dark));
        super.drawAxesWithDefaultSettings();
        canvas.drawBitmap(this.getGraphImage(), 0, 0, this.paint);
    }

    @Override
    protected void drawDataPoints(Canvas canvas, Paint paint, int color, int width)
    {
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        for (int i=0; i<this.dataPoints.size(); i++)
        {
            if (this.dataPoints.get(i).y == this.axesOrigin.y)    //Skip data with 0 values
            {
                continue;
            }
            canvas.drawOval(this.dataPoints.get(i).x - width / 2,
                    this.dataPoints.get(i).y - width / 2,
                    width,
                    width,
                    paint);
        }
        paint.setStyle(Paint.Style.STROKE);
    }

    @Deprecated
    @Override
    public void revalidateLanguage() {}
}
