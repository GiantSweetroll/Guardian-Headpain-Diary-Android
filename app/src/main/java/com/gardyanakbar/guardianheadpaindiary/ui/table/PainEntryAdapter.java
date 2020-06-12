package com.gardyanakbar.guardianheadpaindiary.ui.table;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.constants.Globals;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PainEntryData;
import com.gardyanakbar.guardianheadpaindiary.methods.FileOperation;
import com.gardyanakbar.guardianheadpaindiary.methods.Methods;

import java.util.ArrayList;
import java.util.List;

import giantsweetroll.date.Date;

public class PainEntryAdapter extends RecyclerView.Adapter<PainEntryAdapter.PainEntryHolder>
{
    //Fields
    private static final String TAG = "PainEntryAdapter";
    private List<PainEntryData> dataset;
    private List<Integer> selectedIndexes;
    private Context context;
    private boolean selectAll;

    //Inner Class
    public static class PainEntryHolder extends RecyclerView.ViewHolder
    {
        public boolean isSelected;
        public TextView dateVal, time, painKind, intensity, duration, trigger, recMed, comments, edit, delete;
        public RelativeLayout parentLayout;

        public PainEntryHolder(View v)
        {
            super(v);
            dateVal = v.findViewById(R.id.date);
            time = v.findViewById(R.id.time);
            painKind = v.findViewById(R.id.painKind);
            intensity = v.findViewById(R.id.intensity);
            duration = v.findViewById(R.id.duration);
            trigger = v.findViewById(R.id.trigger);
            recMed = v.findViewById(R.id.recMed);
            comments = v.findViewById(R.id.comments);
            edit = v.findViewById(R.id.edit);
            delete = v.findViewById(R.id.delete);
            parentLayout = v.findViewById(R.id.parent_layout);
            isSelected = false;
            Log.d(TAG, "PainEntryHolder: PainEntryHolder initialized");
        }
    }

    //Constructor
    public PainEntryAdapter(Context context, List<PainEntryData> dataset)
    {
        this.dataset = dataset;
        Log.d(TAG, "PainEntryAdapter: data size: " + dataset.size());
        this.selectedIndexes = new ArrayList<>();
        this.context = context;
        this.selectAll = false;
    }

    //Public Methods
    /**
     * Selects all entries
     */
    public void selectAll()
    {
        this.selectedIndexes.clear();
        if (this.dataset.size() > 0)
        {
            for (int i=0; i<this.dataset.size(); i++)
            {
                this.selectedIndexes.add(i);
            }
            this.selectAll = true;
        }
    }
    /**
     * Deselects all entries
     */
    public void deselectAll()
    {
        this.selectedIndexes.clear();
        this.selectAll = false;
    }
    /**
     * Deletes selected entries
     */
    public void deleteEntries()
    {
        if (this.selectedIndexes.size() > 0)
        {
            Log.d(TAG, "deleteEntries: called");
            AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
            builder.setMessage(R.string.dialog_confirm_delete_text)
                    .setTitle(R.string.dialog_confirm_delete_title_text)
                    .setPositiveButton(R.string.ok_text, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Log.d(TAG, "onClick: user clicked yes");
                            for (int i=0; i<selectedIndexes.size(); i++)
                            {
                                PainEntryData entry = dataset.get(selectedIndexes.get(i));
                                Date date = entry.getDate();
                                String path = Methods.generatePainDataFilePathName(Globals.activePatient, entry);
                                FileOperation.deleteEntry(path);
                                dataset.remove(selectedIndexes.get(i));
                                Log.d(TAG, "onClick: entry at " + path + " deleted");
                            }
                            notifyDataSetChanged();
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

    /**
     * Returns the indexes of the selected entries
     * @return a list of Integer of the selected indexes
     */
    public List<Integer> getSelectedIndexes()
    {
        return this.selectedIndexes;
    }

    //Private Methods
    /**
     * Deletes the entry at the selected index.
     * @param i - the index
     */
    private void deleteEntryAtIndex(final int i)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setMessage(R.string.dialog_confirm_delete_text)
                .setTitle(R.string.dialog_confirm_delete_title_text)
                .setPositiveButton(R.string.ok_text, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Log.d(TAG, "onClick: user clicked yes");
                        PainEntryData entry = dataset.get(i);
                        Date date = entry.getDate();
                        String path = Methods.generatePainDataFilePathName(Globals.activePatient, entry);
                        FileOperation.deleteEntry(path);
                        dataset.remove(i);
                        Log.d(TAG, "onClick: entry at " + path + " deleted");
                        notifyDataSetChanged();
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

    //Overridden Methods
    @NonNull
    @Override
    public PainEntryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Log.d(TAG, "onCreateViewHolder: Holder view being created.");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_painentryitem, parent, false);
        PainEntryHolder holder = new PainEntryHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PainEntryHolder holder, final int position)
    {
        Log.d(TAG, "onBindViewHolder: Holder binded");
        final PainEntryData data = this.dataset.get(position);
        holder.dateVal.setText(data.getDate().toString(Date.DAY, Date.MONTH, Date.YEAR, "-"));
        holder.time.setText(data.getFullTime());
        holder.intensity.setText(data.getIntensity());
        holder.duration.setText(data.getDuration());
        holder.trigger.setText(data.getTrigger());
        holder.recMed.setText(data.getRecentMedication());
        holder.painKind.setText(data.getPainKind());
        holder.comments.setText(data.getComments());
        holder.delete.setText(HtmlCompat.fromHtml(Methods.giveTextHTMLLinkStyle(context.getString(R.string.delete_text)), HtmlCompat.FROM_HTML_MODE_LEGACY));
        holder.edit.setText(HtmlCompat.fromHtml(Methods.giveTextHTMLLinkStyle(context.getString(R.string.edit_text)), HtmlCompat.FROM_HTML_MODE_LEGACY));

        if (this.selectAll)
        {
            holder.parentLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.recyclerview_item_border_selected));
            holder.isSelected = true;
        }
        else
        {
            holder.parentLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.recyclerview_item_border));
            holder.isSelected = false;
        }

        holder.edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d(TAG, "onClick: Edit text clicked");
                Globals.activeEntry = data;
                Globals.isNewEntry = false;
                Globals.bottomNavigationView.setSelectedItemId(R.id.navigation_new_entry);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d(TAG, "onClick: Delete text clicked");
                deleteEntryAtIndex(position);
            }
        });

        holder.parentLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //When cell is clicked, select it if has not been selected previously. Otherwise clear that selection.
                selectAll = false;
                holder.isSelected = !holder.isSelected;
                if (holder.isSelected)
                {
                    holder.parentLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.recyclerview_item_border_selected));
                    selectedIndexes.add(position);
                }
                else
                {
                    holder.parentLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.recyclerview_item_border));
                    selectedIndexes.remove(Integer.valueOf(position));
                }

                //TODO: Pop up
            }
        });
    }

    @Override
    public int getItemCount()
    {
        Log.d(TAG, "getItemCount: item count: " + this.dataset.size());
        return this.dataset.size();
    }
}
