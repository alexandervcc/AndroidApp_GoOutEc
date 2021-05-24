package com.example.appgooutec.Singleton;

import android.graphics.drawable.Drawable;

import com.example.appgooutec.R;

import java.util.HashMap;

public class Icons {

    private static HashMap<String, Drawable> iconsMap=null;


    public Icons(HashMap<String, Drawable> iconsMap){
        this.iconsMap=iconsMap;
    }

    public static HashMap<String,Drawable> getInstance(){
        return iconsMap;
    }
}
