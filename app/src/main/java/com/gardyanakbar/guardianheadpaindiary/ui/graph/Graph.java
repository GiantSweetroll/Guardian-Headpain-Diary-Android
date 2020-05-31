package com.gardyanakbar.guardianheadpaindiary.ui.graph;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.constants.Constants;
import com.gardyanakbar.guardianheadpaindiary.interfaces.LanguageListener;
import com.gardyanakbar.guardianheadpaindiary.methods.Methods;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import giantsweetroll.numbers.GNumbers;

public abstract class Graph extends View implements LanguageListener
{
    //Fields
    private String xAxisName;
    private String yAxisName;
    private LinkedHashMap<String, Double> dataMap;
    private Bitmap graphImage;

    protected Point axesOrigin;
    protected Point axesLength;
    protected Point xAxisEndCoordinates;
    protected Point yAxisEndCoordinates;
    protected List<Point> dataPoints;
    protected List<String> xAxisLabels;
    protected List<Double> yAxisValues;
    protected List<Point> yAxisMarkerPoints;
    protected int maxYAxisMarkerLabelLength;
    protected int maxXAxisMarkerLabelHeight;
    protected int yAxisNameTextHeight;
    protected int maxMarkersXAxis;
    protected int axesPaddingWithPanelEdgeSides = 50;
    protected int axesPaddingWithPanelEdgeBelow = 50;
    protected int axesPaddingWithPanelEdgeTop = 50;
    protected int xAxisNameTextHeight;
    protected Canvas graph2DImage;
    protected Paint paint;
    protected final Point graphImageSize = new Point(1280, 720);

    //Options
    protected boolean enableDataValueMarkers;
    protected boolean displayDataPoint;
    protected boolean showGraphLinesOfY;
    protected boolean showGraphLinesOfX;

    //Constants
    protected final int DATA_POINT_WIDTH = 10;
    protected final int AXES_POINTERS_LENGTH = 15;
    protected final int MARKER_LABEL_PADDING = 5;
    protected final int MAX_MARKERS_IN_Y_AXIS = 10;
    protected final int MAX_MARKERS_IN_X_AXIS = 5;
    protected final int GENERAL_PADDING = 10;
    protected final int DECIMAL_PLACES = 1;
    protected final int X_AXIS_NAME_PADDING = 20;
    protected final int Y_AXIS_NAME_PADDING = 20;
    private final int STROKE_SIZE = 1;

    //Constructors
    public Graph(Context context, LinkedHashMap<String, Double> dataMap)
    {
        super(context);
        this.init(dataMap, "", "");
    }
    public Graph(Context context, LinkedHashMap<String, Double> dataMap, String xAxisName, String yAxisName)
    {
        super(context);
        this.init(dataMap, xAxisName, yAxisName);
    }
    public Graph(Context context, String xAxisName, String yAxisName)
    {
        super(context);
        this.init(null, xAxisName, yAxisName);
    }
    public Graph(Context context)
    {
        super(context);
        this.init(null, "", "");
    }

    //Private Methods
    private void init(LinkedHashMap<String, Double> dataMap, String xAxisName, String yAxisName)
    {
        //Initialization
        this.dataMap = dataMap;
        this.xAxisName = xAxisName;
        this.yAxisName = yAxisName;
        this.maxYAxisMarkerLabelLength = 0;
        this.maxXAxisMarkerLabelHeight = 0;
        this.yAxisNameTextHeight = 0;
        this.xAxisLabels = new ArrayList<String>();
        this.yAxisValues = new ArrayList<Double>();
        this.yAxisMarkerPoints = new ArrayList<Point>();
        this.dataPoints = new ArrayList<Point>();
        this.paint = new Paint();
        this.graphImage = Bitmap.createBitmap(this.graphImageSize.x, this.graphImageSize.y, Bitmap.Config.ARGB_8888);
        this.graph2DImage = new Canvas(this.graphImage);

        //Properties
        this.paint.setAntiAlias(true);

        this.enableDataValueMarkers = false;
        this.displayDataPoint = true;
        this.maxMarkersXAxis = this.MAX_MARKERS_IN_X_AXIS;
        this.showGraphLinesOfX = true;
        this.showGraphLinesOfY = true;

        if (dataMap != null)
        {
            for (Map.Entry<String, Double> entry : dataMap.entrySet())
            {
                this.xAxisLabels.add(entry.getKey());
                this.yAxisValues.add(entry.getValue());
            }
        }

        this.setMinimumWidth(this.graphImageSize.x);
        this.setMinimumHeight(this.graphImageSize.y);
    }

