package com.example.weather2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MapsFragment.Fragment1listener{

    public MapsFragment mapsFragment;
    public Fragment2 fragment2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapsFragment = new MapsFragment();
        fragment2 = new Fragment2();

        getSupportFragmentManager().beginTransaction().add(R.id.container_map, mapsFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.container_fragment , fragment2).commit();

    }


    @Override
    public void sendReport1(Report report) {
        fragment2.setdetails(report);
    }
}