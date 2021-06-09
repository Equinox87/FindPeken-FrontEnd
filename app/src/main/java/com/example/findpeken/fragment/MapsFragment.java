package com.example.findpeken.fragment;

import android.animation.ObjectAnimator;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.example.findpeken.PasarDetail;
import com.example.findpeken.R;
import com.example.findpeken.adapter.ListPasarMapAdapter;
import com.example.findpeken.api.APIClient;
import com.example.findpeken.api.ApiInterface;
import com.example.findpeken.model.ClusterMarker;
import com.example.findpeken.model.PasarModel;
import com.example.findpeken.model.Value;
import com.example.findpeken.util.ClusterRender;
import com.example.findpeken.util.IPasarLocate;
import com.example.findpeken.util.WrapperMapAnimation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MapsFragment extends Fragment implements OnMapReadyCallback, ClusterManager.OnClusterItemInfoWindowClickListener<ClusterMarker> , IPasarLocate, SwipeRefreshLayout.OnRefreshListener {
    public GoogleMap googleMaps;
    private static final String TAG = "MapsFragment";
    private  SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ArrayList<PasarModel> pasarModelArrayList;
    private ClusterManager<ClusterMarker> clusterManager;
    private ClusterRender clusterRender;
    private ArrayList<ClusterMarker> clusterMarkerArrayList= new ArrayList<>();
    private RelativeLayout mMapContainer;
    private static final int MAP_CONTRACTED = 0;
    private static final int MAP_EXPANDED = 1;
    private ApiInterface mApiInterface;
    private int mapLayout = 0;
    private ImageView btnScreen;
    String myValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View maps=inflater.inflate(R.layout.fragment_maps, container, false);
        recyclerView= (RecyclerView)maps.findViewById(R.id.pasarmaplist);
        mMapContainer= maps.findViewById(R.id.map_container);
        btnScreen= maps.findViewById(R.id.btn_fullscreen);
        btnScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mapLayout == MAP_CONTRACTED){
                    mapLayout = MAP_EXPANDED;
                    expandMapAnimation();
                }
                else if(mapLayout == MAP_EXPANDED){
                    mapLayout = MAP_CONTRACTED;
                    contractMapAnimation();
                }
            }
        });
        mApiInterface= APIClient.getClient().create(ApiInterface.class);
        Bundle bundle = this.getArguments();
        if (bundle!=null){
            myValue = bundle.getString("mapid");
        }
        initPasarData();




        return maps ;
    }

    private void initPasarData() {
        try {
            Call<Value> call= mApiInterface.view();
            call.enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    pasarModelArrayList= response.body().getData();
                    if (myValue!=null && !myValue.equals("")){
                        ArrayList<PasarModel> newarray= new ArrayList<>();
                    for (PasarModel pasarModel : pasarModelArrayList){
                        int mapid=Integer.parseInt(myValue);
                        if (mapid==pasarModel.getPasar_id()){
                            newarray.add(pasarModel);
                            ListPasarMapAdapter listPasarMapAdapter = new ListPasarMapAdapter(getActivity(), newarray,MapsFragment.this );
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            listPasarMapAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(listPasarMapAdapter);
                        }

                    }

                    }else{
                        ListPasarMapAdapter listPasarMapAdapter = new ListPasarMapAdapter(getActivity(), pasarModelArrayList,MapsFragment.this );
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        listPasarMapAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(listPasarMapAdapter);
                    }
                    openMap();

                }

                @Override
                public void onFailure(Call<Value> call, Throwable t) {
                    Log.d(TAG, "onFailure: failed load data! : "+t.getMessage());

                }
            });

        }catch (Exception e){
            Log.d(TAG, "loadData: Load Data Failed !!, error: "+e.getMessage());

        }



    }

    private void openMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
            googleMaps=googleMap;
             addMarker();
            googleMap.setOnInfoWindowClickListener(clusterManager);
            clusterManager.setOnClusterItemInfoWindowClickListener(this);



    }

    private void addMarker() {
        try {
            clusterManager= new ClusterManager<ClusterMarker>(getActivity().getApplicationContext(), googleMaps);
            clusterRender= new ClusterRender(
                    getActivity().getApplicationContext(), googleMaps, clusterManager
            );
            clusterManager.setRenderer(clusterRender);
        }catch (Exception e){
            Log.e(TAG, "ClusterManager-AddMarker: "+e.getMessage() );
        }

        for(PasarModel pasarModel: pasarModelArrayList){
        try{
            if (myValue!=null && !myValue.equals("")){
                String snippet = pasarModel.getPasar_alamat();
                int avatar = R.drawable.ic_pasar;
                ClusterMarker newClusterMarker = new ClusterMarker(
                        new LatLng(pasarModel.getLatitude(), pasarModel.getLongitude()),
                        pasarModel.getPasar_nama(),
                        snippet,
                        avatar,
                        pasarModel
                );
                int mapid=Integer.parseInt(myValue);
                if (pasarModel.getPasar_id()==mapid){
                    clusterManager.addItem(newClusterMarker);
                    clusterMarkerArrayList.add(newClusterMarker);
                    googleMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            (new LatLng(pasarModel.getLatitude(),pasarModel.getLongitude())
                            ),12));
                }
            }else {
                String snippet = pasarModel.getPasar_alamat();
                int avatar = R.drawable.ic_pasar;
                ClusterMarker newClusterMarker = new ClusterMarker(
                        new LatLng(pasarModel.getLatitude(), pasarModel.getLongitude()),
                        pasarModel.getPasar_nama(),
                        snippet,
                        avatar,
                        pasarModel
                );
                clusterManager.addItem(newClusterMarker);
                clusterMarkerArrayList.add(newClusterMarker);
                googleMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        (new LatLng(pasarModel.getLatitude(), pasarModel.getLongitude())
                        ), 12));
            }
            }catch (NullPointerException e){
                Log.e(TAG, "addMapMarkers: NullPointerException: " + e.getMessage() );
            }
        }
        clusterManager.cluster();
    }





    //===================== ANIMASI
    private void expandMapAnimation(){
        WrapperMapAnimation mapAnimationWrapper = new WrapperMapAnimation(mMapContainer);
        ObjectAnimator mapAnimation = ObjectAnimator.ofFloat(mapAnimationWrapper,
                "weight",
                50,
                100);
        mapAnimation.setDuration(500);

        WrapperMapAnimation recyclerAnimationWrapper = new WrapperMapAnimation(recyclerView);
        ObjectAnimator recyclerAnimation = ObjectAnimator.ofFloat(recyclerAnimationWrapper,
                "weight",
                50,
                0);
        recyclerAnimation.setDuration(500);

        recyclerAnimation.start();
        mapAnimation.start();
    }
    private void contractMapAnimation(){
        WrapperMapAnimation mapAnimationWrapper = new WrapperMapAnimation(mMapContainer);
        ObjectAnimator mapAnimation = ObjectAnimator.ofFloat(mapAnimationWrapper,
                "weight",
                100,
                50);
        mapAnimation.setDuration(500);

        WrapperMapAnimation recyclerAnimationWrapper = new WrapperMapAnimation(recyclerView);
        ObjectAnimator recyclerAnimation = ObjectAnimator.ofFloat(recyclerAnimationWrapper,
                "weight",
                0,
                50);
        recyclerAnimation.setDuration(500);

        recyclerAnimation.start();
        mapAnimation.start();
    }

    @Override
    public void onClick(int id) {
        try{
            for (ClusterMarker clusterMarker: clusterMarkerArrayList){
                if(id==clusterMarker.getPasarModel().getPasar_id()){
                    googleMaps.animateCamera(CameraUpdateFactory.newLatLng(
                            new LatLng(clusterMarker.getPosition().latitude,clusterMarker.getPosition().longitude)
                    ), 600, null);
                    break;
                }
            }

        }catch (Exception e){
        }

    }

    @Override
    public void onRefresh() {
        initPasarData();

    }

    @Override
    public void onClusterItemInfoWindowClick(ClusterMarker clusterMarker) {
            if (clusterMarker.getPasarModel()!=null){
                Intent pasarDetail= new Intent(getActivity(), PasarDetail.class);
                pasarDetail.putExtra("pasarmodel",clusterMarker.getPasarModel());
                startActivity(pasarDetail);
            }

    }
}