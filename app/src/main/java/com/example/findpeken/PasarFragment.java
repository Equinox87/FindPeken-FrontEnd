package com.example.findpeken;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findpeken.adapter.PasarAdapter;
import com.example.findpeken.api.APIClient;
import com.example.findpeken.api.ApiInterface;
import com.example.findpeken.model.PasarModel;
import com.example.findpeken.model.Value;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasarFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private  SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView pasarrecycler;
    private RecyclerView.Adapter pasarAdapter;
    private ArrayList<PasarModel> listpasar= new ArrayList<>();
    ApiInterface mApiInterface;
    private static final String TAG = "PasarFragment";

    public PasarFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View pasar=inflater.inflate(R.layout.fragment_pasar, container, false);
        pasarrecycler=(RecyclerView) pasar.findViewById(R.id.listpasar);
        Log.d(TAG, "onCreateView: tampilan awal");
        RecyclerView.LayoutManager mLayoutmanager= new LinearLayoutManager(getActivity().getApplicationContext());
        pasarrecycler.setLayoutManager(mLayoutmanager);
        pasarrecycler.setHasFixedSize(true);
        pasarrecycler.setItemViewCacheSize(20);
        pasarrecycler.setDrawingCacheEnabled(true);
        mApiInterface= APIClient.getClient().create(ApiInterface.class);
        swipeRefreshLayout = (SwipeRefreshLayout) pasar.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
            swipeRefreshLayout.setRefreshing(true);
            loadData();
            }
        });


        return pasar ;

    }


    private void loadData() {
        try {
            Call<Value> call= mApiInterface.view();
            call.enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    listpasar= response.body().getData();
                    pasarAdapter = new PasarAdapter(listpasar);
                    pasarAdapter.notifyDataSetChanged();
                    pasarrecycler.setAdapter(pasarAdapter);
                    swipeRefreshLayout.setRefreshing(false);

                }

                @Override
                public void onFailure(Call<Value> call, Throwable t) {
                    Log.d(TAG, "onFailure: failed load data! : "+t.getMessage());
                    swipeRefreshLayout.setRefreshing(false);
                }
            });

        }catch (Exception e){
            Log.d(TAG, "loadData: Load Data Failed !!, error: "+e.getMessage());
            swipeRefreshLayout.setRefreshing(false);
        }


    }

    @Override
    public void onRefresh() {
        loadData();

    }
}