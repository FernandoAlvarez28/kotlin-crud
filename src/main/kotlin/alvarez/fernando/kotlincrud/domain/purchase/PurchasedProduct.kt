package alvarez.fernando.kotlincrud.domain.purchase

import alvarez.fernando.kotlincrud.domain.product.Product
import alvarez.fernando.kotlincrud.extensions.multiply
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.util.*

@Table
class PurchasedProduct (

        var purchaseId: UUID,
        var productId: UUID,
        var productName: String,
        var unitPrice: BigDecimal,
        var quantity: Int,
        var paidPrice: BigDecimal,

        ) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PurchasedProduct

        if (purchaseId != other.purchaseId) return false
        if (productId != other.productId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = purchaseId.hashCode()
        result = 31 * result + productId.hashCode()
        return result
    }

    companion object {
        fun from(purchase: Purchase, product: Product, quantity: Int): PurchasedProduct {
            return PurchasedProduct(
                purchaseId = purchase.id,
                productId = product.id,
                productName = product.name,
                unitPrice = product.unitPrice,
                quantity = quantity,
                paidPrice = product.unitPrice.multiply(quantity)
            )
        }
    }

}