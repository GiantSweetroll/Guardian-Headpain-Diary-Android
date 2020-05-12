package com.gardyanakbar.guardianheadpaindiary.ui.puzzle_image;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.gardyanakbar.guardianheadpaindiary.R;

import java.util.List;
import java.util.Set;

public class ImagePuzzle extends Fragment
{
    //Fields
    private View view;
    private List<ImagePiece> images;
    private Set<String> selectedImages;
    private GridLayout grid;
    private Point rc;

    //Constructor
    public ImagePuzzle(List<ImagePiece> images, int row, int col)
    {
        super();
        this.images = images;
        this.rc = new Point(row, col);
    }

    //Public Methods
    public Set<String> getSelectedImageNames()
    {
        for (ImagePiece piece : this.images)
        {
            if (piece.isColored())
            {
                this.selectedImages.add(piece.getName());
            }
        }

        return this.selectedImages;
    }
    public void resetSelection()
    {
        this.selectedImages.clear();
        for(ImagePiece piece : this.images)
        {
            piece.setColored(false);
        }
    }
    public void setSelection(Set<String> selections)
    {
        this.selectedImages = selections;
        for (String name : this.selectedImages)
        {
            for (ImagePiece piece : this.images)
            {
                if (piece.getName().equals(name))
                {
                    piece.setColored(true);
                    break;
                }
            }
        }
    }

    public void setEnabled(boolean b)
    {
        for (ImagePiece image : this.images)
        {
            image.setEnabled(b);
        }
    }

    //Private Methods
    private void init()
    {
        this.grid = this.view.findViewById(R.id.imagePuzzleGrid);
        this.grid.setRowCount(this.rc.x);
        this.grid.setColumnCount(this.rc.y);
        int generalPad = (int)this.getResources().getDimension(R.dimen.general_padding);
        for(ImagePiece image : this.images)
        {
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.bottomMargin = generalPad;
            params.leftMargin = generalPad;
            params.rightMargin = generalPad;

            CardView card = new CardView(this.getContext());
            card.setLayoutParams(params);
            card.setCardElevation(0f);

            CardView.LayoutParams imageParams = new CardView.LayoutParams(
                    CardView.LayoutParams.MATCH_PARENT,
                    CardView.LayoutParams.WRAP_CONTENT);
            imageParams.setMargins(generalPad,
                    generalPad,
                    generalPad,
                    generalPad);
            imageParams.gravity = Gravity.CENTER_HORIZONTAL;

            image.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            card.addView(image);
            this.grid.addView(card);
        }
    }

    //Overridden Methods
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.view = inflater.inflate(R.layout.fragment_image_puzzle, container, false);

        //Initialization
        this.init();

        return this.view;
    }
}
