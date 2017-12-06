package com.example.pablo.redditchallengepablo.data.remote;

import com.example.pablo.redditchallengepablo.data.modelreddit.RedditResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Pablo on 11/7/2017.
 */

public interface ApiService {

    String QUERY_PARAM_ADDRESS = "address";
    String QUERY_PARAM_LATLNG = "latlng";
    String QUERY_PARAM_KEY = "key";
/*
    @GET("maps/api/geocode/json")
    Observable<GeocodeResponse> getGeocodeResponse(
            @Query(QUERY_PARAM_LATLNG) String location
            , @Query(QUERY_PARAM_KEY) String key);

    @GET("maps/api/geocode/json")
    Observable<GeocodeResponse> getGeocodeResponseAddress(
            @Query(QUERY_PARAM_ADDRESS) String address
            , @Query(QUERY_PARAM_KEY) String key);
*/
   // @GET("r/funny/.json")
   // Observable<RedditResponse> getRedditResponse();

    @GET("r/{search}/.json")
    Observable<RedditResponse> getRedditResponse(@Path("search")String redditSearch);



}
