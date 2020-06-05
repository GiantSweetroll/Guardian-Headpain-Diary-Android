package com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.pain_location;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.constants.Constants;
import com.gardyanakbar.guardianheadpaindiary.constants.Globals;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PainEntryData;
import com.gardyanakbar.guardianheadpaindiary.methods.Methods;
import com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.FormElement;

import java.util.ArrayList;
import java.util.List;

public class PainLocationSelection extends FormElement
{
    //Fields
    private static final String TAG = "PainLocationSelection";
    private RadioButton radPresets, radCustom;
    private PainLocationCustomSelection custom;
    private PainLocationPresetSelection presets;

    //Constructor
    public PainLocationSelection()
    {
        super(true);
    }

    //Public Methods
    public List<String> getSelectedPositions()
    {
        if (this.radPresets.isChecked())
        {
            List<String> list = this.presets.getSelectedPosition();
            Methods.removeDuplicatesFromStringList(list);
            return list;
        }
        else
        {
            List<String> list = new ArrayList<String>();
            for (String loc : this.custom.getLocations())
            {
                list.add(loc);
            }
            Methods.removeDuplicatesFromStringList(list);
            return list;
        }
    }
    public boolean presetLocationSelected()
    {
        return this.radPresets.isSelected();
    }
    public boolean customLocationSelected()
    {
        return this.radCustom.isSelected();
    }

    public void setSelectedPosition(PainEntryData entry)
    {
        List<String> presetLocations = entry.getPresetPainLocations();
//        List<String> customLocations = entry.getCustomPainLocations();

        if (presetLocations.size() == 0)
        {
            this.radCustom.setChecked(true);
//            List<String> list = new ArrayList<String>();
//            for (String location : customLocations)
//            {
//                try
//                {
//                    list.add(location);
//                }
//                catch(Exception ex) {}
//            }
//            this.custom.setLocations(list);
        }
        else
        {
            this.radPresets.setChecked(true);
//            for (String location : presetLocations)
//            {
//                this.presets.setSelected(location);
//            }
        }
    }
    public boolean isPainLocationSelected()
    {
        return this.getSelectedPositions().size() > 0;
    }

    //Overridden Methods
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreateView: called");
        this.view = inflater.inflate(R.layout.fragment_entry_log_painloc, container, false);

        //Initialization
        this.setScroll((ScrollView)this.view.findViewById(R.id.entryLogPainLocScroll));
        this.setFormTitleLabel((TextView)view.findViewById(R.id.entryLogPainLocLabel));
        this.setFormTitle(this.getString(R.string.entry_log_form_painloc_label));
        this.radPresets = (RadioButton)this.view.findViewById(R.id.entryLogPainLocRadPresets);
        this.radCustom = (RadioButton)this.view.findViewById(R.id.entryLogPainlocRadCustom);
        this.custom = (PainLocationCustomSelection)this.getChildFragmentManager().findFragmentById(R.id.entryLogPainlocCustomFragment);
        this.presets = (PainLocationPresetSelection) this.getChildFragmentManager().findFragmentById(R.id.entryLogPainLocPresetFragment);
        this.setName(this.getString(R.string.entry_log_map_button_pain_location_text));

        //Properties
        this.getFormTitleLabel().setTextSize(Constants.FONT_SUB_TITLE_SIZE);
        this.radPresets.setTextSize(Constants.FONT_HEADER_SIZER);
        this.radCustom.setTextSize(Constants.FONT_HEADER_SIZER);
        this.radPresets.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                radCustom.setChecked(!isChecked);
            }
        });
        this.radCustom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                radPresets.setChecked(!isChecked);
            }
        });

        return this.view;
    }

    @Deprecated
    @Override
    public Object getData() {
        return null;
    }

    @Deprecated
    @Override
    public void setData(Object data) {}

    @Override
    public boolean allFilled()
    {
        List<String> list = this.getSelectedPositions();
        return list.size()!=0;
    }

    @Override
    public void refresh() {

    }

    @Override
    public void revalidateLanguage()
    {
        this.radCustom.setText(R.string.entry_log_form_painloc_custom_text);
        this.radPresets.setText(R.string.entry_log_form_painloc_presets_text);
        this.custom.revalidateLanguage();
        this.presets.revalidateLanguage();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!Globals.isNewEntry)
        {
            Log.d(TAG, "onResume: Selecting locations...");
            this.setSelectedPosition(Globals.activeEntry);
        }
        Log.d(TAG, "onResume: called");
    }
}
