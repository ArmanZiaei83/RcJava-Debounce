package com.example.rxjava_debounce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.SearchView;

public class MainActivity extends AppCompatActivity {

    SearchView searchView;
    MainViewModel mainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.searchBox);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        getObservable();
    }

    private void getObservable() {

        mainViewModel.getSearchView(searchView);
        mainViewModel.prepareObservables();
    }


}