package com.gardyanakbar.guardianheadpaindiary.ui.new_entry;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.constants.Globals;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PainEntryData;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PatientData;
import com.gardyanakbar.guardianheadpaindiary.interfaces.GUIFunctions;
import com.gardyanakbar.guardianheadpaindiary.interfaces.HistoryListener;
import com.gardyanakbar.guardianheadpaindiary.interfaces.LanguageListener;
import com.gardyanakbar.guardianheadpaindiary.methods.FileOperation;
import com.gardyanakbar.guardianheadpaindiary.methods.Methods;
import com.gardyanakbar.guardianheadpaindiary.methods.PainDataOperation;
import com.gardyanakbar.guardianheadpaindiary.ui.MyPageAdapter;
import com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.CommentsFragment;
import com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.DateTimeSelectFragment;
import com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.DurationIntensitySelectFragment;
import com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.FormElement;
import com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.PainKindFragment;
import com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.RecentMedicationFragment;
import com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.TriggerFragment;
import com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms.pain_location.PainLocationSelection;

import java.util.ArrayList;
import java.util.List;

import giantsweetroll.date.Date;

public class NewEntryFragment extends Fragment implements HistoryListener, GUIFunctions, LanguageListener
{
    //Fields
    private static final String TAG = "NewEntryFragment";
    private NewEntryViewModel dashboardViewModel;
    private View view;
    private Spinner entryLogFormSpinner;
    private MyPageAdapter pageAdapter;
    private List<FormElement> forms;
    private ViewPager pager;
    private Button bPrev, bSave, bNext;
    private DateTimeSelectFragment fDateTime;
    private DurationIntensitySelectFragment fDurationIntensity;
    private PainLocationSelection fPainLoc;
    private PainKindFragment fPainKind;
    private TriggerFragment fTrigger;
    private RecentMedicationFragment fRecMed;
    private CommentsFragment fComments;
    private PainEntryData oldEntry;
    private PatientData oldPatient;

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
        this.initForms();
        this.pageAdapter = new MyPageAdapter(this.getChildFragmentManager(), this.getFormsAsFragments());
        this.pager = (ViewPager)this.view.findViewById(R.id.entryLogViewPager);
        this.bPrev = (Button)this.view.findViewById(R.id.entryLogPrevButton);
        this.bSave = (Button)this.view.findViewById(R.id.entryLogSaveButton);
        this.bNext = (Button)this.view.findViewById(R.id.entryLogNextButton);
        this.oldPatient = Globals.activePatient;
        this.oldEntry = Globals.activeEntry;
//        this.setAsNewEntry(true);

