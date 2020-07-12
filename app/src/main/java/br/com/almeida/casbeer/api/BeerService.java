package br.com.almeida.casbeer.api;

import java.util.List;

import br.com.almeida.casbeer.model.Beer;
import retrofit2.Call;
import retrofit2.http.GET;

public interface BeerService {

    @GET("beers")
    Call<List<Beer>> recoveryBeersService();
}
