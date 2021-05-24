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

import com.example.appgooutec.Places_Region;
import com.example.appgooutec.R;

import java.util.ArrayList;

public class AdapterRegions extends RecyclerView.Adapter<   AdapterRegions.ViewHolderDatos>{

    private ArrayList<String> listData;
    private ArrayList<String> listDesc;
    private ArrayList<Drawable> listImages;
    private Context con;
    public AdapterRegions(ArrayList<String> listData,ArrayList<String> listDesc,ArrayList<Drawable> images,Context con){
        this.listData=listData;
        this.con=con;
        this.listDesc=listDesc;
        this.listImages=images;
    }

    @Override
    public ViewHolderDatos onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.regions_iterator,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder( AdapterRegions.ViewHolderDatos holder, int position) {
        holder.asignardatos(listData.get(position),listDesc.get(position),listImages.get(position));
       holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Actividad
                Intent intent = new Intent(con, Places_Region.class);
                intent.putExtra("region",listData.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                con.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView tvRegion;
        TextView tvRegionDesc;
        ImageView ivPhoto;

        public ViewHolderDatos(View itemView) {
            super(itemView);
            tvRegion=(TextView) itemView.findViewById(R.id.tv_region);
            tvRegionDesc=(TextView) itemView.findViewById(R.id.tv_region_desc);
            ivPhoto=(ImageView) itemView.findViewById(R.id.iv_region);

                }

        public void asignardatos(String region,String desc,Drawable img) {
            tvRegion.setText(region);
            tvRegionDesc.setText(desc);
            ivPhoto.setImageDrawable(img);

        }
    }
}