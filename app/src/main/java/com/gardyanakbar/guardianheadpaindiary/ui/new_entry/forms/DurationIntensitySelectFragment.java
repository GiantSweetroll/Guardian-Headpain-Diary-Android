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

import java.util.ArrayList;
import java.util.List;

public class DurationIntensitySelectFragment extends FormElement
{
    //Fields
    private TextView durationLabel, durationValueLabel, intensityValueLabel;
    private Spinner durationUnits;
    private SeekBar durationSlider, intensitySlider;

    //Constructor
    public DurationIntensitySelectFragment()
    {
        super(false);
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
        this.intensityValueLabel = (TextView)this.view.findViewById(R.id.entryLogIntensityValue);
        this.durationLabel = (TextView)this.view.findViewById(R.id.entryLogDurationLabel);
        this.durationValueLabel = (TextView)this.view.findViewById(R.id.entryLogDurationValueLabel);
        this.durationUnits = (Spinner)this.view.findViewById(R.id.entryLogDurationUnitSpinner);
        this.durationSlider = (SeekBar)this.view.findViewById(R.id.entryLogDurationSlider);
        this.intensitySlider = (SeekBar)this.view.findViewById(R.id.entryLogIntensitySlider);

        //Properties
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
