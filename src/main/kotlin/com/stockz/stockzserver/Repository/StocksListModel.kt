package com.stockz.stockzserver.Repository

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("StocksList")
class StocksListModel {
    @Id
    lateinit var id: String

    lateinit var data : List<String>

    fun StocksListModel(id: String, data: List<String>) {
        this.id = id
        this.data = data
    }
}