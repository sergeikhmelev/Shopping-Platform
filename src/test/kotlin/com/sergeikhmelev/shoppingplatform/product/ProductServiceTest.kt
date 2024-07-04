package com.sergeikhmelev.shoppingplatform.product

import com.sergeikhmelev.shoppingplatform.discountpolicy.DiscountPolicyService
import com.sergeikhmelev.shoppingplatform.discountpolicy.DiscountPolicyType
import com.sergeikhmelev.shoppingplatform.discountpolicy.FixedPercentageBasedDiscountPolicy
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.argThat
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.then
import java.math.BigDecimal
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class ProductServiceTest {

	@InjectMocks
	private lateinit var testee: ProductService

	@Mock
	private lateinit var productRepository: ProductRepository

	@Mock
	private lateinit var discountPolicyService: DiscountPolicyService

	@Test
	fun `getProduct should return product by id`() {
		// given
		val id = UUID.randomUUID()
		val product = mock<Product>()
		given(productRepository.findByUuid(id)).willReturn(product)

		// when
		val result = testee.getProduct(id)

		// then
		assertThat(result).isEqualTo(product)
	}

	@Test
	fun `getProduct should return null when no product exist by id`() {
		// given
		val id = UUID.randomUUID()
		given(productRepository.findByUuid(id)).willReturn(null)

		// when
		val result = testee.getProduct(id)

		// then
		assertThat(result).isNull()
	}

	@Test
	fun `addProduct should add product to repository`() {
		// given

		// when
		testee.addProduct("name", BigDecimal.TEN, "description")

		// then
		then(productRepository).should().save(argThat {
			assertThat(name).isEqualTo("name")
			assertThat(price).isEqualTo(BigDecimal.TEN)
			assertThat(description).isEqualTo("description")
			true
		})
	}

	@Test
	fun `calculateTotalPrice should apply discount`() {
		// given
		val product = Product(UUID.randomUUID(), "name", BigDecimal.TEN, "description")

		given(discountPolicyService.getPolicy(DiscountPolicyType.FIXED_PERCENTAGE_BASED))
			.willReturn(FixedPercentageBasedDiscountPolicy(10.0))

		// when
		val result = testee.calculateTotalPrice(product, 10, DiscountPolicyType.FIXED_PERCENTAGE_BASED)

		// then
		assertThat(result).isEqualByComparingTo("90.0")
	}
}
