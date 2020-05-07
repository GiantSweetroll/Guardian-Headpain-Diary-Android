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
import com.gardyanakbar.guardianheadpaindiary.constants.PainDataIdentifier;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PatientData;
import com.gardyanakbar.guardianheadpaindiary.interfaces.HistoryListener;
import com.gardyanakbar.guardianheadpaindiary.methods.Methods;
import com.gardyanakbar.guardianheadpaindiary.ui.history.HistoryPanel;

public class RecentMedicationFragment extends FormElement implements HistoryListener
{
    //Fields
    private HistoryPanel recentMedication;
    private HistoryPanel medicineComplaint;

    //Constructor
    public RecentMedicationFragment() {super(false);}

    //Public Methods
    public String getRecentMedication()
    {
        if (this.recentMedication.getSelectedIndex() == 0 || this.recentMedication.getItem().equals(""))
        {
            return "";
        }
        else
        {
            return this.recentMedication.getItem();
        }
    }
    public String getMedicineComplaint()
    {
        if (this.medicineComplaint.getSelectedIndex() == 0 || this.medicineComplaint.getItem().equals(""))
        {
//			return PainDataIdentifier.NONE;
            return "";
        }
        else
        {
            return this.medicineComplaint.getItem();
        }
    }
    public void setRecentMedication(String item)
    {
        if (item.equals(PainDataIdentifier.NONE) || item.equals(""))
        {
            this.recentMedication.setSelectedIndex(0);
        }
        else
        {
            this.recentMedication.setActiveItem(item);
        }
    }
    public void setMedicineComplaint(String item)
    {
        if (item.equals(PainDataIdentifier.NONE) || item.equals(""))
        {
            this.medicineComplaint.setSelectedIndex(0);
        }
        else
        {
            this.medicineComplaint.setActiveItem(item);
        }
    }
    public void setData(String recentMeds, String medComplaint)
    {
        this.setRecentMedication(recentMeds);
        this.setMedicineComplaint(medComplaint);
    }

    //Overridden Methods
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.view = inflater.inflate(R.layout.fragment_entry_log_recent_medication, container, false);

        //Initialization
        this.setScroll((ScrollView)this.view.findViewById(R.id.entryLogRecentMedicationScroll));
        this.setFormTitleLabel((TextView)view.findViewById(R.id.entryLogRecentMedicationLabel));
        View layout = this.view.findViewById(R.id.entryLogRecentMedicationPanel);
        this.recentMedication = new HistoryPanel(this.getContext(),
                                                    (Spinner)layout.findViewById(R.id.historySpinner),
                                                    (EditText) layout.findViewById(R.id.historyEditText),
                                                    Globals.HISTORY_RECENT_MEDICATION,
                                                    PatientData.LAST_RECENT_MEDS,
                                                    Methods.getDefaultPainKinds(this.getContext()),
                                                    true,
                                                    false);
        layout = this.view.findViewById(R.id.entryLogSideEffectsPanel);
        this.medicineComplaint = new HistoryPanel(this.getContext(),
                                            (Spinner)layout.findViewById(R.id.historySpinner),
                                            (EditText) layout.findViewById(R.id.historyEditText),
                                            Globals.HISTORY_MEDICINE_COMPLAINT,
                                            PatientData.LAST_MEDICINE_COMPLAINT,
                                            Methods.getDefaultPainKinds(this.getContext()),
                                            true,
                                            false);

        return this.view;
    }

    @Deprecated
    @Override
    public String getData()
    {
        return null;
    }

    @Deprecated
    @Override
    public void setData(Object obj)
    {}

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
        this.recentMedication.resetDefaults();
        this.medicineComplaint.resetDefaults();
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
            this.recentMedication.refresh(Globals.HISTORY_RECENT_MEDICATION, patient);
//			this.recentMedication.setActiveItem(patient.getLastRecentMeds());
            this.medicineComplaint.refresh(Globals.HISTORY_MEDICINE_COMPLAINT, patient);
//			this.medicineComplaint.setActiveItem(patient.getLastMedicineComplaint());
            this.setData(patient.getLastRecentMeds(), patient.getLastMedicineComplaint());
        }
        catch(NullPointerException ex) {}
    }
}
