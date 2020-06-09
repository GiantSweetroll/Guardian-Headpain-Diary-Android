package com.gardyanakbar.guardianheadpaindiary.ui.graph;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
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
    /*TODO:
    1. Bar Graph when only one entry the bar is too big
    2. Sometimes the x-axis marker label longest label isnt properly calculated
    3. Data is not ordered properly when compared per episode (the time isnt ordered)
     */

    //Fields
    private static final String TAG = "Graph";
    private String xAxisName;
    private String yAxisName;
    private String lastXAxisMarkerLabelText;
    private String longestXAxisMarkerLabelText;
    private LinkedHashMap<String, Double> dataMap;
    private Bitmap graphImage;

    protected Point axesOrigin;
    protected Point axesLength;
    protected Point xAxisEndCoordinates;
    protected Point yAxisEndCoordinates;
    protected List<Point> dataPoints;
    protected List<String> xAxisLabels;
    protected List<Double> yAxisValues;
    protected List<Point> yAxisMarkersPos;
    protected int maxYAxisMarkerLabelLength;
    protected int maxXAxisMarkerLabelHeight;
    protected int yAxisNameTextHeight;
    protected int maxMarkersXAxis;
    protected int axesPaddingWithPanelEdgeLeft = 50;
    protected int axesPaddingWithPanelEdgeRight = this.axesPaddingWithPanelEdgeLeft;
    protected int axesPaddingWithPanelEdgeBelow = 20;
    protected int axesPaddingWithPanelEdgeTop = 50;
    protected int xAxisMarkerLabelRotate = 20;
    protected int xAxisNameTextHeight;
    protected int xAxisMarkerLabelYPos;
    protected int xAxisMarkerLabelYPosExtra;        //For rotation handling
    protected int xAxisMarkerYPos;
    protected int yAxisMarkerXPos;
    protected int dataPointColor;
    protected int distanceBetweenDataPoints;
    protected int xAxisMarkerLabelDiff;
    protected double yAxisMarkerLabelDiff;
    protected List<Point> yAxisMarkerLabelPos;
    protected List<Point> xAxisMarkerLabelsPos;
    protected Canvas graph2DImage;
    protected Paint paint;
    protected Point xAxisNameLabelPos = new Point(0, 0);
    protected Point yAxisNameLabelPos = new Point(0, 0);

    //Options
    protected boolean enableDataValueMarkers;
    protected boolean displayDataPoint;
    protected boolean showGraphLinesOfY;
    protected boolean showGraphLinesOfX;

    //Constants
    protected final int DATA_POINT_WIDTH = 40;
    protected final int AXES_POINTERS_LENGTH = 40;
    protected final int MARKER_LABEL_PADDING = 10;
    protected final int MAX_MARKERS_IN_Y_AXIS = 10;
