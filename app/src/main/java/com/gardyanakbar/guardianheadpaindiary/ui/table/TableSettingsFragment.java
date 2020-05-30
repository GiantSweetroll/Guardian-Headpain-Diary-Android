package com.gardyanakbar.guardianheadpaindiary.ui.table;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.constants.Globals;
import com.gardyanakbar.guardianheadpaindiary.methods.Methods;

import java.util.ArrayList;
import java.util.List;

import giantsweetroll.date.Date;

public class TableSettingsFragment extends Fragment
{
    //Fields
    private static final String TAG = "TableSettingsFragment";
    private TextView labDateFrom, labDateFromValue, labDateTo, labDateToValue;
    private Date dateFrom, dateTo;
    private DatePickerDialog dateFromDialog, dateToDialog;
    private Button btnDateFrom, btnDateTo, btnOk;
    private Spinner filterCatSpinner;
    private EditText tfKeyword;

    //Private Methods
    /**
     * Initializes the ArrayAdapter for the options of the table filter category spinner.
     * @return an ArrayAdapter object with the various filter category options.
     */
    private ArrayAdapter<String> getTableFilterCatSpinnerAdapter()
    {
        List<String> list = new ArrayList<>();

        list.add(this.getView().getResources().getString(R.string.table_settings_filter_pain_kind));
        list.add(this.getView().getResources().getString(R.string.table_settings_filter_intensity));
        list.add(this.getView().getResources().getString(R.string.table_settings_filter_duration));
        list.add(this.getView().getResources().getString(R.string.table_settings_filter_trigger));
        list.add(this.getView().getResources().getString(R.string.table_settings_filter_comments));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), R.layout.support_simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        return adapter;
    }

    /**
     * Updates the date value label
     * @param date - the Date object to hold the new date information
     * @param dateLabel - the TextView to display the current selected date
     */
    private void updateDateSelection(Date date, TextView dateLabel)
    {
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
    public String getFilterCategory()
    {
        if (this.filterCatSpinner != null)
        {
            return this.filterCatSpinner.getSelectedItem().toString();
        }
        else
        {
            Log.d(TAG, "getFilterCategory: filterCatSpinner is null");
            return "";
        }
    }

    /**
     * Get the graph category index from the spinner.
     * @return the selected graph category index.
     */
    public int getFilterCategoryIndex()
    {
        if (this.filterCatSpinner != null)
        {
            return this.filterCatSpinner.getSelectedItemPosition();
        }
        else
        {
            Log.d(TAG, "getFilterCategory: filterCatSpinner is null");
            return 0;
        }
    }
    /**
     * Gets the filter keyword.
     * @return the filter keyword
     */
    public String getFilterKeyword()
    {
        if (this.tfKeyword != null)
        {
            return this.tfKeyword.getText().toString().trim();
        }
        else
        {
            return "";
        }
    }

    //Overridden Methods
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_table_settings, container, false);

        return root;
    }

    @Override
    public void onViewCreated(View root, Bundle savedInstanceState)
    {
        super.onViewCreated(root, savedInstanceState);

        //Initialization
        this.labDateFrom = (TextView)root.findViewById(R.id.tableSettingsDateFromTextView);
        this.labDateFromValue = (TextView)root.findViewById(R.id.tableSettingsDateFromValueTextView);
        this.btnDateFrom = (Button)root.findViewById(R.id.tableSettingsDateFromChangeButton);
        this.labDateTo = (TextView)root.findViewById(R.id.tableSettingsDateToTextView);
        this.labDateToValue = (TextView)root.findViewById(R.id.tableSettingsDateToValueTextView);
        this.btnDateTo = (Button) root.findViewById(R.id.tableSettingsDateToChangeButton);
        this.filterCatSpinner = (Spinner) root.findViewById(R.id.tableFilterCategorySpinner);
        this.dateFromDialog = new DatePickerDialog(this.getContext());
        this.dateToDialog = new DatePickerDialog(this.getContext());
        this.btnOk = (Button)root.findViewById(R.id.tableSettingsBtnOk);
        this.tfKeyword = (EditText)root.findViewById(R.id.tableFilterKeywordEditText);

        //Properties
        this.dateFrom = Globals.tableSettings.getDateFrom();
        this.dateTo = Globals.tableSettings.getDateTo();
        Methods.setDateOnPicker(this.dateFromDialog.getDatePicker(), this.dateFrom);
        Methods.setDateOnPicker(this.dateToDialog.getDatePicker(), this.dateTo);
        updateDateSelection(this.dateFrom, this.labDateFromValue);
        updateDateSelection(this.dateTo, this.labDateToValue);
        this.filterCatSpinner.setAdapter(this.getTableFilterCatSpinnerAdapter());
        this.filterCatSpinner.setSelection(Globals.tableSettings.getFilterCategoryIndex());
        this.tfKeyword.setText(Globals.tableSettings.getFilterKeyword());
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
                Methods.updateDateFromPicker(view, dateFrom);
                updateDateSelection(dateFrom, labDateFromValue);
            }
        });
        this.dateToDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                Methods.updateDateFromPicker(view, dateTo);
                updateDateSelection(dateTo, labDateToValue);
            }
        });
        this.btnOk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getFragmentManager().popBackStackImmediate();
            }
        });
    }

    @Override
    public void onPause()
    {
        Globals.tableSettings.setDateFrom(this.dateFrom);
        Globals.tableSettings.setDateTo(this.dateTo);
        Globals.tableSettings.setFilterCategory(this.getFilterCategory());
        Globals.tableSettings.setFilterCategoryIndex(this.getFilterCategoryIndex());
        Globals.tableSettings.setFilterKeyword(this.getFilterKeyword());
        super.onPause();

        Log.d(TAG, "onPause: Fragment paused");
    }
}
