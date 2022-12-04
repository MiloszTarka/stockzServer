package com.stockz.stockzserver.Repository

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("News")
class NewsModel {
    @Id
    lateinit var id: String

    lateinit var data : List<String>

    fun NewsModel(id: String, data: List<String>) {
        this.id = id
        this.data = data
    }
}