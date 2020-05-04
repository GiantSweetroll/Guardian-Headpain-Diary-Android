package com.gardyanakbar.guardianheadpaindiary.ui.history;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.History;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PatientData;
import com.gardyanakbar.guardianheadpaindiary.interfaces.GUIFunctions;
import com.gardyanakbar.guardianheadpaindiary.interfaces.LanguageListener;
import com.gardyanakbar.guardianheadpaindiary.methods.Methods;

public class HistoryPanel implements GUIFunctions, LanguageListener, AdapterView.OnItemSelectedListener, View.OnTouchListener
{
    //Fields
    private HistorySpinner historySpinner;
    private TextView tfHistory;
    private History history;
    private String historyKey;
    private Context context;

    //Constructor
    public HistoryPanel(Context context, Spinner spinner, TextView tv, History history, String patientDataConstant, String[] defaultOptions, boolean sorted, boolean haveNone)		//patientDataConstant are constants from PatientData class used for the recent selected options
    {
        //Initialization
        this.historySpinner = new HistorySpinner(spinner, history, defaultOptions, sorted, haveNone, context);
        this.tfHistory = tv;
        this.historyKey = patientDataConstant;
        this.history = history;
        this.context = context;

        //Properties
        this.historySpinner.getSpinner().setOnItemSelectedListener(this);
        this.tfHistory.setOnTouchListener(this );
    }

    //Public Methods
    public void setActiveItem(String item)
    {
        if (this.history.itemExists(item))
        {
            this.historySpinner.setSelectedItem(item);
        }
        else
        {
            this.historySpinner.setSelectedToOther();
            this.tfHistory.setText(item);
        }
    }
    public String getItem()
    {
        if (this.historySpinner.otherOptionSelected())
        {
            return Methods.getTextData(this.tfHistory);
        }
        else if (this.historySpinner.noneOptionSelected())
        {
            return "";
        }
        else
        {
            return this.historySpinner.getSelectedItem();
        }
    }
    public void refresh(History history, PatientData patient)
    {
        history.refresh(patient);
        this.historySpinner.setHistory(history);
        this.historySpinner.refresh();
        try
        {
            this.historySpinner.setDefaultSelection(patient.getRecentSelectedOption(this.historyKey));
        }
        catch(NullPointerException ex) {}
    }
    public boolean hasHistory()
    {
        return this.historySpinner.getItemCount()!=0;
    }
    public void revalidateLanguage(String[] options)
    {
        this.historySpinner.revalidateLanguage(options);
    }
    public int getSelectedIndex()
    {
        return this.historySpinner.getSelectedIndex();
    }
    public void setSelectedIndex(int i)
    {
        this.historySpinner.setSelectedIndex(i);
    }

    //Overridden Methods
    @Override
    public void resetDefaults()
    {
        this.historySpinner.resetDefaults();
    }

    @Override
    public void refresh()
    {
        this.historySpinner.refresh();
    }

    @Override
    public void revalidateLanguage()
    {
        this.refresh();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        final String selection = parent.getItemAtPosition(position).toString();

        if (!selection.equals(this.context.getString(R.string.other_text)))
        {
            this.tfHistory.setText("");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        this.historySpinner.setSelectedToOther();

        return false;
    }
}
