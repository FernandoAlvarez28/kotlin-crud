package alvarez.fernando.kotlincrud.api.healthcheck

import alvarez.fernando.kotlincrud.api.ApiEndpoints
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SimpleHealthCheckApiController {

    @GetMapping(ApiEndpoints.ROOT, ApiEndpoints.HEALTH_CHECK)
    fun ok(): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.OK)
    }

}