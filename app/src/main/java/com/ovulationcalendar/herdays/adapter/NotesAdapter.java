package com.ovulationcalendar.herdays.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ovulationcalendar.herdays.R;
import com.ovulationcalendar.herdays.data.NotesItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by mleano on 3/15/2016.
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesHolder> {

    private LayoutInflater inflater;
    List<NotesItem> data = Collections.emptyList();
    Context context;

    public NotesAdapter(List<NotesItem> data, Context context) {
        this.data = data;
        this.context = context;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public NotesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.notes_item, parent, false);
        NotesHolder holder = new NotesHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NotesHolder holder, int position) {

        NotesItem current = data.get(position);

        holder.tvDate.setText(current.getDate());
        holder.tvMessage.setText(current.getMessage());
        holder.tvTitle.setText(current.getTitle());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NotesHolder extends RecyclerView.ViewHolder {

        TextView tvDate, tvTitle, tvMessage;

        public NotesHolder(View itemView) {
            super(itemView);

            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvMessage = (TextView) itemView.findViewById(R.id.tvMessage);
        }
    }
}
