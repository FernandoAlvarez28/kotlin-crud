package alvarez.fernando.kotlincrud.api.purchase

import alvarez.fernando.kotlincrud.domain.purchase.NewPurchase
import alvarez.fernando.kotlincrud.domain.purchase.SelectedProduct
import java.util.*

class PurchaseInput(override val selectedProducts: MutableList<SelectedProductInput>) : NewPurchase

class SelectedProductInput(override val id: UUID, override val quantity: Int) : SelectedProduct
