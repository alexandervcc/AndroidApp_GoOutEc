package com.example.appgooutec.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.example.appgooutec.Adapters.AdapterActivity;
import com.example.appgooutec.R;

public class FragActivity extends Fragment {

    private ArrayList<String> activities;
    private ArrayList<String> icon;
    private RecyclerView rvActivities;

    public FragActivity() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            activities=getArguments().getStringArrayList("activity");
            icon=getArguments().getStringArrayList("icon");
            Log.i("fb-a","ICon1: "+icon.get(0));
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvActivities=getView().findViewById(R.id.rv_activities);
        rvActivities.setLayoutManager(new LinearLayoutManager(getActivity().getParent()));
        launchRecycle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_activity, container, false);
    }

    public void launchRecycle(){
        AdapterActivity adapter=new AdapterActivity(activities,icon);
        rvActivities.setAdapter(adapter);

    }
}