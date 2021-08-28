package com.example.weather2;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Report {

    public String name;
    public Sys sys;
    public Wind wind;
    public ArrayList<Weather> weather;
    public Main main;
    public JsonObject coord;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public JsonObject getCoord() {
        return coord;
    }

    public void setCoord(JsonObject coord) {
        this.coord = coord;
    }
}
