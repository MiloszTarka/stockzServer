package com.stockz.stockzserver.Repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface StocksRepository : MongoRepository<StocksModel, String> {

    @Query("{Symbol :?0}")
    fun getStocksModelBySymbol(symbol: String): StocksModel

    override fun count(): Long
}