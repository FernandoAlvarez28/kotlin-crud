package alvarez.fernando.kotlincrud.api.product

import alvarez.fernando.kotlincrud.domain.product.Product
import java.math.BigDecimal
import java.util.*

data class ProductApiOutput(
        val id: UUID,
        val name: String,
        val unitPrice: BigDecimal,
        val availableQuantity: Int
) {

    constructor(product: Product) : this(
            id = product.id,
            name = product.name,
            unitPrice = product.unitPrice,
            availableQuantity = product.availableQuantity
    )

    companion object {
        @Deprecated("Use Flux#map instead")
        fun from(products: Collection<Product>): List<ProductApiOutput> {
            val outputs = ArrayList<ProductApiOutput>(products.size)
            for (purchasedProduct in products) {
                outputs.add(ProductApiOutput(purchasedProduct))
            }
            return outputs
        }
    }
}

