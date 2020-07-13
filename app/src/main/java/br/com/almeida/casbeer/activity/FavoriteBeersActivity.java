package br.com.almeida.casbeer.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.almeida.casbeer.R;
import br.com.almeida.casbeer.adapter.AdapterBeer;
import br.com.almeida.casbeer.api.BeerService;
import br.com.almeida.casbeer.helper.BeerDAO;
import br.com.almeida.casbeer.model.Beer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteBeersActivity extends AppCompatActivity {

    private RecyclerView recyclerBeers;
    private List<Beer> beers = new ArrayList<>();
    private AdapterBeer adapterBeer;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_beers);
        getSupportActionBar().setTitle("Favorite Beers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //init components
        recyclerBeers = findViewById(R.id.recyclerFavoriteBeersId);
        swipeRefreshLayout = findViewById(R.id.pullToRefresh);

        //config pull to refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recoveryBeersRefresh();
            }
        });

        BeerDAO beerDAO = new BeerDAO(getApplicationContext());
        beers = beerDAO.list();

        //recovery data
        recoveryBeers();
    }

    private void recoveryBeers() {

        BeerDAO beerDAO = new BeerDAO(getApplicationContext());
        beers = beerDAO.list();

        Log.d("tryyy", "recoveryBeers: " + beers.get(0).getName());
        adapterBeer = new AdapterBeer(beers, this);
        recyclerBeers.setHasFixedSize(true);
        recyclerBeers.setLayoutManager(new LinearLayoutManager(this));
        recyclerBeers.setAdapter(adapterBeer);
    }

    private void recoveryBeersRefresh() {

        BeerDAO beerDAO = new BeerDAO(getApplicationContext());
        beers = beerDAO.list();

        Log.d("tryyy", "recoveryBeers: " + beers.get(0).getName());
        adapterBeer = new AdapterBeer(beers, this);
        recyclerBeers.setHasFixedSize(true);
        recyclerBeers.setLayoutManager(new LinearLayoutManager(this));
        recyclerBeers.setAdapter(adapterBeer);
        adapterBeer.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}