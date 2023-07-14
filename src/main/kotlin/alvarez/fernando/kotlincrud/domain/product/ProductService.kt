package alvarez.fernando.kotlincrud.domain.product

import alvarez.fernando.kotlincrud.domain.purchase.PurchasedProduct
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import java.util.stream.Collectors

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun findAll(): Flux<Product> {
        return this.productRepository.findAll()
    }

    fun mapAllAvailableById(ids: Collection<UUID>): Mono<MutableMap<UUID, Product>> {
        return Product.mapById(this.productRepository.findAllAvailableById(ids))
    }

    fun getInexistentProducts(wantedIds: Collection<UUID>, productIdMap: Mono<MutableMap<UUID, Product>> = this.mapAllAvailableById(wantedIds)): Flux<UUID> {
        return productIdMap.flatMapMany {
            val inexistentProductIds = mutableSetOf<UUID>()
            for (wantedId in wantedIds) {
                if (it[wantedId] == null) {
                    inexistentProductIds.add(wantedId)
                }
            }

            return@flatMapMany Flux.fromIterable(inexistentProductIds);
        }
    }

    fun subtractStockQuantities(purchasedProducts: Collection<PurchasedProduct>): Mono<List<Product>> {
        val productIds = purchasedProducts.stream().map { it.productId }.collect(Collectors.toList())
        return this.mapAllAvailableById(productIds).flatMap { productIdMap ->
            for (selectedProduct in purchasedProducts) {
                productIdMap[selectedProduct.productId]?.subtractQuantity(selectedProduct.quantity)
            }

            return@flatMap productRepository.saveAll(productIdMap.values).collectList()
        }
    }

}