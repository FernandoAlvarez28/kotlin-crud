package alvarez.fernando.kotlincrud.domain.product

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.data.repository.query.Param
import reactor.core.publisher.Flux
import java.util.*

interface ProductRepository: R2dbcRepository<Product, UUID> {

    @Query(value = "SELECT * FROM kotlin_crud.PRODUCT ORDER BY name ASC")
    override fun findAll(): Flux<Product>

    @Query(value = "SELECT * FROM kotlin_crud.PRODUCT WHERE id IN (:ids) AND available_quantity > 0")
    fun findAllAvailableById(@Param("ids") ids: Iterable<UUID>): Flux<Product>

}