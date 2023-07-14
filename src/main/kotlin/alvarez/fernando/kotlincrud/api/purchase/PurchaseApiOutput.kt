package alvarez.fernando.kotlincrud.api.purchase

import alvarez.fernando.kotlincrud.domain.purchase.Purchase
import alvarez.fernando.kotlincrud.extensions.toEpochMilli
import java.math.BigDecimal
import java.util.*

data class PurchaseApiOutput (
        val id: UUID,
        val totalValue: BigDecimal,
        val totalProducts: Int,
        val purchasedAtMs: Long
) {

    constructor(purchase: Purchase) : this(
            id = purchase.id,
            totalValue = purchase.totalValue,
            totalProducts = purchase.totalProducts,
            purchasedAtMs = purchase.purchasedAt.toEpochMilli()
    )

    companion object {
        @Deprecated("Use Flux#map instead")
        fun from(purchases: Collection<Purchase>): List<PurchaseApiOutput> {
            val outputs = ArrayList<PurchaseApiOutput>(purchases.size)
            for (purchase in purchases) {
                outputs.add(PurchaseApiOutput(purchase))
            }
            return outputs
        }
    }
}
