package com.kingprinters.jpademo.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {
    @GetMapping("/")
    fun getIndex(): String {
        return "test"
    }
}