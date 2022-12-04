package com.stockz.stockzserver.controllers

import com.stockz.stockzserver.Repository.StocksListRepository
import com.stockz.stockzserver.Repository.StocksRepository
import com.stockz.stockzserver.services.StocksService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.event.LoggingEvent
import org.springframework.context.annotation.Scope
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@Scope("request")
class StocksController(val service: StocksService,
                       val stocksListRepository: StocksListRepository,
                       val stocksRepository: StocksRepository) {

    val STOCKS_ARRAY_ID = "6382aa4c48ae81f92a008e03"

    val logger: Logger = LoggerFactory.getLogger(StocksController::class.java)

    @GetMapping(value=["/allstocks"])
    fun stocksList(): String{
        logger.info("All stocks request")
        return stocksListRepository.findById(STOCKS_ARRAY_ID).get().data.toString()
    }

    @GetMapping(value=["/stock"])
    fun getStock(@RequestParam symbol : String): String{
        logger.info("Requested " + symbol + " data")
        var stock = stocksRepository.getStocksModelBySymbol(symbol.uppercase())
        return service.getStock(stock)
    }

    @GetMapping(value=["/notification/lower"])
    fun checkNotificationLaunchLower(@RequestParam symbol : String, @RequestParam value : String): Boolean{
        logger.info("Requested " + symbol + " status (lower)")
        var stock = stocksRepository.getStocksModelBySymbol(symbol.uppercase())
        return service.getNotificationStatus(stock, value, false)

    }

    @GetMapping(value=["/notification/higher"])
    fun checkNotificationLaunchHigher(@RequestParam symbol : String, @RequestParam value : String): Boolean{
        logger.info("Requested " + symbol + " status (higher)")
        var stock = stocksRepository.getStocksModelBySymbol(symbol.uppercase())
        return service.getNotificationStatus(stock, value, true)
    }

    @GetMapping(value = ["/suggestions"])
    fun getTradingSuggestions(): String {
        logger.info("Requested trading suggestions")
        return service.getTradingSuggestions()
    }
}