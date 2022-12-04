package com.stockz.stockzserver.Repository

import org.springframework.data.mongodb.repository.MongoRepository

interface StocksListRepository : MongoRepository<StocksListModel, String> {
    override fun count(): Long
}