package com.stockz.stockzserver.services

import com.stockz.stockzserver.Repository.StocksListRepository
import com.stockz.stockzserver.Repository.StocksModel
import com.stockz.stockzserver.Repository.StocksRepository

interface IStockService {

    fun initialize(stocksRepository: StocksRepository, stocksListRepository: StocksListRepository)

    fun getStock(stock: StocksModel) : String

    fun getNotificationStatus(stock: StocksModel, value: String, higher: Boolean) : Boolean

    fun getTradingSuggestions() : String
}