    //Protected Methods
    //Misc Methods
    protected void setMaxMarkersInXAxis(int max)
    {
        this.maxMarkersXAxis = max;
    }
    protected void setXAxisName(String text)
    {
        this.xAxisName = text;
        this.invalidate();
    }
    protected void setYAxisName(String text)
    {
        this.yAxisName = text;
        this.invalidate();
    }
    protected String getXAxisName()
    {
        return this.xAxisName;
    }
    protected String getYAxisName()
    {
        return this.yAxisName;
    }
    protected LinkedHashMap<String, Double> getDataMap()
    {
        return this.dataMap;
    }
    protected int getMinWidth()
    {
        return this.yAxisNameTextHeight +
                this.Y_AXIS_NAME_PADDING +
                this.maxYAxisMarkerLabelLength +
                this.MARKER_LABEL_PADDING +
                this.AXES_POINTERS_LENGTH +
                this.axesLength.x +
                this.axesPaddingWithPanelEdgeSides;
    }
    protected int getBehindAxesDifferenceWithPanelEdgeLeft()
    {
        int usage = this.yAxisNameTextHeight + this.Y_AXIS_NAME_PADDING + this.maxYAxisMarkerLabelLength + this.MARKER_LABEL_PADDING + this.AXES_POINTERS_LENGTH;
        return usage - this.axesPaddingWithPanelEdgeSides;
    }
    protected int getBelowAxesDifferenceWithPanelEdgeBelow()
    {
        int usage = this.xAxisNameTextHeight + this.X_AXIS_NAME_PADDING + this.maxXAxisMarkerLabelHeight + this.MARKER_LABEL_PADDING + this.AXES_POINTERS_LENGTH;
        return usage - this.axesPaddingWithPanelEdgeBelow;
    }
    protected void setXAxisLabels(List<String> labels)
    {
        this.xAxisLabels = labels;
    }
    public Bitmap getGraphImage()
    {
        return this.graphImage;
    }
    public void setGraphImageSize(int width, int height)
    {
        this.graphImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.graph2DImage = new Canvas(this.graphImage);
        this.invalidate();
    }
    public void setGraphImageSize(Point dim)
    {
        this.setGraphImageSize(dim.x, dim.y);
    }
    protected void drawAxesWithDefaultSettings()
    {
        this.drawAxes(this.graph2DImage, this.paint, ContextCompat.getColor(this.getContext(), R.color.colorBlack), this.graphImageSize.x-this.axesPaddingWithPanelEdgeSides, this.axesPaddingWithPanelEdgeTop);
    }
    //Draw Sections
    protected abstract void drawDataPoints(Canvas canvas, Paint paint, int color, int width);

    /**
     * Draws the axes of the cartesian plane (positive x and y only)
     * @param canvas
     * @param paint - the new color with its alpha values
     * @param color
     * @param xEnd
     * @param yEnd
     */
    protected void drawAxes(Canvas canvas, Paint paint, int color, int xEnd, int yEnd)
    {
        paint.setColor(color);

        //Draw X-Axis
        canvas.drawLine(this.axesOrigin.x, this.axesOrigin.y, xEnd, this.axesOrigin.y, paint);

        //Draw Y-Axis
        canvas.drawLine(this.axesOrigin.x, this.axesOrigin.y, this.axesOrigin.x, yEnd, paint);

        //Get length
        this.axesLength = new Point(xEnd - this.axesOrigin.x, this.axesOrigin.y - yEnd);

        //Get end coordinates
        this.xAxisEndCoordinates = new Point(xEnd, this.axesOrigin.y);
        this.yAxisEndCoordinates = new Point(this.axesOrigin.x, yEnd);
    }

    /**
     * Generate the coordinates for the data points (in pixels)
     * @param dataValues
     */
    protected void generateDataPoints(List<Double> dataValues)
    {
        //Set Distance between each data entry in the x-axis
        double xDiff = GNumbers.round(this.axesLength.x/dataValues.size(), 0);
        //Set distance between each unit increment in the y-axis
        double yDiff = (float)this.axesLength.y/(float)Methods.getHighestValue(dataValues);

        //Translate into coordinate points
        this.dataPoints = new ArrayList<Point>();
        int xPos = (int)GNumbers.round(xDiff, 1);
        for (int i=0; i<dataValues.size(); i++)
        {
            Double doubleCoordinate = (double)this.axesOrigin.y - yDiff*dataValues.get(i);
            int yCoordinate = (int)GNumbers.round(doubleCoordinate, this.DECIMAL_PLACES);

            this.dataPoints.add(new Point(this.axesOrigin.x + xPos, yCoordinate));
            xPos+=xDiff;
        }
    }

