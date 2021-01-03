package com.thetatechno.pokemonappwithjavaversion.viewmodels;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thetatechno.pokemonappwithjavaversion.Constants;
import com.thetatechno.pokemonappwithjavaversion.model.Pokemon;
import com.thetatechno.pokemonappwithjavaversion.model.PokemonResponse;
import com.thetatechno.pokemonappwithjavaversion.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PokemonViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<ArrayList<Pokemon>> pokemArrayListMutableLiveData = new MutableLiveData<>();
    private LiveData<List<Pokemon>> favPokemArrayListMutableLiveData;
    private static final String TAG = PokemonViewModel.class.getSimpleName();

    @ViewModelInject
    public PokemonViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ArrayList<Pokemon>> getPokemArrayListMutableLiveData() {
        return pokemArrayListMutableLiveData;
    }
    public LiveData<List<Pokemon>> getFromRoomDBLiveData() {
        return favPokemArrayListMutableLiveData;
    }
    public void getPokemons() {
        repository.getPokemons().subscribeOn(Schedulers.io())
                .map(new Function<PokemonResponse, ArrayList<Pokemon>>() {
                    @Override
                    public ArrayList<Pokemon> apply(PokemonResponse pokemonResponse) throws Throwable {
                        ArrayList<Pokemon> list = pokemonResponse.getResults();
                        for (Pokemon pokemon : list) {
                            String url = pokemon.getUrl();
                            String[] pokemonIndexs = url.split("/");
                            pokemon.setUrl(Constants.baseUrlForImages + pokemonIndexs[pokemonIndexs.length - 1] + ".png");
                        }
                        return list;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> pokemArrayListMutableLiveData.setValue(result)
                        , error -> Log.i(TAG, "getPokemons:  " + error)
                );
    }

    public void insertPokemon(Pokemon pokemon) {
        repository.insertPokemon(pokemon);
    }

    public void deletePokemon(String pokemonName) {
        repository.deletePokemon(pokemonName);
    }

    public void getFavPokemon() {
        favPokemArrayListMutableLiveData = repository.getFavPokemons();
    }
}
