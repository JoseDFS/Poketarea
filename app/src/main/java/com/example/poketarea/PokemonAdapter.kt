package com.example.poketarea

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.widget.RecyclerView
import com.example.poketarea.models.Pokemon
import kotlinx.android.synthetic.main.list_element_pokemon.view.*

class PokemonAdapter (private val pokeList : ArrayList<Pokemon>, private val listener : Listener) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {
    interface Listener {
        fun onItemClick(pokemon : Pokemon)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pokeList[position], listener)
    }

    override fun getItemCount(): Int = pokeList.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_element_pokemon, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(pokemon: Pokemon, listener: Listener) {

            itemView.setOnClickListener{ listener.onItemClick(pokemon) }
            itemView.tv_pokemon_name.text = pokemon.name
            itemView.tv_pokemon_type.text = pokemon.type

        }
    }
}