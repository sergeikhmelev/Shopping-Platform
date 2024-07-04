package com.sergeikhmelev.shoppingplatform.product

import com.sergeikhmelev.shoppingplatform.discountpolicy.DiscountPolicyType
import org.junit.jupiter.api.Test
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.util.UUID

@WebMvcTest(ProductController::class)
class ProductControllerTest {

	@Autowired
	private lateinit var mockMvc: MockMvc

	@MockBean
	private lateinit var productService: ProductService

	@Test
	fun `should return product information by uuid`() {
		// given
		val uuid = UUID.randomUUID()
		val product = Product(uuid, "Product A", BigDecimal("100.0"), description = null)
		given(productService.getProduct(uuid)).willReturn(product)

		mockMvc.perform(get("/products/{id}", uuid))
			.andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(uuid.toString()))
			.andExpect(jsonPath("$.name").value(product.name))
			.andExpect(jsonPath("$.price").value(product.price))
	}

	@Test
	fun `should respond with 404 when no product exists by id`() {
		// given
		val uuid = UUID.fromString("3dce9154-e440-4a25-826f-e4a59cde2771")
		given(productService.getProduct(uuid)).willReturn(null)

		mockMvc.perform(get("/products/{id}", uuid))
			.andExpect(status().isNotFound)
	}

	@Test
	fun `should calculate total price with percentage discount`() {
		val quantity = 2
		val totalPrice = BigDecimal("180.0")
		val uuid = UUID.randomUUID()
		val product = Product(uuid, "Product B", BigDecimal("100.0"), description = null)

		given(productService.getProduct(uuid)).willReturn(product)
		given(productService.calculateTotalPrice(product, quantity, DiscountPolicyType.FIXED_PERCENTAGE_BASED)).willReturn(totalPrice)

		mockMvc.perform(
			get("/products/{id}/calculate-price", uuid)
				.param("quantity", quantity.toString())
				.param("discountPolicyType", DiscountPolicyType.FIXED_PERCENTAGE_BASED.name)
		)
			.andExpect(status().isOk)
			.andExpect(content().string(totalPrice.toString()))
	}
}
