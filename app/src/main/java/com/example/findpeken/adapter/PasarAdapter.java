package com.example.findpeken.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.findpeken.PasarDetail;
import com.example.findpeken.R;
import com.example.findpeken.model.PasarModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PasarAdapter extends RecyclerView.Adapter<PasarAdapter.ViewHolder> {

    public ArrayList<PasarModel> arrayPasar;
    Context context;

    public PasarAdapter(Context context, ArrayList<PasarModel> arrayPasar) {
        this.context=context;
        this.arrayPasar = arrayPasar;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.pasar_cardview,parent,false);
        ViewHolder views= new ViewHolder(view);
        return views;
    }

    @Override
    public void onBindViewHolder( PasarAdapter.ViewHolder holder, int position) {
    String deskripsi= arrayPasar.get(position).getPasar_deskripsi();
    String alamat= arrayPasar.get(position).getPasar_alamat();
    double latitude= arrayPasar.get(position).getLatitude();
    double longitude= arrayPasar.get(position).getLongitude();
    String judul= arrayPasar.get(position).getPasar_nama();
    String gambar= arrayPasar.get(position).getPasar_gambar();
    holder.pasardeskripsi.setText(deskripsi);
    holder.pasarjudul.setText(judul);
        Glide.with(context).asBitmap().fitCenter()
                .load(gambar)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady( Bitmap resource,  Transition<? super Bitmap> transition) {
                        holder.pasarimage.setImageBitmap(resource);
                    }
                });
    holder.btndetail.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent detailintent= new Intent(view.getContext(), PasarDetail.class);
            PasarModel pasarModel=arrayPasar.get(position);
            detailintent.putExtra("pasarmodel",pasarModel);
            view.getContext().startActivity(detailintent);
        }
    });

    }

    @Override
    public int getItemCount() {
        return arrayPasar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView pasarjudul, pasardeskripsi;
        private CircleImageView pasarimage;
        private Button btndetail;
        public ViewHolder( View itemView) {
            super(itemView);
        pasarimage=(CircleImageView) itemView.findViewById(R.id.pasarimage);
        pasarjudul=(TextView) itemView.findViewById(R.id.pasarjudul);
        pasardeskripsi=(TextView) itemView.findViewById(R.id.pasardeskripsi);
        btndetail=(Button) itemView.findViewById(R.id.pasardetail);
        }
    }
}
