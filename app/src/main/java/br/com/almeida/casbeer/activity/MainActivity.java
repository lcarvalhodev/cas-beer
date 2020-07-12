package br.com.almeida.casbeer.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.almeida.casbeer.R;
import br.com.almeida.casbeer.adapter.AdapterBeer;
import br.com.almeida.casbeer.api.BeerService;
import br.com.almeida.casbeer.helper.RetrofitConfig;
import br.com.almeida.casbeer.listener.RecyclerItemClickListener;
import br.com.almeida.casbeer.model.Beer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    //Widgets
    private RecyclerView recyclerBeers;
    private MaterialSearchView searchView;

    private List<Beer> beers = new ArrayList<>();
    private AdapterBeer adapterBeer;

    //Retrofit
    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init components
        recyclerBeers = findViewById(R.id.recyclerBeers);
        searchView = findViewById(R.id.searchView);

        //init configs
        retrofit = RetrofitConfig.getRetrofit();


        //Config Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Beer List");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        //Config RecyclerView
        recoveryBeers();

        //config searchview methods
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });
    }

    private void recoveryBeers() {

        BeerService beerService = retrofit.create(BeerService.class);
        Call<List<Beer>> call = beerService.recoveryBeersService();

        call.enqueue(new Callback<List<Beer>>() {
            @Override
            public void onResponse(Call<List<Beer>> call, Response<List<Beer>> response) {
                if (response.isSuccessful()) {
                    beers = response.body();
                    configRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<List<Beer>> call, Throwable t) {

            }
        });
    }

    public void configRecyclerView() {
        adapterBeer = new AdapterBeer(beers, this);
        recyclerBeers.setHasFixedSize(true);
        recyclerBeers.setLayoutManager(new LinearLayoutManager(this));
        recyclerBeers.setAdapter(adapterBeer);

        //config click event
        recyclerBeers.addOnItemTouchListener(new RecyclerItemClickListener(
                this, recyclerBeers, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Beer beer = beers.get(position);

                Intent intent = new Intent(MainActivity.this, BeerDetailsActivity.class);
                intent.putExtra("beer", beer);

                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }
        ));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.menu_search);

        searchView.setMenuItem(item);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_favs:
                Log.i("infoStar", "infoStar");
        }

        return super.onOptionsItemSelected(item);
    }
}