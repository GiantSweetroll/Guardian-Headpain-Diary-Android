package com.gardyanakbar.guardianheadpaindiary.ui.table;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.constants.Globals;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PainEntryData;
import com.gardyanakbar.guardianheadpaindiary.methods.FileOperation;
import com.gardyanakbar.guardianheadpaindiary.methods.Methods;
import com.gardyanakbar.guardianheadpaindiary.methods.PainDataOperation;

import java.util.ArrayList;
import java.util.List;

import giantsweetroll.date.Date;

public class TableFragment extends Fragment
{
    //Fields
    private static final String TAG = "TableFragment";
    private TableViewModel tableViewModel;
    private Button btnSelectAll, btnDeselectAll, btnDelete, btnExport, btnSettings, btnRefresh;
    private RecyclerView recView;
    private TableSettingsFragment settings;
    private PainEntryAdapter adapter;
    private List<PainEntryData> entries;

    //Constructor
    public TableFragment()
    {
        super();
        this.settings = new TableSettingsFragment();
    }

    //Private Methods
    private void updateEntries()
    {
        this.entries.clear();

        Log.d(TAG, "updateEntries: Table Settings data driver dateFrom is null: " + (Globals.tableSettings.getDateFrom() == null));

        Log.d(TAG, "updateEntries: called");
        Date dateFrom = Globals.tableSettings.getDateFrom();
        Log.d(TAG, "updateEntries: Get entry from date: " + dateFrom.toString(Date.DAY, Date.MONTH, Date.YEAR, "-"));
        Date dateTo = Globals.tableSettings.getDateTo();
        Log.d(TAG, "updateEntries: Get entry to date: " + dateTo.toString(Date.DAY, Date.MONTH, Date.YEAR, "-"));
        String filterType = Globals.tableSettings.getFilterCategory();
        Log.d(TAG, "updateEntries: Filter category: " + filterType);
        String keyword = Globals.tableSettings.getFilterKeyword();
        Log.d(TAG, "updateEntries: Filter keyword: " + keyword);

        try
        {
            Log.d(TAG, "updateEntries: Importing all entries...");
            entries.addAll(FileOperation.getListOfEntries(Globals.activePatient.getID(), dateFrom, dateTo));
            Log.d(TAG, "updateEntries: Entries imported.");
        }
        catch(NullPointerException ex)
        {
            Log.d(TAG, "updateEntries: Something went wrong when importing entries");
            Log.e(TAG, "updateEntries: " + ex.getMessage());
        }
        Log.d(TAG, "updateEntries: Entries before category filter: " + entries.size());
        PainDataOperation.getFilteredData(this.getContext(), filterType, keyword, entries);
        Log.d(TAG, "updateEntries: Entries after category filter: " + entries.size());
    }
    private void deleteSelectedEntries()
    {
        final List<Integer> indexes = this.adapter.getSelectedIndexes();

        if (indexes.size() > 0)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setMessage(R.string.dialog_confirm_delete_text)
                    .setTitle(R.string.dialog_confirm_delete_title_text)
                    .setPositiveButton(R.string.ok_text, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Log.d(TAG, "onClick: user clicked yes");
                            for (int i=0; i<indexes.size(); i++)
                            {
                                PainEntryData entry = entries.get(indexes.get(i));
                                Date date = entry.getDate();
                                String path = Methods.generatePainDataFilePathName(Globals.activePatient, entry);
                                FileOperation.deleteEntry(path);
                                entries.remove(indexes.get(i));
                                Log.d(TAG, "onClick: entry at " + path + " deleted");
                            }
                            entries.clear();
                            updateEntries();
                            adapter.notifyDataSetChanged();
                            adapter.deselectAll();
                        }
                    })
                    .setNegativeButton(R.string.cancel_text, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Log.d(TAG, "onClick: user clicked cancel");
                        }
                    });
            builder.show();
        }
    }

    //Overridden Methods
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreateView: TableFragment view created");
        //Initialization
        tableViewModel = ViewModelProviders.of(this).get(TableViewModel.class);
        View root = inflater.inflate(R.layout.fragment_table, container, false);
        this.btnSelectAll = (Button)root.findViewById(R.id.btnSelectAll);
        this.btnDeselectAll = root.findViewById(R.id.btnDeselectAll);
        this.btnDelete = root.findViewById(R.id.btnDelete);
        this.btnExport = root.findViewById(R.id.btnExport);
        this.btnSettings = root.findViewById(R.id.btnSettings);
        this.btnRefresh = root.findViewById(R.id.btnRefresh);
        this.recView = root.findViewById(R.id.tableEntriesListView);
        this.entries = new ArrayList<>();
        this.updateEntries();
        this.adapter = new PainEntryAdapter(this.getContext(), this.entries);

        //Properties
        Globals.isNewEntry = true;
        this.recView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.recView.setAdapter(adapter);
        this.btnSettings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.tableParentLayout, settings);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        this.btnRefresh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                entries.clear();
                updateEntries();
                adapter.notifyDataSetChanged();
            }
        });
        this.btnSelectAll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                adapter.selectAll();
                adapter.notifyDataSetChanged();
            }
        });
        this.btnDeselectAll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                adapter.deselectAll();
                adapter.notifyDataSetChanged();
            }
        });
        this.btnDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                deleteSelectedEntries();
            }
        });
        this.btnExport.setVisibility(View.GONE);        //Remove the Export button for now until functionality can be developed

        return root;
    }
}
