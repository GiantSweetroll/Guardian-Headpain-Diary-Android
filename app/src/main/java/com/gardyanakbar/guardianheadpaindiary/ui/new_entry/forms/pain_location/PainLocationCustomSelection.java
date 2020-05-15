package com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.pain_location;

import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.interfaces.GUIFunctions;
import com.gardyanakbar.guardianheadpaindiary.interfaces.LanguageListener;
import com.gardyanakbar.guardianheadpaindiary.methods.Methods;
import com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.FormElement;
import com.gardyanakbar.guardianheadpaindiary.ui.puzzle_image.ImagePiece;
import com.gardyanakbar.guardianheadpaindiary.ui.puzzle_image.ImagePuzzle;

import java.util.ArrayList;
import java.util.List;

public class PainLocationCustomSelection extends FormElement implements GUIFunctions, LanguageListener
{

    //Fields
    private ImagePuzzle back, front, right, left;
    private List<List<ImagePiece>> imagePuzzles;
    private GridLayout grid;
    private Button bReset;

    //Constructor
    public PainLocationCustomSelection()
    {
        super(false);
    }

    //Private Methods
    private void initLocations()
    {
        //Initialization
        this.grid = this.view.findViewById(R.id.entryLogPainLocCustomGrid);
        int generalPad = (int)this.getResources().getDimension(R.dimen.general_padding);
        this.imagePuzzles = new ArrayList<>();

        //Add to list
        this.imagePuzzles.add(Methods.getPainLocationFront(this.getContext()));
        this.imagePuzzles.add(Methods.getPainLocationBack(this.getContext()));
        this.imagePuzzles.add(Methods.getPainLocationLeft(this.getContext()));
        this.imagePuzzles.add(Methods.getPainLocationRight(this.getContext()));

        //Add to view
        for(List<ImagePiece> puzzle : this.imagePuzzles)
        {
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;

            CardView card = new CardView(this.getContext());
            card.setLayoutParams(params);
//            card.setCardElevation(0f);

            CardView.LayoutParams puzzleParams = new CardView.LayoutParams(
                    CardView.LayoutParams.WRAP_CONTENT,
                    CardView.LayoutParams.WRAP_CONTENT);
            puzzleParams.gravity = Gravity.CENTER_HORIZONTAL;

            GridLayout subGrid = new GridLayout(this.getContext());
            subGrid.setLayoutParams(puzzleParams);
            subGrid.setColumnCount(4);
            subGrid.setRowCount(4);

            for (ImagePiece piece : puzzle)
            {
                GridLayout.LayoutParams subGridParams = new GridLayout.LayoutParams();
                subGridParams.height = GridLayout.LayoutParams.WRAP_CONTENT;

                CardView subGridCard = new CardView(this.getContext());
                subGridCard.setLayoutParams(subGridParams);
//                subGridCard.setCardElevation(0f);

                CardView.LayoutParams subGridPuzzleParams = new CardView.LayoutParams(
                        CardView.LayoutParams.WRAP_CONTENT,
                        CardView.LayoutParams.WRAP_CONTENT);
                subGridPuzzleParams.width = piece.getBackgroundImage().getMinimumWidth();
                subGridPuzzleParams.height = piece.getBackgroundImage().getMinimumHeight();
                subGridPuzzleParams.gravity = Gravity.CENTER_HORIZONTAL;

                piece.setLayoutParams(subGridPuzzleParams);

                subGridCard.addView(piece);
                subGrid.addView(subGridCard);
            }

            card.addView(subGrid);
            this.grid.addView(card);
        }
    }

    //Overridden Methods
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.view = inflater.inflate(R.layout.fragment_entry_log_painloc_custom, container, false);

        //Initialization
        this.bReset = (Button)this.view.findViewById(R.id.entryLogPainLocCustomButtonReset);
        this.initLocations();

        return this.view;
    }

    @Override
    public Object getData()
    {
        return null;
    }

    @Override
    public void setData(Object data)
    {

    }

    @Override
    public boolean allFilled()
    {
        return false;
    }

    @Override
    public void refresh()
    {

    }

    @Override
    public void revalidateLanguage()
    {
        this.bReset.setText(R.string.reset_text);
    }
}
