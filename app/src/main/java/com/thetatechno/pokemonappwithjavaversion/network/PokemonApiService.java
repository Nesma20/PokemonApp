package com.thetatechno.pokemonappwithjavaversion.network;

import com.thetatechno.pokemonappwithjavaversion.model.PokemonResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface PokemonApiService {
    @GET("pokemon")
    Observable<PokemonResponse> getPokemonList();
}
