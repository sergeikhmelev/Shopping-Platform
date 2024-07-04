package com.sergeikhmelev.shoppingplatform.product

import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.UUID

@Service
class ProductRepository {

	private val list: MutableList<Product> = mutableListOf(
		Product(UUID.fromString("c00ff428-40ed-45c0-af28-04f83fde7aa2"), "Duck", BigDecimal("100.0"), "made of rubber"),
		Product(UUID.fromString("a24df3b0-8f18-4403-b470-ecb5cd4a1ad2"), "Coffee", BigDecimal("50.0"), "black as a moonless night"),
		Product(UUID.fromString("169f1db9-d480-48b8-abdc-71386b92fd19"), "Test item", BigDecimal("5000.0"), description = null),
	)

	fun findByUuid(uuid: UUID): Product? =
		list.firstOrNull { it.uuid == uuid }

	fun save(product: Product) {
		list.add(product)
	}
}