    /**
     * Draws the axes markers in uniform steps.
     * @param canvas
     * @param paint - the new color including its alpha values
     * @param color
     */
    protected void drawAxesMarkers(Canvas canvas, Paint paint, int color)
    {
        paint.setColor(color);

        //Draw X-Axis Marker Labels
        int diff = 0;

        if (this.xAxisLabels.size()<=this.maxMarkersXAxis)
        {
            diff = 1;
        }
        else
        {
            diff = (int)GNumbers.round(this.xAxisLabels.size()/this.maxMarkersXAxis, 1);
            if (diff==1)
            {
                diff = 2;
            }
        }

        for (int i=0; i<this.dataPoints.size(); i+=diff)
        {
            canvas.drawLine(this.dataPoints.get(i).x,
                    this.axesOrigin.y + this.AXES_POINTERS_LENGTH/2,
                    this.dataPoints.get(i).x,
                    this.axesOrigin.y - this.AXES_POINTERS_LENGTH/2,
                    paint);
        }

        //Draw Y-Axis Marker Labels
        double yDiff = (double)this.axesLength.y/(double)this.MAX_MARKERS_IN_Y_AXIS;
        this.yAxisMarkerPoints = new ArrayList<Point>();
        for (int i=1; i<=this.MAX_MARKERS_IN_Y_AXIS; i++)
        {
            int x = this.axesOrigin.x - this.AXES_POINTERS_LENGTH/2;
            int y = this.axesOrigin.y - (int)GNumbers.round((yDiff*(double)i), 0);
            canvas.drawLine(x,
                    y,
                    this.axesOrigin.x + this.AXES_POINTERS_LENGTH/2,
                    y,
                    paint);

            this.yAxisMarkerPoints.add(new Point(x, y));	//Take note of the coordinate position of each Y-Axis markers
        }
    }

