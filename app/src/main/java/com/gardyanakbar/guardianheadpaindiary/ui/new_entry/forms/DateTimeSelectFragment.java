package com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PainEntryData;

import giantsweetroll.date.Date;

public class DateTimeSelectFragment extends FormElement
{
    //Fields
    private DatePicker date;
    private TimePicker time;

    //Constructor
    public DateTimeSelectFragment()
    {
        super(false);
    }

    //Public Methods
    public Date getDate()
    {
        Date date = new Date();
        date.setYear(this.date.getYear());
        date.setMonth(this.date.getMonth());
        date.setDay(this.date.getDayOfMonth());
        return date;
    }
    public String getTimeHour()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            return Integer.toString(this.time.getHour());
        }
        else
        {
            return Integer.toString(this.time.getCurrentHour());
        }
    }
    public String getTimeMinutes()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            return Integer.toString(this.time.getMinute());
        }
        else
        {
            return Integer.toString(this.time.getCurrentMinute());
        }
    }
    public void setDate(Date date)
    {
        this.date.updateDate(date.getYear(), date.getMonth(), date.getDay());
    }
    public void setTime(PainEntryData entry)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.time.setHour(Integer.parseInt(entry.getTimeHour()));
        }
        else
        {
            this.time.setCurrentHour(Integer.parseInt(entry.getTimeHour()));
        }
    }
    public void setData(PainEntryData entry)
    {
        this.setDate(entry.getDate());
        this.setTime(entry);
    }

    //Overridden Methods
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.view = inflater.inflate(R.layout.fragment_entry_log_date_time, container, false);

        //Initialization
        this.setScroll((ScrollView)this.view.findViewById(R.id.entryLogDateTimeScroll));
        this.setFormTitleLabel((TextView)view.findViewById(R.id.entryLogDateTimeLabel));
        this.setFormTitle(this.getString(R.string.entry_log_form_date_time_title));
        this.date = (DatePicker)this.view.findViewById(R.id.entryLogDatePicker);
        this.time = (TimePicker)this.view.findViewById(R.id.entryLogTimePicker);
        this.setName(this.getString(R.string.entry_log_map_button_date_time_text));

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
    public void revalidateLanguage() {
        this.setFormTitle(this.getString(R.string.entry_log_form_date_time_title));
    }
}
