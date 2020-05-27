package com.gardyanakbar.guardianheadpaindiary.ui.graph;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.interfaces.LanguageListener;
import com.gardyanakbar.guardianheadpaindiary.methods.Methods;

import java.util.ArrayList;
import java.util.List;

import giantsweetroll.date.Date;

public class GraphSettingsFragment extends Fragment implements LanguageListener
{
    //Fields
    private TextView labDateFrom, labDateFromValue, labDateTo, labDateToValue, labGraphCat;
    private Button btnDateFrom, btnDateTo, btnOk;
    private Date dateFrom, dateTo;
    private DatePickerDialog dateFromDialog, dateToDialog;
    private Spinner graphCatSpinner;
    private CheckBox checkDataVal, checkDataVoid, checkDataPoints;
    private Fragment parent;

    //Constructor
    public GraphSettingsFragment(Fragment parent)
    {
        super();
        this.parent = parent;
    }

    //Private Methods
    /**
     * Initializes the ArrayAdapter for the options of the graph category spinner.
     * @return an ArrayAdapter object with the various graph category options.
     */
    private ArrayAdapter<String> getGraphCatSpinnerAdapter()
    {
        List<String> list = new ArrayList<>();

        list.add(this.getView().getResources().getString(R.string.graph_settings_category_entries_vs_date));
        list.add(this.getView().getResources().getString(R.string.graph_settings_category_avg_intensity_vs_date));
        list.add(this.getView().getResources().getString(R.string.graph_settings_category_avg_duration_vs_date));
        list.add(this.getView().getResources().getString(R.string.graph_settings_category_avg_intensity_vs_month));
        list.add(this.getView().getResources().getString(R.string.graph_settings_category_intensity_vs_episode));
        list.add(this.getView().getResources().getString(R.string.graph_settings_category_duration_vs_episode));
        list.add(this.getView().getResources().getString(R.string.graph_settings_category_pain_kind_vs_amount));
        list.add(this.getView().getResources().getString(R.string.graph_settings_category_trigger_vs_amount));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), R.layout.support_simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        return adapter;
    }

    /**
     * Updates the date selection from the date picker
     * @param dialog - the DatePickerDialog object
     * @param date - the Date object to hold the new date information
     * @param dateLabel - the TextView to display the current selected date
     */
    private void updateDateSelection(DatePickerDialog dialog, Date date, TextView dateLabel)
    {
        date = Methods.getDateFromPicker(dialog.getDatePicker());
        dateLabel.setText(date.toString(Date.DAY, Date.MONTH, Date.YEAR, "-"));
    }

    //Public Methods

    /**
     * Get the initial date range
     * @return the initial date
     */
    public Date getDateFrom()
    {
        if (dateFrom == null)
        {
            return new Date();
        }
        else
        {
            return this.dateFrom;
        }
    }

    /**
     * Get the final date range
     * @return the final date
     */
    public Date getDateTo()
    {
        if (dateTo == null)
        {
            return new Date();
        }
        else
        {
            return this.dateTo;
        }
    }
    /**
     * Get the graph category.
     * @return the selected graph category string.
     */
    public String getGraphCategory()
    {
        if (this.graphCatSpinner != null)
        {
            return this.graphCatSpinner.getSelectedItem().toString();
        }
        else
        {
            return this.getString(R.string.graph_settings_category_entries_vs_date);
        }
    }
    /**
     * Check if the "Show Data Values" check box is selected
     * @return
     */
    public boolean isShowDataValuesSelected()
    {
        if (this.checkDataVal == null)
        {
            return false;
        }
        else
        {
            return this.checkDataVal.isSelected();
        }
    }
    /**
     * Check if the "Display Empty Data" check box is selected
     * @return
     */
    public boolean isDisplayEmptyDataSelected()
    {
        if (this.checkDataVoid == null)
        {
            return true;
        }
        else
        {
            return this.checkDataVoid.isSelected();
        }
    }
    /**
     * Check if the "SDisplay Data Points" check box is selected
     * @return
     */
    public boolean isDisplayDataPointsSelected()
    {
        if (this.checkDataPoints == null)
        {
            return false;
        }
        else
        {
            return this.checkDataPoints.isSelected();
        }
    }

    //Overridden Methods
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_graph_settings, container, false);

        return root;
    }

    @Override
    public void onViewCreated(View root, Bundle savedInstanceState)
    {
        super.onViewCreated(root, savedInstanceState);

        //Initialization
        this.labDateFrom = (TextView)root.findViewById(R.id.graphSettingsDateFromTextView);
        this.labDateFromValue = (TextView)root.findViewById(R.id.graphSettingsDateFromValueTextView);
        this.btnDateFrom = (Button)root.findViewById(R.id.graphSettingsDateFromChangeButton);
        this.labDateTo = (TextView)root.findViewById(R.id.graphSettingsDateToTextView);
        this.labDateToValue = (TextView)root.findViewById(R.id.graphSettingsDateToValueTextView);
        this.btnDateTo = (Button) root.findViewById(R.id.graphSettingsDateToChangeButton);
        this.labGraphCat = (TextView)root.findViewById(R.id.graphSettingsGraphCatTextView);
        this.graphCatSpinner = (Spinner) root.findViewById(R.id.graphSettingsGraphCatSpinner);
        this.checkDataVal = (CheckBox)root.findViewById(R.id.graphSettingsDataValuesCB);
        this.checkDataVoid = (CheckBox)root.findViewById(R.id.graphSettingsDataVoidCB);
        this.checkDataPoints = (CheckBox)root.findViewById(R.id.graphSettingsDataPointsCB);
        this.dateFromDialog = new DatePickerDialog(this.getContext());
        this.dateToDialog = new DatePickerDialog(this.getContext());
        this.btnOk = (Button)root.findViewById(R.id.graphSettingsBtnOk);

        //Properties
        updateDateSelection(dateFromDialog, dateFrom, labDateFromValue);
        updateDateSelection(dateToDialog, dateTo, labDateToValue);
        this.graphCatSpinner.setAdapter(this.getGraphCatSpinnerAdapter());
        this.graphCatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //TODO: Change graph data
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){}
        });
        this.btnDateFrom.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dateFromDialog.show();
            }
        });
        this.btnDateTo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dateToDialog.show();
            }
        });
        this.dateFromDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                //TODO: Update Graph data with new date range
                updateDateSelection(dateFromDialog, dateFrom, labDateFromValue);
            }
        });
        this.dateToDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                //TODO: Update Graph data with new date range
                updateDateSelection(dateToDialog, dateTo, labDateToValue);
            }
        });
        this.btnOk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction transaction = parent.getFragmentManager().beginTransaction();
                transaction.replace(R.id.graphFragmentContainer, GraphFragment.graphPanel);
                transaction.commit();
            }
        });
    }

    @Override
    public void revalidateLanguage()
    {

    }
}
