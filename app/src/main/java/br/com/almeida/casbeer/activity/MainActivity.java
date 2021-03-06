package br.com.almeida.casbeer.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import br.com.almeida.casbeer.R;
import br.com.almeida.casbeer.adapter.AdapterBeer;
import br.com.almeida.casbeer.api.BeerService;
import br.com.almeida.casbeer.helper.Permission;
import br.com.almeida.casbeer.helper.RetrofitConfig;
import br.com.almeida.casbeer.listener.RecyclerItemClickListener;
import br.com.almeida.casbeer.model.Beer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    //Permissions
    private String[] permissions = new String[]{
            Manifest.permission.INTERNET
    };

    //Widgets
    private RecyclerView recyclerBeers;
    private MaterialSearchView searchView;
    private LinearLayout linearLayoutNoResults;
    private LinearLayout linearLayoutNoInternet;

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
        linearLayoutNoResults = findViewById(R.id.linearLayoutNoResultsId);
        linearLayoutNoInternet = findViewById(R.id.linearLayoutNoInternetId);

        //init configs
        retrofit = RetrofitConfig.getRetrofit();

        //validate permissions
        Permission.validatePermission(permissions, this, 1);

        //Config Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        //Config RecyclerView
        recoveryBeers();

        //config searchview methods
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recoveryBeersByName(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recoveryBeersByName(newText);
                return true;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                searchView.setHint(getString(R.string.tituloPesquisa));
            }

            @Override
            public void onSearchViewClosed() {
                recoveryBeers();
            }
        });
    }

    private void recoveryBeers() {

        if (isNetworkAvailable()) {
            linearLayoutNoInternet.setVisibility(View.GONE);
            BeerService beerService = retrofit.create(BeerService.class);
            Call<List<Beer>> call = beerService.recoveryBeersService();

            call.enqueue(new Callback<List<Beer>>() {
                @Override
                public void onResponse(Call<List<Beer>> call, Response<List<Beer>> response) {
                    if (response.isSuccessful()) {
                        linearLayoutNoResults.setVisibility(View.GONE);
                        recyclerBeers.setVisibility(View.VISIBLE);
                        beers = response.body();
                        configRecyclerView();

                    }
                }

                @Override
                public void onFailure(Call<List<Beer>> call, Throwable t) {

                }
            });
        } else {
            linearLayoutNoInternet.setVisibility(View.VISIBLE);
            linearLayoutNoResults.setVisibility(View.GONE);
            recyclerBeers.setVisibility(View.GONE);
        }

    }

    private void recoveryBeersByName(String search) {

        if (isNetworkAvailable()) {
            linearLayoutNoInternet.setVisibility(View.GONE);

            String searchName = search.replaceAll(" ", "_");

            BeerService beerService = retrofit.create(BeerService.class);
            Call<List<Beer>> call = beerService.recoveryBeersServiceByName(searchName);

            call.enqueue(new Callback<List<Beer>>() {
                @Override
                public void onResponse(Call<List<Beer>> call, Response<List<Beer>> response) {
                    if (response.isSuccessful()) {
                        beers = response.body();
                        if (beers.size() > 0) {
                            linearLayoutNoResults.setVisibility(View.GONE);
                            recyclerBeers.setVisibility(View.VISIBLE);
                            configRecyclerView();
                        } else {
                            Log.d("RecyclerViewTest", "configRecyclerView: " + "aqui1222333");
                            linearLayoutNoResults.setVisibility(View.VISIBLE);
                            recyclerBeers.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Beer>> call, Throwable t) {

                }
            });
        } else {
            linearLayoutNoInternet.setVisibility(View.VISIBLE);
            linearLayoutNoResults.setVisibility(View.GONE);
            recyclerBeers.setVisibility(View.GONE);
        }
    }

    public void configRecyclerView() {
        adapterBeer = new AdapterBeer(beers, this);
        recyclerBeers.setHasFixedSize(true);
        recyclerBeers.setLayoutManager(new LinearLayoutManager(this));
        recyclerBeers.setAdapter(adapterBeer);

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
                Intent intent = new Intent(getApplicationContext(), FavoriteBeersActivity.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onRestart() {
        recoveryBeers();
        super.onRestart();
    }
}