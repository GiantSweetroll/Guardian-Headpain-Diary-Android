package com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;

import com.gardyanakbar.guardianheadpaindiary.R;

public class DateTimeSelectFragment extends FormElement
{
    //Fields
    private DatePicker date;
    private TimePicker time;
    private View view;

    //Constructor
    public DateTimeSelectFragment()
    {
        super(false);
    }

    //Overridden Methods
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.view = inflater.inflate(R.layout.fragment_entry_log_date_time, container, false);

        this.setScroll((ScrollView)this.view.findViewById(R.id.entryLogDateTimeScroll));
        this.setFormTitleLabel((TextView)view.findViewById(R.id.entryLogDateTimeLabel));
        this.setFormTitle(this.getString(R.string.entry_log_form_date_time_title));
        this.date = (DatePicker)this.view.findViewById(R.id.entryLogDatePicker);
        this.time = (TimePicker)this.view.findViewById(R.id.entryLogTimePicker);

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
        return true;
    }

    @Deprecated
    @Override
    public void refresh() {}

    @Override
    public void revalidateLanguage()
    {
        this.setFormTitle(this.getString(R.string.entry_log_form_date_time_title));
    }
}
