package com.example.appgooutec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.appgooutec.DTO.PlaceDTO;
import com.example.appgooutec.Singleton.Icons;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.appgooutec.Adapters.AdapterPlaces;


public class Places_Region extends AppCompatActivity {

    private ArrayList<PlaceDTO> arrayPlaces=new ArrayList<PlaceDTO>();
    private ArrayList<Drawable> arrayImgPlaces=new ArrayList<Drawable>();
    private String region;
    private int downloaded=0;
    private Context con;
    private ImageView btnBack;
    RecyclerView recycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_region);

        con=getApplicationContext();
        recycler=(RecyclerView) findViewById(R.id.rv_places);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        btnBack=findViewById(R.id.btn_return3);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Intent Recover
        Intent intent = getIntent();
        String message = intent.getStringExtra("region");
        region=message.toLowerCase();
        if(region.equals("amazonia")){
            region="amazonia2";
        }
        getPlaces(region);

    }

    public void getPlaces(String colName){
        Log.i("fb-p","Getting, from: "+colName);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection(colName).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PlaceDTO p1=document.toObject(PlaceDTO.class);
                                arrayPlaces.add(p1);
                            }
                            //Descarga de Imagenes
                            downloadImages(arrayPlaces,arrayImgPlaces);

                        } else {
                            Log.i("fb-p", "Error getting documents: ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                        Log.i("fb-p","Internet Connection Error");
                    }
                });

    }

    public void downloadImages(ArrayList<PlaceDTO> arrayPlaces,ArrayList<Drawable> arrayImgPlaces) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        for(PlaceDTO dto:arrayPlaces){
            String source=dto.imagen.trim();
            StorageReference islandRef = storage.getReferenceFromUrl(source);
            Log.i("fb-p","Source:"+source);
            islandRef.getBytes(1024 * 1024).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception exception) {
                    Log.i("fb-p","Not Downloaded");
                }
            }).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                @Override
                public void onComplete(Task<byte[]> task) {
                    byte[] img=task.getResult();
                    Log.i("fb-p","Image downloaded: "+img);


                    Drawable image = new BitmapDrawable(BitmapFactory.decodeByteArray(img,  0, img.length));
                    arrayImgPlaces.add(image);
                    downloaded++;
                    int size=arrayImgPlaces.size();
                    if(downloaded==arrayPlaces.size()){
                        //Launch RecycleView
                        adapterPlaces();

                    }
                }
            });
        }


    }
    public void adapterPlaces(){
        Log.i("fb-p",String.valueOf(arrayPlaces.size())+String.valueOf(arrayImgPlaces.size()));

        AdapterPlaces adapter=new AdapterPlaces(arrayPlaces,arrayImgPlaces,con,region);
        recycler.setAdapter(adapter);
    }
}