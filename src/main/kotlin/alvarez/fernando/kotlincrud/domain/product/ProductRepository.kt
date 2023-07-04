package alvarez.fernando.kotlincrud.domain.product

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface ProductRepository: JpaRepository<Product, UUID> {

    @Query(value = "FROM Product ORDER BY name ASC")
    override fun findAll(): MutableList<Product>

    @Query(value = "FROM Product WHERE id IN (:ids) AND availableQuantity > 0")
    fun findAllAvailableById(@Param("ids") ids: Iterable<UUID>): List<Product>

}