package alvarez.fernando.kotlincrud.domain.product

import alvarez.fernando.kotlincrud.domain.purchase.PurchasedProduct
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ProductService(val productRepository: ProductRepository) {

    fun findAll(): List<Product> {
        return this.productRepository.findAll()
    }

    fun mapAllAvailableById(ids: Collection<UUID>): Map<UUID, Product> {
        return Product.mapById(this.productRepository.findAllAvailableById(ids))
    }

    fun getInnexistentProducts(wantedIds: Collection<UUID>, productIdMap: Map<UUID, Product> = this.mapAllAvailableById(wantedIds)): MutableSet<UUID> {
        val innexistentProductIds = mutableSetOf<UUID>()

        for (wantedId in wantedIds) {
            if (productIdMap[wantedId] == null) {
                innexistentProductIds.add(wantedId)
            }
        }

        return innexistentProductIds;
    }

    fun subtractStockQuantities(purchases: Collection<PurchasedProduct>) {
        val products = ArrayList<Product>(purchases.size)

        for (purchase in purchases) {
            purchase.subtractStockQuantity()
            products.add(purchase.product)
        }

        this.productRepository.saveAll(products)
    }

}