//    protected final int MAX_MARKERS_IN_X_AXIS = 5;
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
        Log.d(TAG, "init: Initializer called");
        //Initialization
        this.dataMap = dataMap;
        this.xAxisName = xAxisName;
        this.yAxisName = yAxisName;
        this.lastXAxisMarkerLabelText = "";
        this.longestXAxisMarkerLabelText = "";
        this.maxYAxisMarkerLabelLength = 0;
        this.maxXAxisMarkerLabelHeight = 0;
        this.yAxisNameTextHeight = 0;
        this.xAxisMarkerLabelYPosExtra = 0;
        this.axesOrigin = new Point();
        this.axesLength = new Point();
        this.xAxisLabels = new ArrayList<String>();
        this.yAxisValues = new ArrayList<Double>();
        this.yAxisMarkersPos = new ArrayList<Point>();
        this.dataPoints = new ArrayList<Point>();
        this.xAxisMarkerLabelsPos = new ArrayList<>();
        this.yAxisMarkerLabelPos = new ArrayList<>();
        this.paint = new Paint();
        this.dataPointColor = ContextCompat.getColor(this.getContext(), android.R.color.holo_green_light);

        //Properties
        this.paint.setAntiAlias(true);
        this.paint.setColor(ContextCompat.getColor(this.getContext(), R.color.colorWhite));
        this.paint.setStrokeWidth(this.STROKE_SIZE);
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setTextSize(50);

        this.enableDataValueMarkers = false;
        this.displayDataPoint = true;
    //    this.maxMarkersXAxis = this.MAX_MARKERS_IN_X_AXIS;
        this.maxMarkersXAxis = dataMap.size();
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
        this.postInvalidate();
    }
    protected void setYAxisName(String text)
    {
        this.yAxisName = text;
        this.postInvalidate();
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
                this.axesPaddingWithPanelEdgeLeft;
    }
    protected int getBehindAxesDifferenceWithPanelEdgeLeft()
    {
        int usage = this.yAxisNameTextHeight + this.Y_AXIS_NAME_PADDING + this.maxYAxisMarkerLabelLength + this.MARKER_LABEL_PADDING + this.AXES_POINTERS_LENGTH;
        return usage - this.axesPaddingWithPanelEdgeLeft;
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

    //Calculate component positioning
    /**
     * Calculates the position of the axes name labels.
     */
    protected void calculateAxisNamesPos()
    {
        paint.setTypeface(Constants.FONT_GENERAL);
        Rect bounds = new Rect();

        //Calculate X-Axis name position
        paint.getTextBounds(this.xAxisName, 0, this.xAxisName.length(), bounds);
        this.xAxisNameTextHeight = bounds.height();

        int textLength = bounds.width();
        this.xAxisNameLabelPos.set(this.graphImage.getWidth()/2 - textLength/2,
                this.graphImage.getHeight() - this.axesPaddingWithPanelEdgeBelow);

        //Calculate Y-Axis Name position
        paint.getTextBounds(this.yAxisName, 0, this.yAxisName.length(), bounds);
        textLength = bounds.width();
        this.yAxisNameTextHeight = bounds.height();
        this.yAxisNameLabelPos.set(this.axesPaddingWithPanelEdgeLeft,
                this.graphImage.getHeight()/2 + textLength/2);
    }
    /**
     * calculates the Y position of the x-axis marker labels.
     */
    protected void calculateXAxisMarkerLabelsYPos()
    {
        Rect rect = new Rect();
        this.paint.getTextBounds("A", 0, 1, rect);
        this.xAxisMarkerLabelYPos = this.xAxisNameLabelPos.y - this.X_AXIS_NAME_PADDING - this.X_AXIS_NAME_PADDING - rect.height()/2 - this.xAxisMarkerLabelYPosExtra;
    }
    /**
     * Calculate the Y position of the x-axis markers.
     */
    protected void calculateXAxisMarkersYPos()
    {
        //Calculate the size of the x axis marker labels
        Rect rect = new Rect();
        this.paint.getTextBounds("A", 0, 1, rect);
        int height = rect.height();

        this.xAxisMarkerYPos = this.xAxisMarkerLabelYPos - height - this.MARKER_LABEL_PADDING - this.AXES_POINTERS_LENGTH/2;
    }
    /**
     * Calculates the length of the longest y-axis marker label.
     */
    protected void calculateYAxisMarkerLabelsMaxLength()
    {
        double highestVal = GNumbers.round(Methods.getHighestValue(this.yAxisValues), this.DECIMAL_PLACES);		//Get highest value
        double diff = highestVal/(double)this.MAX_MARKERS_IN_Y_AXIS;			//Get unit increment

        Rect bounds = new Rect();
        String text = Double.toString(highestVal);
        paint.getTextBounds(text, 0, text.length(), bounds);

        this.maxYAxisMarkerLabelLength = bounds.width();

        Log.d(TAG, "calculateYAxisMarkerLabelsMaxLength: Highest value is " + text + " with a width of " + bounds.width());
    }
    /**
     * Calculates the x position of the markers in the y-axis
     */
    protected void calculateYMarkerXPos()
    {
        Log.d(TAG, "calculateYMarkerXPos: this.maxYAxisMarkerLabelLength = " + this.maxYAxisMarkerLabelLength);
        this.yAxisMarkerXPos = this.yAxisNameLabelPos.x + this.yAxisNameTextHeight + 10 + this.maxYAxisMarkerLabelLength + this.MARKER_LABEL_PADDING + this.AXES_POINTERS_LENGTH/2;
    }
    /**
     * Calculates the position of the origin point and how long the axes lines should be and their end points.
     */
    protected void calculateOriginPos()
    {
        this.axesOrigin.set(this.yAxisMarkerXPos, this.xAxisMarkerYPos);
        this.xAxisEndCoordinates = new Point(this.graphImage.getWidth() - this.axesPaddingWithPanelEdgeRight, this.axesOrigin.y);
        this.yAxisEndCoordinates = new Point(this.axesOrigin.x, this.axesPaddingWithPanelEdgeLeft);
        this.axesLength.set(this.xAxisEndCoordinates.x - this.axesOrigin.x,
                this.axesOrigin.y - this.yAxisEndCoordinates.y);

    }
    /**
     * Generates the positions of the data points.
     */
    protected void generateDataPoints()
    {
        this.dataPoints.clear();
        //Set Distance between each data entry in the x-axis
        double xDiff = GNumbers.round((this.axesLength.x)/this.yAxisValues.size(), 0);
        //Set distance between each unit increment in the y-axis
        double yDiff = (float)this.axesLength.y/(float)Methods.getHighestValue(this.yAxisValues);

        //Translate into coordinate points
        int xPos = (int)GNumbers.round(xDiff, 1);
        for (int i=0; i<this.yAxisValues.size(); i++)
        {
            Double doubleCoordinate = (double)this.axesOrigin.y - yDiff*this.yAxisValues.get(i);
            int yCoordinate = (int)GNumbers.round(doubleCoordinate, this.DECIMAL_PLACES);

            this.dataPoints.add(new Point(this.axesOrigin.x + xPos, yCoordinate));
            xPos+=xDiff;
        }

        if (this.dataPoints.size() > 0)
        {
            this.distanceBetweenDataPoints = this.dataPoints.get(0).x - this.axesOrigin.x;
        }
        else
        {
            this.distanceBetweenDataPoints = 0;
        }
    }
    /**
     * Calculates the positions of the x-axis marker labels (including which to show and which to hide)
     */
    protected void calculateXAxisMarkerLabelsPos()
    {
        this.xAxisMarkerLabelsPos.clear();
        int diff = 1;

        //Determine skip according to text size (keep trying until it no longer clashes
        loop:
        while(true)
        {
            Log.d(TAG, "calculateXAxisMarkerLabelsPos: Calculating X-Axis label pos...");
            //Add the points first
            List<String> texts = new ArrayList<>();
            for (int i = 0; i<this.dataPoints.size(); i+=diff)
            {
                String text = this.xAxisLabels.get(i);
                Rect bounds = new Rect();
                paint.getTextBounds(text, 0, text.length(), bounds);
                int textWidth = bounds.width();
                int textHeight = bounds.height();
                int x = 0;
                if (this.xAxisMarkerLabelRotate == 0)
                {
                    x = this.dataPoints.get(i).x - textWidth/2;
                }
                else
                {
                    x = this.dataPoints.get(i).x;
                }
                int y = this.xAxisMarkerLabelYPos;
                if (textWidth > this.longestXAxisMarkerLabelText.length())
                {
                    this.longestXAxisMarkerLabelText = text;
                }
                this.xAxisMarkerLabelsPos.add(new Point(x, y));
                texts.add(text);
            }

            if (texts.size() > 0)
            {
                this.lastXAxisMarkerLabelText = texts.get(texts.size()-1);
            }

            //Check for clashes
            for (int i =0; i<this.xAxisMarkerLabelsPos.size()-1; i++)
            {
                Point pos1 = this.xAxisMarkerLabelsPos.get(i);
                Point pos2 = this.xAxisMarkerLabelsPos.get(i+1);
                String txt1 = texts.get(i);
                String txt2 = texts.get(i);
                Rect rect1 = new Rect();
                Rect rect2 = new Rect();

                this.paint.getTextBounds(txt1, 0, txt1.length(), rect1);
                this.paint.getTextBounds(txt2, 0, txt2.length(), rect2);

                //If the position of the right side of the left text (with padding) is greater than the the poisiton of the left side of the right text, there's collision
                int borderRight = pos1.x + this.MARKER_LABEL_PADDING;
                if (this.xAxisMarkerLabelRotate == 0)
                {
                    borderRight += rect1.width()/2;
                }
                else
                {
//                    borderRight += rect1.width();
              //      borderRight += rect1.width()-(int)GNumbers.round(rect1.width() * ((double)this.xAxisMarkerLabelRotate/90d), 0);
                }
                int borderLeft = pos2.x - rect2.width()/2;
                Log.d(TAG, "calculateXAxisMarkerLabelsPos: Comparing positions: " + borderRight + " vs " + borderLeft);
                if (borderRight > borderLeft)
                {
                    this.xAxisMarkerLabelsPos.clear();
                    Log.d(TAG, "calculateXAxisMarkerLabelsPos: Conflict found, recalculating...");
                    diff++;
                    Log.d(TAG, "calculateXAxisMarkerLabelsPos: new diff = " + diff);
                    continue loop;
                }
            }
            Log.d(TAG, "calculateXAxisMarkerLabelsPos: No more conflicts found.");
            break loop;
        }
        this.xAxisMarkerLabelDiff = diff;
        Log.d(TAG, "calculateXAxisMarkerLabelsPos: xAxisMarkerLabelDiff = " + this.xAxisMarkerLabelDiff);
    }

    /**
     * Calculates the position of the marker labels for the Y-Axis
     */
    protected void calculateYAxisMarkerLabelsPos()
    {
        this.yAxisMarkerLabelPos.clear();
        double highestVal = Methods.getHighestValue(this.yAxisValues);		//Get highest value
        this.yAxisMarkerLabelDiff = highestVal/(double)this.MAX_MARKERS_IN_Y_AXIS;			//Get unit increment

        for (double i = 0; i<this.yAxisMarkersPos.size(); i++)
        {
            String text = Double.toString(GNumbers.round(this.yAxisMarkerLabelDiff*(i+1), this.DECIMAL_PLACES));		//Rounded to X Decimal Place
            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);
            int textWidth = bounds.width();
            int x = this.yAxisMarkerXPos - this.AXES_POINTERS_LENGTH - this.MARKER_LABEL_PADDING - textWidth;
            int y = this.yAxisMarkersPos.get((int)i).y + bounds.height()/2;
            Point pos = new Point(x, y);
            Log.d(TAG, "calculateYAxisMarkerLabelsPos: " + text + " location is at " + pos.x + ", " + pos.y);
            this.yAxisMarkerLabelPos.add(pos);
        }
    }
    protected void calculateYAxisMarkersPos()
    {
        this.yAxisMarkersPos.clear();
        double yDiff = (double)this.axesLength.y/(double)this.MAX_MARKERS_IN_Y_AXIS;

        for (int i=1; i<=this.MAX_MARKERS_IN_Y_AXIS; i++)
        {
            int x = this.axesOrigin.x - this.AXES_POINTERS_LENGTH/2;
            int y = this.axesOrigin.y - (int)GNumbers.round((yDiff*(double)i), 0);

            this.yAxisMarkersPos.add(new Point(x, y));	//Take note of the coordinate position of each Y-Axis markers
        }
    }

    //Draw Sections
    protected abstract void drawDataPoints(Canvas canvas, Paint paint, int color, int width);
    protected void drawAxes()
    {
        int temp = this.paint.getColor();
        this.paint.setColor(ContextCompat.getColor(this.getContext(), R.color.colorBlack));

        //Draw X-Axis
        this.graph2DImage.drawLine(this.axesOrigin.x, this.axesOrigin.y, this.axesOrigin.x + this.axesLength.x, this.axesOrigin.y, this.paint);

        //Draw Y-Axis
        this.graph2DImage.drawLine(this.axesOrigin.x, this.axesOrigin.y, this.axesOrigin.x, this.axesOrigin.y - this.axesLength.y, this.paint);

        this.paint.setColor(temp);
    }
    protected void drawAxesMarkers()
    {
        this.paint.setColor(ContextCompat.getColor(this.getContext(), R.color.colorBlack));

        //Draw X-Axis Markers
        for (int i=0; i<this.dataPoints.size(); i+=this.xAxisMarkerLabelDiff)
        {
            this.graph2DImage.drawLine(this.dataPoints.get(i).x,
                    this.axesOrigin.y + this.AXES_POINTERS_LENGTH/2,
                    this.dataPoints.get(i).x,
                    this.axesOrigin.y - this.AXES_POINTERS_LENGTH/2,
                    paint);
        }

        //Draw Y-Axis Markers
        for (int i=0; i<this.yAxisMarkersPos.size(); i++)
        {
            int x = this.yAxisMarkersPos.get(i).x;
            int y = this.yAxisMarkersPos.get(i).y;
            this.graph2DImage.drawLine(x,
                    y,
                    this.axesOrigin.x + this.AXES_POINTERS_LENGTH/2,
                    y,
                    paint);
        }
    }
    protected void drawYAxisMarkerLabels()
    {
        int temp = this.paint.getColor();
        this.paint.setColor(ContextCompat.getColor(this.getContext(), R.color.colorGraphAxesMarkerLabels));
        for (int i = 0; i<this.yAxisMarkerLabelPos.size(); i++)
        {
            String text = Double.toString(GNumbers.round(this.yAxisMarkerLabelDiff*(i+1), this.DECIMAL_PLACES));		//Rounded to X Decimal Place

            this.graph2DImage.drawText(text,
                    this.yAxisMarkerLabelPos.get(i).x,
                    this.yAxisMarkerLabelPos.get(i).y,
                    this.paint);
        }
        this.paint.setColor(temp);
    }
    protected void drawXAxisMarkerLabels()
    {
        paint.setColor(ContextCompat.getColor(this.getContext(), R.color.colorGraphAxesMarkerLabels));

        for (int i = 0, j=0; i<this.dataPoints.size(); i+=this.xAxisMarkerLabelDiff, j++)
        {
            String text = this.xAxisLabels.get(i);
            int x = this.xAxisMarkerLabelsPos.get(j).x;
            int y = this.xAxisMarkerLabelsPos.get(j).y;
            this.graph2DImage.rotate(this.xAxisMarkerLabelRotate, x, y);
            this.graph2DImage.drawText(text, x, y, paint);
            this.graph2DImage.rotate(this.xAxisMarkerLabelRotate * -1, x, y);
        }
    }
    protected void drawDataValues()
    {
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

            this.graph2DImage.drawText(text, this.dataPoints.get(i).x - textWidth/2, this.dataPoints.get(i).y - this.GENERAL_PADDING, paint);
        }
    }
    protected void drawAxisNames()
    {
        this.paint.setColor(ContextCompat.getColor(this.getContext(), R.color.colorBlack));

        //Draw X-Axis name
        this.graph2DImage.drawText(this.xAxisName, this.xAxisNameLabelPos.x, this.xAxisNameLabelPos.y, this.paint);

        //Draw Y-Axis name
        int cx = this.yAxisNameLabelPos.x;
        int cy = this.yAxisNameLabelPos.y;
        this.graph2DImage.rotate(-90, cx, cy);
        this.graph2DImage.drawText(this.yAxisName, this.yAxisNameLabelPos.x, this.yAxisNameLabelPos.y, this.paint);
        this.graph2DImage.rotate(90, cx, cy);
    }
    protected void drawGraphLines()
    {
        paint.setColor(ContextCompat.getColor(this.getContext(), android.R.color.darker_gray));

        if (this.showGraphLinesOfY)		//Displaying Graph Lines of Y
        {
            for (Point marker : this.yAxisMarkersPos)
            {
                this.graph2DImage.drawLine(this.axesOrigin.x+this.STROKE_SIZE, 	//+ SROKE_SIZE so that it does not get drawn on the Y-Axis
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
                this.graph2DImage.drawLine(marker.x,
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
        Log.d(TAG, "onDraw: called");
        super.onDraw(canvas);

        //Draw graph background
        this.graph2DImage.drawRect(new Rect(0, 0, this.graphImage.getWidth(), this.graphImage.getHeight()), this.paint);

        this.drawAxes();
        try
        {
            this.drawAxesMarkers();
            this.drawXAxisMarkerLabels();
            this.drawYAxisMarkerLabels();
            this.drawGraphLines();
            this.drawAxisNames();

            if (this.displayDataPoint)
            {
                this.drawDataPoints(this.graph2DImage, this.paint, this.dataPointColor, 20);
            }

            if (this.enableDataValueMarkers)
            {
                this.drawDataValues();
            }
        }
        catch(ArithmeticException ex){}
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        Log.d(TAG, "onMeasure: called");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //Set the canvas size
        Log.d(TAG, "onMeasure: graph image sized to be " + widthMeasureSpec + " x " + heightMeasureSpec);
        this.graphImage = Bitmap.createBitmap(widthMeasureSpec + (this.axesPaddingWithPanelEdgeRight - this.axesPaddingWithPanelEdgeLeft), heightMeasureSpec, Bitmap.Config.ARGB_8888);
        this.graph2DImage = new Canvas(this.graphImage);
        Log.d(TAG, "onMeasure: Graph image dims: " + this.graphImage.getWidth() + " x " + this.graphImage.getHeight());

        //Measure padding
        this.calculateAxisNamesPos();
        this.calculateXAxisMarkerLabelsYPos();
        this.calculateXAxisMarkersYPos();
        this.calculateYAxisMarkerLabelsMaxLength();
        this.calculateYMarkerXPos();
        this.calculateOriginPos();
        this.calculateYAxisMarkersPos();
        try
        {
            this.generateDataPoints();
            this.calculateXAxisMarkerLabelsPos();
            this.calculateYAxisMarkerLabelsPos();

            //Calculate X-Axis marker labels rotation pos
            ///Just take into account the longest text
            Rect rect = new Rect();
            this.paint.getTextBounds(this.longestXAxisMarkerLabelText, 0, this.longestXAxisMarkerLabelText.length(), rect);
            this.xAxisMarkerLabelYPosExtra = (int)GNumbers.round(rect.width() * ((double)this.xAxisMarkerLabelRotate /90d), 0) + rect.height();
            //Check for conflict with X-Axis name Y-pos
            if (this.xAxisMarkerLabelYPos + this.xAxisMarkerLabelYPosExtra > this.xAxisNameLabelPos.y)
            {
                int diff = this.xAxisMarkerLabelYPos + this.xAxisMarkerLabelYPosExtra - this.xAxisNameLabelPos.y;
                this.xAxisMarkerLabelYPos -= diff;
                this.measure(widthMeasureSpec, heightMeasureSpec);
            }

            //Check if last visible data point surpasses the size of the graph
            this.paint.getTextBounds(this.lastXAxisMarkerLabelText, 0, this.lastXAxisMarkerLabelText.length(), rect);
            int endPoint = this.dataPoints.get(this.dataPoints.size()-1).x;
            if (this.xAxisMarkerLabelRotate == 0)
            {
                endPoint += rect.width()/2;
            }
            else
            {
                endPoint += rect.width() - (int)GNumbers.round(rect.width() * ((double)this.xAxisMarkerLabelRotate /90d), 0);
            }
            int border = this.graphImage.getWidth() - this.axesPaddingWithPanelEdgeLeft;
            Log.d(TAG, "onSizeChanged: endPoint " + endPoint + " vs border " + border);
            if (endPoint > border) {
                Log.d(TAG, "onSizeChanged: conflict found, recalculating...");
                int diff = endPoint - border;
                this.axesPaddingWithPanelEdgeRight += diff;
                this.measure(widthMeasureSpec + diff, heightMeasureSpec);
            }
        }
        catch(ArithmeticException ex){}

        this.setMeasuredDimension(this.graphImage.getWidth(), this.graphImage.getHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        Log.d(TAG, "onSizeChanged: called");
        super.onSizeChanged(w, h, oldw, oldh);
    }
}