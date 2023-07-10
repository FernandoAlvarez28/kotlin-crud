package alvarez.fernando.kotlincrud.api.purchase

import alvarez.fernando.kotlincrud.api.ApiEndpoints
import alvarez.fernando.kotlincrud.domain.purchase.PurchaseService
import alvarez.fernando.kotlincrud.exception.InvalidInputException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

@RestController
class PurchaseApiController(val logger: Logger = Logger.getLogger(PurchaseApiController::class.qualifiedName), val purchaseService: PurchaseService) {

    @GetMapping(ApiEndpoints.API_V1_PURCHASES, produces = [MediaType.APPLICATION_JSON_VALUE])
    fun listPurchases(model: Model): ResponseEntity<List<PurchaseApiOutput>> {
        val purchases = this.purchaseService.findAllPurchases()
        return if (purchases.isNotEmpty()) ResponseEntity.ok(PurchaseApiOutput.from(purchases)) else ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @GetMapping(ApiEndpoints.API_V1_PURCHASES_DETAIL, produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getDetailedPurchaseById(@PathVariable id: UUID): ResponseEntity<PurchaseApiDetailedOutput> {
        val purchase = this.purchaseService.findPurchaseById(id)

        return if (purchase.isPresent) {
            ResponseEntity(PurchaseApiDetailedOutput(purchase.get()), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping(ApiEndpoints.API_V1_PURCHASES, consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createPurchase(@RequestBody input: PurchaseInput): ResponseEntity<PurchaseApiDetailedOutput> {
        return try {
            ResponseEntity(PurchaseApiDetailedOutput(this.purchaseService.createPurchase(input)), HttpStatus.CREATED)
        } catch (e: InvalidInputException) {
            this.logger.log(Level.WARNING, e) { e.message }
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

}