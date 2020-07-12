package br.com.almeida.casbeer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_beers);
        getSupportActionBar().setTitle("Favorite Beers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //init components
        recyclerBeers = findViewById(R.id.recyclerFavoriteBeersId);

        BeerDAO beerDAO = new BeerDAO(getApplicationContext());
        beers = beerDAO.list();

        //recovery data
        recoveryBeers();
    }

    private void recoveryBeers() {

        BeerDAO beerDAO = new BeerDAO(getApplicationContext());
        beers = beerDAO.list();

        adapterBeer = new AdapterBeer(beers, this);
        recyclerBeers.setHasFixedSize(true);
        recyclerBeers.setLayoutManager(new LinearLayoutManager(this));
        recyclerBeers.setAdapter(adapterBeer);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}