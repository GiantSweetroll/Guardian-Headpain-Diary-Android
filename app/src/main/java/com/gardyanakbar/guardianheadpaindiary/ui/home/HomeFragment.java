package com.gardyanakbar.guardianheadpaindiary.ui.home;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.constants.Globals;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.GraphSettings;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PainEntryData;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PatientData;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.TableSettings;
import com.gardyanakbar.guardianheadpaindiary.methods.FileOperation;
import com.gardyanakbar.guardianheadpaindiary.methods.Methods;

import java.util.List;

import giantsweetroll.date.Date;

public class HomeFragment extends Fragment
{

    //Fields
    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    private TextView tvEntriesCount, tvNewEntry, tvMaxIntensity, tvMaxDuration, tvMaxDurationUnit, tvRecap;
    //Constants
    private final int SECONDS_INDEX = 0,
            MINUTES_INDEX = 1,
            HOURS_INDEX = 2,
            DAYS_INDEX = 3;

    //Private Methods
    private String getDurationUnit(int index)
    {
        if (index == this.SECONDS_INDEX)
        {
            return this.getString(R.string.entry_log_form_duration_intensity_unit_seconds_text);
        }
        else if (index == this.MINUTES_INDEX)
        {
            return this.getString(R.string.entry_log_form_duration_intensity_unit_minutes_text);
        }
        else if (index == this.HOURS_INDEX)
        {
            return this.getString(R.string.entry_log_form_duration_intensity_unit_hours_text);
        }
        else if (index == this.DAYS_INDEX)
        {
            return this.getString(R.string.entry_log_form_duration_intensity_unit_days_text);
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
        //Initialization
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
////        homeViewModel.getText().observe(this, new Observer<String>() {
////            @Override
////            public void onChanged(@Nullable String s) {
////                textView.setText(s);
////            }
////        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        //Initialization
        this.tvEntriesCount = view.findViewById(R.id.entriesCount);
        this.tvNewEntry = view.findViewById(R.id.newEntry);
        this.tvMaxIntensity = view.findViewById(R.id.maxIntensity);
        this.tvMaxDuration = view.findViewById(R.id.maxDuration);
        this.tvMaxDurationUnit = view.findViewById(R.id.durationUnit);
        this.tvRecap = view.findViewById(R.id.todayRecap);

        //Properties
        this.tvNewEntry.setText(HtmlCompat.fromHtml(Methods.giveTextHTMLLinkStyle(this.getString(R.string.home_new_entry_text)), HtmlCompat.FROM_HTML_MODE_LEGACY));
        //Underlines the "Today's Recap" text
        SpannableString content = new SpannableString(this.getString(R.string.home_today_recap_title));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        this.tvRecap.setText(content);
        this.tvEntriesCount.setText("0");
        this.tvMaxIntensity.setText("0");
        this.tvMaxDuration.setText("0");
        this.tvNewEntry.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Globals.bottomNavigationView.setSelectedItemId(R.id.navigation_new_entry);
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();

        //Reload Patient
        try
        {
            //Try to load patient data
            Globals.activePatient = FileOperation.getListOfPatients().get(0);
            Log.d(TAG, "onCreate: Patient data found");
        }
        catch(IndexOutOfBoundsException ex)
        {
            Log.d(TAG, "onCreate: No patient data detected");
            //If no new patient has been registered
            Globals.activePatient = new PatientData();
            FileOperation.savePatientData(Globals.activePatient);
            Log.d(TAG, "onCreate: New patient data saved");
        }
        Globals.tableSettings = new TableSettings();
        Globals.graphSettings = new GraphSettings();
        Globals.isNewEntry = true;

        //Refresh history to match patient
        Globals.HISTORY_MEDICINE_COMPLAINT.refresh(Globals.activePatient);
        Globals.HISTORY_PAIN_KIND.refresh(Globals.activePatient);
        Globals.HISTORY_RECENT_MEDICATION.refresh(Globals.activePatient);
        Globals.HISTORY_TRIGGER.refresh(Globals.activePatient);


        //Get daily stats
        //Get entries from today
        List<PainEntryData> entries = FileOperation.getListOfEntries(Globals.activePatient.getID(), new Date(), new Date());
        int maxInt = 0;
        long maxDur = 0;
        //Update no of entries
        this.tvEntriesCount.setText(Integer.toString(entries.size()));
        //Get max intensity and duration
        for (PainEntryData entry : entries)
        {
            int intensity = Integer.parseInt(entry.getIntensity());
            long duration = Long.parseLong(entry.getDuration());

            if (intensity > maxInt)
            {
                maxInt = intensity;
            }
            if (duration > maxDur)
            {
                maxDur = duration;
            }
        }
        //Update max intensity
        this.tvMaxIntensity.setText(Integer.toString(maxInt));
        //Convert max duration to the appropriate units
        String duration = Long.toString(maxDur);
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
            this.tvMaxDuration.setText(Integer.toString(value));
            this.tvMaxDurationUnit.setText(this.getDurationUnit(index));
        }
        catch(NumberFormatException ex)
        {
            double val = Double.parseDouble(duration);
            long value = (long)val;
            value = value/86400;
            int index = DAYS_INDEX;

            this.tvMaxDuration.setText(Long.toString(value));
            this.tvMaxDurationUnit.setText(this.getDurationUnit(index));
        }
        catch(Exception ex) {ex.printStackTrace();}
    }
}