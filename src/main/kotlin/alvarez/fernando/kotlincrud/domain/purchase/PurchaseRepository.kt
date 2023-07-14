package alvarez.fernando.kotlincrud.domain.purchase

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Flux
import java.util.*

interface PurchaseRepository : R2dbcRepository<Purchase, UUID> {

    @Query(value = "SELECT * FROM kotlin_crud.PURCHASE ORDER BY purchased_at DESC")
    override fun findAll(): Flux<Purchase>

}