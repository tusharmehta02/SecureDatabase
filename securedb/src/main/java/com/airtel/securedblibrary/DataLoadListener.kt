package com.airtel.securedblibrary

interface DataLoadListener {
    fun onDataLoaded(pair: Pair<String, String?>)
}