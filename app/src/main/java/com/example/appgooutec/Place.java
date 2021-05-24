package com.example.appgooutec;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.appgooutec.DTO.PlaceDTO;
import com.example.appgooutec.Fragments.FragActivity;
import com.example.appgooutec.Fragments.FragComent;
import com.example.appgooutec.Fragments.FragMap;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class Place extends AppCompatActivity {
    private ImageView rvImage;
    private TextView tvDesc;
    private PlaceDTO place;
    private Button btnComent;
    private Button btnActivity;
    private Button btnMap;
    private ImageView btnBack;
    private boolean tienepermisos=false;

    Fragment fragmentoActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        //Objects
        rvImage=(ImageView)findViewById(R.id.iv_place_detail);
        tvDesc=findViewById(R.id.tv_place_desc);
        btnActivity=findViewById(R.id.btn_actividades);
        btnComent=findViewById(R.id.btn_comentarios);
        btnMap=findViewById(R.id.btn_map);
        btnBack=findViewById(R.id.btn_return);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnComent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearFragmentoComentario();
            }
        });

        btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearFragmentoActividades();
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearFragmentoMap();

            }
        });

        //DTO Intent

        Intent intent = getIntent();
        String uriPlace = intent.getStringExtra("place");
        String uriRegion = intent.getStringExtra("region");


        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection(uriRegion).whereEqualTo("nombreLugar",uriPlace)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //place=task.getResult().getDocuments().get(0).toObjects(PlaceDTO.class);
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            place=document.toObject(PlaceDTO.class);
                        }

                        Log.i("fb-l",place.nombreLugar);
                        getImage(place);
                    }

                });


    }

    private void getImage(PlaceDTO place){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String source=place.imagen.trim();
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
                rvImage.setImageDrawable(image);
                tvDesc.setText(place.descripcion);

            }
        });
    }

    private void crearFragmentoComentario(){
        FragmentManager fragmentMan= getSupportFragmentManager();
        FragmentTransaction fragmentTran=fragmentMan.beginTransaction();
        FragComent frComent= new FragComent();
        Bundle arg=new Bundle();
        arg.putString("coment",place.comentario);
        frComent.setArguments(arg);

        fragmentTran.replace(R.id.rl_Fragments,frComent);
        fragmentoActual=frComent;
        fragmentTran.commit();
    }

    private void crearFragmentoActividades(){
        FragmentManager fragmentMan= getSupportFragmentManager();
        FragmentTransaction fragmentTran=fragmentMan.beginTransaction();
        FragActivity frActivity= new FragActivity();
        Bundle arg=new Bundle();
        arg.putStringArrayList("activity",place.actividades);
        arg.putStringArrayList("icon",place.icon);
        Log.i("fb-a",place.icon.get(0));
        frActivity.setArguments(arg);

        fragmentTran.replace(R.id.rl_Fragments,frActivity);
        fragmentoActual=frActivity;
        fragmentTran.commit();
    }

    private void crearFragmentoMap(){
        FragmentManager fragmentMan= getSupportFragmentManager();
        FragmentTransaction fragmentTran=fragmentMan.beginTransaction();
        FragMap frMap= new FragMap();
        Bundle arg=new Bundle();
        arg.putString("lat",place.ubicacion.get(0));
        arg.putString("lon",place.ubicacion.get(1));
        arg.putString("place",place.nombreLugar);
        frMap.setArguments(arg);

        fragmentTran.replace(R.id.rl_Fragments,frMap);
        fragmentoActual=frMap;
        fragmentTran.commit();
//        getPermisos();

    }




    public void configMap(GoogleMap map){
        if(map!=null){
            int permisosFineLocation=ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            tienepermisos=permisosFineLocation==PackageManager.PERMISSION_GRANTED;
            if(tienepermisos){
                map.setMyLocationEnabled(true);
            }
            UiSettings uiSettings = map.getUiSettings();
            uiSettings.setZoomControlsEnabled(true);
            uiSettings.setMyLocationButtonEnabled(true);


        }
    }

    public void getPermisos(){
        int permisosFineLocation=ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        tienepermisos=permisosFineLocation==PackageManager.PERMISSION_GRANTED;
        String[] permisos= {Manifest.permission.ACCESS_FINE_LOCATION};
        if(tienepermisos){
            Log.i("fb-m","Tiene Permisos de Mapa");
        }else {
            ActivityCompat.requestPermissions(this, permisos ,1);
        }

    }
}