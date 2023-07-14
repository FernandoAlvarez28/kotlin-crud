package alvarez.fernando.kotlincrud.api.product

import alvarez.fernando.kotlincrud.api.ApiEndpoints
import alvarez.fernando.kotlincrud.domain.product.ProductService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class ProductApiController(private val productService: ProductService) {

    @GetMapping(ApiEndpoints.API_V1_PRODUCTS)
    fun listProducts(): Flux<ProductApiOutput> {
        return this.productService.findAll()
            .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.NO_CONTENT)))
            .map { ProductApiOutput(it) }
    }

}