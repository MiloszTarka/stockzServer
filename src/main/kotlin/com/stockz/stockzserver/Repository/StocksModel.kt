package com.stockz.stockzserver.Repository

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("Stocks")
class StocksModel {
    @Id
    lateinit var id: String
    lateinit var Image: String
    lateinit var Name: String
    lateinit var Symbol: String
    lateinit var Series : List<Series>

    fun StocksModel(Id: String,
                    Image: String,
                    Name: String,
                    Symbol: String,
                    Series: List<Series>) {
        this.id = id
        this.Image = Image
        this.Name = Name
        this.Symbol = Symbol
        this.Series = Series
    }
}

class Series {
    lateinit var date : String
    lateinit var data : Data

    fun StocksModel(date: String,
                    data: Data) {
        this.date = date
        this.data = data
    }
}

class Data {
    lateinit var open : String
    lateinit var high : String
    lateinit var low : String
    lateinit var close : String
    lateinit var volume : String

    fun Data(open: String,
                    high: String,
                    low: String,
                    close: String,
                    volume: String) {
        this.open = open
        this.high = high
        this.low = low
        this.close = close
        this.volume = volume
    }
}