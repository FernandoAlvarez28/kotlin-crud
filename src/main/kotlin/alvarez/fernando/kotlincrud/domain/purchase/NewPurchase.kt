package alvarez.fernando.kotlincrud.domain.purchase

import java.util.*

/**
 * Defines the required data to create a [Purchase].
 */
interface NewPurchase {

    val selectedProducts: MutableList<out SelectedProduct>

    fun getSelectedProductsIds(): Set<UUID> {
        val ids = mutableSetOf<UUID>()
        for (selectedProduct in selectedProducts) {
            ids.add(selectedProduct.id)
        }
        return ids
    }

}

interface SelectedProduct {

    val id: UUID

    val quantity: Int

}