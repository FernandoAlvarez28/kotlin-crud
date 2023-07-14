package alvarez.fernando.kotlincrud.api.purchase

import alvarez.fernando.kotlincrud.api.ApiEndpoints
import alvarez.fernando.kotlincrud.domain.purchase.Purchase
import alvarez.fernando.kotlincrud.domain.purchase.PurchaseService
import alvarez.fernando.kotlincrud.exception.InvalidInputException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

@RestController
class PurchaseApiController(private val logger: Logger = Logger.getLogger(PurchaseApiController::class.qualifiedName),
                            private val purchaseService: PurchaseService) {

    @GetMapping(ApiEndpoints.API_V1_PURCHASES, produces = [MediaType.APPLICATION_JSON_VALUE])
    fun listPurchases(model: Model): Flux<PurchaseApiOutput> {
        return this.purchaseService.findAllPurchases()
            .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.NO_CONTENT)))
            .map { PurchaseApiOutput(it) }
    }

    @GetMapping(ApiEndpoints.API_V1_PURCHASES_DETAIL, produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getDetailedPurchaseById(@PathVariable id: UUID): Mono<PurchaseApiDetailedOutput> {
        return this.createDetailedOutputMono(
            this.purchaseService.findPurchaseById(id)
                .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)))
        )
    }

    @PostMapping(ApiEndpoints.API_V1_PURCHASES, consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun createPurchase(@RequestBody input: PurchaseInput): Mono<PurchaseApiDetailedOutput> {
        return this.createDetailedOutputMono(this.purchaseService.createPurchase(input))
            .onErrorResume{e ->
                val message: String?
                val status = if (e is InvalidInputException) {
                    message = e.message
                    HttpStatus.BAD_REQUEST
                } else {
                    message = "Internal server error :/"
                    this.logger.log(Level.SEVERE, e) { e.message }
                    HttpStatus.INTERNAL_SERVER_ERROR
                }
                return@onErrorResume Mono.error(ResponseStatusException(status, message))
            }
    }

    private fun createDetailedOutputMono(purchase: Mono<Purchase>): Mono<PurchaseApiDetailedOutput> {
        return purchase.flatMap {
            return@flatMap this.purchaseService.getPurchasedProducts(it)
                .collectList()
                .map {
                        purchasedProducts -> PurchaseApiDetailedOutput(it, purchasedProducts)
                }
        }
    }

}