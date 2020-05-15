package com.gardyanakbar.guardianheadpaindiary.ui.new_entry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.ui.MyPageAdapter;
import com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.CommentsFragment;
import com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.DateTimeSelectFragment;
import com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.DurationIntensitySelectFragment;
import com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.PainKindFragment;
import com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.RecentMedicationFragment;
import com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.TriggerFragment;
import com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.pain_location.PainLocationCustomSelection;
import com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.pain_location.PainLocationPresetSelection;
import com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.pain_location.PainLocationSelection;

import java.util.ArrayList;
import java.util.List;

public class NewEntryFragment extends Fragment
{

    private NewEntryViewModel dashboardViewModel;
    private View view;
    private Spinner entryLogFormSpinner;
    private MyPageAdapter pageAdapter;
    private ViewPager pager;
    private Button bPrev, bSave, bNext;

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
        this.pageAdapter = new MyPageAdapter(this.getChildFragmentManager(), this.getFragments());
        this.pager = (ViewPager)this.view.findViewById(R.id.entryLogViewPager);
        this.bPrev = (Button)this.view.findViewById(R.id.entryLogPrevButton);
        this.bSave = (Button)this.view.findViewById(R.id.entryLogSaveButton);
        this.bNext = (Button)this.view.findViewById(R.id.entryLogNextButton);

        //Properties
        this.pager.setAdapter(this.pageAdapter);
        this.entryLogFormSpinner.setAdapter(this.getFormsSpinnerAdapter());
        this.entryLogFormSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                final String selection = adapterView.getItemAtPosition(position).toString();

                //Selection
                if (selection.equals(getString(R.string.entry_log_map_button_date_time_text)))
                {
                    pager.setCurrentItem(0);
                }
                else if (selection.equals(getString(R.string.entry_log_map_button_duration_intensity_text)))
                {
                    pager.setCurrentItem(1);
                }
                else if (selection.equals(getString(R.string.entry_log_map_button_pain_location_text)))
                {
                    pager.setCurrentItem(2);
                }
                else if (selection.equals(getString(R.string.entry_log_map_button_pain_kind)))
                {
                    pager.setCurrentItem(3);
                }
                else if (selection.equals(getString(R.string.entry_log_map_button_trigger_text)))
                {
                    pager.setCurrentItem(4);
                }
                else if (selection.equals(getString(R.string.entry_log_map_button_recent_medication_text)))
                {
                    pager.setCurrentItem(5);
                }
                else if (selection.equals(getString(R.string.entry_log_map_button_comments_text)))
                {
                    pager.setCurrentItem(6);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        this.bPrev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int index = pager.getCurrentItem();
                if (index > 0)
                {
                    pager.setCurrentItem(index-1);
                }
            }
        });
        this.bSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //TO-DO save entry
            }
        });
        this.bNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int index = pager.getCurrentItem();
                if (index < pageAdapter.getCount())
                {
                    pager.setCurrentItem(index+1);
                }
            }
        });
        this.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position)
            {
                entryLogFormSpinner.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        return root;
    }

    //Other Methods
    private ArrayAdapter<String> getFormsSpinnerAdapter()
    {
        List<String> list = new ArrayList<>();

        list.add(this.view.getResources().getString(R.string.entry_log_map_button_date_time_text));
        list.add(this.view.getResources().getString(R.string.entry_log_map_button_duration_intensity_text));
        list.add(this.view.getResources().getString(R.string.entry_log_map_button_pain_location_text));
        list.add(this.view.getResources().getString(R.string.entry_log_map_button_pain_kind));
        list.add(this.view.getResources().getString(R.string.entry_log_map_button_trigger_text));
        list.add(this.view.getResources().getString(R.string.entry_log_map_button_recent_medication_text));
        list.add(this.view.getResources().getString(R.string.entry_log_map_button_comments_text));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), R.layout.support_simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        return adapter;
    }
    private List<Fragment> getFragments()
    {
        List<Fragment> list = new ArrayList<>();

        list.add(new DateTimeSelectFragment());
        list.add(new DurationIntensitySelectFragment());
        list.add(new PainLocationSelection());
//        list.add(new PainLocationCustomSelection());
//        list.add(new PainLocationPresetSelection());
        list.add(new PainKindFragment());
        list.add(new TriggerFragment());
        list.add(new RecentMedicationFragment());
        list.add(new CommentsFragment());

        return list;
    }
}