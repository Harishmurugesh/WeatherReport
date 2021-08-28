package com.example.weather2;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    TextView description;
    TextView speed;
    TextView humidity;
    TextView pressure;
    TextView temp;
    TextView mmtemp;
    TextView name;
    TextView latlon;
    TextToSpeech speak;
    ImageButton mic;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_2, container, false);

        description = v.findViewById(R.id.description);
        speed = v.findViewById(R.id.speed);
        humidity = v.findViewById(R.id.humidity);
        pressure = v.findViewById(R.id.pressure);
        temp = v.findViewById(R.id.temp);
        mmtemp = v.findViewById(R.id.minmax);
        name = v.findViewById(R.id.name);
        latlon = v.findViewById(R.id.latlon);
        mic = v.findViewById(R.id.mic);

        return v;
    }


    public void setdetails(Report report) {

        String s = "";

        description.setText(report.getWeather().get(0).getDescription());
        speed.setText(report.getWind().getSpeed() + " km/hr");
        humidity.setText(report.getMain().getHumidity() + "%");
        pressure.setText(report.getMain().getPressure() + "hPa");
        temp.setText(report.getMain().getTemp() + "celsius");
        mmtemp.setText(report.getMain().getTemp_min() + "/" + report.getMain().getTemp_max() + "celsius");
        name.setText(report.getName());
        latlon.setText(report.getCoord().get("lat").getAsString() + "/" + report.getCoord().get("lon").getAsString());


        s = "The placed you have touched is " + name.getText().toString() + ".";
        s = s + " The latitude and longitude of this place is " + latlon.getText().toString() + ".";
        s = s + " This place currently has a " + description.getText().toString() + " climate.";
        s = s + " Now, The wind speed here is " + speed.getText().toString() + ".";
        s = s + " Now, The pressure here is " + pressure.getText().toString() + ".";
        s = s + " The average temperature felt here is " + temp.getText().toString() + ".";
        s = s + " The humidity here is " + humidity.getText().toString() + ".";


        String f = s;
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Speak(f);
            }
        });
    }


    public void Speak(String string){


        Log.i("Tag" , "hi");
        speak = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result = speak.setLanguage(Locale.ENGLISH);

                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("Tag", "Language not supported");
                    }
                }else{
                    Log.e("Tag","");
                }
            }
        });



        speak.speak(string, TextToSpeech.QUEUE_ADD , null);

    }



}