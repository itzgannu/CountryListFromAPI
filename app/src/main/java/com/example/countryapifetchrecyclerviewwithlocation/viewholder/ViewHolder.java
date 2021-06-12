package com.example.countryapifetchrecyclerviewwithlocation.viewholder;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.countryapifetchrecyclerviewwithlocation.R;
import com.example.countryapifetchrecyclerviewwithlocation.database.FirebaseRepo;
import com.example.countryapifetchrecyclerviewwithlocation.databinding.RowLayoutBinding;
import com.example.countryapifetchrecyclerviewwithlocation.helper.CommonFunctions;
import com.example.countryapifetchrecyclerviewwithlocation.model.Country;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    CommonFunctions functions = new CommonFunctions();
    private final String TAG = this.getClass().getCanonicalName();

    RowLayoutBinding binding;
    Country singleRowData;
    Context context;

    FireViewHolder fireViewHolder;
    Country country = new Country();

    public ViewHolder( RowLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.binding.getRoot().setOnClickListener(this);
    }

    public void binder(Context context, Country country){
        this.context = context;
        this.singleRowData = country;
        String fullName, name, code, capital, flagURL;
        name = this.singleRowData.getCountryName();
        code = this.singleRowData.getCountryCode();
        capital = this.singleRowData.getCountryCapital();
        fullName = name+" , "+code;
        String flagImageURL = "https://www.countryflags.io/"+code+"/flat/64.png";
        Glide.with(context)
                .load(flagImageURL)
                .placeholder(ContextCompat.getDrawable(context, android.R.drawable.ic_media_next))
                .into(binding.rowImage);
        this.binding.rowName.setText(fullName);
        this.binding.rowCapital.setText(capital);
        String favImage = "https://www.iconninja.com/files/425/784/838/like-favorite-heart-love-icon.png";
        Glide.with(context)
                .load(favImage)
                .placeholder(ContextCompat.getDrawable(context, android.R.drawable.btn_star_big_off))
                .into(binding.rowFav);

        // need to bind favURL
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        if(position != RecyclerView.NO_POSITION){
            functions.LogCatD(TAG, "onClick: Position Clicked");
            functions.ToastMessageLong(context, "Added to favorite List");
            //Add to fav list & save to db
            String favImage = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f1/Heart_coraz%C3%B3n.svg/1200px-Heart_coraz%C3%B3n.svg.png";
            Glide.with(context)
                    .load(favImage)
                    .placeholder(ContextCompat.getDrawable(context, android.R.drawable.btn_star_big_off))
                    .into(binding.rowFav);

            //firebase
            FirebaseRepo db = new FirebaseRepo();
            db.addFav(singleRowData);
        }
    }

}
