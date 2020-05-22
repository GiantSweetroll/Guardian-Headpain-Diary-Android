package com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.constants.Constants;
import com.gardyanakbar.guardianheadpaindiary.constants.XMLIdentifier;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PainEntryData;
import com.gardyanakbar.guardianheadpaindiary.methods.Methods;

import java.util.ArrayList;
import java.util.List;

public class DurationIntensitySelectFragment extends FormElement
{
    //Fields
    private TextView durationLabel, durationValueLabel, intensityValueLabel, intensityLabel;
    private Spinner durationUnits;
    private SeekBar durationSlider, intensitySlider;
    //Constants
    private final int SECONDS_INDEX = 0,
            MINUTES_INDEX = 1,
            HOURS_INDEX = 2,
            DAYS_INDEX = 3;

    //Constructor
    public DurationIntensitySelectFragment()
    {
        super(false);
    }

    //Public Methods
    public String getDuration()
    {
        if (this.durationUnits.getSelectedItemPosition() == this.SECONDS_INDEX)		//Seconds
        {
            return Integer.toString(this.durationSlider.getProgress());
        }
        else if (this.durationUnits.getSelectedItemPosition() == this.MINUTES_INDEX)	//Minutes
        {
            return Integer.toString(Methods.minutesToSeconds(this.durationSlider.getProgress()));
        }
        else if (this.durationUnits.getSelectedItemPosition() == this.HOURS_INDEX)		//Hours
        {
            return Integer.toString(Methods.hoursToSeconds(this.durationSlider.getProgress()));
        }
        else															//Days
        {
            return Long.toString(Methods.daysToSeconds(this.durationSlider.getProgress()));
        }
    }
    public void setDuration(String duration)
    {
        try
        {
            int value = Integer.parseInt(duration);
            int index = SECONDS_INDEX;

            if (value >=86400d)								//Days
            {
                value = Methods.secondsToDays(value);
                index = DAYS_INDEX;
            }
            else if (value>=3600d && value<86400d)	//Hours
            {
                value = Methods.secondsToHours(value);
                index = HOURS_INDEX;
            }
            else if (value<3600d && value>=60d)		//Minutes
            {
                value = Methods.secondsToMinutes(value);
                index = MINUTES_INDEX;
            }

            this.durationSlider.setProgress(value);
            this.durationUnits.setSelection(index);
        }
        catch(NumberFormatException ex)
        {
            double val = Double.parseDouble(duration);
            long value = (long)val;
            value = value/86400;
            int index = DAYS_INDEX;

            this.durationSlider.setProgress(Integer.parseInt(Long.toString(value)));
            this.durationUnits.setSelection(index);
        }
        catch(Exception ex) {ex.printStackTrace();}
    }
    public String getIntensity()
    {
        return Integer.toString(this.intensitySlider.getProgress());
    }
    public void setIntensity(String intensity)
    {
        this.intensitySlider.setProgress(Integer.parseInt(intensity));
    }

    //Overridden Methods
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.view = inflater.inflate(R.layout.fragment_entry_log_duration_intensity, container, false);

        //Initialization
        this.setScroll((ScrollView)this.view.findViewById(R.id.entryLogDurationIntensityScroll));
        this.setFormTitleLabel((TextView)view.findViewById(R.id.entryLogDurationIntensityLabel));
        this.setFormTitle("");
        this.intensityLabel = (TextView)this.view.findViewById(R.id.entryLogIntensityLabel);
        this.intensityValueLabel = (TextView)this.view.findViewById(R.id.entryLogIntensityValue);
        this.durationLabel = (TextView)this.view.findViewById(R.id.entryLogDurationLabel);
        this.durationValueLabel = (TextView)this.view.findViewById(R.id.entryLogDurationValueLabel);
        this.durationUnits = (Spinner)this.view.findViewById(R.id.entryLogDurationUnitSpinner);
        this.durationSlider = (SeekBar)this.view.findViewById(R.id.entryLogDurationSlider);
        this.intensitySlider = (SeekBar)this.view.findViewById(R.id.entryLogIntensitySlider);
        this.setName(this.getString(R.string.entry_log_map_button_duration_intensity_text));

        //Properties
        this.durationLabel.setTextSize(Constants.FONT_SUB_TITLE_SIZE);
        this.durationUnits.setAdapter(this.getDurationSpinnerAdapter());
        this.durationUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                final String selection = adapterView.getItemAtPosition(position).toString();

                if (selection.equals(getString(R.string.entry_log_form_duration_intensity_unit_seconds_text)))
                {
                    durationSlider.setMax(60);
                }
                else if (selection.equals(getString(R.string.entry_log_form_duration_intensity_unit_minutes_text)))
                {
                    durationSlider.setMax(60);
                }
                else if (selection.equals(getString(R.string.entry_log_form_duration_intensity_unit_hours_text)))
                {
                    durationSlider.setMax(24);
                }
                else if (selection.equals(getString(R.string.entry_log_form_duration_intensity_unit_days_text)))
                {
                    durationSlider.setMax(31);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        this.intensityLabel.setTextSize(Constants.FONT_SUB_TITLE_SIZE);
        this.intensitySlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                intensityValueLabel.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        this.durationSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                durationValueLabel.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        return this.view;
    }

    @Deprecated
    @Override
    public Object getData()
    {
        return null;
    }

    @Deprecated
    @Override
    public void setData(Object data)
    {
        if (data instanceof PainEntryData)
        {
            this.setDuration(((PainEntryData) data).getDuration());
            this.setIntensity(((PainEntryData) data).getIntensity());
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
    public void revalidateLanguage()
    {
        this.setFormTitle(this.getString(R.string.entry_log_form_duration_intensity_label));
    }

    //Other Methods
    private ArrayAdapter<String> getDurationSpinnerAdapter()
    {
        List<String> list = new ArrayList<>();

        list.add(this.view.getResources().getString(R.string.entry_log_form_duration_intensity_unit_seconds_text));
        list.add(this.view.getResources().getString(R.string.entry_log_form_duration_intensity_unit_minutes_text));
        list.add(this.view.getResources().getString(R.string.entry_log_form_duration_intensity_unit_hours_text));
        list.add(this.view.getResources().getString(R.string.entry_log_form_duration_intensity_unit_days_text));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), R.layout.support_simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        return adapter;
    }
}
