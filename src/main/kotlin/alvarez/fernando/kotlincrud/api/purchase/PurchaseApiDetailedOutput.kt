package alvarez.fernando.kotlincrud.api.purchase

import alvarez.fernando.kotlincrud.domain.purchase.Purchase
import alvarez.fernando.kotlincrud.domain.purchase.PurchasedProduct
import alvarez.fernando.kotlincrud.extensions.toEpochMilli
import java.math.BigDecimal
import java.util.*

/**
 * Object for exposing [Purchase] data to API.
 *
 * It exposes more data than [PurchaseApiOutput].
 */
data class PurchaseApiDetailedOutput (
        val id: UUID,
        val totalValue: BigDecimal,
        val purchasedAtMs: Long,
        val purchasedProducts: List<PurchasedProductApiOutput> //Should be Flux<PurchasedProductApiOutput> ?
) {

    constructor(purchase: Purchase, purchasedProducts: Collection<PurchasedProduct>) : this(
            id = purchase.id,
            totalValue = purchase.totalValue,
            purchasedAtMs = purchase.purchasedAt.toEpochMilli(),
            purchasedProducts = PurchasedProductApiOutput.from(purchasedProducts)
    )
}

/**
 * Object for exposing [PurchasedProduct] data to API.
 */
data class PurchasedProductApiOutput(val id: UUID, val name: String, val unitPrice: BigDecimal, val quantity: Int, val paidPrice: BigDecimal) {

    constructor(purchasedProduct: PurchasedProduct) : this(
            id = purchasedProduct.productId,
            name = purchasedProduct.productName,
            unitPrice = purchasedProduct.unitPrice,
            quantity = purchasedProduct.quantity,
            paidPrice = purchasedProduct.paidPrice,
    )

    companion object {
        fun from(purchasedProducts: Collection<PurchasedProduct>): List<PurchasedProductApiOutput> {
            val outputs = ArrayList<PurchasedProductApiOutput>(purchasedProducts.size)
            for (purchasedProduct in purchasedProducts) {
                outputs.add(PurchasedProductApiOutput(purchasedProduct))
            }
            return outputs
        }
    }
}
