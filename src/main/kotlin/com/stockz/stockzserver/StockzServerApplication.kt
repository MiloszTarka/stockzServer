package com.stockz.stockzserver


import com.stockz.stockzserver.Repository.StocksListRepository
import com.stockz.stockzserver.Repository.StocksRepository
import com.stockz.stockzserver.services.StocksService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories
class StockzServerApplication(stocksRepository: StocksRepository,
                              stocksListRepository: StocksListRepository){

    init{
        StocksService.initialize(stocksRepository, stocksListRepository)
    }
}

fun main(args: Array<String>) {
    runApplication<StockzServerApplication>(*args, )
}
