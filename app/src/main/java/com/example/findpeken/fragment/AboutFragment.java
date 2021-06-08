package com.example.findpeken.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.findpeken.R;
import com.example.findpeken.adapter.PasarAdapter;
import com.example.findpeken.api.APIClient;
import com.example.findpeken.api.ApiInterface;
import com.example.findpeken.model.UserModel;
import com.example.findpeken.model.Value;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AboutFragment extends Fragment {
    private TextView emailuser, namauser;
    private CircleImageView imageuser;
    private ApiInterface mApiInterface;

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View about=inflater.inflate(R.layout.fragment_about, container, false);
        emailuser=(TextView)about.findViewById(R.id.emailuser);
        namauser=(TextView)about.findViewById(R.id.namauser);
        imageuser=(CircleImageView)about.findViewById(R.id.user_profile_photo);
        mApiInterface= APIClient.getClient().create(ApiInterface.class);
        loaddata();
        return about ;
    }

    private void loaddata() {
        try {
            Call<UserModel> call= mApiInterface.viewuser();
            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                emailuser.setText(response.body().getEmail());
                namauser.setText(response.body().getName());
                Glide.with(getActivity()).load(response.body().getGambar()).fitCenter().into(imageuser);
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {

                }
            });

        }catch (Exception e){
            Toast.makeText(getActivity(),"Eror Load Data About Me : "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}