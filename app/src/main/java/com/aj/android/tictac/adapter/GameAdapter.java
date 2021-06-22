package com.aj.android.tictac.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.aj.android.tictac.R;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.MyViewHolder> {
    int count;
    onCLick onCLick;
    int width;

    public GameAdapter(int count, int length, onCLick onCLick) {
        this.count = count;
        this.onCLick = onCLick;
        this.width = length;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams((width - 90) / 3, (width - 90) / 3));
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCLick.OnClickListener(position, v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageButton view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.element);
        }
    }

    public interface onCLick {
        void OnClickListener(int position, View view);
    }
}
