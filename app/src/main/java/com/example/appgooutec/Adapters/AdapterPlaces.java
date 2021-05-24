package com.example.appgooutec.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appgooutec.DTO.PlaceDTO;
import com.example.appgooutec.Place;
import com.example.appgooutec.R;
import java.util.ArrayList;

public class AdapterPlaces extends RecyclerView.Adapter<AdapterPlaces.ViewHolderDatos>{

    private String region;
    private ArrayList<PlaceDTO> places;
    private ArrayList<Drawable> placesImages;
    private Context con;

    public AdapterPlaces(ArrayList<PlaceDTO> places,ArrayList<Drawable> placesImages,Context con,String region){
        this.places=places;
        this.placesImages=placesImages;
        this.con=con;
        this.region=region;
    }

    @Override
    public AdapterPlaces.ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.place_iterator,null,false);
        return new AdapterPlaces.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(AdapterPlaces.ViewHolderDatos holder, int position) {

        holder.asignardatos(places.get(position).nombreLugar,placesImages.get(position));
        holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Actividad
                Intent intent = new Intent(con, Place.class);
                intent.putExtra("place",places.get(position).nombreLugar);
                intent.putExtra("region",region);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                con.startActivity(intent);





            }
        });

    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView tvPlace;
        TextView tvPlaceLoc;
        ImageView ivPhoto;

        public ViewHolderDatos(View itemView) {
            super(itemView);
            tvPlace=itemView.findViewById(R.id.tv_place_title);
            tvPlaceLoc= itemView.findViewById(R.id.tv_place_location);
            ivPhoto= itemView.findViewById(R.id.iv_place);

        }

        public void asignardatos(String place,Drawable img) {
            tvPlace.setText(place);
            ivPhoto.setImageDrawable(img);

        }
    }
}
