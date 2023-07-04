package alvarez.fernando.kotlincrud.domain.purchase

import alvarez.fernando.kotlincrud.domain.product.Product
import alvarez.fernando.kotlincrud.extensions.multiply
import jakarta.persistence.*
import java.io.Serializable
import java.math.BigDecimal
import java.util.*

@Entity
@IdClass(PurchasedProductId::class)
class PurchasedProduct (

        @Id
        var purchase: Purchase,
        @Id
        var product: Product,
        var quantity: Int,
        var paidPrice: BigDecimal = product.unitPrice.multiply(quantity),

        ) {

    fun subtractStockQuantity() {
        this.product.subtractQuantity(this.quantity)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PurchasedProduct

        if (purchase != other.purchase) return false
        if (product != other.product) return false

        return true
    }

    override fun hashCode(): Int {
        var result = purchase.hashCode()
        result = 31 * result + product.hashCode()
        return result
    }
}

@Embeddable
class PurchasedProductId (

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "purchase_id", referencedColumnName = "id", nullable = false, foreignKey = ForeignKey(name = "purchased_product_purchase_id_fk"))
        var purchase: Purchase,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, foreignKey = ForeignKey(name = "purchased_product_product_id_fk"))
        var product: Product

) : Serializable
