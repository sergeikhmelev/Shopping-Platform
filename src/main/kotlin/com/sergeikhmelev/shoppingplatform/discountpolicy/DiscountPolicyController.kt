package com.sergeikhmelev.shoppingplatform.discountpolicy

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Positive
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody

@RestController
@RequestMapping(
	"/discount-policies",
	produces = [MediaType.APPLICATION_JSON_VALUE],
	consumes = [MediaType.APPLICATION_JSON_VALUE],
)
@Validated
class DiscountPolicyController(
	private val discountPolicyService: DiscountPolicyService,
) {

	@Operation(summary = "Updates fixed percentage-based discount policy")
	@ApiResponses(
		value = [
			ApiResponse(responseCode = "200", description = "The policy was updated successfully"),
			ApiResponse(responseCode = "400", description = "Invalid request body", content = [Content()]),
		]
	)
	@PostMapping("/update-fixed-percentage-based")
	fun updateFixedPercentageBased(@Valid @RequestBody discountPolicy: FixedPercentageBasedDiscountPolicyDto) {
		discountPolicyService.updatePolicy(
			DiscountPolicyType.FIXED_PERCENTAGE_BASED,
			FixedPercentageBasedDiscountPolicy(discountPolicy.percentage)
		)
	}

	@Operation(
		summary = "Updates count-based discount policy",
		requestBody = SwaggerRequestBody(
			description = "A list of purchase quantities and corresponding discount rates. 'itemQuantity' is inclusive and the discount will be applied based on the max quantity reached.",
		)
	)
	@ApiResponses(
		value = [
			ApiResponse(responseCode = "200", description = "The policy was updated successfully"),
			ApiResponse(responseCode = "400", description = "Invalid request body", content = [Content()]),
		]
	)
	@PostMapping("/update-count-based")
	fun updateCountBased(@Valid @RequestBody discountPolicy: CountBasedDiscountPolicyDto) {
		discountPolicy.verify()

		discountPolicyService.updatePolicy(
			DiscountPolicyType.COUNT_BASED,
			CountBasedDiscountPolicy(
				discountPolicy.discounts
					.map { CountBasedDiscountPolicy.Discount(it.itemQuantity, it.percentage) }
			)
		)
	}

	private fun CountBasedDiscountPolicyDto.verify() {
		val quantities = discounts.map { it.itemQuantity }
		if (quantities.size != quantities.distinct().size) {
			throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Item quantities are not set correctly")
		}
	}
}

data class FixedPercentageBasedDiscountPolicyDto(
	@DecimalMin("0")
	@DecimalMax("50")
	val percentage: Double,
)

data class CountBasedDiscountPolicyDto(
	val discounts: List<CountBasedDiscount>,
)

data class CountBasedDiscount(
	@Positive
	val itemQuantity: Int,
	@DecimalMin("0")
	@DecimalMax("50")
	val percentage: Double,
)
