package com.sergeikhmelev.shoppingplatform.discountpolicy

import org.springframework.stereotype.Service

@Service
class DiscountPolicyService {

	private val repository = mutableMapOf(
		DiscountPolicyType.FIXED_PERCENTAGE_BASED to FixedPercentageBasedDiscountPolicy(10.5),
		DiscountPolicyType.COUNT_BASED to CountBasedDiscountPolicy(
			listOf(
				CountBasedDiscountPolicy.Discount(itemQuantity = 10, percentage = 5.0),
				CountBasedDiscountPolicy.Discount(itemQuantity = 20, percentage = 10.0),
			)
		)
	)

	fun getPolicy(type: DiscountPolicyType): DiscountPolicy {
		return repository[type] ?: error("No policy with type $type")
	}

	fun updatePolicy(type: DiscountPolicyType, policy: DiscountPolicy) {
		repository[type] = policy
	}
}
