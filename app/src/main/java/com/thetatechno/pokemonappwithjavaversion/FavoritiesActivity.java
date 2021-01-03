package com.thetatechno.pokemonappwithjavaversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.thetatechno.pokemonappwithjavaversion.adapters.PokemonAdapter;
import com.thetatechno.pokemonappwithjavaversion.model.Pokemon;
import com.thetatechno.pokemonappwithjavaversion.viewmodels.PokemonViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint

public class FavoritiesActivity extends AppCompatActivity {
    private PokemonViewModel viewModel;
    private RecyclerView recyclerView;
    private PokemonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorities);
        recyclerView = findViewById(R.id.favorite_recyclerview);
        adapter = new PokemonAdapter(this);
        recyclerView.setAdapter(adapter);
        setUpSwipeWithRightDirection();
        Button toFavBTn = findViewById(R.id.to_home_button);
        toFavBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            finish();
            }
        });
        viewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        viewModel.getFavPokemon();
        viewModel.getFromRoomDBLiveData().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                adapter.setmList((ArrayList<Pokemon>) pokemons);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setUpSwipeWithRightDirection() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPokemonPosition = viewHolder.getAdapterPosition();
                Pokemon pokemon = adapter.getPokemonAt(swipedPokemonPosition);
                viewModel.deletePokemon(pokemon.getName());
                adapter.notifyDataSetChanged();
                Toast.makeText(FavoritiesActivity.this, "Pokemon deleted from fav", Toast.LENGTH_SHORT).show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}