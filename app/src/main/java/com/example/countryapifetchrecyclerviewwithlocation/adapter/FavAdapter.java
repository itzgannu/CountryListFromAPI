package com.example.countryapifetchrecyclerviewwithlocation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.countryapifetchrecyclerviewwithlocation.databinding.RowLayoutBinding;
import com.example.countryapifetchrecyclerviewwithlocation.helper.CommonFunctions;
import com.example.countryapifetchrecyclerviewwithlocation.model.Country;
import com.example.countryapifetchrecyclerviewwithlocation.viewholder.FavViewHolder;
import com.example.countryapifetchrecyclerviewwithlocation.viewholder.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavAdapter extends RecyclerView.Adapter<FavViewHolder>{

    CommonFunctions functions = new CommonFunctions();

    private List<Country> countryArrayList;
    private Context context;

    public FavAdapter(List<Country> countryArrayList1, Context context) {
        this.countryArrayList = countryArrayList1;
        this.context = context;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowLayoutBinding binding = RowLayoutBinding.inflate(LayoutInflater.from(this.context), parent,false);
        return new FavViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        Country country = this.countryArrayList.get(position);
        holder.binder(context, country);
    }

    @Override
    public int getItemCount() {
        return this.countryArrayList.size();
    }
}
