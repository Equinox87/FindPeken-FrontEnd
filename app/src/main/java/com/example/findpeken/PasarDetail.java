package com.example.findpeken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.findpeken.model.PasarModel;

import com.squareup.picasso.Picasso;

public class PasarDetail extends AppCompatActivity   {
   private ImageView detailimage;
   private TextView detailnama, detailalamat, detaildeskripsi, detaillatitude, detaillongitude, pasarid;
   private PasarModel pasarModel;
   private Button btnmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasar_detail);
//        Toolbar toolbar = findViewById(R.id.toolbar_pasar_detail);
//        setSupportActionBar(toolbar);

        detailimage=(ImageView) findViewById(R.id.detailpasarimage);
        pasarid=(TextView) findViewById(R.id.pasarid);
        detailnama=(TextView) findViewById(R.id.detailpasarnama);
        detailalamat=(TextView) findViewById(R.id.detailpasaralamat);
        detaildeskripsi=(TextView) findViewById(R.id.detailpasardeskripsi);
        detaillatitude=(TextView) findViewById(R.id.detailpasarlatitude);
        detaillongitude=(TextView) findViewById(R.id.detailpasarlongitude);
        btnmap=(Button) findViewById(R.id.mapredirect);
        loaddata();

        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home= new Intent(PasarDetail.this, Home.class);
                home.putExtra("key","1");
                home.putExtra("mapid",pasarid.getText().toString());
                startActivity(home);
                finish();
            }
        });
    }

    private void loaddata() {

        if(getIntent().getExtras()!=null){
            pasarModel = (PasarModel) getIntent().getSerializableExtra("pasarmodel");
            detailnama.setText(pasarModel.getPasar_nama());
            detailalamat.setText(pasarModel.getPasar_alamat());
            detaildeskripsi.setText(pasarModel.getPasar_deskripsi());
            detaillatitude.setText(Double.toString(pasarModel.getLatitude()) );
            detaillongitude.setText(Double.toString(pasarModel.getLongitude()) );
            pasarid.setText(Integer.toString(pasarModel.getPasar_id()));
            Picasso.get().load(pasarModel.getPasar_gambar()).noFade().fit().into(detailimage);
        }
    }



}