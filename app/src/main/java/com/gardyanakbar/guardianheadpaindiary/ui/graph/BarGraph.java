package com.gardyanakbar.guardianheadpaindiary.ui.graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.LinkedHashMap;

public class BarGraph extends Graph
{
    //Constructors
    public BarGraph(Context context, LinkedHashMap<String, Double> dataMap)
    {
        super(context, dataMap);
        super.setMaxMarkersInXAxis(dataMap.size());
    }
    public BarGraph(Context context, LinkedHashMap<String, Double> dataMap, String xAxisName, String yAxisName)
    {
        super(context, dataMap, xAxisName, yAxisName);
        super.setMaxMarkersInXAxis(dataMap.size());
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

    @Override
    protected void drawDataPoints(Canvas canvas, Paint paint, int color, int width) {

    }

    @Override
    public void revalidateLanguage() {

    }
}
