package com.example.poketarea
import com.example.poketarea.models.Pokemon

import io.reactivex.Observable



interface GetData {

    fun getData() : Observable<List<Pokemon>>
}