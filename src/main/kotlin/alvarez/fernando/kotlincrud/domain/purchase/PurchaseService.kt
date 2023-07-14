package alvarez.fernando.kotlincrud.domain.purchase

import alvarez.fernando.kotlincrud.domain.product.Product
import alvarez.fernando.kotlincrud.domain.product.ProductService
import alvarez.fernando.kotlincrud.exception.InvalidInputException
import org.springframework.stereotype.Service
import org.springframework.util.CollectionUtils
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class PurchaseService(
        private val purchaseRepository: PurchaseRepository,
        private val purchaseInsertRepository: PurchaseInsertRepository,
        private val purchasedProductRepository: PurchasedProductRepository,
        private val purchasedProductInsertRepository: PurchasedProductInsertRepository,
        private val productService: ProductService
) {

    fun findAllPurchases(): Flux<Purchase> {
        return this.purchaseRepository.findAll()
    }

    fun findPurchaseById(id: UUID): Mono<Purchase> {
        return this.purchaseRepository.findById(id)
    }

    fun getPurchasedProducts(purchase: Purchase): Flux<PurchasedProduct> {
        return this.purchasedProductRepository.findAllByPurchase(purchase.id)
    }

    fun createPurchase(newPurchase: NewPurchase): Mono<Purchase> {
        val selectedProductsIds: Set<UUID> = newPurchase.getSelectedProductsIds()
        if (selectedProductsIds.isEmpty()) {
            return Mono.error(InvalidInputException("No product selected"))
        }

        val productIdMapMono: Mono<MutableMap<UUID, Product>> = this.productService.mapAllAvailableById(selectedProductsIds).cache()

        val createdPurchase = Purchase()

        return verifyIfInexistentProductIsSelected(selectedProductsIds, productIdMapMono)
            .onErrorStop()
            .then(Mono.just(createdPurchase))
            .zipWith( //Create a tuple with createdPurchased and the purchasedProducts
                productIdMapMono.map { productIdMap ->
                    val purchasedProducts = ArrayList<PurchasedProduct>(selectedProductsIds.size)

                    for (selectedProduct in newPurchase.selectedProducts) {
                        productIdMap[selectedProduct.id]?.let { productFound ->
                            purchasedProducts.add(
                                PurchasedProduct.from(
                                    purchase = createdPurchase,
                                    product = productFound,
                                    quantity = selectedProduct.quantity.coerceAtMost(productFound.availableQuantity)
                                )
                            )
                        }
                    }

                    createdPurchase.calculateTotals(purchasedProducts)
                    return@map purchasedProducts
                }
            ).flatMap {tupleWithPurchaseAndItsProducts ->
                val purchasedProducts = tupleWithPurchaseAndItsProducts.t2
                return@flatMap purchaseInsertRepository.insert(createdPurchase)
                    .then(
                        this.purchasedProductInsertRepository.insert(purchasedProducts)
                            .then(this.productService.subtractStockQuantities(purchasedProducts))
                    )
                    .thenReturn(createdPurchase)
            }

    }

    private fun verifyIfInexistentProductIsSelected(selectedProductsIds: Collection<UUID>, productIdMap: Mono<MutableMap<UUID, Product>>): Mono<Unit> {
        return this.productService.getInexistentProducts(selectedProductsIds, productIdMap)
            .collectList()
            .map {inexistentProductIds ->
                if (!CollectionUtils.isEmpty(inexistentProductIds)) {
                    throw InvalidInputException("The following products doesn't exists or aren't available: $inexistentProductIds")
                }
            }
    }

}