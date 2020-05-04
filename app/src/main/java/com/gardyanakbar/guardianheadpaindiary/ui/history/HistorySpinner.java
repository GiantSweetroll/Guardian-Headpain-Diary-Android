package com.gardyanakbar.guardianheadpaindiary.ui.history;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.History;
import com.gardyanakbar.guardianheadpaindiary.interfaces.GUIFunctions;
import com.gardyanakbar.guardianheadpaindiary.interfaces.LanguageListener;
import com.gardyanakbar.guardianheadpaindiary.methods.Methods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HistorySpinner implements GUIFunctions
{
    //Fields
    private Spinner spinner;
    private History history;
    private String defaultSelection;
    private String[] defaultOptions;
    private boolean sorted, haveNone;
    private Context context;
    private ArrayAdapter<String> adapter;

    //Constants
    private final String NO_DEFAULTS = "";

    //Constructor
    public HistorySpinner(Spinner spinner, History history, String[] defaults, boolean sorted, boolean haveNone, Context context)
    {
        this.spinner = spinner;
        this.sorted = sorted;
        this.haveNone = haveNone;
        this.context = context;
        this.setDefaultOptions(defaults);
        this.setDefaultSelection(this.NO_DEFAULTS);
        this.setHistory(history);
        this.refresh();
    }

    //Private Methods
    private ArrayAdapter initAdapter(@org.jetbrains.annotations.NotNull History history)
    {
        List<String> list = new ArrayList<String>();
        List<String> histories = history.getHistory();
        Set<String> set = new HashSet<String>();
        List<String> subList = new ArrayList<String>();

        for (String str : defaultOptions)
        {
            set.add(str);
        }
        for (String str : histories)
        {
            set.add(str);
        }

        for (String str : set)
        {
            subList.add(str);
        }
        if (this.sorted)
        {
            Collections.sort(subList);
        }

        if (this.haveNone)
        {
            list.add(Methods.frameStringWithDashes(this.context.getString(R.string.choose_none_text)));
        }
        list.addAll(subList);
        list.add(Methods.frameStringWithDashes(this.context.getString(R.string.other_text)));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.context, R.layout.support_simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        return adapter;
    }
    private boolean hasCustomDefaultOption()
    {
        try
        {
            return !this.defaultSelection.equals(this.NO_DEFAULTS);
        }
        catch(NullPointerException ex)
        {
            return false;
        }
    }

    //Public Methods
    public void setHistory(History history)
    {
        this.history = history;
        this.refresh();
    }
    public void setSpinner(Spinner spinner) {
        this.spinner = spinner;
    }
    public Spinner getSpinner()
    {
        return this.spinner;
    }
    public void setDefaultOptions(String[] defaults)
    {
        this.defaultOptions = defaults;
    }
    public void setDefaultSelection(String option)
    {
        this.defaultSelection = option;
    }
    public void setSelectedToOther()
    {
        this.spinner.setSelection(this.adapter.getCount()-1);			//Last index
    }
    public void setSelectedToNone()
    {
        if(this.haveNone)
        {
            this.spinner.setSelection(0);
        }
        else
        {
            this.setSelectedToOther();
        }
    }
    public boolean lastIndexSelected()
    {
        return this.spinner.getSelectedItemPosition() == this.adapter.getCount()-1;
    }
    public boolean otherOptionSelected()
    {
        return this.lastIndexSelected();
    }
    public boolean noneOptionSelected()
    {
        if (this.haveNone)
        {
            return this.spinner.getSelectedItemPosition() == 0;
        }
        else
        {
            return false;
        }
    }
    public void revalidateLanguage(String[] defaultOptions)
    {
        this.setDefaultOptions(defaultOptions);
        this.refresh();
    }
    public void setSelectedItem(String item)
    {
        //Find item
        int index = 0;
        for (int i=0; i<this.adapter.getCount(); i++)
        {
            if (this.adapter.getItem(i).equals(item))
            {
                index = i;
                break;
            }
        }

        //Set spinner to selection
        this.spinner.setSelection(index);
    }
    public String getSelectedItem()
    {
        return this.spinner.getSelectedItem().toString();
    }
    public int getItemCount()
    {
        return this.adapter.getCount();
    }
    public int getSelectedIndex()
    {
        return this.spinner.getSelectedItemPosition();
    }
    public void setSelectedIndex(int i)
    {
        this.spinner.setSelection(i);
    }

    //Overridden Methods
    @Override
    public void resetDefaults()
    {
        if (this.hasCustomDefaultOption())
        {
            this.setSelectedItem(this.defaultSelection);
        }
        else
        {
            this.setSelectedToNone();
        }
    }

    @Override
    public void refresh()
    {
        this.spinner.setAdapter(this.initAdapter(this.history));
    }
}
