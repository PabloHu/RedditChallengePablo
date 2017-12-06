package com.example.pablo.redditchallengepablo.data.remote;

import android.location.Location;

import com.example.pablo.redditchallengepablo.data.modelreddit.RedditResponse;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pablo on 11/7/2017.
 */


public class ApiProvider {


  //  private static final String BASE_URL = "https://maps.googleapis.com/";
    private static final String BASE_URL_REDDIT = "https://www.reddit.com/";
  //  public static final String Key = "AIzaSyCFWH68PiBVwUatCkHGNAHg4Z-MKssPPa4";

    public static Retrofit create() {


        //looging interceptor would log the request and response of the network call
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL_REDDIT)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /*
    public static Observable<GeocodeResponse> getGeocodeObs(Location location) {
        String latitude = String.valueOf(location.getLatitude());
        String longitude = String.valueOf(location.getLongitude());
        Retrofit retrofit = create();
        ApiService apiService = retrofit.create(ApiService.class);
        String locationStr = latitude + "," + longitude;
        return apiService.getGeocodeResponse(locationStr, Key);
    }

    public static Observable<GeocodeResponse> getGeocodeAddress(String s) {
        String latitude = String.valueOf(s);
        // String longitude = String.valueOf(location.getLongitude());
        Retrofit retrofit = create();
        ApiService apiService = retrofit.create(ApiService.class);
        String locationStr = String.valueOf(s);

        return apiService.getGeocodeResponseAddress(locationStr, Key);
    }
*/
    public static Observable<RedditResponse> getRedditObs(String redditSearch) {

        Retrofit retrofit = create();
        ApiService apiService = retrofit.create(ApiService.class);

        return apiService.getRedditResponse(redditSearch);
    }
}
