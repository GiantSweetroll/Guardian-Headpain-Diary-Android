package com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.pain_location;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.FormElement;

public class PainLocationSelection extends FormElement
{
    //Fields
    private RadioButton radPresets, radCustom;
    private PainLocationCustomSelection custom;
    private PainLocationPresetSelection presets;

    //Constructor
    public PainLocationSelection()
    {
        super(true);
    }

    //Private Methods

    //Overridden Methods
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.view = inflater.inflate(R.layout.fragment_entry_log_painloc, container, false);

        //Initialization
        this.setScroll((ScrollView)this.view.findViewById(R.id.entryLogPainLocScroll));
        this.setFormTitleLabel((TextView)view.findViewById(R.id.entryLogPainLocLabel));
        this.setFormTitle(this.getString(R.string.entry_log_form_painloc_label));
        this.radPresets = (RadioButton)this.view.findViewById(R.id.entryLogPainLocRadPresets);
        this.radCustom = (RadioButton)this.view.findViewById(R.id.entryLogPainlocRadCustom);
        this.custom = (PainLocationCustomSelection)this.getFragmentManager().findFragmentById(R.id.entryLogPainlocCustomFragment);
        this.presets = (PainLocationPresetSelection) this.getFragmentManager().findFragmentById(R.id.entryLogPainLocPresetFragment);

        return this.view;
    }

    @Override
    public Object getData() {
        return null;
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public boolean allFilled() {
        return false;
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
}
