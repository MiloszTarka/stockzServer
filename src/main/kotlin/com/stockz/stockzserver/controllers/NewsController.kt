package com.stockz.stockzserver.controllers

import com.stockz.stockzserver.Repository.NewsRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Scope
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@Scope("request")
class NewsController(val newsRepository: NewsRepository) {

    val NEWS_ARRAY_ID = "6382b033fd118b9e979ffa99"

    val logger: Logger = LoggerFactory.getLogger(NewsController::class.java)

    @GetMapping(value=["/news"])
    fun getStock(): String {
        logger.info("Requested news")
        return newsRepository.findById(NEWS_ARRAY_ID).get().data.toString()
    }
}