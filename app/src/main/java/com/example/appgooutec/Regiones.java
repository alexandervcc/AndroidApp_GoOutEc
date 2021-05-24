package com.example.appgooutec;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.appgooutec.Singleton.Icons;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.appgooutec.Adapters.AdapterRegions;

public class Regiones extends AppCompatActivity {

    private ArrayList<String> regions= new ArrayList<String>();
    private ArrayList<String> desc= new ArrayList<String>();
    private ArrayList<Drawable> regionImages=new ArrayList<Drawable>();
    private ImageView btnBack;
    private boolean tienepermisos=false;

    RecyclerView recycler;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Permisos de Mapa
        int permisosFineLocation= ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        tienepermisos=permisosFineLocation== PackageManager.PERMISSION_GRANTED;
        String[] permisos= {Manifest.permission.ACCESS_FINE_LOCATION};
        if(tienepermisos){
            Log.i("fb-m","Tiene Permisos de Mapa");
        }else {
            ActivityCompat.requestPermissions(this, permisos, 1);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiones);
        Context con=getApplicationContext();
        recycler=findViewById(R.id.rv_regiones);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        btnBack=findViewById(R.id.btn_return2);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        regions.add("Costa");
        desc.add("La costa se encuentra a 500 metros sobre el nivel del mar. Bordea\n" +
                "el océano Pacífico, con tramos de 300 millas de playas, bosques de manglares y\n" +
                "pequeños pueblos de pescadores.");
        regions.add("Sierra");
        desc.add("La región inter-andina se extiende de norte a sur por los Andes del\n" +
                "Ecuador. Se caracteriza por sus elevaciones montañosas, volcanes y nevados.");
        regions.add("Amazonia");
        desc.add("La región amazónica del Ecuador, también llamada “el pulmón de la\n" +
                "tierra” es una región natural conformada por un área aproximada de 120.000km de\n" +
                "Amazonía. Se extiende por un área de exuberante vegetación y un clima húmedo\n" +
                "tropical.");
        regions.add("Insular");
        desc.add("Galápagos está compuesto por 13 islas principales, 6 islas\n" +
                "medianas y 40 islotes pequeños que brotaron a partir de varias erupciones\n" +
                "volcánicas.");


        regionImages.add(getResources().getDrawable( R.drawable.region_costa ));
        regionImages.add(getResources().getDrawable( R.drawable.region_sierra ));
        regionImages.add(getResources().getDrawable( R.drawable.region_ama ));
       regionImages.add(getResources().getDrawable( R.drawable.region_insular ));

        AdapterRegions adapter=new AdapterRegions(regions,desc,regionImages,con);
        recycler.setAdapter(adapter);

        instanceSingleton();

    }

    public void instanceSingleton(){
        //Fill Icons
        HashMap<String, Drawable> iconsMap=new HashMap<String, Drawable>();
        iconsMap.put("arqueologia",getResources().getDrawable(R.drawable.arqueologia));
        iconsMap.put("ave",getResources().getDrawable(R.drawable.ave));
        iconsMap.put("aventura",getResources().getDrawable(R.drawable.aventura));
        iconsMap.put("ballena",getResources().getDrawable(R.drawable.ballena));
        iconsMap.put("bici",getResources().getDrawable(R.drawable.bici));
        iconsMap.put("bote",getResources().getDrawable(R.drawable.bote));
        iconsMap.put("buceo",getResources().getDrawable(R.drawable.buceo));
        iconsMap.put("buceotanque",getResources().getDrawable(R.drawable.buceotanque));
        iconsMap.put("caballo",getResources().getDrawable(R.drawable.caballo));
        iconsMap.put("cacao",getResources().getDrawable(R.drawable.cacao));
        iconsMap.put("camara",getResources().getDrawable(R.drawable.camara));
        iconsMap.put("caminata",getResources().getDrawable(R.drawable.caminata));
        iconsMap.put("camping",getResources().getDrawable(R.drawable.camping));
        iconsMap.put("canoa",getResources().getDrawable(R.drawable.canoa));
        iconsMap.put("cascada",getResources().getDrawable(R.drawable.cascadas));
        iconsMap.put("ciclismo",getResources().getDrawable(R.drawable.ciclismo));
        iconsMap.put("columpio",getResources().getDrawable(R.drawable.columpio));
        iconsMap.put("discoteca",getResources().getDrawable(R.drawable.discoteca));
        iconsMap.put("flamenco",getResources().getDrawable(R.drawable.flamenco));
        iconsMap.put("hamaca",getResources().getDrawable(R.drawable.hamaca));
        iconsMap.put("kayak",getResources().getDrawable(R.drawable.kayak));
        iconsMap.put("mariposa",getResources().getDrawable(R.drawable.mariposa));
        iconsMap.put("mirador",getResources().getDrawable(R.drawable.mirador));
        iconsMap.put("nadar",getResources().getDrawable(R.drawable.nadar));
        iconsMap.put("naturaleza",getResources().getDrawable(R.drawable.naturaleza));
        iconsMap.put("orquidea",getResources().getDrawable(R.drawable.orquidea));
        iconsMap.put("panga",getResources().getDrawable(R.drawable.panga));
        iconsMap.put("parapente",getResources().getDrawable(R.drawable.parapente));
        iconsMap.put("pesca",getResources().getDrawable(R.drawable.pesca));
        iconsMap.put("piscina",getResources().getDrawable(R.drawable.piscina));
        iconsMap.put("playa",getResources().getDrawable(R.drawable.playa));
        iconsMap.put("quimica",getResources().getDrawable(R.drawable.quimica));
        iconsMap.put("rafting",getResources().getDrawable(R.drawable.rafting));
        iconsMap.put("selva",getResources().getDrawable(R.drawable.selva));
        iconsMap.put("senderismo",getResources().getDrawable(R.drawable.senderismo));
        iconsMap.put("surf",getResources().getDrawable(R.drawable.surf));
        iconsMap.put("tortuga",getResources().getDrawable(R.drawable.tortuga));
        iconsMap.put("trekking",getResources().getDrawable(R.drawable.trekking));
        iconsMap.put("tren",getResources().getDrawable(R.drawable.tren));
        iconsMap.put("visita",getResources().getDrawable(R.drawable.visita));
        iconsMap.put("windsurf",getResources().getDrawable(R.drawable.windsurfing));

        Icons icons=new Icons(iconsMap);
        Log.i("fb-a","Icon Main: "+icons);
    }

    /*
    public void getRegions(Context con){

    FirebaseFirestore db=FirebaseFirestore.getInstance();

    Log.i("fb-r","Making Query");
    db.collection("regiones").get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    Log.i("fb-r","Completed Task");
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.i("fb-region", document.getId() + " => " + document.getData());

                        }
                    } else {
                        Log.i("fb-region", "Error getting documents: ", task.getException());
                    }
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(con,"Internet Connection Error",Toast.LENGTH_SHORT);
                }
            });

}
*/
}