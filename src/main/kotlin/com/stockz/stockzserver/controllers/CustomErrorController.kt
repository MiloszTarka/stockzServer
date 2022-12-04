package com.stockz.stockzserver.controllers

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest


@RestController
class CustomErrorController : ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    fun error(request: HttpServletRequest?): String {
        return "<h1>Error occurred</h1>"
    }

    val errorPath: String
        get() = "/error"
}