        //Properties
        this.pager.setAdapter(this.pageAdapter);
        this.pager.setOffscreenPageLimit(this.pageAdapter.getCount());
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
                else
                {
                    getFragmentManager().popBackStackImmediate();
                }
            }
        });
        this.bSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                export(getSelectedPatient(), getData());
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
                if (position == 0)
                {
                    bPrev.setText(getString(R.string.cancel_text));
                }
                else
                {
                    bPrev.setText(getString(R.string.previous_text));
                }

                if (position == entryLogFormSpinner.getCount()-1)
                {
                    bNext.setVisibility(View.INVISIBLE);
                }
                else
                {
                    bNext.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        return root;
    }

    @Override
    public void refreshHistory(PatientData patientData)
    {
        this.fPainKind.refreshHistory(patientData);
        this.fTrigger.refreshHistory(patientData);
        this.fRecMed.refreshHistory(patientData);
    }

    @Override
    public void resetDefaults()
    {
        for (FormElement<?> form : this.forms)
        {
            form.resetDefaults();
        }
        this.pager.setCurrentItem(0);
    }

    @Override
    public void refresh()
    {
        this.refreshHistory(this.oldPatient);
    }

    @Override
    public void revalidateLanguage()
    {
        for (FormElement<?> form : this.forms)
        {
            form.revalidateLanguage();
        }
    }

    @Override
    public void onPause()
    {
        Log.d(TAG, "onPause: called");
        super.onPause();
        Globals.isNewEntry = true;
    }

    //Private Methods
    /**
     * Add the FormElement objects from a list of FormElement to a list of Fragments
     * @return
     */
    private List<Fragment> getFormsAsFragments()
    {
        List<Fragment> list = new ArrayList<>();

        list.addAll(this.forms);

        return list;
    }
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
    private void initForms()
    {
        this.forms = new ArrayList<>();

        this.fDateTime = new DateTimeSelectFragment();
        this.fDurationIntensity = new DurationIntensitySelectFragment();
        this.fPainLoc = new PainLocationSelection();
        this.fPainKind = new PainKindFragment();
        this.fTrigger = new TriggerFragment();
        this.fRecMed = new RecentMedicationFragment();
        this.fComments = new CommentsFragment();

        this.forms.add(this.fDateTime);
        this.forms.add(this.fDurationIntensity);
        this.forms.add(this.fPainLoc);
        this.forms.add(this.fPainKind);
        this.forms.add(this.fTrigger);
        this.forms.add(this.fRecMed);
        this.forms.add(this.fComments);
    }
    private void fillData(PainEntryData entry)
    {
        if (this.fComments != null)
        {
            this.fComments.setData(entry.getComments());
            this.fDateTime.setDate(entry.getDate());
            this.fDateTime.setTime(entry);
            this.fDurationIntensity.setDuration(entry.getDuration());
            this.fDurationIntensity.setIntensity(entry.getIntensity());
            this.fPainKind.setData(entry);
            this.fPainLoc.setSelectedPosition(entry);
            this.fRecMed.setData(entry.getRecentMedication(), entry.getMedicineComplaint());
            this.fTrigger.setData(entry);
        }
        else
        {
            Log.d(TAG, "fillData: fComments is null");
        }
    }
    private void setOldPatientData(PatientData patient)
    {
        this.oldPatient = patient;
    }
    private void setOldEntry(PainEntryData entry)
    {
        this.oldEntry = entry;
    }
    private PatientData getSelectedPatient()
    {
        return this.oldPatient;
    }

    //Public Methods
    public void loadData(PatientData patient, PainEntryData entry)
    {
        this.resetDefaults();
//        this.activeUser.setData(patient);
        this.setOldEntry(entry);
        this.setOldPatientData(patient);
   //     this.fillData(patient, entry);
//        this.setAsNewEntry(false);
    }

    /**
     * Gets the user inputs from each of the individual forms as a PainEntryData object
     * @return a PainEtnryData object containing the inputs of the individual forms.
     */
    public PainEntryData getData()
    {
        PainEntryData entry = new PainEntryData();

        entry.setComments(this.fComments.getData());
        entry.setDate(this.fDateTime.getDate());
        entry.setTime(this.fDateTime.getTimeHour(), this.fDateTime.getTimeMinutes());
        entry.setDuration(this.fDurationIntensity.getDuration());
        entry.setIntensity(fDurationIntensity.getIntensity());
        entry.setPainKind(this.fPainKind.getData());
        if (this.fPainLoc.presetLocationSelected())
        {
            entry.setPresetPainLocations(this.fPainLoc.getSelectedPositions());
        }
        else
        {
            entry.setCustomPainLocations(this.fPainLoc.getSelectedPositions());
        }
        entry.setRecentMedicaiton(this.fRecMed.getRecentMedication());
        entry.setMedicineComplaint(this.fRecMed.getMedicineComplaint());
        entry.setTrigger(this.fTrigger.getData());

        return entry;
    }

    /**
     * Checks if all FormElement objects has been filled.
     * @return true if all forms are filled.
     */
    public boolean allRequiredFieldsFilled()
    {
        for (FormElement<?> form : this.forms)
        {
            if (!form.allFilled())
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Get a list of the forms that have not been filled yet.
     * @return a list of the names of the forms which has not been filled.
     */
    public List<String> getUnfilledRequiredFieldsNames()
    {
        List<String> list = new ArrayList<String>();

        int index = 0;
        for (FormElement<?> form : this.forms)
        {
            if (!form.allFilled())
            {
                list.add(form.getName());
                index++;
            }
        }

        return list;
    }

    public PainEntryData getOldPainEntryData()
    {
        return this.oldEntry;
    }
    public PatientData getOldPatientData()
    {
        return this.oldPatient;
    }

    //Protected Methods
    protected boolean lastPainKindSame(PatientData patient, PainEntryData entry)
    {
        try
        {
            return patient.getLastPainKind().equals(entry.getPainKind());
        }
        catch(NullPointerException ex)
        {
            return false;
        }
    }
    protected boolean lastRecentMedsSame(PatientData patient, PainEntryData entry)
    {
        try
        {
            return patient.getLastRecentMeds().equals(entry.getRecentMedication());
        }
        catch(NullPointerException ex)
        {
            return false;
        }
    }
    protected boolean lastMedicineComplaintSame(PatientData patient, PainEntryData entry)
    {
        try
        {
            return patient.getLastMedicineComplaint().equals(entry.getMedicineComplaint());
        }
        catch(NullPointerException ex)
        {
            return false;
        }
    }
    protected boolean lastTriggerSame(PatientData patient, PainEntryData entry)
    {
        try
        {
            return patient.getLastTrigger().equals(entry.getTrigger());
        }
        catch(NullPointerException ex)
        {
            return false;
        }
    }
    protected void updateLastSelection(PatientData patient, PainEntryData entry)
    {
        if(!this.lastPainKindSame(patient, entry))
        {
            patient.setLastPainKind(entry.getPainKind());
        }
        if(!this.lastRecentMedsSame(patient, entry))
        {
            patient.setLastRecentMeds(entry.getRecentMedication());
        }
        if(!this.lastMedicineComplaintSame(patient, entry))
        {
            patient.setLastMedicineComplaint(entry.getMedicineComplaint());
        }
        if(!this.lastTriggerSame(patient, entry))
        {
            patient.setLastTrigger(entry.getTrigger());
        }
    }

    /**
     * Exports the current entry as a single entry.
     * @param patient - The PatientData object corresponding to the entry.
     * @param entry - The entry to be exported
     */
    protected void exportSingle(PatientData patient, PainEntryData entry)
    {
        this.updateLastSelection(patient, entry);
        FileOperation.savePatientData(patient);
        FileOperation.updateHistory(Globals.HISTORY_RECENT_MEDICATION, patient, entry.getRecentMedication());
        FileOperation.updateHistory(Globals.HISTORY_MEDICINE_COMPLAINT, patient, entry.getMedicineComplaint());
        FileOperation.updateHistory(Globals.HISTORY_PAIN_KIND, patient, entry.getPainKind());
        FileOperation.updateHistory(Globals.HISTORY_TRIGGER, patient, entry.getTrigger());
        FileOperation.exportPainData(patient, entry);

        if (!Globals.isNewEntry)
        {
            Date curDate = this.fDateTime.getDate();
            Date oldDate = oldEntry.getDate();
            Log.d(TAG, "exportSingle: " + curDate.toString(Date.DAY, Date.MONTH, Date.YEAR, "-") + " vs " + oldDate.toString(Date.DAY, Date.MONTH, Date.YEAR, "-"));
            boolean sameDate = Date.areSameDate(curDate, oldDate);
            Log.d(TAG, "exportSingle: dates the same = " + sameDate);
            int curHour = Integer.parseInt(this.fDateTime.getTimeHour());
            int curMin = Integer.parseInt(this.fDateTime.getTimeMinutes());
            int oldHour = Integer.parseInt(oldEntry.getTimeHour());
            int oldMin = Integer.parseInt(oldEntry.getTimeMinutes());
            Log.d(TAG, "exportSingle: " + curHour + ":" + curMin + " vs " + oldHour + ":" + oldMin);
            boolean sameTime = Methods.isSameTime(curHour, curMin, oldHour, oldMin);
            Log.d(TAG, "exportSingle: time is the same = " + sameTime);
            if (!sameDate||!sameTime)		//Check if the start time or date has been altered
            {
                Log.d(TAG, "exportSingle: date or time of current entry is different from the old one. The old one will be deleted.");
                FileOperation.deleteEntry(Methods.generatePainDataFilePathName(this.getSelectedPatient(), this.oldEntry));
            }
        }
        Methods.refreshHistories(this.getSelectedPatient());
        this.resetDefaults();
    }
    /**
     * Exports the current entry as multiple entries.
     * @param patient - The PatientData object corresponding to the entry.
     * @param entry - The entry to be exported
     */
    protected void exportMultiple(PatientData patient, PainEntryData entry)
    {
        List<PainEntryData> duplicateEntries = PainDataOperation.generateDuplicates(entry, new Date(entry.getDate().getDay() + Methods.secondsToDays(Long.parseLong(entry.getDuration())),
                entry.getDate().getMonth(),
                entry.getDate().getYear()));

        this.updateLastSelection(patient, entry);
        FileOperation.savePatientData(patient);
        FileOperation.updateHistory(Globals.HISTORY_RECENT_MEDICATION, this.getSelectedPatient(), entry.getRecentMedication());
        FileOperation.updateHistory(Globals.HISTORY_MEDICINE_COMPLAINT, this.getSelectedPatient(), entry.getMedicineComplaint());
        FileOperation.updateHistory(Globals.HISTORY_PAIN_KIND, this.getSelectedPatient(), entry.getPainKind());
        FileOperation.updateHistory(Globals.HISTORY_TRIGGER, this.getSelectedPatient(), entry.getTrigger());
        for (PainEntryData painEntry : duplicateEntries)
        {
            FileOperation.exportPainData(patient, painEntry);
        }

        Methods.refreshHistories(this.getSelectedPatient());
        this.refresh();
        this.resetDefaults();
    }
    /**
     * Exports the current entry. It can determine if it's a single or multiple entry.
     * @param patient - The PatientData object corresponding to the entry.
     * @param entry - The entry to be exported
     */
    protected void export(final PatientData patient, final PainEntryData entry)
    {
        if (this.allRequiredFieldsFilled())
        {
            if(entry.isSingleEntry())
            {
                if (FileOperation.entryExists(patient, entry) && Globals.isNewEntry)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                    builder.setTitle(this.getString(R.string.dialog_confirm_overwrite_title_text));
                    builder.setMessage(this.getString(R.string.dialog_confirm_overwrite_text));
                    builder.setCancelable(false);
                    builder.setPositiveButton(this.getString(R.string.ok_text), new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            exportSingle(patient, entry);
                        }
                    });
                    builder.setNegativeButton(this.getString(R.string.cancel_text), new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else
                {
                    this.exportSingle(patient, entry);
                }
                this.refresh();
            }
            else
            {
                this.exportMultiple(patient, entry);
            }
            Toast.makeText(this.getContext(), this.getString(R.string.entry_log_entry_saved_text), Toast.LENGTH_SHORT).show();
            this.getFragmentManager().popBackStackImmediate();
        }
        else
        {
            String msg = this.getString(R.string.dialog_required_fields_text) + "\n";
            List<String> fields = this.getUnfilledRequiredFieldsNames();

            for (String name : fields)
            {
                msg += "\n- " + name;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setTitle(this.getString(R.string.dialog_required_fields_title_text));
            builder.setMessage(msg);
            builder.setCancelable(false);
            builder.setPositiveButton(this.getString(R.string.ok_text), new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}