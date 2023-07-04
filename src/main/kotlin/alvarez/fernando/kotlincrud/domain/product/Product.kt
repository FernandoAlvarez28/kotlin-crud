package alvarez.fernando.kotlincrud.domain.product

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
class Product (

    @Id
    var id: UUID = UUID.randomUUID(),
    var name: String,
    var unitPrice: BigDecimal,
    var availableQuantity: Int,
    var addedAt: LocalDateTime = LocalDateTime.now(),

) {

    fun subtractQuantity(quantityToSubtract: Int) {
        this.availableQuantity = this.availableQuantity - quantityToSubtract
    }

    companion object {
        fun mapById(products: Collection<Product>): Map<UUID, Product> {
            val map = HashMap<UUID, Product>(products.size)
            for (product in products) {
                map[product.id] = product
            }
            return map;
        }
    }
}