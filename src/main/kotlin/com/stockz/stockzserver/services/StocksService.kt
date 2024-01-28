package com.stockz.stockzserver.services

import com.stockz.stockzserver.Repository.StocksListRepository
import com.stockz.stockzserver.Repository.StocksModel
import com.stockz.stockzserver.Repository.StocksRepository
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.*


@Component
@Scope("session")
@Service
object StocksService : IStockService {
    var suggestions = JSONArray()

    val STOCKS_ARRAY_ID = "65ad7a6f0c900555335dbab8"
    val days : Int = 8
    val daysFactor = 4.0 / (days + 1)
    val logger: Logger = LoggerFactory.getLogger(StocksService::class.java)

    override fun initialize( stocksRepository: StocksRepository, stocksListRepository: StocksListRepository){
        logger.info("Initializing StockService - Calculating Suggestions")
        val stockList: List<String> = stocksListRepository.findById(STOCKS_ARRAY_ID).get().data

        stockList.map { stock ->
            suggestions.put(
                calculateSuggestion(
                    stocksRepository.getStocksModelBySymbol(JSONObject(stock).get("symbol").toString().uppercase())))
         }

        suggestions = sortSuggestions()
    }


    override fun getStock(stock: StocksModel) : String {
        return JSONObject(stock).toString()
    }

    override fun getNotificationStatus(stock: StocksModel, value: String, higher: Boolean) : Boolean {
        val closeValue = stock.Series.get(0).data.close

        return if (higher) {
            (closeValue.toFloat() > value.toFloat())
        } else {
            (closeValue.toFloat() < value.toFloat())
        }
    }

    override fun getTradingSuggestions() : String {
        var jsonValues = getMostExtremeSuggestions()
        return jsonValues.toString()
    }

    private fun calculateSuggestion(stock: StocksModel) : JSONObject {
        logger.info("Calculating " + stock.Symbol)

        var results = DoubleArray(days)
        var stocksCloses = DoubleArray(days)

        for(i in 0 until days){
            stocksCloses[i] = stock.Series.get(days-1-i).data.close.toDouble()
        }

        val relativeEma = (calculateEmasHelper(stocksCloses,days-1, results) - stocksCloses[days-1])/stocksCloses[days-1] * 100

        return createSuggestion(stock, relativeEma)
    }

    private fun createSuggestion(stock : StocksModel, relativeEma : Double) : JSONObject {
        var suggestion = JSONObject()
        suggestion.put("Name", stock.Name)
        suggestion.put("Symbol", stock.Symbol)
        suggestion.put("Image", stock.Image)
        suggestion.put("Estimate", relativeEma)
        return suggestion
    }

    fun calculateEmasHelper (stocksCloses: DoubleArray, i: Int, results: DoubleArray): Double {
        if (i == 0) {
            results[0] = stocksCloses[0].toString().toDouble()
            return results[0]
        }

        val close = stocksCloses[i].toString().toDouble()
        results[i] = close * daysFactor + (1 - daysFactor) * calculateEmasHelper(stocksCloses, i - 1, results)
        return results[i]
    }

    private fun sortSuggestions() : JSONArray {
        val jsonValues: MutableList<JSONObject> = ArrayList()

        for (i in 0 until suggestions.length()) {
            jsonValues.add(suggestions.getJSONObject(i))
        }

        Collections.sort(jsonValues, object : Comparator<JSONObject?> {
            private val KEY_NAME = "Estimate"
            override fun compare(o1: JSONObject?, o2: JSONObject?): Int {
                var valA = 0.0
                var valB = 0.0

                try {
                    valA = o1?.get(KEY_NAME) as Double
                    valB = o2?.get(KEY_NAME) as Double
                } catch (e: JSONException) { }

                return if(valA < valB) { -1 }
                else if(valB < valA) { 1 }
                else { 0 }
            }
        })

        return JSONArray(jsonValues)
    }

    private fun getMostExtremeSuggestions() : JSONObject {
        val jsonRiseSuggestions: MutableList<JSONObject> = ArrayList()
        val jsonDropSuggestions: MutableList<JSONObject> = ArrayList()

        for (i in 0 until 3) {
            jsonDropSuggestions.add(suggestions.getJSONObject(i))
        }
        for (i in 0 until 3) {
            jsonRiseSuggestions.add(suggestions.getJSONObject(suggestions.length()-1-i))
        }
        return JSONObject()
            .put("dropSuggestions", jsonDropSuggestions)
            .put("riseSuggestions", jsonRiseSuggestions)
    }

}