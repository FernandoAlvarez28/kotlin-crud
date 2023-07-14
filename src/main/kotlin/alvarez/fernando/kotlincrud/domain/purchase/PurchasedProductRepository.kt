package alvarez.fernando.kotlincrud.domain.purchase

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.data.repository.query.Param
import reactor.core.publisher.Flux
import java.util.*

interface PurchasedProductRepository : R2dbcRepository<PurchasedProduct, UUID> {

    @Query(value = "SELECT * FROM kotlin_crud.PURCHASED_PRODUCT WHERE purchase_id = :purchaseId")
    fun findAllByPurchase(@Param("purchaseId") purchaseId: UUID): Flux<PurchasedProduct>

}