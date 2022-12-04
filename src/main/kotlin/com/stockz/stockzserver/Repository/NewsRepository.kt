package com.stockz.stockzserver.Repository

import org.springframework.data.mongodb.repository.MongoRepository

interface NewsRepository : MongoRepository<NewsModel, String> {
    override fun count(): Long
}