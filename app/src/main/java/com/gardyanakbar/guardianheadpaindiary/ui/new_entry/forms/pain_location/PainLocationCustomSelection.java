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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PainLocationCustomSelection extends FormElement implements GUIFunctions, LanguageListener
{

    //Fields
    private Map<String, ImagePiece> imagePuzzles;
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
        this.imagePuzzles = new HashMap<>();
        List<ImagePiece> list = Methods.getCustomPainLocationList(this.getContext());

        //Properties
        this.grid.setColumnCount(8);
        this.grid.setRowCount(8);

        //Add to view
        for (ImagePiece piece : list)
        {
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = piece.getBackgroundImage().getMinimumWidth();
            params.height = piece.getBackgroundImage().getMinimumHeight();
            params.setGravity(Gravity.CENTER_HORIZONTAL);

            piece.setLayoutParams(params);


            this.imagePuzzles.put(piece.getName(), piece);  //Add to map
            this.grid.addView(piece);
        }

        /*
        //Add to list
        List<List<ImagePiece>> list = new ArrayList<>();
        list.add(Methods.getPainLocationFront(this.getContext()));
        list.add(Methods.getPainLocationBack(this.getContext()));
        list.add(Methods.getPainLocationLeft(this.getContext()));
        list.add(Methods.getPainLocationRight(this.getContext()));

        //Add to view
        for(List<ImagePiece> puzzle : list)
        {
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.setGravity(Gravity.CENTER);

            GridLayout subGrid = new GridLayout(this.getContext());
            subGrid.setLayoutParams(params);
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

                this.imagePuzzles.put(piece.getName(), piece);  //Add to map
            }

            this.grid.addView(subGrid);
        }
        */
    }

    //Public Methods
    /**
     * Returns a list of selected location names (by checking which buttons are highlighted)
     * @return a List of the selected location names
     */
    public List<String> getLocations()
    {
        List<String> list = new ArrayList<String>();

        for (Map.Entry entry : this.imagePuzzles.entrySet())
        {
            if (((ImagePiece)entry.getValue()).isColored())
            {
                list.add(entry.getKey().toString());
            }
        }

        return list;
    }

    /**
     * Highlighted the selected pain locations
     * @param locations - a list of the selected locations String
     */
    public void setLocations(List<String> locations)
    {
        for (String location : locations)
        {
            this.imagePuzzles.get(location).setColored(true);
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

        //Properties
        this.bReset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                for (Map.Entry<String, ImagePiece> entrySet : imagePuzzles.entrySet())
                {
                    entrySet.getValue().setColored(false);
                }
            }
        });

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
