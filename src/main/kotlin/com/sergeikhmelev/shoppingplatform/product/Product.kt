package com.sergeikhmelev.shoppingplatform.product

import java.math.BigDecimal
import java.util.UUID

class Product(
	val uuid: UUID,
	val name: String,
	val price: BigDecimal,
	val description: String?,
)
