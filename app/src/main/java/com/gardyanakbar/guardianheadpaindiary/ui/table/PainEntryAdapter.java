package com.gardyanakbar.guardianheadpaindiary.ui.table;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PainEntryData;

import java.util.List;

import giantsweetroll.date.Date;

public class PainEntryAdapter extends RecyclerView.Adapter<PainEntryAdapter.PainEntryHolder>
{
    //Fields
    private static final String TAG = "PainEntryAdapter";
    private List<PainEntryData> dataset;

    //Inner Class
    public static class PainEntryHolder extends RecyclerView.ViewHolder
    {
        public TextView dateVal, time, painKind, intensity, duration, trigger, recMed, comments;
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
            parentLayout = v.findViewById(R.id.parent_layout);
            Log.d(TAG, "PainEntryHolder: PainEntryHolder initialized");
        }
    }

    //Constructor
    public PainEntryAdapter(List<PainEntryData> dataset)
    {
        this.dataset = dataset;
        Log.d(TAG, "PainEntryAdapter: data size: " + dataset.size());
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
    public void onBindViewHolder(@NonNull PainEntryHolder holder, int position)
    {
        Log.d(TAG, "onBindViewHolder: Holder binded");
        PainEntryData data = this.dataset.get(position);
        holder.dateVal.setText(data.getDate().toString(Date.DAY, Date.MONTH, Date.YEAR, "-"));
        holder.time.setText(data.getFullTime());
        holder.intensity.setText(data.getIntensity());
        holder.duration.setText(data.getDuration());
        holder.trigger.setText(data.getTrigger());
        holder.recMed.setText(data.getRecentMedication());
        holder.painKind.setText(data.getPainKind());
        holder.comments.setText(data.getComments());

        holder.parentLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
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
