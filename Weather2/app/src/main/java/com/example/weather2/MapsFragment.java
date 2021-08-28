package com.example.weather2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsFragment extends Fragment {

    public GoogleMap GoogleMap;
    public Location current;
    public Report reportDetails;


    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            GoogleMap = googleMap;
            GoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull LatLng latLng) {
                    GoogleMap.clear();
                    GoogleMap.addMarker(new MarkerOptions().position(latLng));
                    GoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                    getDetails(latLng.latitude , latLng.longitude);
                }
            });

        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    public Fragment1listener listener;
    public FusedLocationProviderClient client;
    public final int CODE = 5;
    public Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public ApiInterface apiInterface = retrofit.create(ApiInterface.class);

    String s = "66d3c40e41923675b227ef88bb92ffa0";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View v =  inflater.inflate(R.layout.fragment_maps, container, false);
       client = LocationServices.getFusedLocationProviderClient(getContext());

       getPermission();
      return v;
    }


    public void getPermission(){


        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},CODE);

        }
        else
        {
           Task<Location> task = client.getLastLocation();
           task.addOnSuccessListener(new OnSuccessListener<Location>() {
               @SuppressLint("MissingPermission")
               @Override
               public void onSuccess(Location location) {
                      if(location != null){
                          current = location;
                          getDetails(current.getLatitude(), current.getLongitude());
                          LatLng mine = new LatLng(current.getLatitude() , current.getLongitude());
                          GoogleMap.addMarker(new MarkerOptions().position(mine));
                          GoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mine,10));
                      }
                      else{
                          LocationRequest locationRequest = LocationRequest.create();
                          locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                          locationRequest.setInterval(10000);
                          locationRequest.setFastestInterval(10000 / 2);

                          LocationCallback locationCallback = new LocationCallback() {
                              @Override
                              public void onLocationResult(@NonNull LocationResult locationResult) {

                                  current = locationResult.getLastLocation();
                                  getDetails(current.getLatitude(), current.getLongitude());
                                  LatLng mine = new LatLng(current.getLatitude() , current.getLongitude());
                                  GoogleMap.addMarker(new MarkerOptions().position(mine));
                                  GoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mine,10));
                                  super.onLocationResult(locationResult);
                              }
                          };
                      client.requestLocationUpdates(locationRequest , locationCallback , Looper.myLooper());
                      }
               }
           });
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CODE)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                getPermission();
            }
            else
            {
                ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},CODE);

            }
        }
    }



     public void getDetails(double x , double y){



         Call<Report> call = apiInterface.getReport(x , y , s);
         call.enqueue(new Callback<Report>() {
             @Override
             public void onResponse(Call<Report> call, Response<Report> response) {
                 if (response.isSuccessful()) {
                     Log.i("tag", "" + response.body().toString());
                     Log.i("tag", "" + response.code());
                     reportDetails = response.body();
                     listener.sendReport1(reportDetails);

                 }
             }

             @Override
             public void onFailure(Call<Report> call, Throwable t) {
                 Log.i("tag", "hji");

             }
         });

    }




    interface Fragment1listener{
        void sendReport1(Report report);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof Fragment1listener){
            listener = (Fragment1listener) context;
        }else{
            throw new RuntimeException(context.toString() + "implement");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


}