package com.example.betterhelp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class R_RecyclerViewAdapter extends RecyclerView.Adapter<R_RecyclerViewAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<ReflectionModel> reflectionModels;

    public R_RecyclerViewAdapter(Context context, ArrayList<ReflectionModel> reflectionModels, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.reflectionModels = reflectionModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public R_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new R_RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull R_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.textViewNames.setText(reflectionModels.get(position).getReflectionName());
        holder.textViewDescriptions.setText(reflectionModels.get(position).getReflectionDescription());
    }

    @Override
    public int getItemCount() {
        return reflectionModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

            TextView textViewNames, textViewDescriptions;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            textViewNames = itemView.findViewById(R.id.textViewEmotion);
            textViewDescriptions = itemView.findViewById(R.id.textViewDescription);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }
}
