package com.example.poketarea

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.example.poketarea.models.Pokemon
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), PokemonAdapter.Listener {


    private var myAdapter: PokemonAdapter? = null

    private val BASE_URL = "https://pokeapi.co/api/v2/"

    private var myCompositeDisposable: CompositeDisposable? = null

    private var PokemonArrayList: ArrayList<Pokemon>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myCompositeDisposable = CompositeDisposable()

        loadData()

        initRecycler()
    }


    private fun initRecycler() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rv_pokemon_list.layoutManager = layoutManager

    }

    private fun loadData() {


        val requestInterface = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(GetData::class.java)

        myCompositeDisposable?.add(
            requestInterface.getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse)
        )

    }

    private fun handleResponse(pokeList: List<Pokemon>) {

        PokemonArrayList = ArrayList(pokeList)
        myAdapter = PokemonAdapter(PokemonArrayList!!, this)

        rv_pokemon_list.adapter = myAdapter

    }

    override fun onItemClick(pokemon: Pokemon) {


        Toast.makeText(this, "You clicked: ${pokemon.name}", Toast.LENGTH_LONG).show()

    }

    override fun onDestroy() {
        super.onDestroy()
        myCompositeDisposable?.clear()

    }
}
