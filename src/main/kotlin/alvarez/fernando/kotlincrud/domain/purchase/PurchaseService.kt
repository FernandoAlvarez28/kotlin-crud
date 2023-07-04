package alvarez.fernando.kotlincrud.domain.purchase

import alvarez.fernando.kotlincrud.domain.product.ProductService
import alvarez.fernando.kotlincrud.exception.InvalidInputException
import org.springframework.stereotype.Service
import java.util.*

@Service
class PurchaseService(
        val purchaseRepository: PurchaseRepository,
        val productService: ProductService
) {

    fun findAllPurchases(): List<Purchase> {
        return this.purchaseRepository.findAll()
    }

    fun findPurchaseById(id: UUID): Optional<Purchase> {
        return this.purchaseRepository.findById(id)
    }

    @Throws(InvalidInputException::class)
    fun createPurchase(newPurchase: NewPurchase): Purchase {
        if (newPurchase.selectedProducts.isEmpty()) {
            throw InvalidInputException("No product selected")
        }

        val selectedProductsIds = newPurchase.getSelectedProductsIds()
        val productIdMap = this.productService.mapAllAvailableById(selectedProductsIds)

        val innexistentProductIds = this.productService.getInnexistentProducts(selectedProductsIds, productIdMap)
        if (innexistentProductIds.isNotEmpty()) {
            throw InvalidInputException("The following products doesn't exists or is not available: $innexistentProductIds")
        }

        val createdPurchase = Purchase(purchasedProducts = mutableListOf())

        for (selectedProduct in newPurchase.selectedProducts) {
            productIdMap[selectedProduct.id]?.let {
                createdPurchase.purchasedProducts.add(PurchasedProduct(
                        purchase = createdPurchase,
                        product = it,
                        quantity = selectedProduct.quantity.coerceAtMost(it.availableQuantity)
                ))
            }
        }

        createdPurchase.calculateTotals()

        this.purchaseRepository.save(createdPurchase)

        this.productService.subtractStockQuantities(createdPurchase.purchasedProducts)

        return createdPurchase
    }

}