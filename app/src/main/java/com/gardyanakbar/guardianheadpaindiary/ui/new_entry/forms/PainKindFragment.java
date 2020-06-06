package com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.constants.Constants;
import com.gardyanakbar.guardianheadpaindiary.constants.Globals;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PainEntryData;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PatientData;
import com.gardyanakbar.guardianheadpaindiary.interfaces.HistoryListener;
import com.gardyanakbar.guardianheadpaindiary.methods.Methods;
import com.gardyanakbar.guardianheadpaindiary.ui.history.HistoryPanel;

public class PainKindFragment extends FormElement implements HistoryListener
{
    //Fields
    private static final String TAG = "PainKindFragment";
    private HistoryPanel painKind;

    //Constructor
    public PainKindFragment() {super(false);}

    //Public Methods
    public void setPainKind(String painKind)
    {
        this.painKind.setActiveItem(painKind);
    }

    //Overridden Methods
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.view = inflater.inflate(R.layout.fragment_entry_log_painkind, container, false);

        //Initialization
        this.setName(this.getString(R.string.entry_log_map_button_pain_kind));
        this.setScroll((ScrollView)this.view.findViewById(R.id.entryLogPainKindScroll));
        this.setFormTitleLabel((TextView)view.findViewById(R.id.entryLogPainKindLabel));
        View layout = this.view.findViewById(R.id.entryLogPainKindHistoryPanel);
        this.setName(this.getString(R.string.entry_log_map_button_pain_kind));
        this.painKind = new HistoryPanel(this.getContext(),
                                            (Spinner)layout.findViewById(R.id.historySpinner),
                                            (EditText) layout.findViewById(R.id.historyEditText),
                                            Globals.HISTORY_PAIN_KIND,
                                            PatientData.LAST_PAIN_KIND,
                                            Methods.getDefaultPainKinds(this.getContext()),
                                        true,
                                        false);

        //Properties
        this.getFormTitleLabel().setTextSize(Constants.FONT_SUB_TITLE_SIZE);
        if (!Globals.isNewEntry)
        {
            this.setData(Globals.activeEntry);
        }
        else
        {
            //Set the selection to the previous selection if it is present
            Log.d(TAG, "onCreateView: Last pain kind was " + Globals.activePatient.getLastPainKind());
            this.setPainKind(Globals.activePatient.getLastPainKind());
        }

        return this.view;
    }

    @Override
    public String getData()
    {
        return this.painKind.getItem();
    }

    @Override
    public void setData(Object obj)
    {
        if (obj instanceof PainEntryData)
        {
            String painKind = ((PainEntryData)obj).getPainKind();
            Log.d(TAG, "setData: Not new entry, setting up pain kind as " + painKind);
            this.setPainKind(painKind);
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
        this.painKind.resetDefaults();
    }

    @Override
    public void revalidateLanguage()
    {
        this.setFormTitle(this.getString(R.string.entry_log_form_painkind_label));
    }

    @Override
    public void refreshHistory(PatientData patient)
    {
        try
        {
            Log.d(TAG, "refreshHistory: history refreshed");
            this.painKind.refresh(Globals.HISTORY_PAIN_KIND, patient);
            this.painKind.setActiveItem(patient.getLastPainKind());
        }
        catch(NullPointerException ex) {}
    }
}
