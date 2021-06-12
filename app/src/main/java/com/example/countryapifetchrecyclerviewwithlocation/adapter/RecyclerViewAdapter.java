package com.example.countryapifetchrecyclerviewwithlocation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.countryapifetchrecyclerviewwithlocation.databinding.RowLayoutBinding;
import com.example.countryapifetchrecyclerviewwithlocation.helper.CommonFunctions;
import com.example.countryapifetchrecyclerviewwithlocation.model.Country;
import com.example.countryapifetchrecyclerviewwithlocation.viewholder.ViewHolder;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    CommonFunctions functions = new CommonFunctions();

    private ArrayList<Country> countryArrayList;
    private Context context;

    public RecyclerViewAdapter(ArrayList<Country> countryArrayList1, Context context) {
        this.countryArrayList = countryArrayList1;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowLayoutBinding binding = RowLayoutBinding.inflate(LayoutInflater.from(this.context), parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Country country = this.countryArrayList.get(position);
        holder.binder(context, country);
    }

    @Override
    public int getItemCount() {
        return this.countryArrayList.size();
    }
}