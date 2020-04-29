package com.gardyanakbar.guardianheadpaindiary.ui.new_entry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gardyanakbar.guardianheadpaindiary.MainActivity;
import com.gardyanakbar.guardianheadpaindiary.R;

import java.util.ArrayList;
import java.util.List;

public class NewEntryFragment extends Fragment
{

    private NewEntryViewModel dashboardViewModel;
    private View view;
    private Spinner entryLogFormSpinner;

    //Overridden Methods
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        //Initialization
        dashboardViewModel =
                ViewModelProviders.of(this).get(NewEntryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_new_entry, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        this.view = root;
        this.entryLogFormSpinner = (Spinner)this.view.findViewById(R.id.entryLogSelectionSpinner);


        //Properties
        this.entryLogFormSpinner.setAdapter(this.getFormsSpinnerAdapter());
        this.entryLogFormSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                final String selection = adapterView.getItemAtPosition(position).toString();

                //Selection
                if (selection.equals(getString(R.string.entry_log_map_button_date_time_text)))
                {

                }
                else if (selection.equals(getString(R.string.entry_log_map_button_duration_intensity_text)))
                {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        return root;
    }

    //Other Methods
    private ArrayAdapter<String> getFormsSpinnerAdapter()
    {
        List<String> list = new ArrayList<>();

        list.add(this.view.getResources().getString(R.string.entry_log_map_button_date_time_text));
        list.add(this.view.getResources().getString(R.string.entry_log_map_button_duration_intensity_text));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), R.layout.support_simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        return adapter;
    }
}