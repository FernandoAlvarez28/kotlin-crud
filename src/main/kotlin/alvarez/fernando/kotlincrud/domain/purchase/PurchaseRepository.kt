package alvarez.fernando.kotlincrud.domain.purchase

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface PurchaseRepository : JpaRepository<Purchase, UUID> {

    @Query(value = "FROM Purchase ORDER BY purchasedAt DESC")
    override fun findAll(): MutableList<Purchase>

}