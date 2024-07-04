package com.sergeikhmelev.shoppingplatform.discountpolicy

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.math.BigDecimal

class DiscountPolicyTest {

	@ParameterizedTest
	@CsvSource(
		value = [
			"5.0, 10.0, 100, 950.0",
			"50.0, 10.0, 100, 500.0",
			"15.0, 150.0, 10, 1275.0",
		]
	)
	fun `FixedPercentageBasedDiscountPolicy should be properly applied`(
		percentage: Double,
		itemPrice: BigDecimal,
		quantity: Int,
		expectedPrice: BigDecimal,
	) {
		// given
		val discountPolicy = FixedPercentageBasedDiscountPolicy(percentage)

		// when
		val result = discountPolicy.apply(itemPrice, quantity)

		// then
		assertThat(result).isEqualByComparingTo(expectedPrice)
	}

	@ParameterizedTest
	@CsvSource(
		value = [
			"10, 95.00",
			"15, 142.50",
			"20, 180.00",
			"25, 225.00",
		]
	)
	fun `CountBasedDiscountPolicy should be properly applied`(
		quantity: Int,
		expectedPrice: BigDecimal,
	) {
		// given
		val discountPolicy = CountBasedDiscountPolicy(
			listOf(
				CountBasedDiscountPolicy.Discount(itemQuantity = 10, percentage = 5.0),
				CountBasedDiscountPolicy.Discount(itemQuantity = 20, percentage = 10.0),
			)
		)

		// when
		val result = discountPolicy.apply(BigDecimal("10.0"), quantity)

		// then
		assertThat(result).isEqualByComparingTo(expectedPrice)
	}

	@Test
	fun `CountBasedDiscountPolicy should not be applied in a case of insufficient quantity`() {
		// given
		val discountPolicy = CountBasedDiscountPolicy(
			listOf(
				CountBasedDiscountPolicy.Discount(itemQuantity = 10, percentage = 5.0),
				CountBasedDiscountPolicy.Discount(itemQuantity = 20, percentage = 10.0),
			)
		)

		// when
		val result = discountPolicy.apply(BigDecimal("10.0"), 5)

		// then
		assertThat(result).isEqualByComparingTo("50.0")
	}
}
