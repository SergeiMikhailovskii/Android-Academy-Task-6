package com.mikhailovskii.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private final List<Note> notes;
    private OnItemClickListener<Note> onItemClickListener;

    RecyclerViewAdapter(List<Note> notes, Context context){
        this.notes = notes;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.recycler_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(itemView);

        viewHolder.itemView.setOnClickListener((view)->{
            int position = viewHolder.getAdapterPosition();
            if (position!=RecyclerView.NO_POSITION){
                fireItemClicked(position, notes.get(position));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.title.setText(note.getTitle());
        holder.body.setText(note.getBody());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    private void fireItemClicked(int position, Note note){
        if (onItemClickListener!=null){
            onItemClickListener.onItemClicked(position, note);
        }
    }

    void setOnItemClickListener(OnItemClickListener<Note> listener){
        onItemClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView title;
        final TextView body;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_textview);
            body = itemView.findViewById(R.id.body_textview);
        }
    }

    public interface OnItemClickListener<T>{
        void onItemClicked(int position, T item);
    }

}