package alvarez.fernando.kotlincrud.domain.users

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

/**
 * [UserRepository] implementation that have only one hardcoded user: "`Temp O'Rary`", as defined inside Mockoon's JSON.
 *
 * Useful if Mockoon ([UserRepositoryMockoonImpl]) can't be used/reached in the environment.
 */
@Repository
@ConditionalOnProperty(name = ["users.api.impl"], havingValue = "fake")
class UserRepositoryFakeImpl(private val user: User = User(UUID.fromString("0787f0bc-46df-4d15-abe9-c9c3e6dcfe21"), "Temp O'Rary", "temp orary", "withoutPassword")): UserRepository {

    override fun getAll(): Flux<User> {
        return Flux.fromIterable(Collections.singleton(this.user))
    }

    override fun getById(id: UUID): Mono<User> {
        return if (this.user.id == id) Mono.just(this.user) else Mono.empty()
    }

    override fun getByUsername(username: String): Mono<User> {
        return if (this.user.username == username) Mono.just(this.user) else Mono.empty()
    }

}