package alvarez.fernando.kotlincrud.domain.purchase

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
class Purchase (
        //"Purchase" and not "Order" to not conflict with DB keywords

        @Id
        var id: UUID = UUID.randomUUID(),
        var totalValue: BigDecimal = BigDecimal.ZERO,
        var totalProducts: Int = 0,
        var purchasedAt: LocalDateTime = LocalDateTime.now(),

        @OneToMany(mappedBy = "purchase", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        var purchasedProducts: MutableList<PurchasedProduct>

) {

    fun calculateTotals() {
        this.totalProducts = this.purchasedProducts.size
        this.totalValue = BigDecimal.ZERO

        for (purchasedProduct in this.purchasedProducts) {
            this.totalValue = this.totalValue.add(purchasedProduct.paidPrice)
        }
    }

}