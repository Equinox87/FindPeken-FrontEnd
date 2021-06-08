package com.example.findpeken.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findpeken.R;
import com.example.findpeken.model.PasarModel;
import com.example.findpeken.util.IPasarLocate;

import java.util.ArrayList;

public class ListPasarMapAdapter extends RecyclerView.Adapter<ListPasarMapAdapter.ViewHolder> {
    Context context;
    ArrayList<PasarModel> pasarModelArrayList;
    IPasarLocate iPasarLocate;

    public ListPasarMapAdapter(Context context, ArrayList<PasarModel> pasarModelArrayList, IPasarLocate iPasarLocate) {
        this.context = context;
        this.pasarModelArrayList = pasarModelArrayList;
        this.iPasarLocate = iPasarLocate;
    }

    @Override
    public ListPasarMapAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.pasarlocation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ListPasarMapAdapter.ViewHolder holder, int position) {
        holder.pasarnama.setText(pasarModelArrayList.get(position).getPasar_nama());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iPasarLocate.onClick(pasarModelArrayList.get(position).getPasar_id());
            }
        });

    }

    @Override
    public int getItemCount() {
        return pasarModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pasarnama;
        public ViewHolder( View itemView) {
            super(itemView);
            pasarnama=(TextView) itemView.findViewById(R.id.pasarlocationaname);

        }
    }
}
