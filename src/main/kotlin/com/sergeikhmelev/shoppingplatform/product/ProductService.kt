package com.sergeikhmelev.shoppingplatform.product

import com.sergeikhmelev.shoppingplatform.discountpolicy.DiscountPolicyService
import com.sergeikhmelev.shoppingplatform.discountpolicy.DiscountPolicyType
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.UUID

@Service
class ProductService(
	private val productRepository: ProductRepository,
	private val discountPolicyService: DiscountPolicyService,
) {

	fun getProduct(uuid: UUID): Product? =
		productRepository.findByUuid(uuid)

	fun addProduct(name: String, price: BigDecimal, description: String?): UUID {
		val uuid = UUID.randomUUID()
		productRepository.save(Product(uuid, name, price, description))
		return uuid
	}

	fun calculateTotalPrice(product: Product, quantity: Int, discountPolicyType: DiscountPolicyType): BigDecimal {
		return discountPolicyService.getPolicy(discountPolicyType).apply(product.price, quantity)
	}
}
