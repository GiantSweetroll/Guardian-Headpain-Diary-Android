package com.gardyanakbar.guardianheadpaindiary.ui.puzzle_image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.gardyanakbar.guardianheadpaindiary.R;

public class ImagePiece extends View implements View.OnTouchListener
{
    //Fields
    private Paint paint;
    private Rect rect;
    private String name;
    private boolean isColored;
    private Drawable image;

    //Constructors
    public ImagePiece(Context context, int imageDrawable, String name, boolean canColor)
    {
        super(context);
        this.image = ContextCompat.getDrawable(context, imageDrawable);
        this.paint = new Paint();
        this.rect = new Rect();
        this.name = name;
        this.isColored = false;

        //Properties
        this.image.setBounds(0, 0, this.image.getMinimumWidth(), this.image.getMinimumHeight());
        this.paint.setAntiAlias(true);
        if (canColor)
        {
            this.setOnTouchListener(this);
        }
    }

    //Setters and Getters
    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return this.name;
    }
    public void setColored(boolean b)
    {
        this.isColored = b;
    }
    public boolean isColored()
    {
        return this.isColored;
    }
    public void setImage(int drawableID)
    {
        this.image = ContextCompat.getDrawable(this.getContext(), drawableID);
    }
    public void color()
    {
        if (this.isEnabled())
        {
            this.setColored(!this.isColored());
        }
    }
    public Drawable getBackgroundImage()
    {
        return this.image;
    }

    //Overridden Methods
    @Override
    public void setEnabled(boolean b)
    {
        super.setEnabled(b);

        if (b)
        {
            this.setBackgroundColor(ContextCompat.getColor(this.getContext(), R.color.colorWhite));
        }
        else
        {
            this.setBackgroundColor(ContextCompat.getColor(this.getContext(), R.color.colorDisabledComponent));
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        this.image.draw(canvas);

        //Draw highlight
        if (this.isColored())
        {
            paint.setColor(ContextCompat.getColor(this.getContext(), R.color.colorCustomPainLocationHighlight));
            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        this.setColored(!this.isColored());
        this.invalidate();
        return false;
    }
}
