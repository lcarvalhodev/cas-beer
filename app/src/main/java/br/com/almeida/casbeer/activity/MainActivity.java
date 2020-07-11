package br.com.almeida.casbeer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import br.com.almeida.casbeer.R;
import br.com.almeida.casbeer.adapter.AdapterBeer;
import br.com.almeida.casbeer.model.Beer;

public class MainActivity extends AppCompatActivity {

    //Widgets
    private RecyclerView recyclerBeers;

    private List<Beer> beers = new ArrayList<>();
    private AdapterBeer adapterBeer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Init components
        recyclerBeers = findViewById(R.id.recyclerBeers);


        //Config Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Beer List");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        //Config RecyclerView
        recoveryBeers();
        adapterBeer = new AdapterBeer(beers, this);
        recyclerBeers.setHasFixedSize(true);
        recyclerBeers.setLayoutManager(new LinearLayoutManager(this));
        recyclerBeers.setAdapter(adapterBeer);
    }

    private void recoveryBeers(){
        Beer beer1 = new Beer();
        beer1.setName("testBeer");
        beer1.setTagline("testBeerTagLine");
        beers.add(beer1);

        Beer beer2 = new Beer();
        beer2.setName("testBeer2");
        beer2.setTagline("testBeerTagLine2");
        beers.add(beer2);
    }
}