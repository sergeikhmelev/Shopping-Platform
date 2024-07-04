package com.sergeikhmelev.shoppingplatform.discountpolicy

import org.springframework.stereotype.Service

@Service
class DiscountPolicyService {

	private val repository = listOf(
		FixedPercentageBasedDiscountPolicy(10.5),
		CountBasedDiscountPolicy(
			listOf(
				CountBasedDiscountPolicy.Discount(itemQuantity = 10, percentage = 5.0),
				CountBasedDiscountPolicy.Discount(itemQuantity = 20, percentage = 10.0),
			)
		)
	)

	fun getPolicies(): List<DiscountPolicy> {
		return repository
	}
}
