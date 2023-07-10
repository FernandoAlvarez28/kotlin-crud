package alvarez.fernando.kotlincrud.domain.users

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

interface UserRepository {

    fun getAll(): Flux<User>

    fun getById(id: UUID): Mono<User>

    fun getByUsername(username: String): Mono<User>

}