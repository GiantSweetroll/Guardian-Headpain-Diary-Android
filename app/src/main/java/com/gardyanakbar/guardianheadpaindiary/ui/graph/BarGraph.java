package com.gardyanakbar.guardianheadpaindiary.ui.graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import java.util.LinkedHashMap;

public class BarGraph extends Graph
{
    //Constructors
    public BarGraph(Context context, LinkedHashMap<String, Double> dataMap)
    {
        super(context, dataMap);
        super.setMaxMarkersInXAxis(dataMap.size());
        this.dataPointColor = ContextCompat.getColor(this.getContext(), android.R.color.holo_red_dark);
    }
    public BarGraph(Context context, LinkedHashMap<String, Double> dataMap, String xAxisName, String yAxisName)
    {
        super(context, dataMap, xAxisName, yAxisName);
        super.setMaxMarkersInXAxis(dataMap.size());
        this.dataPointColor = ContextCompat.getColor(this.getContext(), android.R.color.holo_red_dark);
    }

    //Private Methods
    private boolean xAxisMarkerLabelHaveSpace(Canvas canvas, Paint paint)
    {
        //Predict amount of space needed
        long pixlesNeeded = 0;
        for (String item : super.xAxisLabels)
        {
            Rect bounds = new Rect();
            paint.getTextBounds(item, 0, item.length(), bounds);
            pixlesNeeded += bounds.width();
        }

        //Check if there's room
        if (pixlesNeeded > super.axesLength.x)
        {
            return false;
        }

        return true;
    }

    //Overridden Methods
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        this.drawDataPoints(this.graph2DImage, this.paint, ContextCompat.getColor(this.getContext(), android.R.color.holo_red_dark), this.distanceBetweenDataPoints/2);
        canvas.drawBitmap(this.getGraphImage(), 0, 0, this.paint);
    }

    @Override
    protected void drawDataPoints(Canvas canvas, Paint paint, int color, int width)
    {
        int oldColor = this.paint.getColor();
        paint.setColor(color);
        Paint.Style oldStyle = paint.getStyle();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        for (Point point : this.dataPoints)
        {
            canvas.drawRect(point.x-width/2,
                            point.y,
                            point.x + width/2,
                            this.axesOrigin.y,
                            paint);
        }

        paint.setStyle(oldStyle);
        paint.setColor(oldColor);
    }

    @Override
    public void revalidateLanguage() {

    }
}
