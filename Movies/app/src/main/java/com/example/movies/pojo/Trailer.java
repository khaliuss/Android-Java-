package com.example.movies.pojo;

import com.google.gson.annotations.SerializedName;

public class Trailer {

    @SerializedName("name")
    private String trailerName;
    @SerializedName("url")
    private String url;

    public Trailer(String trailerName, String url) {
        this.trailerName = trailerName;
        this.url = url;
    }

    public String getTrailerName() {
        return  trailerName;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Trailer{" +
                "trailerName='" + trailerName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
