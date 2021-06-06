package com.example.findpeken.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.findpeken.R;
import com.example.findpeken.model.PasarModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PasarAdapter extends RecyclerView.Adapter<PasarAdapter.ViewHolder> {

    public ArrayList<PasarModel> arrayPasar;

    public PasarAdapter(ArrayList<PasarModel> arrayPasar) {
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
    String judul= arrayPasar.get(position).getPasar_nama();
    String gambar= arrayPasar.get(position).getPasar_gambar();
    holder.pasardeskripsi.setText(deskripsi);
    holder.pasarjudul.setText(judul);
    Picasso.get().load(gambar).noFade().fit().into(holder.pasarimage);

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
