package alvarez.fernando.kotlincrud.domain.users

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Repository
@ConditionalOnProperty(name = ["users.api.impl"], havingValue = "mockoon", matchIfMissing = true)
class UserRepositoryMockoonImpl(webClientBuilder: WebClient.Builder,
                                @Value("\${users.api.mockoon.url}") baseUrl: String,
                                private val webClient: WebClient = webClientBuilder.baseUrl(baseUrl).build()
): UserRepository {

    override fun getAll(): Flux<User> {
        return this.webClient.get()
            .uri("/api/v1/users")
            .retrieve()
            .bodyToFlux(User::class.java)
    }

    override fun getById(id: UUID): Mono<User> {
        return this.webClient.get()
            .uri("/api/v1/users/$id")
            .retrieve()
            .bodyToMono(User::class.java)
    }

    override fun getByUsername(username: String): Mono<User> {
        return this.webClient.get()
            .uri("/api/v1/users")
            .attribute("username", username)
            .retrieve()
            .bodyToFlux(User::class.java).next()
    }

}