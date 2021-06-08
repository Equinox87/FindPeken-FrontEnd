package com.example.findpeken.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.findpeken.R;
import com.example.findpeken.model.ClusterMarker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;

import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ClusterRender extends DefaultClusterRenderer<ClusterMarker> {
    private static final String TAG = "ClusterRender";
    private final IconGenerator iconGenerator;
    private ImageView imageView;
    private final  int markerWidth;
    private final  int markerHeight;
    private  Context contexts;
    Bitmap icon;

    public ClusterRender(Context context, GoogleMap map, ClusterManager<ClusterMarker> clusterManager) {
        super(context, map, clusterManager);
        contexts=context;
        iconGenerator = new IconGenerator(context.getApplicationContext());
        imageView= new ImageView(context.getApplicationContext());
        markerHeight= (int) context.getResources().getDimension(R.dimen.custom_marker_image);
        markerWidth= (int) context.getResources().getDimension(R.dimen.custom_marker_image);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(markerWidth, markerHeight));
        int padding= (int) context.getResources().getDimension(R.dimen.custom_marker_padding);
        imageView.setPadding(padding,padding,padding,padding);
        iconGenerator.setContentView(imageView);
    }


    protected void onBeforeClusterItemRendered(final ClusterMarker item, final MarkerOptions markerOptions) {
         icon = iconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(item.getTitle());


    }

    @Override
    protected void onClusterItemRendered(ClusterMarker clusterItem, Marker marker) {
        Glide.with(contexts).asBitmap().fitCenter()
                .load(clusterItem.pasarModel.getPasar_gambar())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady( Bitmap resource,  Transition<? super Bitmap> transition) {
                        imageView.setImageBitmap(resource);
                        icon = iconGenerator.makeIcon();
                        marker.setIcon(BitmapDescriptorFactory.fromBitmap(icon));
                    }
                });

    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster cluster) {
        return false;
    }


}
