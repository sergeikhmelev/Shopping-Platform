package com.sergeikhmelev.shoppingplatform.product

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import jakarta.validation.constraints.Positive
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.util.UUID

@RestController
@RequestMapping(
	"/products",
	produces = [MediaType.APPLICATION_JSON_VALUE],
	consumes = [MediaType.APPLICATION_JSON_VALUE],
)
@Validated
class ProductController {

	@Operation(summary = "Retrieve information about a product by its id")
	@ApiResponses(
		value = [
			ApiResponse(responseCode = "200", description = "Information about the product"),
			ApiResponse(responseCode = "400", description = "Invalid id supplied", content = [Content()]),
			ApiResponse(responseCode = "404", description = "Product not found", content = [Content()])
		]
	)
	@GetMapping("/{id}")
	fun getInformationAboutProduct(
		@Parameter(description = "id of product to be searched")
		@PathVariable("id") productUuid: UUID,
	): ProductInformation {
		return ProductInformation(
			id = productUuid,
			name = "Lynda Neal",
			price = BigDecimal.TEN,
			description = "dolorum"
		)
	}

	@Operation(summary = "Calculate the total price for a given product and quantity, applying the necessary discounts")
	@ApiResponses(
		value = [
			ApiResponse(responseCode = "200", description = "Total price for the given product and quantity"),
			ApiResponse(responseCode = "400", description = "Invalid id supplied", content = [Content()]),
			ApiResponse(responseCode = "404", description = "Product not found", content = [Content()])
		]
	)
	@GetMapping("/{id}/calculate-price")
	fun calculatePrice(
		@Parameter(description = "product id")
		@PathVariable("id") productUuid: UUID,
		@Parameter(description = "quantity of the product")
		@RequestParam @Positive quantity: Int,
	): BigDecimal {
		return BigDecimal.TEN
	}

	@Operation(summary = "Add product to the database")
	@ApiResponses(
		value = [
			ApiResponse(responseCode = "200", description = "Information about the product"),
			ApiResponse(responseCode = "400", description = "Invalid request body", content = [Content()]),
		]
	)
	@PostMapping
	fun addProduct(@Valid @RequestBody newProductInformation: NewProductInformation) {
	}
}

data class ProductInformation(
	val id: UUID,
	val name: String,
	@Positive
	val price: BigDecimal,
	val description: String?,
)

data class NewProductInformation(
	val id: UUID,
	val name: String,
	@Positive
	val price: BigDecimal,
	val description: String?,
)
