package com.example.appgooutec.Adapters;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appgooutec.R;
import com.example.appgooutec.Singleton.Icons;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterActivity  extends RecyclerView.Adapter<AdapterActivity.ViewHolderDatos>{
    private ArrayList<String> activities;
    private ArrayList<String > imgActivities;
    private HashMap<String,Drawable> icons;

    public AdapterActivity(ArrayList<String> activities,ArrayList<String> imgActivities){
        this.activities=activities;
        this.imgActivities=imgActivities;
        icons= Icons.getInstance();
        Log.i("fb-a","Icon: "+icons+"  -  Icon1: "+icons.get(0));
    }

    @Override
    public AdapterActivity.ViewHolderDatos onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activities_iterator,null,false);
        return new AdapterActivity.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(AdapterActivity.ViewHolderDatos holder, int position) {
        holder.asignardatos(activities.get(position),imgActivities.get(position));
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView tvActivity;
        ImageView ivIcono;

        public ViewHolderDatos(View itemView) {
            super(itemView);
            tvActivity=(TextView) itemView.findViewById(R.id.tv_activity_item);
            ivIcono=(ImageView) itemView.findViewById(R.id.iv_icon);
        }

        public void asignardatos(String activity,String icon) {
            tvActivity.setText(activity);
            ivIcono.setImageDrawable(icons.get(icon));
        }
    }
}