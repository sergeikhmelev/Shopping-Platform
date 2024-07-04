package com.sergeikhmelev.shoppingplatform.discountpolicy

import java.math.BigDecimal
import java.math.RoundingMode

sealed interface DiscountPolicy {

	fun apply(itemPrice: BigDecimal, quantity: Int): BigDecimal
}

class FixedPercentageBasedDiscountPolicy(
	private val percentage: Double,
) : DiscountPolicy {

	override fun apply(itemPrice: BigDecimal, quantity: Int): BigDecimal =
		calculateDiscount(itemPrice, quantity, percentage)
}

class CountBasedDiscountPolicy(
	discounts: List<Discount>,
) : DiscountPolicy {

	private val discounts: List<Discount> = discounts.sortedByDescending { it.itemQuantity }

	data class Discount(
		val itemQuantity: Int,
		val percentage: Double,
	)

	override fun apply(itemPrice: BigDecimal, quantity: Int): BigDecimal {
		val discountPercentage = discounts.firstOrNull { quantity >= it.itemQuantity }?.percentage ?: 0.0
		return calculateDiscount(itemPrice, quantity, discountPercentage)
	}
}

private fun calculateDiscount(itemPrice: BigDecimal, quantity: Int, discountPercentage: Double): BigDecimal {
	val ratio = BigDecimal(100F - discountPercentage).divide(BigDecimal("100"), 4, RoundingMode.HALF_UP)
	return itemPrice * BigDecimal(quantity) * ratio
}
