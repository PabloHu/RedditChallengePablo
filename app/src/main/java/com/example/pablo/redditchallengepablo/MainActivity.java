package com.example.pablo.redditchallengepablo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pablo.redditchallengepablo.data.modelreddit.Child;
import com.example.pablo.redditchallengepablo.data.modelreddit.RedditResponse;
import com.example.pablo.redditchallengepablo.data.remote.ApiProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG =  "MainActivityTag";
    private static final String REDDIT_FRAGMENT_TAG = "RedditFragment";
    List<RedditResponse>redditResponseList = new ArrayList<>();
    EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearch = (EditText) findViewById(R.id.etSearch);

        getReddit("funny");
    }
    /*
    private void getAddressUsingGeocoding(Location location) {

        //create an observable that will emit the response from the network call
        Observable<GeocodeResponse> responseObservable = ApiProvider.getGeocodeObs(location);

        //create an observer that is going to read the emitted values
        Observer<GeocodeResponse> responseObserver = new Observer<GeocodeResponse>() {
            String address;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");

            }

            @Override
            public void onNext(@NonNull GeocodeResponse geocodeResponse) {
                address = geocodeResponse.getResults().get(0).getFormattedAddress();
                Log.d(TAG, "onNext: " + address);

                Toast.makeText(MainActivity.this, address, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: " + e.toString());

            }

            @Override
            public void onComplete() {
               // Log.d(TAG, "onComplete: ");
               // finishedSaved = address;
            }
        };

        //subscribe the oberver to the observable
        responseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseObserver);
        responseObservable.unsubscribeOn(Schedulers.io());

    }
*/
    private void getReddit(String redditSearch) {

        //create an observable that will emit the response from the network call
        Observable<RedditResponse> responseObservable = ApiProvider.getRedditObs(redditSearch);

        //create an observer that is going to read the emitted values
        Observer<RedditResponse> responseObserver = new Observer<RedditResponse>() {
            String address;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");

            }

            @Override
            public void onNext(@NonNull RedditResponse redditResponse) {

             //   try {
                for (int i = 0; i < redditResponse.getData().getChildren().size() ; i++) {
                    Log.d(TAG, "onNext: " + redditResponse.getData().getChildren().get(i).getData().getAuthor());

                }
                redditResponseList.add(redditResponse);

              //  }catch (Exception e){
              //      Log.d(TAG, "onNext error: " + e.toString());
              //  }
            }
/*
            @Override
            public void onNext(@NonNull GeocodeResponse geocodeResponse) {
                address = geocodeResponse.getResults().get(0).getFormattedAddress();
                Log.d(TAG, "onNext: " + address);

                Toast.makeText(MainActivity.this, address, Toast.LENGTH_SHORT).show();

            }
*/
            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: " + e.toString());

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: " + redditResponseList.get(0).getData().getChildren().size());
                for (int i = 0; i <  redditResponseList.get(0).getData().getChildren().size(); i++) {
                    Log.d(TAG, "onComplete: "+redditResponseList.get(0).getData().getChildren().get(i).getData().getAuthor());
                }
                // Log.d(TAG, "onComplete: ");
                // finishedSaved = address;
                List<Child> childList = new ArrayList<>();
                childList = redditResponseList.get(0).getData().getChildren();
                injectDataToFragment(childList);
            }
        };

        //subscribe the oberver to the observable
        responseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseObserver);
        responseObservable.unsubscribeOn(Schedulers.io());

    }

    public void injectDataToFragment(List<Child> childList){
        RedditFragment redditFragment  = RedditFragment.newInstance(childList);
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flFragHolderCarOptions, redditFragment, REDDIT_FRAGMENT_TAG)
                .addToBackStack(REDDIT_FRAGMENT_TAG)

                .commit();
    }

    public void redditSearch(View view) {

            switch (view.getId()){
                case R.id.btnSearch:
                    redditResponseList.clear();
                    Toast.makeText(this, ""+etSearch.getText(), Toast.LENGTH_SHORT).show();
                    getReddit(etSearch.getText().toString());

                    break;
        }


    }



    //-----------------------------------

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // below line to be commented to prevent crash on nougat.
        // http://blog.sqisland.com/2016/09/transactiontoolargeexception-crashes-nougat.html
        //
        //super.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
}