    /**
     * Draw the marker labels of the Y-Axis (the value of each steps)
     * @param canvas
     * @param paint
     * @param color - the new color including its alpha values
     * @param typeface
     */
    protected void drawYAxisMarkerLabels(Canvas canvas, Paint paint, int color, Typeface typeface)
    {
        paint.setColor(color);
        paint.setTypeface(typeface);

        double highestVal = Methods.getHighestValue(this.yAxisValues);		//Get highest value
        double diff = highestVal/(double)this.MAX_MARKERS_IN_Y_AXIS;			//Get unit increment

        this.maxYAxisMarkerLabelLength = 0;

        for (double i = 0; i<this.yAxisMarkerPoints.size(); i++)
        {
            String text = Double.toString(GNumbers.round(diff*(i+1), this.DECIMAL_PLACES));		//Rounded to X Decimal Place
            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);
            int textWidth = bounds.width();

            if (textWidth > this.maxYAxisMarkerLabelLength)
            {
                this.maxYAxisMarkerLabelLength = textWidth;
            }

            canvas.drawText(text,
                    this.axesOrigin.x - this.AXES_POINTERS_LENGTH - this.MARKER_LABEL_PADDING - textWidth,
                    this.yAxisMarkerPoints.get((int)i).y + 4,
                    paint);
        }
    }
    /**
     * Draw the marker labels of the X-Axis (the value of each steps)
     * @param canvas
     * @param paint
     * @param color - the new color including its alpha values
     * @param typeface
     */
    protected void drawXAxisMarkerLabels(Canvas canvas, Paint paint, int color, Typeface typeface)
    {
        paint.setColor(color);
        paint.setTypeface(typeface);

        int diff = 0;
        this.maxXAxisMarkerLabelHeight = 0;

        if (this.xAxisLabels.size()<=this.maxMarkersXAxis)
        {
            diff = 1;
        }
        else
        {
            diff = (int)GNumbers.round(this.xAxisLabels.size()/this.maxMarkersXAxis, 1);
            if (diff==1)
            {
                diff = 2;
            }
        }

        for (int i = 0; i<this.dataPoints.size(); i+=diff)
        {
            String text = this.xAxisLabels.get(i);
            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);
            int textWidth = bounds.width();
            int textHeight = bounds.height();
            int x = this.dataPoints.get(i).x - textWidth/2;
            int y = this.axesOrigin.y + this.AXES_POINTERS_LENGTH + this.MARKER_LABEL_PADDING;
            canvas.drawText(text, x, y, paint);

            //Get max height
            if (textHeight > this.maxXAxisMarkerLabelHeight)
            {
                this.maxXAxisMarkerLabelHeight = textHeight;
            }
        }
    }

    /**
     * Draw the data values (the exact value for each data points above them)
     * @param canvas
     * @param paint
     * @param color - the new color including its alpha values
     */
    protected void drawDataValues(Canvas canvas, Paint paint, int color)
    {
        paint.setColor(color);

        paint.setTypeface(Constants.FONT_GENERAL_BOLD);
        for (int i=0; i<this.yAxisValues.size(); i++)
        {
            Double value = GNumbers.round(this.yAxisValues.get(i), this.DECIMAL_PLACES);
            if (value <= 0d)
            {
                continue;
            }
            String text = Double.toString(value);
            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);
            int textWidth = bounds.width();

            canvas.drawText(text, this.dataPoints.get(i).x - textWidth/2, this.dataPoints.get(i).y - this.GENERAL_PADDING, paint);
        }
        paint.setTypeface(Constants.FONT_GENERAL);
    }

    /**
     * Draws the names of the axes
     * @param canvas
     * @param paint
     * @param colorx - new color for the X-Axis label including alpha values.
     * @param colory - new color for the Y-Axis label including alpha values.
     * @param x
     * @param y
     */
    protected void drawAxisNames(Canvas canvas, Paint paint, int colorx, int colory, String x, String y)
    {
        paint.setTypeface(Constants.FONT_GENERAL);
        paint.setTextSize(Constants.FONT_SIZE_A_BIT_BIGGER);
        Rect bounds = new Rect();
        paint.getTextBounds(x, 0, x.length(), bounds);
        this.xAxisNameTextHeight = bounds.height();
        //Draw X-Axis name
        paint.setColor(colorx);

        int textLength = bounds.width();
        canvas.drawText(x,
                this.axesOrigin.x + (this.axesLength.x/2) - textLength/2,
                this.axesOrigin.y + this.AXES_POINTERS_LENGTH + this.MARKER_LABEL_PADDING + this.maxXAxisMarkerLabelHeight + this.X_AXIS_NAME_PADDING,
                paint);

        //Draw Y-Axis Name
        paint.getTextBounds(y, 0, y.length(), bounds);
        textLength = bounds.width();
        paint.setColor(colory);
//		g2.drawString(y, this.axesOrigin.x - this.MARKER_LABEL_PADDING - this.maxYAxisMarkerLabelLength - this.Y_AXIS_NAME_PADDING, this.axesOrigin.y - this.axesLength.y/2 + textLength/2);
        this.yAxisNameTextHeight = bounds.height();
        Matrix matrix = new Matrix();
        matrix.preRotate(90);
        canvas.setMatrix(matrix);       //Needs evaluating
        //	g2.drawString(y, (this.axesOrigin.y - this.axesLength.y/2 + textLength/2)*-1, (this.axesLength.y-this.axesLength.y/2+textLength/2)/4 - this.MARKER_LABEL_PADDING - this.maxYAxisMarkerLabelLength*1.8f);
        canvas.drawText(y,
                (this.axesOrigin.y - this.axesLength.y/2 + textLength/2)*-1,
                this.axesOrigin.x - this.AXES_POINTERS_LENGTH - this.MARKER_LABEL_PADDING - this.maxYAxisMarkerLabelLength - this.Y_AXIS_NAME_PADDING,
                paint);
        matrix.postRotate(90);
    }

    /**
     * Draws the little graph lines of the cartesian plane
     * @param canvas
     * @param paint
     * @param color - new color for the graph lines including alpha values.
     */
    protected void drawGraphLines(Canvas canvas, Paint paint, int color)
    {
        paint.setColor(color);

        if (this.showGraphLinesOfY)		//Displaying Graph Lines of Y
        {
            for (Point marker : this.yAxisMarkerPoints)
            {
                canvas.drawLine(this.axesOrigin.x+this.STROKE_SIZE, 	//+ SROKE_SIZE so that it does not get drawn on the Y-Axis
                        marker.y,
                        this.xAxisEndCoordinates.x,
                        marker.y,
                        paint);
            }
        }

        if (this.showGraphLinesOfX)		//Displaying Graph Lines of X
        {
            for (Point marker : this.dataPoints)
            {
                canvas.drawLine(marker.x,
                        this.axesOrigin.y-this.STROKE_SIZE, //- SROKE_SIZE so that it does not get drawn on the X-Axis
                        marker.x,
                        this.yAxisEndCoordinates.y,
                        paint);
            }
        }
    }

    /**
     * Draws the label of the current medication currently being filtered.
     * @param canvas
     * @param paint
     * @param color
     */
    protected void drawRecentMedicationText(Canvas canvas, Paint paint, int color)
    {
        //TODO: Make graph filter panel
//        if (Globals.GRAPH_FILTER_PANEL.isRecentMedicationSelected())
//        {
//            paint.setColor(color);
//
//            canvas.drawText(Methods.getLanguageText(XMLIdentifier.RECENT_MEDICATION_LABEL) + ": " + Globals.GRAPH_FILTER_PANEL.getRecentMedicationFilter(),
//                    this.axesOrigin.x,
//                    this.axesOrigin.y + this.AXES_POINTERS_LENGTH + this.MARKER_LABEL_PADDING + this.maxXAxisMarkerLabelHeight + this.X_AXIS_NAME_PADDING,
//                    paint);
//        }
    }

    //Graph Settings
    protected void displayDataValues(boolean bool)
    {
        this.enableDataValueMarkers = bool;
        this.invalidate();
    }
    protected void displayDataPoint(boolean show)
    {
        this.displayDataPoint = show;
        this.invalidate();
    }
    protected void displayGraphLinesOfY(boolean show)
    {
        this.showGraphLinesOfY = show;
        this.invalidate();
    }
    protected void displayGraphLinesOfX(boolean show)
    {
        this.showGraphLinesOfX = show;
        this.invalidate();
    }

    //Overridden Methods
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        this.paint.setColor(ContextCompat.getColor(this.getContext(), R.color.colorWhite));
        this.paint.setStrokeWidth(this.STROKE_SIZE);
        this.paint.setStyle(Paint.Style.FILL);
        this.graph2DImage.drawRect(new Rect(0, 0, this.graphImageSize.x, this.graphImageSize.y), this.paint);

        this.axesOrigin = new Point(this.axesPaddingWithPanelEdgeSides, this.graphImageSize.y-this.axesPaddingWithPanelEdgeBelow);
        this.drawAxesWithDefaultSettings();
        try
        {
            this.generateDataPoints(this.yAxisValues);
            this.drawAxesMarkers(this.graph2DImage, this.paint, ContextCompat.getColor(this.getContext(), R.color.colorBlack));
            this.drawXAxisMarkerLabels(this.graph2DImage, this.paint, ContextCompat.getColor(this.getContext(), R.color.colorGraphAxesMarkerLabels), Constants.FONT_GENERAL_BOLD);
            this.drawYAxisMarkerLabels(this.graph2DImage, this.paint, ContextCompat.getColor(this.getContext(), R.color.colorGraphAxesMarkerLabels), Constants.FONT_GENERAL_BOLD);
            this.drawGraphLines(this.graph2DImage, this.paint, ContextCompat.getColor(this.getContext(), android.R.color.darker_gray));
            this.drawAxisNames(this.graph2DImage, this.paint, ContextCompat.getColor(this.getContext(), R.color.colorBlack), ContextCompat.getColor(this.getContext(), R.color.colorBlack), this.xAxisName, this.yAxisName);
            this.drawRecentMedicationText(this.graph2DImage, this.paint, ContextCompat.getColor(this.getContext(), R.color.colorBlack));
            this.paint.setTypeface(Constants.FONT_GENERAL);
            if (this.enableDataValueMarkers)
            {
                this.drawDataValues(this.graph2DImage, this.paint, ContextCompat.getColor(this.getContext(), R.color.colorBlack));
            }

            if (this.getBehindAxesDifferenceWithPanelEdgeLeft()>0)
            {
                this.axesPaddingWithPanelEdgeSides += this.getBehindAxesDifferenceWithPanelEdgeLeft();
            }
            else if (this.getBelowAxesDifferenceWithPanelEdgeBelow()>0)
            {
                this.axesPaddingWithPanelEdgeBelow += this.getBelowAxesDifferenceWithPanelEdgeBelow();
            }

            if (this.displayDataPoint)
            {
                this.drawDataPoints(this.graph2DImage, this.paint, ContextCompat.getColor(this.getContext(), android.R.color.holo_green_dark), this.DATA_POINT_WIDTH);
            }
        }
        catch(ArithmeticException ex)
        {
//			ex.printStackTrace();
        }
    }
}