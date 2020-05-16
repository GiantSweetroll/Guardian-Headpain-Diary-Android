package com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.constants.Globals;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PainEntryData;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PatientData;
import com.gardyanakbar.guardianheadpaindiary.interfaces.HistoryListener;
import com.gardyanakbar.guardianheadpaindiary.methods.Methods;
import com.gardyanakbar.guardianheadpaindiary.ui.history.HistoryPanel;

public class TriggerFragment extends FormElement implements HistoryListener
{
    //Fields
    private HistoryPanel trigger;

    //Constructor
    public TriggerFragment()
    {
        super(true);
    }

    //Overridden Methods
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.view = inflater.inflate(R.layout.fragment_entry_log_trigger, container, false);

        //Initialization
        this.setScroll((ScrollView)this.view.findViewById(R.id.entryLogTriggerScroll));
        this.setFormTitleLabel((TextView)view.findViewById(R.id.entryLogTriggerLabel));
        this.setFormTitle(this.getString(R.string.entry_log_form_trigger_label));
        View layout = this.view.findViewById(R.id.entryLogTriggerHistoryPanel);
        this.trigger = new HistoryPanel(this.getContext(),
                                            (Spinner)layout.findViewById(R.id.historySpinner),
                                            (EditText) layout.findViewById(R.id.historyEditText),
                                            Globals.HISTORY_TRIGGER,
                                            PatientData.LAST_TRIGGER,
                                            Methods.getDefaultTrigger(this.getContext()),
                                            true,
                                            false);

        return this.view;
    }

    @Override
    public void resetDefaults()
    {
        super.resetDefaults();
        this.trigger.resetDefaults();
    }

    @Override
    public String getData()
    {
        return this.trigger.getItem();
    }

    @Override
    public void setData(Object entry)
    {
        if (entry instanceof PainEntryData)
        {
            String trigger = ((PainEntryData)entry).getTrigger();
            this.trigger.setActiveItem(trigger);
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
    public void revalidateLanguage() {
        this.setFormTitle(this.getString(R.string.entry_log_form_date_time_title));
    }

    @Override
    public void refreshHistory(PatientData patient)
    {
        try
        {
            this.trigger.refresh(Globals.HISTORY_TRIGGER, patient);
            this.trigger.setActiveItem(patient.getLastTrigger());
        }
        catch(NullPointerException ex) {}
    }
}
