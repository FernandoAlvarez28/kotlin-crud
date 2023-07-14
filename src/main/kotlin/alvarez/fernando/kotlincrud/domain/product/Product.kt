package alvarez.fernando.kotlincrud.domain.product

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Table
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
        @Deprecated("Use Flux#collectMap instead")
        fun mapById(products: Collection<Product>): Map<UUID, Product> {
            val map = HashMap<UUID, Product>(products.size)
            for (product in products) {
                map[product.id] = product
            }
            return map;
        }

        fun mapById(products: Flux<Product>): Mono<MutableMap<UUID, Product>> {
            return products.collectMap { product -> product?.id }
        }
    }
}