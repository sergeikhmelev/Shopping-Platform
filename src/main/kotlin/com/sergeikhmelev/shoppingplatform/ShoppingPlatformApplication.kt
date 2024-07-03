package com.sergeikhmelev.shoppingplatform

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ShoppingPlatformApplication

fun main(args: Array<String>) {
	runApplication<ShoppingPlatformApplication>(*args)
}
