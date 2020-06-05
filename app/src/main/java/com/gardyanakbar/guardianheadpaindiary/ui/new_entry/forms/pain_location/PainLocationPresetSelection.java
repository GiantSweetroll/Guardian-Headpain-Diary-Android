package com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.pain_location;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.constants.Globals;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PainEntryData;
import com.gardyanakbar.guardianheadpaindiary.interfaces.GUIFunctions;
import com.gardyanakbar.guardianheadpaindiary.interfaces.LanguageListener;
import com.gardyanakbar.guardianheadpaindiary.methods.Methods;
import com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.FormElement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PainLocationPresetSelection extends FormElement implements GUIFunctions, LanguageListener
{
    //Fields
    private static final String TAG = "PainLocationPresetSelection";
    private List<Button> buttons;
    private List<String> selectedPos;
    private GridLayout grid;

    //Constructor
    public PainLocationPresetSelection()
    {
        super(false);
    }

    //Private Methods
    private void initButtons()
    {
        this.selectedPos = new ArrayList<>();
        try
        {
            this.buttons.clear();
        }
        catch(NullPointerException ex)
        {
            this.buttons = new ArrayList<>();
        }

        this.grid = this.view.findViewById(R.id.entryLogPainLocPresetGrid);
        int generalPad = (int)this.getResources().getDimension(R.dimen.general_padding);
        for(Map.Entry<String, LinkedHashMap<Integer, String>> entry : Methods.generatePainLocationsTextIDMap(this.getContext()).entrySet())
        {
            for (Map.Entry<Integer, String> subEntry : entry.getValue().entrySet())
            {
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.height = GridLayout.LayoutParams.WRAP_CONTENT;
                params.bottomMargin = generalPad;
                params.leftMargin = generalPad;
                params.rightMargin = generalPad;

                CardView card = new CardView(this.getContext());
                card.setLayoutParams(params);
                card.setCardElevation(0f);
                card.setBackground(ContextCompat.getDrawable(this.getContext(), R.drawable.preset_pain_location_unselected));

                CardView.LayoutParams buttonParams = new CardView.LayoutParams(
                        CardView.LayoutParams.MATCH_PARENT,
                        CardView.LayoutParams.WRAP_CONTENT);
                buttonParams.setMargins(generalPad,
                                        generalPad,
                                        generalPad,
                                        generalPad);
                buttonParams.gravity = Gravity.CENTER_HORIZONTAL;

                final Button button = new Button(this.getContext());
                button.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                button.setPadding(generalPad,
                        generalPad,
                        generalPad,
                        generalPad);
                button.setText(entry.getKey());
                button.setCompoundDrawablesWithIntrinsicBounds(0, subEntry.getKey(), 0,0);
                button.setTransitionName(subEntry.getValue());
                button.setBackground(ContextCompat.getDrawable(this.getContext(), R.drawable.preset_pain_location_unselected));
                button.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (isMarked(button))
                        {
                            setMarked(button, false);
                            Methods.deleteElement(selectedPos, button.getTransitionName(), false);
                        }
                        else
                        {
                            setMarked(button, true);
                            appendLocation(button.getTransitionName());
                        }
                    }
                });

                this.buttons.add(button);
                card.addView(button);
                this.grid.addView(card);
            }
        }
    }

    //Public Methods
    public void unmarkAllButtons()
    {
        for (Button button : this.buttons)
        {
            this.setMarked(button, false);
        }
    }
    private void setMarked(Button button, boolean mark)
    {
        if (mark)
        {
            button.setBackground(ContextCompat.getDrawable(this.getContext(), R.drawable.preset_pain_location_selected));
        }
        else
        {
            button.setBackground(ContextCompat.getDrawable(this.getContext(), R.drawable.preset_pain_location_unselected));
        }
    }
    public List<String> getSelectedPosition()
    {
        return this.selectedPos;
    }
    public boolean isMarked(Button button)
    {
//        //If it has border, that means it's selected
//        return button.getBackground() == ContextCompat.getDrawable(this.getContext(), R.drawable.rounded_button_border);
        return selectedPos.contains(button.getTransitionName());    //Check if button id is in list
    }
    public void enableButtons(boolean enable)
    {
        for (Button button : this.buttons)
        {
            button.setEnabled(enable);
//            for(Component component : button.getComponents())
//            {
//                component.setEnabled(enable);
//            }
        }
    }
    public void setSelected(String painLocationConstant)		//Parameter is String from PainLocationConstants
    {
        for (Button button : this.buttons)
        {
            if (button.getTransitionName().equals(painLocationConstant))
            {
                this.setMarked(button, true);
                this.appendLocation(painLocationConstant);
                break;
            }
        }
    }
    public void appendLocation(String painLocationConstant)		//Parameter is String from PainLocationConstants
    {
        if (!Methods.elementExists(this.selectedPos, painLocationConstant, false));
        {
            this.selectedPos.add(painLocationConstant);
        }
    }

    //Overridden Methods
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreateView: called");
        this.view = inflater.inflate(R.layout.fragment_entry_log_painloc_preset, container, false);

        //Initialization
        this.initButtons();
        if (!Globals.isNewEntry)
        {
            Log.d(TAG, "onCreateView: not a new entry, so preset location will be updated");
            this.setData(Globals.activeEntry);
        }

        return this.view;
    }

    @Override
    public List<String> getData()
    {
        return this.selectedPos;
    }

    @Override
    public void setData(Object obj)
    {
        if (obj instanceof PainEntryData)
        {
            Log.d(TAG, "setData: obj is an instance of PainEntryData");
            List<String> presets = ((PainEntryData)obj).getPresetPainLocations();
            Log.d(TAG, "setData: size of preets " + presets.size());
            for (String location : presets)
            {
                Log.d(TAG, "setData: pain location " + location);
                this.setSelected(location);
            }
        }
    }

    @Override
    public boolean allFilled()
    {
        return true;
    }

    @Deprecated
    @Override
    public void refresh() {}

    @Override
    public void resetDefaults()
    {
        super.resetDefaults();
        this.unmarkAllButtons();
    }

    @Override
    public void revalidateLanguage()
    {}

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: called");
    }
}
