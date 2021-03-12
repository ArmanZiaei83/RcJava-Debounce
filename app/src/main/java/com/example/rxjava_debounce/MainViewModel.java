package com.example.rxjava_debounce;

import android.widget.SearchView;

import androidx.lifecycle.ViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    SearchView searchView;
    final static long firstRequest = System.currentTimeMillis();

    public void prepareObservables (){

        Observable<String> observable = Observable.
                create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@org.jetbrains.annotations.NotNull ObservableEmitter<String> emitter) throws Exception {

                        retrunSearchView().setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String s) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String s) {
                                if(!emitter.isDisposed()){
                                    emitter.onNext(s);
                                }
                                return false;
                            }
                        });
                    }
                }).debounce(500 , TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io());

        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@org.jetbrains.annotations.NotNull Disposable d) {
                System.out.println("<---Subscribed--->");
            }

            @Override
            public void onNext(@org.jetbrains.annotations.NotNull String s) {
                System.out.println("Time Spent on Operation : " + String.valueOf(System.currentTimeMillis() - firstRequest));
            }

            @Override
            public void onError(@org.jetbrains.annotations.NotNull Throwable e) {
                System.out.println("Error  : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("<---Completed--->");

            }
        });
    }

    public void getSearchView (SearchView searchView){
        this.searchView = searchView;
    }

    public SearchView retrunSearchView (){
        return searchView;
    }
}
