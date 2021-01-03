package com.thetatechno.pokemonappwithjavaversion.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thetatechno.pokemonappwithjavaversion.R;
import com.thetatechno.pokemonappwithjavaversion.model.Pokemon;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    private ArrayList<Pokemon> mList = new ArrayList<>();
    private Context mContext;

    public PokemonAdapter( Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PokemonViewHolder(LayoutInflater.from(mContext).inflate(R.layout.pokemon_layout_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
holder.pokemonNameTxt.setText(mList.get(position).getName());
        Glide.with(mContext).load(mList.get(position).getUrl()).into(holder.pokemonImage);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setmList(ArrayList<Pokemon> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }
public Pokemon getPokemonAt(int position){
        return mList.get(position);
}
    class PokemonViewHolder extends RecyclerView.ViewHolder {
        private ImageView pokemonImage;
        private TextView pokemonNameTxt;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonImage = itemView.findViewById(R.id.pokemonImageView);
            pokemonNameTxt = itemView.findViewById(R.id.pokemonNameTxtView);

        }
    }
}
