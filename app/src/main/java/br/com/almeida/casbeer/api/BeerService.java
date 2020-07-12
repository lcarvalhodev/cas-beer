package br.com.almeida.casbeer.api;

import java.util.List;

import br.com.almeida.casbeer.model.Beer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BeerService {

    @GET("beers")
    Call<List<Beer>> recoveryBeersService();

    @GET("beers")
    Call<List<Beer>> recoveryBeersServiceByName(
            @Query("beer_name") String beer_name
    );
}
