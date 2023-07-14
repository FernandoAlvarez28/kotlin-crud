package alvarez.fernando.kotlincrud.domain.purchase

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Table
class Purchase (
        //Named "Purchase" and not "Order" to not conflict with DB keywords

        @Id
        var id: UUID = UUID.randomUUID(),
        var totalValue: BigDecimal = BigDecimal.ZERO,
        var totalProducts: Int = 0,
        var purchasedAt: LocalDateTime = LocalDateTime.now(),

        //Spring Data R2DBC doesn't support @OneToMany to map the PurchasedProducts :(

) {

    fun calculateTotals(purchasedProducts: Collection<PurchasedProduct>) {
        this.totalProducts = purchasedProducts.size
        this.totalValue = BigDecimal.ZERO

        for (purchasedProduct in purchasedProducts) {
            this.totalValue = this.totalValue.add(purchasedProduct.paidPrice)
        }
    }

}